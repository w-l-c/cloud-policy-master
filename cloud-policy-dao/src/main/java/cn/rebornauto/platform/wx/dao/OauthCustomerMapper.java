package cn.rebornauto.platform.wx.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.rebornauto.platform.wx.entity.OauthCustomer;

public interface OauthCustomerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OauthCustomer record);

    OauthCustomer selectByPrimaryKey(Integer id);

    OauthCustomer selectByOpenId(String openid);

    List<OauthCustomer> selectAll();

    int updateByPrimaryKey(OauthCustomer record);

    int updateBindingFieldByOpenId(OauthCustomer record);

    int countByPhoneAndOpenid(@Param("phone") String phone, @Param("openid") String openid);

    List<String> selectOpenIdsByPhone(@Param("phone") String phone);

    List<String> selectPhoneByOpenId(@Param("openid") String openid);

    String selectNextOpenid(@Param("appid") String appid);

    int countByUnionIdAndAppId(@Param("unionid")String unionid, @Param("appid")String appid);
}