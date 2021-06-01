package cn.rebornauto.platform.business.form;

import cn.rebornauto.platform.common.data.request.Form;
import lombok.Data;

@Data
public class PaymentChannelForm extends Form {
	/**
     * 支付公司类型
     */
    private Integer paymentChannelId;
    /**
     *  系统开关类型
     */
    private String sysSwitch;
    /**
     * 关键字
     */
    private String key;
    /**
     * 值
     */
    private String value;
    /**
     * 描述
     */
    private String remark;
    /**
     * 数据状态
     */
    private Integer dataStatus;
}
