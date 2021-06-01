package cn.rebornauto.platform.configuration;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "wx.config")
@Data
public class WxProperties {
 
    private String applicationWxOpenid;

    private String appid;
    
    private String appsecret;
}
