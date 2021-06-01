package cn.rebornauto.platform.wx.response;

import java.util.List;

import cn.rebornauto.platform.wx.entity.UserSummary;

/**
 * @author peiyu
 */
public class GetUserSummaryResponse extends BaseResponse {

    private List<UserSummary> list;

    public List<UserSummary> getList() {
        return list;
    }

    public void setList(List<UserSummary> list) {
        this.list = list;
    }
}
