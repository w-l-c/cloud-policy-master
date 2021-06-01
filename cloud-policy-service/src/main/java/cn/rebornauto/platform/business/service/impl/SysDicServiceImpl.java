package cn.rebornauto.platform.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rebornauto.platform.business.dao.SysDicMapper;
import cn.rebornauto.platform.business.service.SysDicService;

@Service
public class SysDicServiceImpl  implements SysDicService{
   private String	SYS_SWITCH="sys_switch";
   
   private String	 IS_MERGE="is_merge";
   
   private String UPLOAD_MAX_DATA = "uploadmaxdata";
   
	@Autowired
	SysDicMapper sysDicMapper;

	@Override
	public String selectSysPaySwitch() {
		
		return	sysDicMapper.selectOneSysDic(SYS_SWITCH).getValue();
	}	
	
	
	@Override
	public String selectIsMerge() {
		
		return	sysDicMapper.selectOneSysDic(IS_MERGE).getValue();
	}	
	
	@Override
	public String selectUploadMaxData() {
		
		return	sysDicMapper.selectOneSysDic(UPLOAD_MAX_DATA).getValue();
	}	
}
