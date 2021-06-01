package cn.rebornauto.platform.business.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "`t_order_detail_merge`")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailMerge implements Serializable {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 订单合并编号
     */
    @Column(name = "`merge_id`")
    private String mergeId;

    /**
     * 订单号
     */
    @Column(name = "`order_id`")
    private Integer orderId;

    /**
     * 代征主体id
     */
    @Column(name = "`dai_zheng_id`")
    private Integer daiZhengId;

    /**
     * 支付通道id
     */
    @Column(name = "`payment_channel_id`")
    private Integer paymentChannelId;

    /**
     * 保费累计
     */
    @Column(name = "`policy_amount`")
    private BigDecimal policyAmount;

    /**
     * 佣金累计
     */
    @Column(name = "`amount`")
    private BigDecimal amount;

    /**
     * 代理人id,通过身份证匹配到代理人id，便于将来关联代理人查询
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
     * 数据状态   1有效   -1无效
     */
    @Column(name = "`data_status`")
    private Integer dataStatus;

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
     * 注册时间
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
        sb.append(", mergeId=").append(mergeId);
        sb.append(", orderId=").append(orderId);
        sb.append(", daiZhengId=").append(daiZhengId);
        sb.append(", paymentChannelId=").append(paymentChannelId);
        sb.append(", policyAmount=").append(policyAmount);
        sb.append(", amount=").append(amount);
        sb.append(", agentId=").append(agentId);
        sb.append(", agentName=").append(agentName);
        sb.append(", idcardno=").append(idcardno);
        sb.append(", openBankNo=").append(openBankNo);
        sb.append(", dataStatus=").append(dataStatus);
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
        OrderDetailMerge other = (OrderDetailMerge) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMergeId() == null ? other.getMergeId() == null : this.getMergeId().equals(other.getMergeId()))
            && (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId()))
            && (this.getDaiZhengId() == null ? other.getDaiZhengId() == null : this.getDaiZhengId().equals(other.getDaiZhengId()))
            && (this.getPaymentChannelId() == null ? other.getPaymentChannelId() == null : this.getPaymentChannelId().equals(other.getPaymentChannelId()))
            && (this.getPolicyAmount() == null ? other.getPolicyAmount() == null : this.getPolicyAmount().equals(other.getPolicyAmount()))
            && (this.getAmount() == null ? other.getAmount() == null : this.getAmount().equals(other.getAmount()))
            && (this.getAgentId() == null ? other.getAgentId() == null : this.getAgentId().equals(other.getAgentId()))
            && (this.getAgentName() == null ? other.getAgentName() == null : this.getAgentName().equals(other.getAgentName()))
            && (this.getIdcardno() == null ? other.getIdcardno() == null : this.getIdcardno().equals(other.getIdcardno()))
            && (this.getOpenBankNo() == null ? other.getOpenBankNo() == null : this.getOpenBankNo().equals(other.getOpenBankNo()))
            && (this.getDataStatus() == null ? other.getDataStatus() == null : this.getDataStatus().equals(other.getDataStatus()))
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
        result = prime * result + ((getMergeId() == null) ? 0 : getMergeId().hashCode());
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        result = prime * result + ((getDaiZhengId() == null) ? 0 : getDaiZhengId().hashCode());
        result = prime * result + ((getPaymentChannelId() == null) ? 0 : getPaymentChannelId().hashCode());
        result = prime * result + ((getPolicyAmount() == null) ? 0 : getPolicyAmount().hashCode());
        result = prime * result + ((getAmount() == null) ? 0 : getAmount().hashCode());
        result = prime * result + ((getAgentId() == null) ? 0 : getAgentId().hashCode());
        result = prime * result + ((getAgentName() == null) ? 0 : getAgentName().hashCode());
        result = prime * result + ((getIdcardno() == null) ? 0 : getIdcardno().hashCode());
        result = prime * result + ((getOpenBankNo() == null) ? 0 : getOpenBankNo().hashCode());
        result = prime * result + ((getDataStatus() == null) ? 0 : getDataStatus().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreateoper() == null) ? 0 : getCreateoper().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getModifyoper() == null) ? 0 : getModifyoper().hashCode());
        result = prime * result + ((getModifytime() == null) ? 0 : getModifytime().hashCode());
        return result;
    }
}