package cn.rebornauto.platform.wx.response;


import java.util.List;

import cn.rebornauto.platform.wx.entity.UpstreamMsgDist;

/**
 * @author peiyu
 */
public class GetUpstreamMsgDistResponse extends BaseResponse {

    private List<UpstreamMsgDist> list;

    public List<UpstreamMsgDist> getList() {
        return list;
    }

    public void setList(List<UpstreamMsgDist> list) {
        this.list = list;
    }
}
