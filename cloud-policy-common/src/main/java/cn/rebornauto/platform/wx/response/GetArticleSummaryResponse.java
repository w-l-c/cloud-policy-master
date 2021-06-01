package cn.rebornauto.platform.wx.response;


import java.util.List;

import cn.rebornauto.platform.wx.entity.ArticleSummary;

/**
 * @author peiyu
 */
public class GetArticleSummaryResponse extends BaseResponse {

    private List<ArticleSummary> list;

    public List<ArticleSummary> getList() {
        return list;
    }

    public void setList(List<ArticleSummary> list) {
        this.list = list;
    }
}
