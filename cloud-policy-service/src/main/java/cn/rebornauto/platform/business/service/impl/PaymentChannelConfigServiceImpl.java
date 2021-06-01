package cn.rebornauto.platform.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import cn.rebornauto.platform.business.dao.PaymentChannelConfigMapper;
import cn.rebornauto.platform.business.entity.PaymentChannelConfig;
import cn.rebornauto.platform.business.service.PaymentChannelConfigService;
import cn.rebornauto.platform.common.Const;
import cn.rebornauto.platform.pay.sandpay.entity.SandInfo;
import cn.rebornauto.platform.pay.sandpay.entity.SandKey;
import cn.rebornauto.platform.pay.tonglian.entity.TongLianInfo;
import cn.rebornauto.platform.pay.tonglian.entity.TongLianKey;

/** Title: 
 * Description: 
 * Company: 
 * @author kgc
 * @date May 2, 2019 9:37:54 AM
 */
@Service
public class PaymentChannelConfigServiceImpl implements
		PaymentChannelConfigService {

	@Autowired
	PaymentChannelConfigMapper paymentChannelConfigMapper;
	
	/**
	 * 获取通联必要参数
	 */
	@Override
	public TongLianInfo getTongLianInfo(Integer paymentChannelId,String sysSwitch) {
		TongLianInfo bean = new TongLianInfo();
		Example example = new Example(PaymentChannelConfig.class);
        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("daiZhengId", daiZhengId);
        criteria.andEqualTo("dataStatus", Const.DATA_STATUS_1);
        criteria.andEqualTo("sysSwitch", sysSwitch);
        criteria.andEqualTo("paymentChannelId", paymentChannelId);
		List<PaymentChannelConfig> configList = paymentChannelConfigMapper.selectByExample(example);
		if(null!=configList && configList.size()>0) {
			for (int i = 0; i < configList.size(); i++) {
				if(TongLianKey.url.equalsIgnoreCase(configList.get(i).getKey())) {
					bean.setUrl(configList.get(i).getValue());
				}else if(TongLianKey.merchantId.equalsIgnoreCase(configList.get(i).getKey())) {
					bean.setMerchantId(configList.get(i).getValue());
				}else if(TongLianKey.businessCodeDf.equalsIgnoreCase(configList.get(i).getKey())) {
					bean.setBusinessCodeDf(configList.get(i).getValue());
				}else if(TongLianKey.downloadUrl.equalsIgnoreCase(configList.get(i).getKey())) {
					bean.setDownloadUrl(configList.get(i).getValue());
				}else if(TongLianKey.password.equalsIgnoreCase(configList.get(i).getKey())) {
					bean.setPassword(configList.get(i).getValue());
				}else if(TongLianKey.pfxPassword.equalsIgnoreCase(configList.get(i).getKey())) {
					bean.setPfxPassword(configList.get(i).getValue());
				}else if(TongLianKey.pfxPath.equalsIgnoreCase(configList.get(i).getKey())) {
					bean.setPfxPath(configList.get(i).getValue());
				}else if(TongLianKey.rightLoanPrefix.equalsIgnoreCase(configList.get(i).getKey())) {
					bean.setRightLoanPrefix(configList.get(i).getValue());
				}else if(TongLianKey.tltcerPath.equalsIgnoreCase(configList.get(i).getKey())) {
					bean.setTltcerPath(configList.get(i).getValue());
				}else if(TongLianKey.userName.equalsIgnoreCase(configList.get(i).getKey())) {
					bean.setUserName(configList.get(i).getValue());
				}
			}
		}
		return bean;
	}
	
	
	/**
	 * 获取杉徳必要参数
	 */
	@Override
	public SandInfo getSandInfo(Integer paymentChannelId,String sysSwitch) {
		SandInfo bean = new SandInfo();
		Example example = new Example(PaymentChannelConfig.class);
        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("daiZhengId", daiZhengId);
        criteria.andEqualTo("dataStatus", Const.DATA_STATUS_1);
        criteria.andEqualTo("sysSwitch", sysSwitch);
        criteria.andEqualTo("paymentChannelId", paymentChannelId);
		List<PaymentChannelConfig> configList = paymentChannelConfigMapper.selectByExample(example);
		if(null!=configList && configList.size()>0) {
			for (int i = 0; i < configList.size(); i++) {
				if(SandKey.sandsdkUrl.equalsIgnoreCase(configList.get(i).getKey())) {
					bean.setSandsdkUrl(configList.get(i).getValue());
				}else if(SandKey.sandsdkMid.equalsIgnoreCase(configList.get(i).getKey())) {
					bean.setSandsdkMid(configList.get(i).getValue());
				}else if(SandKey.sandsdkPlMid.equalsIgnoreCase(configList.get(i).getKey())) {
					bean.setSandsdkPlMid(configList.get(i).getValue());
				}else if(SandKey.sandsdkSandCertPath.equalsIgnoreCase(configList.get(i).getKey())) {
					bean.setSandsdkSandCertPath(configList.get(i).getValue());
				}else if(SandKey.sandsdkSignCertPath.equalsIgnoreCase(configList.get(i).getKey())) {
					bean.setSandsdkSignCertPath(configList.get(i).getValue());
				}else if(SandKey.sandsdkSignCertPwd.equalsIgnoreCase(configList.get(i).getKey())) {
					bean.setSandsdkSignCertPwd(configList.get(i).getValue());
				}
			}
		}
		return bean;
	}
}
