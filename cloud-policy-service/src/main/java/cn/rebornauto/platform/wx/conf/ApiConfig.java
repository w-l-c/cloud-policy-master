package cn.rebornauto.platform.wx.conf;

import cn.rebornauto.platform.wx.config.ChangeType;
import cn.rebornauto.platform.wx.config.ConfigChangeNotice;
import cn.rebornauto.platform.wx.entity.WxApiConf;
import cn.rebornauto.platform.wx.exception.WeixinException;
import cn.rebornauto.platform.wx.handle.ApiConfigChangeHandle;
import cn.rebornauto.platform.wx.response.GetJsApiTicketResponse;
import cn.rebornauto.platform.wx.response.GetTokenResponse;
import cn.rebornauto.platform.wx.service.WxApiConfService;
import cn.rebornauto.platform.wx.util.JSONUtil;
import cn.rebornauto.platform.wx.util.NetWorkCenter;
import cn.rebornauto.platform.wx.util.StrUtil;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Date;
import java.util.Observable;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * API配置类，项目中请保证其为单例
 * 实现观察者模式，用于监控token变化
 *
 * @author peiyu
 * @since 1.2
 */
public final class ApiConfig extends Observable implements Serializable, InitializingBean {

    private static final Logger LOG = LoggerFactory.getLogger(ApiConfig.class);

    private final AtomicBoolean tokenRefreshing = new AtomicBoolean(false);
    private final AtomicBoolean jsRefreshing = new AtomicBoolean(false);

    private final String appid;
    private String secret;
    private String accessToken;
    private String jsApiTicket;
    private boolean enableJsApi;
    private long jsTokenStartTime;
    private long weixinTokenStartTime;

    @Autowired
    private WxApiConfService wxApiConfService;

    /**
     * 构造方法一，实现同时获取access_token。不启用jsApi
     *
     * @param appid  公众号appid
     * @param secret 公众号secret
     */
    public ApiConfig(String appid, String secret) {
        this(appid, secret, false);
    }

    /**
     * 构造方法二，实现同时获取access_token，启用jsApi
     *
     * @param appid       公众号appid
     * @param secret      公众号secret
     * @param enableJsApi 是否启动js api
     */
    public ApiConfig(String appid, String secret, boolean enableJsApi) {
        this.appid = appid;
        this.secret = secret;
        this.enableJsApi = enableJsApi;

    }

    public String getAppid() {
        return appid;
    }

    public String getSecret() {
        return secret;
    }

    public String getAccessToken() {
        long now = System.currentTimeMillis();
        //从数据库中获取
        if (wxApiConfService != null) {
            WxApiConf wxapiconf = wxApiConfService.apiConf(appid);
            if (wxapiconf == null) {
                return null;
            }
            Date st = wxapiconf.getAccessTokenStarttime();
            if (st != null) {
                weixinTokenStartTime = st.getTime();
                accessToken = wxapiconf.getAccessToken();
            }
        }
        long time = now - this.weixinTokenStartTime;
        try
        {
            /*
             * 判断优先顺序：
             * 1.官方给出的超时时间是7200秒，这里用7000秒来做，防止出现已经过期的情况
             * 2.刷新标识判断，如果已经在刷新了，则也直接跳过，避免多次重复刷新，如果没有在刷新，则开始刷新
             */
            if (time > 7000000 && this.tokenRefreshing.compareAndSet(false, true)) {
                LOG.info("准备刷新token.............");
                initToken(now);
            }
        } catch (Exception e) {
            LOG.warn("刷新Token出错.", e);
            //刷新工作出现有异常，将标识设置回false
            this.tokenRefreshing.set(false);
        }
        LOG.info("获取Token成功{}", accessToken);
        return accessToken;
    }

    public String getJsApiTicket() {
        if (enableJsApi) {
            long now = System.currentTimeMillis();
            try {
                //官方给出的超时时间是7200秒，这里用7100秒来做，防止出现已经过期的情况
                if (now - this.jsTokenStartTime > 7100000 && this.jsRefreshing.compareAndSet(false, true)) {
                    getAccessToken();
                    initJSToken(now);
                }
            } catch (Exception e) {
                LOG.warn("刷新jsTicket出错.", e);
                //刷新工作出现有异常，将标识设置回false
                this.jsRefreshing.set(false);
            }
        } else {
            jsApiTicket = null;
        }
        return jsApiTicket;
    }

