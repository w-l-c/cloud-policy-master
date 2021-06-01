package cn.rebornauto.platform.wx.response;


import java.util.List;

import cn.rebornauto.platform.wx.entity.UpstreamMsgHour;

/**
 * @author peiyu
 */
public class GetUpstreamMsgHourResponse extends BaseResponse {

    private List<UpstreamMsgHour> list;

    public List<UpstreamMsgHour> getList() {
        return list;
    }

    public void setList(List<UpstreamMsgHour> list) {
        this.list = list;
    }
}
