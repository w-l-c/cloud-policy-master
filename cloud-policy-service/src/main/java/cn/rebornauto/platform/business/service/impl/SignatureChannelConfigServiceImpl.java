package cn.rebornauto.platform.business.service.impl;

import cn.rebornauto.platform.bestSign.entity.GxjjhzhbxyInfo;
import cn.rebornauto.platform.bestSign.entity.GxjjhzhbxyKey;
import cn.rebornauto.platform.bestSign.entity.XmsmqswjwtsInfo;
import cn.rebornauto.platform.bestSign.entity.XmsmqswjwtsKey;
import cn.rebornauto.platform.business.dao.SignatureChannelConfigMapper;
import cn.rebornauto.platform.business.entity.SignatureChannelConfig;
import cn.rebornauto.platform.business.service.SignatureChannelConfigService;
import cn.rebornauto.platform.common.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
@Service
public class SignatureChannelConfigServiceImpl implements SignatureChannelConfigService{
	
	  @Autowired
	   private  SignatureChannelConfigMapper  SignatureChannelConfigMapper;

	@Override
	public GxjjhzhbxyInfo getGxjjhzhbxyInfo(Integer daiZhengId,
			Integer signatureChannelId, String sysSwitch) {
		// TODO Auto-generated method stub
		GxjjhzhbxyInfo bean = new GxjjhzhbxyInfo();
		Example example = new Example(SignatureChannelConfig.class);
	    Example.Criteria criteria = example.createCriteria();
	    criteria.andEqualTo("daiZhengId", daiZhengId);
	    criteria.andEqualTo("dataStatus", Const.DATA_STATUS_1);
	    criteria.andEqualTo("sysSwitch", sysSwitch);
		List<SignatureChannelConfig> configList = SignatureChannelConfigMapper.selectByExample(example);
		if(null!=configList && configList.size()>0) {
			for (int i = 0; i < configList.size(); i++) {
				 if(GxjjhzhbxyKey.modelGxjjhzhbxyName.equalsIgnoreCase(configList.get(i).getKey())) {
					bean.setModelGxjjhzhbxyName(configList.get(i).getValue());
				}else if(GxjjhzhbxyKey.gxjjhzhbxyName.equalsIgnoreCase(configList.get(i).getKey())) {
					bean.setGxjjhzhbxyName(configList.get(i).getValue());
				}else if(GxjjhzhbxyKey.gxjjhzhbxyPageSize.equalsIgnoreCase(configList.get(i).getKey())) {
					bean.setGxjjhzhbxyPageSize(configList.get(i).getValue());
				}else if(GxjjhzhbxyKey.gxjjhzhbxyPageNum.equalsIgnoreCase(configList.get(i).getKey())) {
					bean.setGxjjhzhbxyPageNum(configList.get(i).getValue());
				}else if(GxjjhzhbxyKey.gxjjhzhbxyX.equalsIgnoreCase(configList.get(i).getKey())) {
					bean.setGxjjhzhbxyX(configList.get(i).getValue());
				}else if(GxjjhzhbxyKey.gxjjhzhbxyY.equalsIgnoreCase(configList.get(i).getKey())) {
					bean.setGxjjhzhbxyY(configList.get(i).getValue());
				}else if(GxjjhzhbxyKey.persongxjjhzhbxyX.equalsIgnoreCase(configList.get(i).getKey())) {
					bean.setPersongxjjhzhbxyX(configList.get(i).getValue());
				}else if(GxjjhzhbxyKey.persongxjjhzhbxyY.equalsIgnoreCase(configList.get(i).getKey())) {
					bean.setPersongxjjhzhbxyY(configList.get(i).getValue());
				}
			}
		}
		return bean;
	}

	@Override
	public XmsmqswjwtsInfo getXmsmqswjwtsInfo(Integer daiZhengId, Integer signatureChannelId, String sysSwitch) {
		XmsmqswjwtsInfo bean = new XmsmqswjwtsInfo();
		Example example = new Example(SignatureChannelConfig.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("daiZhengId", daiZhengId);
		criteria.andEqualTo("dataStatus", Const.DATA_STATUS_1);
		criteria.andEqualTo("sysSwitch", sysSwitch);
		List<SignatureChannelConfig> configList = SignatureChannelConfigMapper.selectByExample(example);
		if(null!=configList && configList.size()>0) {
			for (int i = 0; i < configList.size(); i++) {
				if(XmsmqswjwtsKey.modelXmsmqswjwtsName.equalsIgnoreCase(configList.get(i).getKey())) {
					bean.setModelXmsmqswjwtsName(configList.get(i).getValue());
				}else if(XmsmqswjwtsKey.xmsmqswjwtsName.equalsIgnoreCase(configList.get(i).getKey())) {
					bean.setXmsmqswjwtsName(configList.get(i).getValue());
				}else if(XmsmqswjwtsKey.xmsmqswjwtsPageSize.equalsIgnoreCase(configList.get(i).getKey())) {
					bean.setXmsmqswjwtsPageSize(configList.get(i).getValue());
				}else if(XmsmqswjwtsKey.xmsmqswjwtsPageNum.equalsIgnoreCase(configList.get(i).getKey())) {
					bean.setXmsmqswjwtsPageNum(configList.get(i).getValue());
				}else if(XmsmqswjwtsKey.xmsmqswjwtsX.equalsIgnoreCase(configList.get(i).getKey())) {
					bean.setXmsmqswjwtsX(configList.get(i).getValue());
				}else if(XmsmqswjwtsKey.xmsmqswjwtsY.equalsIgnoreCase(configList.get(i).getKey())) {
					bean.setXmsmqswjwtsY(configList.get(i).getValue());
				}else if(XmsmqswjwtsKey.personxmsmqswjwtsX.equalsIgnoreCase(configList.get(i).getKey())) {
					bean.setPersonxmsmqswjwtsX(configList.get(i).getValue());
				}else if(XmsmqswjwtsKey.personxmsmqswjwtsY.equalsIgnoreCase(configList.get(i).getKey())) {
					bean.setPersonxmsmqswjwtsY(configList.get(i).getValue());
				}
			}
		}
		return bean;
	}
}
