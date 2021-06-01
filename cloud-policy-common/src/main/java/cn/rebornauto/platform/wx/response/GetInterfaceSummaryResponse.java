package cn.rebornauto.platform.wx.response;


import java.util.List;

import cn.rebornauto.platform.wx.entity.InterfaceSummary;

/**
 * @author peiyu
 */
public class GetInterfaceSummaryResponse extends BaseResponse {

    private List<InterfaceSummary> list;

    public List<InterfaceSummary> getList() {
        return list;
    }

    public void setList(List<InterfaceSummary> list) {
        this.list = list;
    }
}
