package cn.rebornauto.platform.wx.conf;

import cn.rebornauto.platform.business.service.SysConfigService;
import cn.rebornauto.platform.business.service.SysDicService;
import cn.rebornauto.platform.wx.api.*;
import cn.rebornauto.platform.wx.entity.AuthorizeInfo;
import cn.rebornauto.platform.wx.entity.WxApiConf;
import cn.rebornauto.platform.wx.service.WxApiConfService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
	
    @Autowired
    private WxApiConfService wxApiConfService;
    
    @Autowired
    private   SysConfigService  sysConfigService;
    
    @Autowired
    private   SysDicService  sysDicService;

    @Bean
    public ApiConfig apiConfig() {
    	AuthorizeInfo authorizeInfo = sysConfigService.getAuthorizeInfo(sysDicService.selectSysPaySwitch());
		WxApiConf wxapiconf = wxApiConfService.apiConf(authorizeInfo.getAppid());
        return new ApiConfig(wxapiconf.getAppid(), wxapiconf.getAccessToken());
    }

    @Bean
    public OauthAPI oauthAPI() {
        return new OauthAPI(apiConfig());
    }

    @Bean
    public TemplateMsgAPI templateMsgAPI() {
        return new TemplateMsgAPI(apiConfig());
    }

    @Bean
    public CustomAPI customAPI() {
        return new CustomAPI(apiConfig());
    }

    @Bean
    public UserAPI userAPI() {
        return new UserAPI(apiConfig());
    }

    @Bean
    public WxDealerConfig dealerConfig() {
        return new WxDealerConfig(WechatConst.DEARLER_APPID, WechatConst.DEARLER_APPSECRET);
    }@Bean
    public WxBidderConfig bidderConfig() {
        return new WxBidderConfig(WechatConst.BIDDER_APPID, WechatConst.BIDDER_APPSECRET);
    }
    @Bean
    public WxSmallProgramAPI dealerAPI(){
        return new WxSmallProgramAPI(apiConfig(),dealerConfig(),bidderConfig());
    }
}
