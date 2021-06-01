package cn.rebornauto.platform.wx.conf;

/**
 * @author ligewei
 * @create 2019/04/01 16:05
 */
public final class WxBidderConfig {

    private final String appid;

    private String secret;

    public WxBidderConfig(String appid, String secret){
        this.appid = appid;
        this.secret = secret;
    }

    public String getAppid() {
        return appid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
