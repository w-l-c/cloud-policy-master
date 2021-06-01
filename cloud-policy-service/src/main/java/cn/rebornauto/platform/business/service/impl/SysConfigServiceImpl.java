package cn.rebornauto.platform.business.service.impl;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.rebornauto.platform.bestSign.entity.BestSignInfo;
import cn.rebornauto.platform.bestSign.entity.BestSignKey;
import cn.rebornauto.platform.business.service.SysConfigService;
import cn.rebornauto.platform.business.service.SysDicService;
import cn.rebornauto.platform.common.Const;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.sys.dao.SysConfigMapper;
import cn.rebornauto.platform.sys.entity.SysConfig;
import cn.rebornauto.platform.upload.entity.UploadInfo;
import cn.rebornauto.platform.upload.entity.UploadKey;
import cn.rebornauto.platform.wx.entity.AuthorizeInfo;
import cn.rebornauto.platform.wx.entity.AuthorizeKey;
import tk.mybatis.mapper.entity.Example;
@Service
public class SysConfigServiceImpl  implements SysConfigService{

	private static final LinkedHashMap<String, String> cache = new LinkedHashMap<>();
	@Autowired
	SysConfigMapper  sysConfigMapper;
	@Autowired
	SysDicService sysDicService;

	@Override
	public UploadInfo getUploadInfo(String sysPaySwitch) {
		// TODO Auto-generated method stub
		UploadInfo bean = new UploadInfo();
		Example example = new Example(SysConfig.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("sysSwitch", sysPaySwitch);
        List<SysConfig> configList = sysConfigMapper.selectByExample(example);     
		if(null!=configList && configList.size()>0) {
			for (int i = 0; i < configList.size(); i++) {
				if(UploadKey.key.equalsIgnoreCase(configList.get(i).getKey())) {
					bean.setKey(configList.get(i).getValue());
				}else if(UploadKey.local.equalsIgnoreCase(configList.get(i).getKey())) {
					bean.setLocal(configList.get(i).getValue());
				}else if(UploadKey.url.equalsIgnoreCase(configList.get(i).getKey())) {
					bean.setUrl(configList.get(i).getValue());
				}else if(UploadKey.domain.equalsIgnoreCase(configList.get(i).getKey())) {
					bean.setDomain(configList.get(i).getValue());
				}
			}
		}
		return bean;
    }

	@Override
	public AuthorizeInfo getAuthorizeInfo(String sysPaySwitch) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		AuthorizeInfo bean = new AuthorizeInfo();
		Example example = new Example(SysConfig.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("sysSwitch", sysPaySwitch);
        List<SysConfig> configList = sysConfigMapper.selectByExample(example);     
		if(null!=configList && configList.size()>0) {
			for (int i = 0; i < configList.size(); i++) {
				if(AuthorizeKey.authorize.equalsIgnoreCase(configList.get(i).getKey())) {
					bean.setAuthorize(configList.get(i).getValue());
				}
				if(AuthorizeKey.appid.equalsIgnoreCase(configList.get(i).getKey())) {
					bean.setAppid(configList.get(i).getValue());
				}
				if(AuthorizeKey.publicNumber.equalsIgnoreCase(configList.get(i).getKey())) {
					bean.setPublicNumber(configList.get(i).getValue());
				}
			}
		}
		return bean;
	}
	
	
	@Override
	public String findValueByKey(String key,String sysSwitch) {
		SysConfig query = new SysConfig();
		query.setDataStatus(Const.DATA_STATUS_1);
		query.setSysSwitch(sysSwitch);
		query.setKey(key);
		SysConfig sysConfig = sysConfigMapper.selectOne(query);  
		return sysConfig != null ? sysConfig.getValue() : null;
    }
	
	@Override
	public String clearCache(String key) {
        if (StringUtils.isEmpty(key)) {
           return "";
        }else{
            cache.put(key, null);
            return this.findValueByKeyInCache(key);
        }
    }
	
	@Override
	public String findValueByKeyInCache(String key) {
        String val = cache.get(key);
        if (StringUtils.hasText(val)) {
            return val;
        } else {
        	String sysSwitch = sysDicService.selectSysPaySwitch();
        	SysConfig query = new SysConfig();
    		query.setDataStatus(Const.DATA_STATUS_1);
    		query.setKey(key);
    		query.setSysSwitch(sysSwitch);
    		SysConfig sysConfig = sysConfigMapper.selectOne(query);  
            if (sysConfig != null) {
                val = sysConfig.getValue();
                cache.put(key, val);
            }
        }
        return val;
    }
	
	/**
	 * 无缓存
	 * @param key
	 * @return
	 */
	public String findValueByKey(String key) {
		String val = "";
    	String sysSwitch = sysDicService.selectSysPaySwitch();
    	SysConfig query = new SysConfig();
		query.setDataStatus(Const.DATA_STATUS_1);
		query.setKey(key);
		query.setSysSwitch(sysSwitch);
		SysConfig sysConfig = sysConfigMapper.selectOne(query);  
        if (sysConfig != null) {
            val = sysConfig.getValue();
        }
        return val;
    }

	@Override
	public BestSignInfo getBestSignInfo(String sysPaySwitch) {
		// TODO Auto-generated method stub
		BestSignInfo bean = new BestSignInfo();
		Example example = new Example(SysConfig.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("sysSwitch", sysPaySwitch);
        List<SysConfig> configList = sysConfigMapper.selectByExample(example);     
		if(null!=configList && configList.size()>0) {
			for (int i = 0; i < configList.size(); i++) {
				if(BestSignKey.developerId.equalsIgnoreCase(configList.get(i).getKey())) {
					bean.setDeveloperId(configList.get(i).getValue());
				}
				else if(BestSignKey.privateKey.equalsIgnoreCase(configList.get(i).getKey())) {
					bean.setPrivateKey(configList.get(i).getValue());
				}
				else	if(BestSignKey.serverHost.equalsIgnoreCase(configList.get(i).getKey())) {
					bean.setServerHost(configList.get(i).getValue());
				}
				else if(BestSignKey.account.equalsIgnoreCase(configList.get(i).getKey())) {
					bean.setAccount(configList.get(i).getValue());
				}
				else if(BestSignKey.simsun.equalsIgnoreCase(configList.get(i).getKey())) {
					bean.setSimsun(configList.get(i).getValue());
				}
				else if(BestSignKey.modelPdfPath.equalsIgnoreCase(configList.get(i).getKey())) {
					bean.setModelPdfPath(configList.get(i).getValue());
				}
				else if(BestSignKey.pdfPath.equalsIgnoreCase(configList.get(i).getKey())) {
					bean.setPdfPath(configList.get(i).getValue());
				}
				else  if(BestSignKey.downloadPdfPath.equalsIgnoreCase(configList.get(i).getKey())) {
					bean.setDownloadPdfPath(configList.get(i).getValue());
				}
				else if(BestSignKey.downloadPdfZipPath.equalsIgnoreCase(configList.get(i).getKey())) {
					bean.setDownloadPdfZipPath(configList.get(i).getValue());
				}
				else if(BestSignKey.callbackInterface.equalsIgnoreCase(configList.get(i).getKey())) {
					bean.setCallbackInterface(configList.get(i).getValue());
				}
			}
		}
		return bean;
	}

	/**
	 * 修改支付通道
	 */
	@Override
	public Response changePaymentChannelId(SysConfig record,String sysSwitch,String key) {
		record.setModifytime(LocalDateTime.now());
		Example example = new Example(SysConfig.class);
		example.createCriteria()
		 .andEqualTo("sysSwitch", sysSwitch)
		 .andEqualTo("key", key)
		 .andEqualTo("dataStatus",Const.STATUS_1);
		sysConfigMapper.updateByExampleSelective(record, example);
		return Response.ok();
	}

}
