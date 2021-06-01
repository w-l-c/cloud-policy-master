package cn.rebornauto.platform.business.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "`t_customer_total_quota`")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerTotalQuota implements Serializable {
    /**
     * id
     */
    @Id
    @Column(name = "`id`")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 账户总余额（元）
     */
    @Column(name = "`customer_balance`")
    private BigDecimal customerBalance;

    /**
     * 可用总余额（元）
     */
    @Column(name = "`available_balance`")
    private BigDecimal availableBalance;

    /**
     * 冻结总金额（元）
     */
    @Column(name = "`frozen_amount`")
    private BigDecimal frozenAmount;

    /**
     * 待审核充值总金额（元）
     */
    @Column(name = "`pending_appr_amount`")
    private BigDecimal pendingApprAmount;

    /**
     * 累计付款总金额（元）
     */
    @Column(name = "`loan_amount`")
    private BigDecimal loanAmount;

    /**
     * 累计充值总金额（元）
     */
    @Column(name = "`recharge_amount`")
    private BigDecimal rechargeAmount;

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
     * 创建时间
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
        sb.append(", customerBalance=").append(customerBalance);
        sb.append(", availableBalance=").append(availableBalance);
        sb.append(", frozenAmount=").append(frozenAmount);
        sb.append(", pendingApprAmount=").append(pendingApprAmount);
        sb.append(", loanAmount=").append(loanAmount);
        sb.append(", rechargeAmount=").append(rechargeAmount);
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
        CustomerTotalQuota other = (CustomerTotalQuota) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCustomerBalance() == null ? other.getCustomerBalance() == null : this.getCustomerBalance().equals(other.getCustomerBalance()))
            && (this.getAvailableBalance() == null ? other.getAvailableBalance() == null : this.getAvailableBalance().equals(other.getAvailableBalance()))
            && (this.getFrozenAmount() == null ? other.getFrozenAmount() == null : this.getFrozenAmount().equals(other.getFrozenAmount()))
            && (this.getPendingApprAmount() == null ? other.getPendingApprAmount() == null : this.getPendingApprAmount().equals(other.getPendingApprAmount()))
            && (this.getLoanAmount() == null ? other.getLoanAmount() == null : this.getLoanAmount().equals(other.getLoanAmount()))
            && (this.getRechargeAmount() == null ? other.getRechargeAmount() == null : this.getRechargeAmount().equals(other.getRechargeAmount()))
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
        result = prime * result + ((getCustomerBalance() == null) ? 0 : getCustomerBalance().hashCode());
        result = prime * result + ((getAvailableBalance() == null) ? 0 : getAvailableBalance().hashCode());
        result = prime * result + ((getFrozenAmount() == null) ? 0 : getFrozenAmount().hashCode());
        result = prime * result + ((getPendingApprAmount() == null) ? 0 : getPendingApprAmount().hashCode());
        result = prime * result + ((getLoanAmount() == null) ? 0 : getLoanAmount().hashCode());
        result = prime * result + ((getRechargeAmount() == null) ? 0 : getRechargeAmount().hashCode());
        result = prime * result + ((getDataStatus() == null) ? 0 : getDataStatus().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreateoper() == null) ? 0 : getCreateoper().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getModifyoper() == null) ? 0 : getModifyoper().hashCode());
        result = prime * result + ((getModifytime() == null) ? 0 : getModifytime().hashCode());
        return result;
    }
}