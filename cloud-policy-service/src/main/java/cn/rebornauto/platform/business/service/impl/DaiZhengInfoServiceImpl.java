package cn.rebornauto.platform.business.service.impl;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rebornauto.platform.business.dao.DaiZhengInfoMapper;
import cn.rebornauto.platform.business.entity.DaiZhengInfo;
import cn.rebornauto.platform.business.service.DaiZhengInfoService;

/** Title: 
 * Description: 
 * Company: 
 * @author kgc
 * @date May 1, 2019 9:35:43 AM
 */
@Service
@Slf4j
public class DaiZhengInfoServiceImpl implements DaiZhengInfoService {

	@Autowired
	DaiZhengInfoMapper daiZhengInfoMapper;
	
	/**
	 * 获取代征id
	 * @return
	 */
	@Override
	public Integer getDaiZhengId() {
		Integer daiZhengId = 0;
		List<DaiZhengInfo> list = daiZhengInfoMapper.selectAll();
		if(list!=null && list.size()>0) {
			daiZhengId = list.get(0).getId();
		}
		return daiZhengId;
	}
}
