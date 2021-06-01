package cn.rebornauto.platform.business.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "`t_transaction_flow`")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionFlow implements Serializable {
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
     * 订单明细合并编号
     */
    @Column(name = "`merge_id`")
    private String mergeId;

    /**
     * 订单明细ID
     */
    @Column(name = "`order_detail_id`")
    private Integer orderDetailId;

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
     * 业务类型id  1线上支付、2线下支付等等
     */
    @Column(name = "`ft_type_id`")
    private Integer ftTypeId;

    /**
     * 完成时间
     */
    @Column(name = "`complete_time`")
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
     * 支付状态  1未支付、2已支付、3支付失败、4支付处理中、5待人工干预
     */
    @Column(name = "`pay_status`")
    private Integer payStatus;

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

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", transactionFlowId=").append(transactionFlowId);
        sb.append(", orderId=").append(orderId);
        sb.append(", mergeId=").append(mergeId);
        sb.append(", orderDetailId=").append(orderDetailId);
        sb.append(", customerId=").append(customerId);
        sb.append(", paymentChannelId=").append(paymentChannelId);
        sb.append(", daiZhengId=").append(daiZhengId);
        sb.append(", policyNo=").append(policyNo);
        sb.append(", policyAmount=").append(policyAmount);
        sb.append(", amount=").append(amount);
        sb.append(", agentId=").append(agentId);
        sb.append(", agentName=").append(agentName);
        sb.append(", idcardno=").append(idcardno);
        sb.append(", openBankNo=").append(openBankNo);
        sb.append(", outtime=").append(outtime);
        sb.append(", source=").append(source);
        sb.append(", ftTypeId=").append(ftTypeId);
        sb.append(", completeTime=").append(completeTime);
        sb.append(", bankCode=").append(bankCode);
        sb.append(", tradeCode=").append(tradeCode);
        sb.append(", payStatus=").append(payStatus);
        sb.append(", result=").append(result);
        sb.append(", remark=").append(remark);
        sb.append(", createoper=").append(createoper);
        sb.append(", createtime=").append(createtime);
        sb.append(", modifyoper=").append(modifyoper);
        sb.append(", modifytime=").append(modifytime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TransactionFlow other = (TransactionFlow) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTransactionFlowId() == null ? other.getTransactionFlowId() == null : this.getTransactionFlowId().equals(other.getTransactionFlowId()))
            && (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId()))
            && (this.getMergeId() == null ? other.getMergeId() == null : this.getMergeId().equals(other.getMergeId()))
            && (this.getOrderDetailId() == null ? other.getOrderDetailId() == null : this.getOrderDetailId().equals(other.getOrderDetailId()))
            && (this.getCustomerId() == null ? other.getCustomerId() == null : this.getCustomerId().equals(other.getCustomerId()))
            && (this.getPaymentChannelId() == null ? other.getPaymentChannelId() == null : this.getPaymentChannelId().equals(other.getPaymentChannelId()))
            && (this.getDaiZhengId() == null ? other.getDaiZhengId() == null : this.getDaiZhengId().equals(other.getDaiZhengId()))
            && (this.getPolicyNo() == null ? other.getPolicyNo() == null : this.getPolicyNo().equals(other.getPolicyNo()))
            && (this.getPolicyAmount() == null ? other.getPolicyAmount() == null : this.getPolicyAmount().equals(other.getPolicyAmount()))
            && (this.getAmount() == null ? other.getAmount() == null : this.getAmount().equals(other.getAmount()))
            && (this.getAgentId() == null ? other.getAgentId() == null : this.getAgentId().equals(other.getAgentId()))
            && (this.getAgentName() == null ? other.getAgentName() == null : this.getAgentName().equals(other.getAgentName()))
            && (this.getIdcardno() == null ? other.getIdcardno() == null : this.getIdcardno().equals(other.getIdcardno()))
            && (this.getOpenBankNo() == null ? other.getOpenBankNo() == null : this.getOpenBankNo().equals(other.getOpenBankNo()))
            && (this.getOuttime() == null ? other.getOuttime() == null : this.getOuttime().equals(other.getOuttime()))
            && (this.getSource() == null ? other.getSource() == null : this.getSource().equals(other.getSource()))
            && (this.getFtTypeId() == null ? other.getFtTypeId() == null : this.getFtTypeId().equals(other.getFtTypeId()))
            && (this.getCompleteTime() == null ? other.getCompleteTime() == null : this.getCompleteTime().equals(other.getCompleteTime()))
            && (this.getBankCode() == null ? other.getBankCode() == null : this.getBankCode().equals(other.getBankCode()))
            && (this.getTradeCode() == null ? other.getTradeCode() == null : this.getTradeCode().equals(other.getTradeCode()))
            && (this.getPayStatus() == null ? other.getPayStatus() == null : this.getPayStatus().equals(other.getPayStatus()))
            && (this.getResult() == null ? other.getResult() == null : this.getResult().equals(other.getResult()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreateoper() == null ? other.getCreateoper() == null : this.getCreateoper().equals(other.getCreateoper()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getModifyoper() == null ? other.getModifyoper() == null : this.getModifyoper().equals(other.getModifyoper()))
            && (this.getModifytime() == null ? other.getModifytime() == null : this.getModifytime().equals(other.getModifytime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTransactionFlowId() == null) ? 0 : getTransactionFlowId().hashCode());
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        result = prime * result + ((getMergeId() == null) ? 0 : getMergeId().hashCode());
        result = prime * result + ((getOrderDetailId() == null) ? 0 : getOrderDetailId().hashCode());
        result = prime * result + ((getCustomerId() == null) ? 0 : getCustomerId().hashCode());
        result = prime * result + ((getPaymentChannelId() == null) ? 0 : getPaymentChannelId().hashCode());
        result = prime * result + ((getDaiZhengId() == null) ? 0 : getDaiZhengId().hashCode());
        result = prime * result + ((getPolicyNo() == null) ? 0 : getPolicyNo().hashCode());
        result = prime * result + ((getPolicyAmount() == null) ? 0 : getPolicyAmount().hashCode());
        result = prime * result + ((getAmount() == null) ? 0 : getAmount().hashCode());
        result = prime * result + ((getAgentId() == null) ? 0 : getAgentId().hashCode());
        result = prime * result + ((getAgentName() == null) ? 0 : getAgentName().hashCode());
        result = prime * result + ((getIdcardno() == null) ? 0 : getIdcardno().hashCode());
        result = prime * result + ((getOpenBankNo() == null) ? 0 : getOpenBankNo().hashCode());
        result = prime * result + ((getOuttime() == null) ? 0 : getOuttime().hashCode());
        result = prime * result + ((getSource() == null) ? 0 : getSource().hashCode());
        result = prime * result + ((getFtTypeId() == null) ? 0 : getFtTypeId().hashCode());
        result = prime * result + ((getCompleteTime() == null) ? 0 : getCompleteTime().hashCode());
        result = prime * result + ((getBankCode() == null) ? 0 : getBankCode().hashCode());
        result = prime * result + ((getTradeCode() == null) ? 0 : getTradeCode().hashCode());
        result = prime * result + ((getPayStatus() == null) ? 0 : getPayStatus().hashCode());
        result = prime * result + ((getResult() == null) ? 0 : getResult().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreateoper() == null) ? 0 : getCreateoper().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getModifyoper() == null) ? 0 : getModifyoper().hashCode());
        result = prime * result + ((getModifytime() == null) ? 0 : getModifytime().hashCode());
        return result;
    }
}