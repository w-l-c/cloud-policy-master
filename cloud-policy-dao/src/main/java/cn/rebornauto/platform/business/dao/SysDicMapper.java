package cn.rebornauto.platform.business.dao;

import java.util.List;

import cn.rebornauto.platform.business.entity.SysDic;
import cn.rebornauto.platform.common.data.view.SysDicOptionVO;
import tk.mybatis.mapper.common.Mapper;

public interface SysDicMapper extends Mapper<SysDic> {

	List<SysDicOptionVO> selectSysDics(String group);
	
	SysDicOptionVO selectOneSysDic(String group);

}