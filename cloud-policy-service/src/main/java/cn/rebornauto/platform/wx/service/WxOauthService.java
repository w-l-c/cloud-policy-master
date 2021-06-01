package cn.rebornauto.platform.wx.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rebornauto.platform.wx.api.UserAPI;
import cn.rebornauto.platform.wx.conf.ApiConfig;
import cn.rebornauto.platform.wx.conf.WxBidderConfig;
import cn.rebornauto.platform.wx.conf.WxDealerConfig;
import cn.rebornauto.platform.wx.dao.OauthCustomerMapper;
import cn.rebornauto.platform.wx.entity.OauthCustomer;
import cn.rebornauto.platform.wx.response.CodeToSessionResponse;
import cn.rebornauto.platform.wx.response.GetUserInfoResponse;
import cn.rebornauto.platform.wx.response.GetUsersResponse;

@Service
public class WxOauthService {

    @Autowired
    private OauthCustomerMapper oauthCustomerMapper;

    @Autowired
    private ApiConfig apiConfig;

    @Autowired
    private WxDealerConfig dealerConfig;

    @Autowired
    private WxBidderConfig bidderConfig;

    @Autowired
    private UserAPI userAPI;

    public boolean binded(String mobile, String openid) {
        return oauthCustomerMapper.countByPhoneAndOpenid(mobile, openid) > 0;
    }

    public int bind(GetUserInfoResponse user, String mobile) {
        if (binded(mobile, user.getOpenid())) {
            return 1;
        }
        OauthCustomer record = new OauthCustomer();
        record.setAppid(apiConfig.getAppid());
        record.setCreatedTime(new Date());
        record.setNickname(user.getNickname());
        record.setOpenid(user.getOpenid());
        record.setPhone(mobile);
        record.setUnionid(user.getUnionid());
        return oauthCustomerMapper.insert(record);
    }

    public int saveDealerOpenId(CodeToSessionResponse response, String mobile, String name) {
        if (binded(mobile, response.getOpenId())) {
            return 1;
        }
        OauthCustomer record = new OauthCustomer();
        record.setAppid(dealerConfig.getAppid());
        record.setCreatedTime(new Date());
        record.setOpenid(response.getOpenId());
        record.setCustomerName(name);
        record.setPhone(mobile);
        record.setUnionid(response.getUnionId());
        return oauthCustomerMapper.insert(record);
    }
    public int savebidderOpenId(CodeToSessionResponse response, String mobile, String name) {
        if (binded(mobile, response.getOpenId())) {
            return 1;
        }
        OauthCustomer record = new OauthCustomer();
        record.setAppid(bidderConfig.getAppid());
        record.setCreatedTime(new Date());
        record.setOpenid(response.getOpenId());
        record.setCustomerName(name);
        record.setPhone(mobile);
        record.setUnionid(response.getUnionId());
        return oauthCustomerMapper.insert(record);
    }
    public int savePublicOpenId(GetUserInfoResponse response, String name) {
        if(oauthCustomerMapper.countByUnionIdAndAppId(response.getUnionid(), apiConfig.getAppid()) > 0){
            return 1;
        }
        OauthCustomer record = new OauthCustomer();
        record.setAppid(apiConfig.getAppid());
        record.setCreatedTime(new Date());
        record.setOpenid(response.getOpenid());
        //record.setCustomerName(name);
        record.setUnionid(response.getUnionid());
        return oauthCustomerMapper.insert(record);
    }
    public void getUserList(){
        String nextOpenid = oauthCustomerMapper.selectNextOpenid(apiConfig.getAppid());
        GetUsersResponse getUsersResponse = userAPI.getUsers(nextOpenid);
        GetUsersResponse.Openid data = getUsersResponse.getData();
        if(getUsersResponse.getCount()!=0) {
            for (String openid : data.getOpenid()) {
                GetUserInfoResponse getUserInfoResponse = userAPI.getUserInfo(openid);
                savePublicOpenId(getUserInfoResponse, getUserInfoResponse.getNickname());
            }
        }
    }

}
