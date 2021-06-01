package cn.rebornauto.platform.sys.service;

import cn.rebornauto.platform.common.service.BaseService;
import cn.rebornauto.platform.common.data.request.Query;
import cn.rebornauto.platform.sys.entity.*;

import java.util.List;

public interface SysMenuService extends BaseService<SysMenu, Integer, SysMenuCriteria,Query> {

    List<SysMenu> findChildByParentId(int parentid);

    SysMenu rootMenu();

    int del(int id);

    List<SysMenu> findSysMenuByRoleId(int roleId);

    List<SysMenu> findSysMenuByRoleIds(List<Integer> roleIds);

    List<SysMenu> findSysMenuByUserId(int userId);

    List<Integer> findSysMenuIdsByUserId(int userId);

    List<SysMenu> findButtonByUserId(SysUser user);

    List<SysMenu> findMenusByUserId(SysUser user);

}
