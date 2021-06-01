package cn.rebornauto.platform.wx.response;


import java.util.List;

import cn.rebornauto.platform.wx.entity.UserShareHour;

/**
 * @author peiyu
 */
public class GetUserShareHourResponse extends BaseResponse {

    private List<UserShareHour> list;

    public List<UserShareHour> getList() {
        return list;
    }

    public void setList(List<UserShareHour> list) {
        this.list = list;
    }
}
