package cn.rebornauto.platform.wx.response;


import java.util.List;

import cn.rebornauto.platform.wx.entity.UpstreamMsgWeek;

/**
 * @author peiyu
 */
public class GetUpstreamMsgWeekResponse extends BaseResponse {

    private List<UpstreamMsgWeek> list;

    public List<UpstreamMsgWeek> getList() {
        return list;
    }

    public void setList(List<UpstreamMsgWeek> list) {
        this.list = list;
    }
}
