package cn.rebornauto.platform.sys.dao;

import cn.rebornauto.platform.common.dao.BaseMapper;
import cn.rebornauto.platform.sys.entity.SysDept;
import cn.rebornauto.platform.sys.entity.SysDeptCriteria;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysDeptMapper extends BaseMapper<SysDept,Integer,SysDeptCriteria> {
}