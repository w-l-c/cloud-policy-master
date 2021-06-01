package cn.rebornauto.platform.wx.response;


import java.util.List;

import cn.rebornauto.platform.wx.entity.UserShare;

/**
 * @author peiyu
 */
public class GetUserShareResponse extends BaseResponse {

    private List<UserShare> list;

    public List<UserShare> getList() {
        return list;
    }

    public void setList(List<UserShare> list) {
        this.list = list;
    }
}