    public boolean isEnableJsApi() {
        return enableJsApi;
    }

    public void setEnableJsApi(boolean enableJsApi) {
        this.enableJsApi = enableJsApi;
        if (!enableJsApi)
            this.jsApiTicket = null;
    }

    /**
     * 添加配置变化监听器
     *
     * @param handle 监听器
     */
    public void addHandle(final ApiConfigChangeHandle handle) {
        super.addObserver(handle);
    }

    /**
     * 移除配置变化监听器
     *
     * @param handle 监听器
     */
    public void removeHandle(final ApiConfigChangeHandle handle) {
        super.deleteObserver(handle);
    }

    /**
     * 移除所有配置变化监听器
     */
    public void removeAllHandle() {
        super.deleteObservers();
    }

    /**
     * 初始化微信配置，即第一次获取access_token
     *
     * @param refreshTime 刷新时间
     */
    private void initToken(final long refreshTime) {
        LOG.info("开始初始化access_token........");
        //记住原本的时间，用于出错回滚
        final long oldTime = this.weixinTokenStartTime;
        this.weixinTokenStartTime = refreshTime;
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + this.appid + "&secret=" + this.secret;
        NetWorkCenter.get(url, null, new NetWorkCenter.ResponseCallback() {
            @Override
            public void onResponse(int resultCode, String resultJson) {
                if (HttpStatus.SC_OK == resultCode) {
                    GetTokenResponse response = JSONUtil.toBean(resultJson, GetTokenResponse.class);
                    LOG.info("获取access_token:{}", response.getAccessToken());
                    if (null == response.getAccessToken()) {
                        //刷新时间回滚
                        weixinTokenStartTime = oldTime;
                        throw new WeixinException
                                ("微信公众号token获取出错，错误信息:" +
                                        response.getErrcode() + "," + response.getErrmsg());
                    }
                    accessToken = response.getAccessToken();
                    //每次更新完都存入数据库
                    WxApiConf wxapiconf = wxApiConfService.apiConf(appid);
                    wxapiconf.setAccessToken(accessToken);
                    wxapiconf.setAccessTokenStarttime(new Date(refreshTime));
                    wxApiConfService.update(wxapiconf);
                    //设置通知点
                    setChanged();
                    notifyObservers(new ConfigChangeNotice(appid, ChangeType.ACCESS_TOKEN, accessToken));
                }
            }
        });
        //刷新工作做完，将标识设置回false
        this.tokenRefreshing.set(false);
    }

    /**
     * 初始化微信JS-SDK，获取JS-SDK token
     *
     * @param refreshTime 刷新时间
     */
    private void initJSToken(final long refreshTime) {
        LOG.info("初始化 jsapi_ticket........");
        //记住原本的时间，用于出错回滚
        final long oldTime = this.jsTokenStartTime;
        this.jsTokenStartTime = refreshTime;
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + accessToken + "&type=jsapi";
        NetWorkCenter.get(url, null, new NetWorkCenter.ResponseCallback() {
            @Override
            public void onResponse(int resultCode, String resultJson) {
                if (HttpStatus.SC_OK == resultCode) {
                    GetJsApiTicketResponse response = JSONUtil.toBean(resultJson, GetJsApiTicketResponse.class);
                    LOG.info("获取jsapi_ticket:{}", response.getTicket());
                    if (StrUtil.isBlank(response.getTicket())) {
                        //刷新时间回滚
                        jsTokenStartTime = oldTime;
                        throw new WeixinException("微信公众号jsToken获取出错，错误信息:" + response.getErrcode() + "," + response.getErrmsg());
                    }
                    jsApiTicket = response.getTicket();
                    //设置通知点
                    setChanged();
                    notifyObservers(new ConfigChangeNotice(appid, ChangeType.JS_TOKEN, jsApiTicket));
                }
            }
        });
        //刷新工作做完，将标识设置回false
        this.jsRefreshing.set(false);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        WxApiConf conf = wxApiConfService.apiConf(this.appid);
        if (conf == null) {
            return;
        }
        if (conf != null) {
            this.secret = conf.getAppsecret();
            WechatConst.APPSECRET = this.secret;
            WechatConst.Domain = conf.getCallbackDomain();
            WechatConst.TOKEN = conf.getToken();
        }
        long now = System.currentTimeMillis();
        initToken(now);
        if (enableJsApi) {
            initJSToken(now);
        }
    }
}
