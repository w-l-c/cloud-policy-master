package cn.rebornauto.platform.shiro;

import cn.rebornauto.platform.common.Const;
import cn.rebornauto.platform.sys.entity.SysMenu;
import cn.rebornauto.platform.sys.entity.SysRole;
import cn.rebornauto.platform.sys.entity.SysUser;
import cn.rebornauto.platform.sys.service.SysMenuService;
import cn.rebornauto.platform.sys.service.SysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class SysShiroRealm extends AuthorizingRealm {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 获取角色和权限
     *
     * @param principalCollection 身份集合
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //用户名
        SysUser user = (SysUser) principalCollection.getPrimaryPrincipal();
        //根据用户名来添加相应的权限和角色
        if(user!=null && !StringUtils.isEmpty(user.getAccount())){
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            if(user.getIssys()== Const.ISSYS){
                info.addRole("ROLE_ADMIN");
                info.addStringPermission("*:*");
                return info;
            }
            //添加角色
            SysUser userrole = sysUserService.findById_Relative(user.getId());
            List<SysRole> roles = userrole.getSysRoleList();
            List<Integer> roleIds= new ArrayList<Integer>();
            //添加角色
            if(roles!=null && roles.size()>0){
               for(SysRole r:roles){
                   roleIds.add(r.getId());
                   info.addRole("role_"+r.getId());
               }
               //添加权限
                List<SysMenu> menus = sysMenuService.findSysMenuByRoleIds(roleIds);
                if(menus!=null && menus.size()>0){
                    for(SysMenu m:menus){
                        if(StringUtils.hasText(m.getPermission())){
                            info.addStringPermission(m.getPermission());
                        }
                    }
                }
            }
            return info;
        }
        return null;
    }

    /**
     * 登录认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        logger.info("doGetAuthenticationInfo +" + authenticationToken.toString());
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        UsernamePasswordToken upToken = (UsernamePasswordToken)token;
        String username = upToken.getUsername();
        if (username == null) {
            throw new AccountException("Null usernames are not allowed by this realm.");
        }
        //去数据库根据用户名查询密码post
        SysUser sysUser = sysUserService.findByAccount(username);
        if(sysUser==null){
            throw new AccountException("not found user");
        }
        if(sysUser.getStatus()!= Const.Status_Normal){
            throw new AccountException("user is disable");
        }
        String encodedPassword = sysUser.getPassword();
        SimpleAuthenticationInfo ai = new SimpleAuthenticationInfo(sysUser, encodedPassword, getName());
        ai.setCredentialsSalt(ByteSource.Util.bytes(username));
        this.clearCache(new SimplePrincipalCollection(sysUser,this.getName()));
        return ai;
    }

    @Override
    protected Object getAuthorizationCacheKey(PrincipalCollection principals) {
        SysUser user = (SysUser) principals.getPrimaryPrincipal();
        return user.getId();
    }
}
