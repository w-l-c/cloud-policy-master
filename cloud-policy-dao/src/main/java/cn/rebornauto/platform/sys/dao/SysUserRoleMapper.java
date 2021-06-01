package cn.rebornauto.platform.sys.dao;

import cn.rebornauto.platform.common.dao.BaseMapper;
import cn.rebornauto.platform.sys.entity.SysUserRole;
import cn.rebornauto.platform.sys.entity.SysUserRoleCriteria;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserRoleMapper  extends BaseMapper<SysUserRole,Integer,SysUserRoleCriteria>{
}