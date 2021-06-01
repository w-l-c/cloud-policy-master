package cn.rebornauto.platform.business.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "`t_collection_repayment_sign`")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CollectionRepaymentSign implements Serializable {
    /**
     * id
     */
    @Id
    @Column(name = "`id`")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 支付流水号
     */
    @Id
    @Column(name = "`request_sn`")
    private String requestSn;

    /**
     * 交易流水表标识
     */
    @Column(name = "`transaction_flow_id`")
    private String transactionFlowId;

    /**
     * 交易代码，与支付结构匹配   比如100014
     */
    @Column(name = "`trade_code`")
    private String tradeCode;

    /**
     * 银行代码,支付通道需要的银行行号之类信息
     */
    @Column(name = "`bank_code`")
    private String bankCode;

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
     * 金额
     */
    @Column(name = "`amount`")
    private BigDecimal amount;

    /**
     * 完成时间
     */
    @Column(name = "`complete_time`")
    private LocalDateTime completeTime;

    /**
     * 提交时间
     */
    @Column(name = "`post_time`")
    private LocalDateTime postTime;

    /**
     * 备注
     */
    @Column(name = "`remark`")
    private String remark;

    /**
     * 交易状态
     */
    @Column(name = "`status`")
    private Integer status;

    /**
     * 返回值中的状态码
     */
    @Column(name = "`status1`")
    private String status1;

    /**
     * 返回值详请中的状态码
     */
    @Column(name = "`status2`")
    private String status2;

    /**
     * 操作员
     */
    @Column(name = "`createoper`")
    private String createoper;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", requestSn=").append(requestSn);
        sb.append(", transactionFlowId=").append(transactionFlowId);
        sb.append(", tradeCode=").append(tradeCode);
        sb.append(", bankCode=").append(bankCode);
        sb.append(", agentName=").append(agentName);
        sb.append(", idcardno=").append(idcardno);
        sb.append(", openBankNo=").append(openBankNo);
        sb.append(", amount=").append(amount);
        sb.append(", completeTime=").append(completeTime);
        sb.append(", postTime=").append(postTime);
        sb.append(", remark=").append(remark);
        sb.append(", status=").append(status);
        sb.append(", status1=").append(status1);
        sb.append(", status2=").append(status2);
        sb.append(", createoper=").append(createoper);
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
        CollectionRepaymentSign other = (CollectionRepaymentSign) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getRequestSn() == null ? other.getRequestSn() == null : this.getRequestSn().equals(other.getRequestSn()))
            && (this.getTransactionFlowId() == null ? other.getTransactionFlowId() == null : this.getTransactionFlowId().equals(other.getTransactionFlowId()))
            && (this.getTradeCode() == null ? other.getTradeCode() == null : this.getTradeCode().equals(other.getTradeCode()))
            && (this.getBankCode() == null ? other.getBankCode() == null : this.getBankCode().equals(other.getBankCode()))
            && (this.getAgentName() == null ? other.getAgentName() == null : this.getAgentName().equals(other.getAgentName()))
            && (this.getIdcardno() == null ? other.getIdcardno() == null : this.getIdcardno().equals(other.getIdcardno()))
            && (this.getOpenBankNo() == null ? other.getOpenBankNo() == null : this.getOpenBankNo().equals(other.getOpenBankNo()))
            && (this.getAmount() == null ? other.getAmount() == null : this.getAmount().equals(other.getAmount()))
            && (this.getCompleteTime() == null ? other.getCompleteTime() == null : this.getCompleteTime().equals(other.getCompleteTime()))
            && (this.getPostTime() == null ? other.getPostTime() == null : this.getPostTime().equals(other.getPostTime()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getStatus1() == null ? other.getStatus1() == null : this.getStatus1().equals(other.getStatus1()))
            && (this.getStatus2() == null ? other.getStatus2() == null : this.getStatus2().equals(other.getStatus2()))
            && (this.getCreateoper() == null ? other.getCreateoper() == null : this.getCreateoper().equals(other.getCreateoper()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getRequestSn() == null) ? 0 : getRequestSn().hashCode());
        result = prime * result + ((getTransactionFlowId() == null) ? 0 : getTransactionFlowId().hashCode());
        result = prime * result + ((getTradeCode() == null) ? 0 : getTradeCode().hashCode());
        result = prime * result + ((getBankCode() == null) ? 0 : getBankCode().hashCode());
        result = prime * result + ((getAgentName() == null) ? 0 : getAgentName().hashCode());
        result = prime * result + ((getIdcardno() == null) ? 0 : getIdcardno().hashCode());
        result = prime * result + ((getOpenBankNo() == null) ? 0 : getOpenBankNo().hashCode());
        result = prime * result + ((getAmount() == null) ? 0 : getAmount().hashCode());
        result = prime * result + ((getCompleteTime() == null) ? 0 : getCompleteTime().hashCode());
        result = prime * result + ((getPostTime() == null) ? 0 : getPostTime().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getStatus1() == null) ? 0 : getStatus1().hashCode());
        result = prime * result + ((getStatus2() == null) ? 0 : getStatus2().hashCode());
        result = prime * result + ((getCreateoper() == null) ? 0 : getCreateoper().hashCode());
        return result;
    }
}