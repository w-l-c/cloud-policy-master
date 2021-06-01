package cn.rebornauto.platform.business.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

@Data
public class CollectionRepaymentSignVO implements Serializable {
    /**
     * id
     */
    private Integer id;

    /**
     * 支付流水号
     */
    private String requestSn;

    /**
     * 交易流水表标识
     */
    private String transactionFlowId;

    /**
     * 交易代码，与支付结构匹配   比如100014
     */
    private String tradeCode;

    /**
     * 银行代码,支付通道需要的银行行号之类信息
     */
    private String bankCode;

    /**
     * 姓名
     */
    private String agentName;

    /**
     * 身份证
     */
    private String idcardno;

    /**
     * 银行卡号
     */
    private String openBankNo;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 完成时间
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime completeTime;

    /**
     * 提交时间
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime postTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 交易状态
     */
    private Integer status;

    /**
     * 返回值中的状态码
     */
    private String status1;

    /**
     * 返回值详请中的状态码
     */
    private String status2;

    /**
     * 操作员
     */
    private String createoper;
    /**
     * 客户id
     */
    private String customerId;
    /**
     * 订单id
     */
    private Integer orderId;
    
    private Integer ftTypeId;
    
    private String policyNo;
    
    private BigDecimal policyAmount;
    
    /**
     * 出单时间
     */
    private LocalDateTime outtime;
    
    /**
     * 佣金千分位
     */
    private String amountFin;
    
    /**
     * 保费千分位
     */
    private String policyAmountFin;

    private static final long serialVersionUID = 1L;

}