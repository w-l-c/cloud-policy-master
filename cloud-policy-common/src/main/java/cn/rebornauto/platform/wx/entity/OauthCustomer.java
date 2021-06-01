package cn.rebornauto.platform.wx.entity;

import java.io.Serializable;
import java.util.Date;

public class OauthCustomer implements Serializable {
    public final static byte IS = 1;
    public final  static byte NOTIS = 0;
    private Integer id;

    private String appid;

    private String unionid;

    private String openid;

    private String phone;

    private String nickname;

    private String customerName;

    private Date createdTime;

    private Date updatedTime;

    private Byte subscribe;

    private Date subscribeTime;

    private Byte binding;

    private Date bindingTime;

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

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid == null ? null : unionid.trim();
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Byte getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Byte subscribe) {
        this.subscribe = subscribe;
    }

    public Date getSubscribeTime() {
        return subscribeTime;
    }

    public void setSubscribeTime(Date subscribeTime) {
        this.subscribeTime = subscribeTime;
    }

    public Byte getBinding() {
        return binding;
    }

    public void setBinding(Byte binding) {
        this.binding = binding;
    }

    public Date getBindingTime() {
        return bindingTime;
    }

    public void setBindingTime(Date bindingTime) {
        this.bindingTime = bindingTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", appid=").append(appid);
        sb.append(", unionid=").append(unionid);
        sb.append(", openid=").append(openid);
        sb.append(", phone=").append(phone);
        sb.append(", nickname=").append(nickname);
        sb.append(", customerName=").append(customerName);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", updatedTime=").append(updatedTime);
        sb.append(", subscribe=").append(subscribe);
        sb.append(", subscribeTime=").append(subscribeTime);
        sb.append(", binding=").append(binding);
        sb.append(", bindingTime=").append(bindingTime);
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
        OauthCustomer other = (OauthCustomer) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getAppid() == null ? other.getAppid() == null : this.getAppid().equals(other.getAppid()))
            && (this.getUnionid() == null ? other.getUnionid() == null : this.getUnionid().equals(other.getUnionid()))
            && (this.getOpenid() == null ? other.getOpenid() == null : this.getOpenid().equals(other.getOpenid()))
            && (this.getPhone() == null ? other.getPhone() == null : this.getPhone().equals(other.getPhone()))
            && (this.getNickname() == null ? other.getNickname() == null : this.getNickname().equals(other.getNickname()))
            && (this.getCustomerName() == null ? other.getCustomerName() == null : this.getCustomerName().equals(other.getCustomerName()))
            && (this.getCreatedTime() == null ? other.getCreatedTime() == null : this.getCreatedTime().equals(other.getCreatedTime()))
            && (this.getUpdatedTime() == null ? other.getUpdatedTime() == null : this.getUpdatedTime().equals(other.getUpdatedTime()))
            && (this.getSubscribe() == null ? other.getSubscribe() == null : this.getSubscribe().equals(other.getSubscribe()))
            && (this.getSubscribeTime() == null ? other.getSubscribeTime() == null : this.getSubscribeTime().equals(other.getSubscribeTime()))
            && (this.getBinding() == null ? other.getBinding() == null : this.getBinding().equals(other.getBinding()))
            && (this.getBindingTime() == null ? other.getBindingTime() == null : this.getBindingTime().equals(other.getBindingTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getAppid() == null) ? 0 : getAppid().hashCode());
        result = prime * result + ((getUnionid() == null) ? 0 : getUnionid().hashCode());
        result = prime * result + ((getOpenid() == null) ? 0 : getOpenid().hashCode());
        result = prime * result + ((getPhone() == null) ? 0 : getPhone().hashCode());
        result = prime * result + ((getNickname() == null) ? 0 : getNickname().hashCode());
        result = prime * result + ((getCustomerName() == null) ? 0 : getCustomerName().hashCode());
        result = prime * result + ((getCreatedTime() == null) ? 0 : getCreatedTime().hashCode());
        result = prime * result + ((getUpdatedTime() == null) ? 0 : getUpdatedTime().hashCode());
        result = prime * result + ((getSubscribe() == null) ? 0 : getSubscribe().hashCode());
        result = prime * result + ((getSubscribeTime() == null) ? 0 : getSubscribeTime().hashCode());
        result = prime * result + ((getBinding() == null) ? 0 : getBinding().hashCode());
        result = prime * result + ((getBindingTime() == null) ? 0 : getBindingTime().hashCode());
        return result;
    }
}