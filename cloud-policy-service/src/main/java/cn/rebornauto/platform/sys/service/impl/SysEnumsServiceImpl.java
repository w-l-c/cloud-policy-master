package cn.rebornauto.platform.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rebornauto.platform.common.Const;
import cn.rebornauto.platform.sys.dao.SysEnumsMapper;
import cn.rebornauto.platform.sys.entity.SysEnums;
import cn.rebornauto.platform.sys.service.SysEnumsService;

/** Title: 
 * Description: 
 * Company: 
 * @author kgc
 * @date Mar 18, 2020 4:50:31 PM
 */
@Service
public class SysEnumsServiceImpl implements SysEnumsService {

	@Autowired
	SysEnumsMapper sysEnumsMapper;
	
	@Override
	public List<SysEnums> getEnumsByCategory(String category){
		SysEnums query = new SysEnums();
		query.setCategory(category);
		query.setDataStatus(Const.DATA_STATUS_1);
		List<SysEnums> ennumList = sysEnumsMapper.select(query);
		
		return ennumList;
	}
}
