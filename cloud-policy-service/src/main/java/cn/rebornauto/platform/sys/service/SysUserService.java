package cn.rebornauto.platform.sys.service;

import cn.rebornauto.platform.common.service.BaseService;
import cn.rebornauto.platform.sys.entity.SysUser;
import cn.rebornauto.platform.sys.entity.SysUserCriteria;
import cn.rebornauto.platform.sys.form.SysUserForm;
import cn.rebornauto.platform.sys.query.SysUserQuery;
import java.util.List;

public interface SysUserService extends BaseService<SysUser, Integer, SysUserCriteria,SysUserQuery> {

    SysUser findByAccount(String account);

    int save(SysUserForm sysUserForm);

    int update(SysUserForm sysUserForm);
    
    int updatePassword(SysUserForm sysUserForm);

    int del(int id);

    List<SysUser> findPageByExample_Relative(SysUserCriteria sysUserCriteria);

    SysUser findById_Relative(int id);
    
    int resetPassword(SysUserForm sysUserForm);
}
