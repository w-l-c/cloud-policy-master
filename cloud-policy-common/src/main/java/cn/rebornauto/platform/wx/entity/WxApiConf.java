package cn.rebornauto.platform.wx.entity;

import java.io.Serializable;
import java.util.Date;

public class WxApiConf implements Serializable {
    private Integer id;

    private String appid;

    private String appsecret;

    private String token;

    private String accessToken;

    private Date accessTokenStarttime;

    private String callbackDomain;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    public String getAppsecret() {
        return appsecret;
    }

    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret == null ? null : appsecret.trim();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken == null ? null : accessToken.trim();
    }

    public Date getAccessTokenStarttime() {
        return accessTokenStarttime;
    }

    public void setAccessTokenStarttime(Date accessTokenStarttime) {
        this.accessTokenStarttime = accessTokenStarttime;
    }

    public String getCallbackDomain() {
        return callbackDomain;
    }

    public void setCallbackDomain(String callbackDomain) {
        this.callbackDomain = callbackDomain == null ? null : callbackDomain.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", appid=").append(appid);
        sb.append(", appsecret=").append(appsecret);
        sb.append(", token=").append(token);
        sb.append(", accessToken=").append(accessToken);
        sb.append(", accessTokenStarttime=").append(accessTokenStarttime);
        sb.append(", callbackDomain=").append(callbackDomain);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        WxApiConf other = (WxApiConf) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getAppid() == null ? other.getAppid() == null : this.getAppid().equals(other.getAppid()))
            && (this.getAppsecret() == null ? other.getAppsecret() == null : this.getAppsecret().equals(other.getAppsecret()))
            && (this.getToken() == null ? other.getToken() == null : this.getToken().equals(other.getToken()))
            && (this.getAccessToken() == null ? other.getAccessToken() == null : this.getAccessToken().equals(other.getAccessToken()))
            && (this.getAccessTokenStarttime() == null ? other.getAccessTokenStarttime() == null : this.getAccessTokenStarttime().equals(other.getAccessTokenStarttime()))
            && (this.getCallbackDomain() == null ? other.getCallbackDomain() == null : this.getCallbackDomain().equals(other.getCallbackDomain()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getAppid() == null) ? 0 : getAppid().hashCode());
        result = prime * result + ((getAppsecret() == null) ? 0 : getAppsecret().hashCode());
        result = prime * result + ((getToken() == null) ? 0 : getToken().hashCode());
        result = prime * result + ((getAccessToken() == null) ? 0 : getAccessToken().hashCode());
        result = prime * result + ((getAccessTokenStarttime() == null) ? 0 : getAccessTokenStarttime().hashCode());
        result = prime * result + ((getCallbackDomain() == null) ? 0 : getCallbackDomain().hashCode());
        return result;
    }
}