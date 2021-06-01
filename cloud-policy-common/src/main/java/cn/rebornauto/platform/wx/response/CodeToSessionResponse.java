package cn.rebornauto.platform.wx.response;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author ligewei
 * @create 2019/04/01 15:36
 */
public class CodeToSessionResponse {

    @JSONField(name="openid")
    private String openId;
    @JSONField(name="session_key")
    private String sessionKey;
    @JSONField(name="unionid")
    private String unionId;
    @JSONField(name="errcode")
    private String errCode;
    @JSONField(name="errmsg")
    private String errMsg;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
