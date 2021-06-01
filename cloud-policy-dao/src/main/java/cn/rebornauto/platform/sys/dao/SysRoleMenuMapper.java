package cn.rebornauto.platform.sys.dao;

import cn.rebornauto.platform.common.dao.BaseMapper;
import cn.rebornauto.platform.sys.entity.SysRoleMenu;
import cn.rebornauto.platform.sys.entity.SysRoleMenuCriteria;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu,Integer,SysRoleMenuCriteria> {
}