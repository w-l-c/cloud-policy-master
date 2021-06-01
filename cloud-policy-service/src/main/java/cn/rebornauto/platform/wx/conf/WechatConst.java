package cn.rebornauto.platform.wx.conf;

public class WechatConst {

    public static final String APPID = "wxa48d7c24fbf58245";
    public static final String MSG_TEMPLATE_AUCTION_ID = "X2Pc_XcTbNmToD8up61OSAAZI4_mI0t255xP2gP5SlM";

    //ApiConfig.init会重置下面数据
    public static String Domain = "http://test.wx.shiyugroup.com";

    public static String APPSECRET = "0aa6abf1ce8962fc5ae21c1bdd63a6f6";
    public static String TOKEN = "qwertyuioasdfghjkl9876";
    public static final String AESKEY = null;


    public static String authorizeUrl() {
        String callback = Domain + "/api/weixin/oauth/index";
        return "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + APPID + "&redirect_uri=" + callback + "&response_type=code&scope=snsapi_userinfo&state=bind_phone#wechat_redirect";
    }

    public static final String DEARLER_APPID = "wxb82756add66f79d7";

    public static final String DEARLER_APPSECRET= "6c76be5407a2fe40e33e9e292e68bd98";

    public static final String BIDDER_APPID = "wx8fcd01b86fe20143";

    public static final String BIDDER_APPSECRET= "093b19c700150f5d97032139c213bcef";

}
