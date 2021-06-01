package cn.rebornauto.platform.sys.dao;

import cn.rebornauto.platform.common.dao.BaseMapper;
import cn.rebornauto.platform.sys.entity.SysUser;
import cn.rebornauto.platform.sys.entity.SysUserCriteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser, Integer, SysUserCriteria> {
    List<SysUser> selectByExample_Relative(SysUserCriteria example);

    SysUser selectByPrimaryKey_Relative(Integer id);
}