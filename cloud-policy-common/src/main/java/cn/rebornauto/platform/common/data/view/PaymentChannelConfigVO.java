package cn.rebornauto.platform.common.data.view;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PaymentChannelConfigVO implements Serializable{
	private static final long serialVersionUID = -2125070462407197899L;

	/**
     * 支付通道id
     */
    private Integer id;
    
    /**
     * 支付通道id
     */
    private Integer paymentChannelId;
    
    /**
     *  代征id
     */
    private Integer daiZhengId;

    /**
     * 支付通道名称
     */
    private String channelName;
    
    /**
     * 系统开关类型
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
     * 数据状态   1有效   -1无效
     */
    private Integer dataStatus;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private String createoper;

    /**
     * 创建时间
     */
    private LocalDateTime createtime;

    /**
     * 修改人
     */
    private String modifyoper;

    /**
     * 修改时间
     */
    private LocalDateTime modifytime;
}
