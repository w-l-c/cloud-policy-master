package cn.rebornauto.platform.sys.dao;

import cn.rebornauto.platform.common.dao.BaseMapper;
import cn.rebornauto.platform.sys.entity.SysRole;
import cn.rebornauto.platform.sys.entity.SysRoleCriteria;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysRoleMapper  extends BaseMapper<SysRole,Integer,SysRoleCriteria>{
}