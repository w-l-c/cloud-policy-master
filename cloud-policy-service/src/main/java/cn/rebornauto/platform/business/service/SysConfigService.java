package cn.rebornauto.platform.business.service;

import cn.rebornauto.platform.bestSign.entity.BestSignInfo;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.sys.entity.SysConfig;
import cn.rebornauto.platform.upload.entity.UploadInfo;
import cn.rebornauto.platform.wx.entity.AuthorizeInfo;

public interface SysConfigService {

	UploadInfo getUploadInfo(String sysPaySwitch);

	AuthorizeInfo getAuthorizeInfo(String selectSysPaySwitch);

	String findValueByKey(String key,String sysSwitch);
	
	String clearCache(String key);
	
	String findValueByKeyInCache(String key);
	
	String findValueByKey(String key);

	BestSignInfo getBestSignInfo(String selectSysPaySwitch);
	
	Response changePaymentChannelId(SysConfig record,String sysSwitch,String key);
}
