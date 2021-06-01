package cn.rebornauto.platform.wx.response;

import java.util.List;

import cn.rebornauto.platform.wx.entity.CustomAccount;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author peiyu
 */
public class GetCustomAccountsResponse extends BaseResponse {

    @JSONField(name = "kf_list")
    private List<CustomAccount> customAccountList;

    public List<CustomAccount> getCustomAccountList() {
        return customAccountList;
    }

    public void setCustomAccountList(List<CustomAccount> customAccountList) {
        this.customAccountList = customAccountList;
    }
}
