package cn.rebornauto.platform.sys.dao;

import cn.rebornauto.platform.common.dao.BaseMapper;
import cn.rebornauto.platform.sys.entity.SysMenu;
import cn.rebornauto.platform.sys.entity.SysMenuCriteria;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu, Integer, SysMenuCriteria> {
    List<SysMenu> findSysMenuByRoleId(int roleId);

    List<SysMenu> findSysMenuByRoleIds(List<Integer> roleIds);

    List<SysMenu> findSysMenuByUserId(int userId);

    List<Integer> findSysMenuIdsByUserId(int userId);

    List<SysMenu> findSysMenuByUserIdAndType(@Param("userId") int userId, @Param("menutype") int menutype);

    List<Integer> findSysMenuIdsByUserIdAndType(@Param("userId") int userId, @Param("menutype") int menutype);
}