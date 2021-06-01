package cn.rebornauto.platform.business.service;

import cn.rebornauto.platform.bestSign.entity.GxjjhzhbxyInfo;
import cn.rebornauto.platform.bestSign.entity.XmsmqswjwtsInfo;

public interface SignatureChannelConfigService {

	GxjjhzhbxyInfo getGxjjhzhbxyInfo(Integer daiZhengId, Integer signatureChannelId,
			String sysSwitch);

	XmsmqswjwtsInfo getXmsmqswjwtsInfo(Integer daiZhengId, Integer signatureChannelId,String sysSwitch);

}
