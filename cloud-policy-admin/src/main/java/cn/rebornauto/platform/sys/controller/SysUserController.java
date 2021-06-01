package cn.rebornauto.platform.sys.controller;

import java.util.Arrays;
import java.util.List;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.rebornauto.platform.common.controller.BaseController;
import cn.rebornauto.platform.common.data.request.Pagination;
import cn.rebornauto.platform.common.data.request.Request;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.common.data.view.TableBody;
import cn.rebornauto.platform.shiro.PasswordHander;
import cn.rebornauto.platform.sys.entity.SysUser;
import cn.rebornauto.platform.sys.form.SysUserForm;
import cn.rebornauto.platform.sys.query.SysUserQuery;
import cn.rebornauto.platform.sys.service.SysMenuService;
import cn.rebornauto.platform.sys.service.SysUserService;

@RestController
@RequestMapping("/sys/user")
public class SysUserController extends BaseController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 保存
     *
     * @return
     */
    @PostMapping("/save")
    @RequiresPermissions("sys:user:save")
    public Response save(@RequestBody @Validated Request<SysUserForm, SysUserQuery> request) {
        //密码加密
        SysUserForm sysUserForm = request.getForm();
        String password = sysUserForm.getPassword();
        String username = sysUserForm.getUsername();
        String encodedPassword = PasswordHander.encodedPassword(password, username);
        sysUserForm.setPassword(encodedPassword);
        int code = sysUserService.save(sysUserForm);
        if (code < 1) {
            return Response.factory().code(-1).message("用户名重复或者部门不存在");
        }
        return list(request);
    }

    /**
     * 更新
     * 此处有一个bug 修改登陆名之后不能登陆了
     *
     * @return
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:user:update")
    public Response update(@RequestBody @Validated Request<SysUserForm, SysUserQuery> request) {
        SysUserForm sysUserForm = request.getForm();
        int code = sysUserService.update(sysUserForm);
        if (code < 1) {
            return Response.factory().code(code).message("用户名重复或者部门不存在");
        }
        return list(request);
    }

    /**
     * 分页查询
     *
     * @return
     */
    @PostMapping("/list")
    @RequiresPermissions("sys:user:list")
    public Response list(@RequestBody Request<SysUserForm, SysUserQuery> request) {
        Pagination pagination = request.getPagination();
        SysUserQuery query = request.getQuery();

        TableBody body = TableBody.factory();
        body.setPagination(pagination);
        //获取总条数
        long rowcount = sysUserService.countQuery(query);
        pagination.setTotal(rowcount);
        //获取分页数据
        List<SysUser> list = sysUserService.pageQuery(pagination, query);
        for (SysUser s : list) {
            List<Integer> menuids = sysMenuService.findSysMenuIdsByUserId(s.getId());
            s.setMenuids(menuids);
            if(org.apache.commons.lang.StringUtils.isNotBlank(s.getPhones())) {
            	s.setMobiles(Arrays.asList(s.getPhones().split(",")));
            }
        }
        body.setList(list);

        body.putDictionary("status", statusDic());
        return Response.ok().body(body);
    }

    /**
     * 根据id查询一条
     *
     * @return
     */
    @RequestMapping("/get")
    @RequiresPermissions(value = {"sys:user:list", "sys:user:id"}, logical = Logical.AND)
    public Response get(@RequestParam int id) {
        SysUser sysuser = sysUserService.findById_Relative(id);
        return Response.ok().body(sysuser);
    }

    @PostMapping("/del")
    @RequiresPermissions(value = {"sys:user:list", "sys:user:del"}, logical = Logical.AND)
    public Response del(@RequestBody Request<SysUserForm, SysUserQuery> request) {
        int id = request.getForm().getId();
        sysUserService.del(id);
        return list(request);
    }

    /**
     * 重置密码
     * @param userForm
     * @param id
     * @return
     */
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public Response resetPassword(@RequestBody  Request<SysUserForm, SysUserQuery> request) {
        logger.info("重置密码{}");
        SysUserForm userForm = request.getForm();
        int id = request.getForm().getId();
        SysUser sysuser = sysUserService.findById_Relative(id);
        if(null==sysuser) {
        	return Response.error().message("用户不存在!");
        }
        userForm.setId(userForm.getId());
        userForm.setPassword("123456");
        String encodedPassword = PasswordHander.encodedPassword(userForm.getPassword(), sysuser.getUsername());
        userForm.setNewpassword(encodedPassword);
        int z = sysUserService.resetPassword(userForm);
        if(z==1){
            return list(request);
        }else{
            return Response.error().message("重置密码失败!");
        }
    }
}
