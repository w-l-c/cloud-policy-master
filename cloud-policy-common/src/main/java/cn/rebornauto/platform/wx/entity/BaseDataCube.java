package cn.rebornauto.platform.wx.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * @author peiyu
 */
public class BaseDataCube extends BaseModel {

    @JSONField(name = "ref_date", format = "yyyy-MM-dd")
    private Date refDate;

    public Date getRefDate() {
        return refDate;
    }

    public void setRefDate(Date refDate) {
        this.refDate = refDate;
    }
}
