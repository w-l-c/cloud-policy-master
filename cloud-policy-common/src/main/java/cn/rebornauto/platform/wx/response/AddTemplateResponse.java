package cn.rebornauto.platform.wx.response;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 添加模版响应
 */
public class AddTemplateResponse extends BaseResponse {
    /**
     * 模版id
     */
    @JSONField(name = "template_id")
    private String templateId;

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }
}
