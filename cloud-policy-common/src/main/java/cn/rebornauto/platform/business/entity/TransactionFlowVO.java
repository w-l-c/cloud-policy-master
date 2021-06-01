package cn.rebornauto.platform.business.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "`t_transaction_flow`")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionFlowVO implements Serializable {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 交易流水表标识 yyyyMMddHHmmss+6位随机数字
     */
    @Column(name = "`transaction_flow_id`")
    private String transactionFlowId;

    /**
     * 订单号
     */
    @Column(name = "`order_id`")
    private Integer orderId;

    /**
     * 客户编号
     */
    @Column(name = "`customer_id`")
    private String customerId;

    /**
     * 支付通道id，再次支付 更换支付通道时需要
     */
    @Column(name = "`payment_channel_id`")
    private Integer paymentChannelId;

    /**
     * 代征主体id，再次支付 更换代征主体时需要
     */
    @Column(name = "`dai_zheng_id`")
    private Integer daiZhengId;

    /**
     * 保单号
     */
    @Column(name = "`policy_no`")
    private String policyNo;

    /**
     * 保费
     */
    @Column(name = "`policy_amount`")
    private BigDecimal policyAmount;

    /**
     * 佣金
     */
    @Column(name = "`amount`")
    private BigDecimal amount;

    /**
     * 代理人id,通过身份证匹配到代理人id，并且便于再次支付时获取代理人其他银行卡信息
     */
    @Column(name = "`agent_id`")
    private Integer agentId;

    /**
     * 姓名
     */
    @Column(name = "`agent_name`")
    private String agentName;

    /**
     * 身份证
     */
    @Column(name = "`idcardno`")
    private String idcardno;

    /**
     * 银行卡号
     */
    @Column(name = "`open_bank_no`")
    private String openBankNo;

    /**
     * 出单时间
     */
    @Column(name = "`outtime`")
    private LocalDateTime outtime;

    /**
     * 来源   单次上传  或者  xxx.xls
     */
    @Column(name = "`source`")
    private String source;

    /**
     * 支付状态  1未支付、2已支付、3支付失败、4支付处理中、5待人工干预
     */
    @Column(name = "`pay_status`")
    private Integer payStatus;

    /**
     * 业务类型id  1线上支付、2线下支付等等
     */
    @Column(name = "`ft_type_id`")
    private Integer ftTypeId;

    /**
     * 完成时间
     */
    @Column(name = "`complete_time`")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime completeTime;

    /**
     * 银行代码,支付通道需要的银行行号之类信息
     */
    @Column(name = "`bank_code`")
    private String bankCode;

    /**
     * 交易代码，与支付结构匹配   比如100014
     */
    @Column(name = "`trade_code`")
    private String tradeCode;

    /**
     * 支付结果  支付成功、余额不足、已挂失卡等等支付渠道返回信息
     */
    @Column(name = "`result`")
    private String result;

    /**
     * 备注
     */
    @Column(name = "`remark`")
    private String remark;

    /**
     * 创建人
     */
    @Column(name = "`createoper`")
    private String createoper;

    /**
     * 提交时间
     */
    @Column(name = "`createtime`")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createtime;

    /**
     * 修改人
     */
    @Column(name = "`modifyoper`")
    private String modifyoper;

    /**
     * 修改时间
     */
    @Column(name = "`modifytime`")
    private LocalDateTime modifytime;
    
    private Integer totalCountQuery;
    
    private BigDecimal totalAmountQuery;
    
    private String daiZhengName;
    
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime postTime;
    
    private String requestSn;
    /**
     * 银行卡号数组字符串 
     */
    private String agentOpenBankNoArray;
    /**
     * 合并id
     */
    private String mergeId;
    
    /**
     * 保费千分位
     */
    private String policyAmountFin;

    /**
     * 佣金千分位
     */
    private String amountFin;
    /**
     * 总金额千分位
     */
    private String totalAmountQueryFin;
    /**
     * 批次号
     */
    private String batchNo;
    
    private String customerName;
    
    private Integer count;

    private static final long serialVersionUID = 1L;
}