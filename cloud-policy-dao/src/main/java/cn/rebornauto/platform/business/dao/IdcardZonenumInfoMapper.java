package cn.rebornauto.platform.business.dao;

import java.util.List;
import java.util.Map;

import cn.rebornauto.platform.business.entity.IdcardZonenumInfo;
import tk.mybatis.mapper.common.Mapper;

public interface IdcardZonenumInfoMapper extends Mapper<IdcardZonenumInfo> {

	List<Map<String, Object>> selectZoneNums();
}