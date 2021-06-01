package cn.rebornauto.platform.sys.service;

import cn.rebornauto.platform.common.service.BaseService;
import cn.rebornauto.platform.sys.entity.SysRole;
import cn.rebornauto.platform.sys.entity.SysRoleCriteria;
import cn.rebornauto.platform.sys.form.SysRoleForm;
import cn.rebornauto.platform.sys.query.SysRoleQuery;

import java.util.List;

public interface SysRoleService extends BaseService<SysRole,Integer,SysRoleCriteria,SysRoleQuery>{
    int save(SysRoleForm sysRole);
    int update(SysRoleForm sysRole);
    int del(int id);
    List<Integer> findMenuids(int roleid);
}
