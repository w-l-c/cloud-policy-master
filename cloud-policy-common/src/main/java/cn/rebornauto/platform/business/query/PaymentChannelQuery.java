package cn.rebornauto.platform.business.query;

import cn.rebornauto.platform.common.data.request.Query;
import lombok.Data;

@Data
public class PaymentChannelQuery extends Query {
	
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
    /**
     * 支付公司名称
     */
    private String channelName;
    
}
