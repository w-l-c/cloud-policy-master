package cn.rebornauto.platform.wx.api;

import cn.rebornauto.platform.wx.conf.ApiConfig;
import cn.rebornauto.platform.wx.conf.WxBidderConfig;
import cn.rebornauto.platform.wx.conf.WxDealerConfig;
import cn.rebornauto.platform.wx.enums.ResultType;
import cn.rebornauto.platform.wx.response.BaseResponse;
import cn.rebornauto.platform.wx.util.BeanUtil;
import cn.rebornauto.platform.wx.util.CollectionUtil;
import cn.rebornauto.platform.wx.util.NetWorkCenter;

import java.io.File;
import java.util.List;

/**
 * API基类，提供一些通用方法
 * 包含自动刷新token、通用get post请求等
 *
 * @author peiyu
 * @since 1.2
 */
public abstract class BaseAPI {

    protected static final String BASE_API_URL = "https://api.weixin.qq.com/";

    protected final ApiConfig config;

    protected WxDealerConfig dealerConfig;

    protected WxBidderConfig bidderConfig;

    /**
     * 构造方法，设置apiConfig
     *
     * @param config 微信API配置对象
     */
    protected BaseAPI(ApiConfig config, WxDealerConfig dealerConfig, WxBidderConfig bidderConfig) {
        this.config = config;
        this.dealerConfig=dealerConfig;
        this.bidderConfig=bidderConfig;
    }

    protected BaseAPI(ApiConfig config){
        this.config=config;
    }


    /**
     * 通用post请求
     *
     * @param url  地址，其中token用#代替
     * @param json 参数，json格式
     * @return 请求结果
     */
    protected BaseResponse executePost(String url, String json) {
        return executePost(url, json, null);
    }

    /**
     * 通用post请求
     *
     * @param url  地址，其中token用#代替
     * @param json 参数，json格式
     * @param file 上传的文件
     * @return 请求结果
     */
    protected BaseResponse executePost(String url, String json, File file) {
        BaseResponse response;
        BeanUtil.requireNonNull(url, "url is null");
        List<File> files = null;
        if (null != file) {
            files = CollectionUtil.newArrayList(file);
        }
        //需要传token
        String postUrl = url.replace("#", config.getAccessToken());
        response = NetWorkCenter.post(postUrl, json, files);
        return response;
    }


    /**
     * 通用get请求
     *
     * @param url 地址，其中token用#代替
     * @return 请求结果
     */
    protected BaseResponse executeGet(String url) {
        BaseResponse response;
        cn.rebornauto.platform.wx.util.BeanUtil.requireNonNull(url, "url is null");
        //需要传token
        String getUrl = url.replace("#", config.getAccessToken());
        response = NetWorkCenter.get(getUrl);
        return response;
    }

    /**
     * 通用get请求
     *
     * @param url 地址，其中token用#代替
     * @return 请求结果
     */
    protected BaseResponse executeGetWithoutToken(String url) {
        BaseResponse response;
        cn.rebornauto.platform.wx.util.BeanUtil.requireNonNull(url, "url is null");
        response = NetWorkCenter.get(url);
        return response;
    }

    /**
     * 判断本次请求是否成功
     *
     * @param errCode 错误码
     * @return 是否成功
     */
    protected boolean isSuccess(String errCode) {
        return ResultType.SUCCESS.getCode().toString().equals(errCode);
    }
}
