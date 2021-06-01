package cn.rebornauto.platform.wx.response;


import java.util.List;

import cn.rebornauto.platform.wx.entity.ArticleTotal;

/**
 * @author peiyu
 */
public class GetArticleTotalResponse extends BaseResponse {

    private List<ArticleTotal> list;

    public List<ArticleTotal> getList() {
        return list;
    }

    public void setList(List<ArticleTotal> list) {
        this.list = list;
    }
}
