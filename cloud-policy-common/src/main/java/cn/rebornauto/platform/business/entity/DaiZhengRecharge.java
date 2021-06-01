package cn.rebornauto.platform.business.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "`t_dai_zheng_recharge`")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DaiZhengRecharge implements Serializable {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 申请时间
     */
    @Column(name = "`applytime`")
    private LocalDateTime applytime;

    /**
     * 客户编号
     */
    @Column(name = "`customer_id`")
    private String customerId;

    /**
     * 代理人佣金（付款金额）
     */
    @Column(name = "`agent_commission`")
    private BigDecimal agentCommission;

    /**
     * 实际充值金额
     */
    @Column(name = "`real_payment`")
    private BigDecimal realPayment;

    /**
     * 充值状态 1已申请，2待审核，3已到账，4已驳回
     */
    @Column(name = "`recharge`")
    private Integer recharge;

    /**
     * 充值凭证图片
     */
    @Column(name = "`recharge_voucher_pic_url`")
    private String rechargeVoucherPicUrl;

    /**
     * 到账时间
     */
    @Column(name = "`arrivetime`")
    private LocalDateTime arrivetime;

    /**
     * 代征主体 id
     */
    @Column(name = "`dai_zheng_id`")
    private Integer daiZhengId;

    /**
     * 客户服务费率
     */
    @Column(name = "`customer_service_rate`")
    private BigDecimal customerServiceRate;

    /**
     * 服务费
     */
    @Column(name = "`service_amount`")
    private BigDecimal serviceAmount;

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
    
    /**
     * 审核人
     */
    @Column(name = "`checkoper`")
    private String checkoper;

    /**
     * 审核时间
     */
    @Column(name = "`checktime`")
    private LocalDateTime checktime;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", applytime=").append(applytime);
        sb.append(", customerId=").append(customerId);
        sb.append(", agentCommission=").append(agentCommission);
        sb.append(", realPayment=").append(realPayment);
        sb.append(", recharge=").append(recharge);
        sb.append(", rechargeVoucherPicUrl=").append(rechargeVoucherPicUrl);
        sb.append(", arrivetime=").append(arrivetime);
        sb.append(", daiZhengId=").append(daiZhengId);
        sb.append(", customerServiceRate=").append(customerServiceRate);
        sb.append(", serviceAmount=").append(serviceAmount);
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
        DaiZhengRecharge other = (DaiZhengRecharge) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getApplytime() == null ? other.getApplytime() == null : this.getApplytime().equals(other.getApplytime()))
            && (this.getCustomerId() == null ? other.getCustomerId() == null : this.getCustomerId().equals(other.getCustomerId()))
            && (this.getAgentCommission() == null ? other.getAgentCommission() == null : this.getAgentCommission().equals(other.getAgentCommission()))
            && (this.getRealPayment() == null ? other.getRealPayment() == null : this.getRealPayment().equals(other.getRealPayment()))
            && (this.getRecharge() == null ? other.getRecharge() == null : this.getRecharge().equals(other.getRecharge()))
            && (this.getRechargeVoucherPicUrl() == null ? other.getRechargeVoucherPicUrl() == null : this.getRechargeVoucherPicUrl().equals(other.getRechargeVoucherPicUrl()))
            && (this.getArrivetime() == null ? other.getArrivetime() == null : this.getArrivetime().equals(other.getArrivetime()))
            && (this.getDaiZhengId() == null ? other.getDaiZhengId() == null : this.getDaiZhengId().equals(other.getDaiZhengId()))
            && (this.getCustomerServiceRate() == null ? other.getCustomerServiceRate() == null : this.getCustomerServiceRate().equals(other.getCustomerServiceRate()))
            && (this.getServiceAmount() == null ? other.getServiceAmount() == null : this.getServiceAmount().equals(other.getServiceAmount()))
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
        result = prime * result + ((getApplytime() == null) ? 0 : getApplytime().hashCode());
        result = prime * result + ((getCustomerId() == null) ? 0 : getCustomerId().hashCode());
        result = prime * result + ((getAgentCommission() == null) ? 0 : getAgentCommission().hashCode());
        result = prime * result + ((getRealPayment() == null) ? 0 : getRealPayment().hashCode());
        result = prime * result + ((getRecharge() == null) ? 0 : getRecharge().hashCode());
        result = prime * result + ((getRechargeVoucherPicUrl() == null) ? 0 : getRechargeVoucherPicUrl().hashCode());
        result = prime * result + ((getArrivetime() == null) ? 0 : getArrivetime().hashCode());
        result = prime * result + ((getDaiZhengId() == null) ? 0 : getDaiZhengId().hashCode());
        result = prime * result + ((getCustomerServiceRate() == null) ? 0 : getCustomerServiceRate().hashCode());
        result = prime * result + ((getServiceAmount() == null) ? 0 : getServiceAmount().hashCode());
        result = prime * result + ((getDataStatus() == null) ? 0 : getDataStatus().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreateoper() == null) ? 0 : getCreateoper().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getModifyoper() == null) ? 0 : getModifyoper().hashCode());
        result = prime * result + ((getModifytime() == null) ? 0 : getModifytime().hashCode());
        return result;
    }
}