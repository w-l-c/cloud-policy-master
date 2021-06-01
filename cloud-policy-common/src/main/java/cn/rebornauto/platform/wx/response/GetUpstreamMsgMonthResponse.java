package cn.rebornauto.platform.wx.response;


import java.util.List;

import cn.rebornauto.platform.wx.entity.UpstreamMsgMonth;

/**
 * @author peiyu
 */
public class GetUpstreamMsgMonthResponse extends BaseResponse {

    private List<UpstreamMsgMonth> list;

    public List<UpstreamMsgMonth> getList() {
        return list;
    }

    public void setList(List<UpstreamMsgMonth> list) {
        this.list = list;
    }
}
