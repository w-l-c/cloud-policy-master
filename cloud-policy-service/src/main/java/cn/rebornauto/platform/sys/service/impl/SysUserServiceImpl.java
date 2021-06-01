package cn.rebornauto.platform.sys.service.impl;

import cn.rebornauto.platform.common.Const;
import cn.rebornauto.platform.common.service.impl.BaseServiceImpl;
import cn.rebornauto.platform.common.data.request.Pagination;
import cn.rebornauto.platform.sys.dao.SysDeptMapper;
import cn.rebornauto.platform.sys.dao.SysUserMapper;
import cn.rebornauto.platform.sys.dao.SysUserRoleMapper;
import cn.rebornauto.platform.sys.entity.*;
import cn.rebornauto.platform.sys.form.SysUserForm;
import cn.rebornauto.platform.sys.query.SysUserQuery;
import cn.rebornauto.platform.sys.service.SysUserService;
import cn.rebornauto.platform.common.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SysUserServiceImpl
        extends BaseServiceImpl<SysUser, Integer, SysUserCriteria, SysUserMapper,SysUserQuery> implements SysUserService {

    private static Logger logger = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public SysUser findByAccount(String account) {
        SysUserCriteria suc = new SysUserCriteria();
        suc.createCriteria().andAccountEqualTo(account);
        List<SysUser> users = this.mapper.selectByExample(suc);
        return (users != null && users.size() == 1) ? users.get(0) : null;
    }

    @Override
    @Transactional
    public int save(SysUserForm sysUserForm) {
        logger.info("添加用户"+sysUserForm);
        SysUserCriteria suc = new SysUserCriteria();
        suc.createCriteria()
                .andAccountEqualTo(sysUserForm.getUsername());
        List<SysUser> list = mapper.selectByExample(suc);
        if (list != null && list.size() > 0) {
           // throw new BizException("用户名重复");
            logger.error("save用户名重复"+sysUserForm);
            return -1;
        }
        Integer deptid = sysUserForm.getDeptid();
        if (deptid != null) {
            SysDept dept = sysDeptMapper.selectByPrimaryKey(deptid);
            if (dept == null) {
                //throw new BizException("部门不存在");
                logger.error("save部门不存在"+sysUserForm);
                return -2;
            }
        }

        SysUser user = sysUserForm.getUser();
        user.setCreatetime(new Date());
        user.setUid(RandomUtil.randomStr(11));
        user.setPhones(String.join(",", sysUserForm.getMobiles()));
        //如果不选 客户名称，默认管理员
        if(org.apache.commons.lang.StringUtils.isBlank(user.getCustomerId())) {
        	user.setCustomerId(Const.ADMINISTRATOR_CUSTOMER_ID);
        }
        int iuser = mapper.insertSelective(user);
        //保存用户和角色关联
        if (iuser == 1) {
            int userId = user.getId();
            List<String> roleids = sysUserForm.getRoleids();
            if (roleids!=null) {
                for (String rid : roleids) {
                    int roleid = Integer.parseInt(rid);
                    SysUserRole sysUserRole = new SysUserRole();
                    sysUserRole.setCreatetime(new Date());
                    sysUserRole.setUserid(userId);
                    sysUserRole.setRoleid(roleid);
                    sysUserRoleMapper.insert(sysUserRole);
                }
            }
        }
        return 1;
    }

    @Override
    @Transactional
    public int update(SysUserForm sysUserForm) {
        logger.info("修改用户"+sysUserForm);
        SysUser oldUser = mapper.selectByPrimaryKey(sysUserForm.getId());
        if (oldUser == null) {
            //throw new BizException("用户不存在"); -3用户不存在 -1用户名重复  -2 不能不存在
            logger.error("update用户不存在"+sysUserForm);
            return -3;
        }
        Integer deptid = sysUserForm.getDeptid();
        if (deptid != null) {
            SysDept dept = sysDeptMapper.selectByPrimaryKey(deptid);
            if (dept == null) {
               // throw new BizException("部门不存在");
                logger.error("update部门不存在"+sysUserForm);
                return -2;
            }
        }

        //判断登录名是否已经存在
        SysUserCriteria example = new SysUserCriteria();
        example.createCriteria()
                .andAccountEqualTo(sysUserForm.getUsername())
                .andIdNotEqualTo(sysUserForm.getId());
        List<SysUser> list = mapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            // throw new BizException("account已经存在");
            logger.error("updateAccount已经存在"+sysUserForm);
            return -1;
        }
        //更新用户的信息
        oldUser.setUpdatetime(new Date());
        oldUser.setAccount(sysUserForm.getUsername());
        oldUser.setStatus(sysUserForm.getStatus());
        oldUser.setRemark(sysUserForm.getRemark());
        oldUser.setEmail(sysUserForm.getEmail());
        oldUser.setDeptid(sysUserForm.getDeptid());
        oldUser.setMobile(sysUserForm.getMobile());
        oldUser.setNickname(sysUserForm.getNickname());
        oldUser.setPhones(String.join(",", sysUserForm.getMobiles()));
        int z = mapper.updateByPrimaryKeySelective(oldUser);

        //更新角色
        SysUserRoleCriteria urexample = new SysUserRoleCriteria();
        urexample.createCriteria().andUseridEqualTo(oldUser.getId());
        List<SysUserRole> roles = sysUserRoleMapper.selectByExample(urexample);
        List<Integer> oldids = new ArrayList<Integer>();
        if (roles != null) {
            for (SysUserRole r : roles) {
                oldids.add(r.getRoleid());
            }
        }
        //先删除去掉的角色，再添加新增的角色
        List<String> roleids = sysUserForm.getRoleids();
        List<Integer> added = added(oldids, roleids);
        List<Integer> removed = removed(oldids, roleids);
        if(removed!=null && removed.size()>0){
            SysUserRoleCriteria reexample = new SysUserRoleCriteria();
            reexample.createCriteria().andUseridEqualTo(oldUser.getId()).andRoleidIn(removed);
            sysUserRoleMapper.deleteByExample(reexample);
        }
        if(added!=null && added.size()>0){
            for(Integer id:added){
                SysUserRole record = new SysUserRole();
                record.setRoleid(id);
                record.setUserid(oldUser.getId());
                record.setCreatetime(new Date());
                sysUserRoleMapper.insertSelective(record);
            }
        }
        return 1;
    }

    @Override
    public int updatePassword(SysUserForm sysUserForm) {
        SysUser u = mapper.selectByPrimaryKey(sysUserForm.getId());
        String old = sysUserForm.getPassword();
        if(!u.getPassword().equals(old)){
            return -1;
        }
        SysUser user = new SysUser();
        user.setId(sysUserForm.getId());
        user.setPassword(sysUserForm.getNewpassword());
        return mapper.updateByPrimaryKeySelective(user);
    }

    @Override
    @Transactional
    public int del(int id) {
        SysUser record = new SysUser();
        record.setId(id);
        record.setStatus(Const.Status_Del);
        record.setUpdatetime(new Date());
        //删除关联关系
        SysUserRoleCriteria example = new SysUserRoleCriteria();
        example.createCriteria().andUseridEqualTo(id);
        sysUserRoleMapper.deleteByExample(example);
        //更新主表状态
        return mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<SysUser> findPageByExample_Relative(SysUserCriteria sysUserCriteria) {
        return mapper.selectByExample_Relative(sysUserCriteria);
    }

    @Override
    public SysUser findById_Relative(int id) {
        return mapper.selectByPrimaryKey_Relative(id);
    }


    /**
     * 获取新增的角色id
     * @param oldids
     * @param newids
     * @return
     */
    private List<Integer> added(List<Integer> oldids, List<String> newids) {
        List<Integer> newid = new ArrayList<Integer>();
        if (newids!=null) {
            for (String role : newids) {
                if (!oldids.contains(Integer.parseInt(role))) {
                    newid.add(Integer.parseInt(role));
                }
            }
            return newid;
        }
        return null;
    }

    /**
     * 获取需要删除的角色id
     * @param oldids
     * @param newids
     * @return
     */
    private List<Integer> removed(List<Integer> oldids, List<String> newids) {
        List<Integer> newid = new ArrayList<Integer>();
        List<Integer> remove = new ArrayList<Integer>();
        if (newids!=null) {
            for (String role : newids) {
                newid.add(Integer.parseInt(role));
            }
            for (Integer i : oldids) {
                if (!newid.contains(i)) {
                    remove.add(i);
                }
            }
            return remove;
        }
        return null;
    }

    @Override
    public long countQuery(SysUserQuery query) {
        SysUserCriteria criteria = new SysUserCriteria();
        SysUserCriteria.Criteria ec = criteria.createCriteria();
        //ec.andStatusEqualTo(Const.Status_Normal);
        if(StringUtils.hasText(query.getUsername())){
            ec.andAccountLike("%"+query.getUsername()+"%");
        }
        if(StringUtils.hasText(query.getMobile())){
            ec.andMobileEqualTo(query.getMobile());
        }
        if(query.getStatus()!=null && query.getStatus()>0){
            ec.andStatusEqualTo(query.getStatus());
        }

        return mapper.countByExample(criteria);
    }

    @Override
    public List<SysUser> pageQuery(Pagination pagination, SysUserQuery query) {
        SysUserCriteria criteria = new SysUserCriteria();
        criteria.setLimitStart(pagination.getOffset());
        criteria.setLimitLength(pagination.getPageSize());
        criteria.setOrderByClause(" id desc ");
        SysUserCriteria.Criteria ec = criteria.createCriteria();
        //ec.andStatusEqualTo(Const.Status_Normal);
        if(StringUtils.hasText(query.getUsername())){
            ec.andAccountLike("%"+query.getUsername()+"%");
        }
        if(StringUtils.hasText(query.getMobile())){
            ec.andMobileEqualTo(query.getMobile());
        }
        if(query.getStatus()!=null && query.getStatus()>0){
            ec.andStatusEqualTo(query.getStatus());
        }

        return mapper.selectByExample_Relative(criteria);
    }
    
    /**
     * 重置密码
     */
    @Override
    public int resetPassword(SysUserForm sysUserForm) {
        SysUser user = new SysUser();
        user.setId(sysUserForm.getId());
        user.setPassword(sysUserForm.getNewpassword());
        return mapper.updateByPrimaryKeySelective(user);
    }
}
