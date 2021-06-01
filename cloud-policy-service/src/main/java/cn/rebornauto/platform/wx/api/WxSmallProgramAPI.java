package cn.rebornauto.platform.wx.api;

import cn.rebornauto.platform.wx.conf.ApiConfig;
import cn.rebornauto.platform.wx.conf.WxBidderConfig;
import cn.rebornauto.platform.wx.conf.WxDealerConfig;
import cn.rebornauto.platform.wx.response.BaseResponse;
import cn.rebornauto.platform.wx.response.CodeToSessionResponse;
import cn.rebornauto.platform.wx.util.BeanUtil;
import cn.rebornauto.platform.wx.util.JSONUtil;


/**
 * @author ligewei
 * @create 2019/04/01 15:44
 */
public final class WxSmallProgramAPI extends BaseAPI{

    /**
     * 构造方法，设置apiConfig
     *

     * @param config 微信API配置对象
     * @param dealerConfig
     * @param bidderConfig
     */
    public WxSmallProgramAPI(ApiConfig config, WxDealerConfig dealerConfig, WxBidderConfig bidderConfig
    ) {
        super(config, dealerConfig, bidderConfig);
    }


    /**
     * 根据code获取openId
     *
     * @param code 授权后得到的code
     * @return token对象
     */
    public CodeToSessionResponse getDealerOpenId(String code) {
        BeanUtil.requireNonNull(code, "code is null");
        CodeToSessionResponse response = null;
        String url = BASE_API_URL + "sns/jscode2session?appid=" + this.dealerConfig.getAppid() + "&secret=" + this.dealerConfig.getSecret() + "&js_code=" + code + "&grant_type=authorization_code";
        BaseResponse r = executeGetWithoutToken(url);
        String resultJson = isSuccess(r.getErrcode()) ? r.getErrmsg() : r.toJsonString();
        response = JSONUtil.toBean(resultJson, CodeToSessionResponse.class);
        return response;
    }

    /**
     * 根据code获取openId
     *
     * @param code 授权后得到的code
     * @return token对象
     */
    public CodeToSessionResponse getBidderOpenId(String code) {
        BeanUtil.requireNonNull(code, "code is null");
        CodeToSessionResponse response = null;
        String url = BASE_API_URL + "sns/jscode2session?appid=" + this.bidderConfig.getAppid() + "&secret=" + this.bidderConfig.getSecret() + "&js_code=" + code + "&grant_type=authorization_code";
        BaseResponse r = executeGetWithoutToken(url);
        String resultJson = isSuccess(r.getErrcode()) ? r.getErrmsg() : r.toJsonString();
        response = JSONUtil.toBean(resultJson, CodeToSessionResponse.class);
        return response;
    }

    public String getBidderAppId(){
        return this.bidderConfig.getAppid();
    }
    public String getBidderSecret(){
        return this.bidderConfig.getSecret();
    }
    public String getDealerAppId(){
        return this.dealerConfig.getAppid();
    }
    public String getDealerSecret(){
        return this.dealerConfig.getSecret();
    }

}
