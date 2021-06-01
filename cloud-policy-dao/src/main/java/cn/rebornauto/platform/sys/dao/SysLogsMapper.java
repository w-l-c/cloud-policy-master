package cn.rebornauto.platform.sys.dao;

import cn.rebornauto.platform.common.dao.BaseMapper;
import cn.rebornauto.platform.sys.entity.SysLogs;
import cn.rebornauto.platform.sys.entity.SysLogsCriteria;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysLogsMapper extends BaseMapper<SysLogs,Integer,SysLogsCriteria> {
}