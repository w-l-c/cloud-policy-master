package cn.rebornauto.platform.business.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "`t_order`")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 客户编号
     */
    @Column(name = "`customer_id`")
    private String customerId;

    /**
     * 代理人佣金（订单明细表佣金总和）
     */
    @Column(name = "`agent_commission`")
    private BigDecimal agentCommission;

    /**
     * 支付总笔数
     */
    @Column(name = "`total_count`")
    private Integer totalCount;

    /**
     * 支付状态  1未支付、2已支付、3支付失败、4支付处理中、5待人工干预
     */
    @Column(name = "`pay_status`")
    private Integer payStatus;

    /**
     * 审核状态  1已申请(订单申请已经到账的数据)、2已上传(已提交excel或单次数据)、3待审核（提交放款申请）、4已审核（已放款）、5已驳回
     */
    @Column(name = "`check_status`")
    private Integer checkStatus;

    /**
     * 订单状态   0未完成   1已完成（全部支付成功的 或者 全部关闭的 或者 合并数量和成功数量相同）
     */
    @Column(name = "`order_status`")
    private Integer orderStatus;

    /**
     * 申请人
     */
    @Column(name = "`createoper`")
    private String createoper;

    /**
     * 申请时间
     */
    @Column(name = "`createtime`")
    private LocalDateTime createtime;

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

    /**
     * 成功数量
     */
    @Column(name = "`succ_count`")
    private Integer succCount;

    /**
     * 失败数量
     */
    @Column(name = "`fail_count`")
    private Integer failCount;

    /**
     * 合并数量
     */
    @Column(name = "`merge_count`")
    private Integer mergeCount;

    /**
     * 支付统计
     */
    @Column(name = "`pay_stat`")
    private String payStat;

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
     * 上传备注
     */
    @Column(name = "`upload_remark`")
    private String uploadRemark;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", customerId=").append(customerId);
        sb.append(", agentCommission=").append(agentCommission);
        sb.append(", totalCount=").append(totalCount);
        sb.append(", payStatus=").append(payStatus);
        sb.append(", checkStatus=").append(checkStatus);
        sb.append(", orderStatus=").append(orderStatus);
        sb.append(", createoper=").append(createoper);
        sb.append(", createtime=").append(createtime);
        sb.append(", checkoper=").append(checkoper);
        sb.append(", checktime=").append(checktime);
        sb.append(", succCount=").append(succCount);
        sb.append(", failCount=").append(failCount);
        sb.append(", mergeCount=").append(mergeCount);
        sb.append(", payStat=").append(payStat);
        sb.append(", dataStatus=").append(dataStatus);
        sb.append(", remark=").append(remark);
        sb.append(", uploadRemark=").append(uploadRemark);
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
        Order other = (Order) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCustomerId() == null ? other.getCustomerId() == null : this.getCustomerId().equals(other.getCustomerId()))
            && (this.getAgentCommission() == null ? other.getAgentCommission() == null : this.getAgentCommission().equals(other.getAgentCommission()))
            && (this.getTotalCount() == null ? other.getTotalCount() == null : this.getTotalCount().equals(other.getTotalCount()))
            && (this.getPayStatus() == null ? other.getPayStatus() == null : this.getPayStatus().equals(other.getPayStatus()))
            && (this.getCheckStatus() == null ? other.getCheckStatus() == null : this.getCheckStatus().equals(other.getCheckStatus()))
            && (this.getOrderStatus() == null ? other.getOrderStatus() == null : this.getOrderStatus().equals(other.getOrderStatus()))
            && (this.getCreateoper() == null ? other.getCreateoper() == null : this.getCreateoper().equals(other.getCreateoper()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getCheckoper() == null ? other.getCheckoper() == null : this.getCheckoper().equals(other.getCheckoper()))
            && (this.getChecktime() == null ? other.getChecktime() == null : this.getChecktime().equals(other.getChecktime()))
            && (this.getSuccCount() == null ? other.getSuccCount() == null : this.getSuccCount().equals(other.getSuccCount()))
            && (this.getFailCount() == null ? other.getFailCount() == null : this.getFailCount().equals(other.getFailCount()))
            && (this.getMergeCount() == null ? other.getMergeCount() == null : this.getMergeCount().equals(other.getMergeCount()))
            && (this.getPayStat() == null ? other.getPayStat() == null : this.getPayStat().equals(other.getPayStat()))
            && (this.getDataStatus() == null ? other.getDataStatus() == null : this.getDataStatus().equals(other.getDataStatus()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getUploadRemark() == null ? other.getUploadRemark() == null : this.getUploadRemark().equals(other.getUploadRemark()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCustomerId() == null) ? 0 : getCustomerId().hashCode());
        result = prime * result + ((getAgentCommission() == null) ? 0 : getAgentCommission().hashCode());
        result = prime * result + ((getTotalCount() == null) ? 0 : getTotalCount().hashCode());
        result = prime * result + ((getPayStatus() == null) ? 0 : getPayStatus().hashCode());
        result = prime * result + ((getCheckStatus() == null) ? 0 : getCheckStatus().hashCode());
        result = prime * result + ((getOrderStatus() == null) ? 0 : getOrderStatus().hashCode());
        result = prime * result + ((getCreateoper() == null) ? 0 : getCreateoper().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getCheckoper() == null) ? 0 : getCheckoper().hashCode());
        result = prime * result + ((getChecktime() == null) ? 0 : getChecktime().hashCode());
        result = prime * result + ((getSuccCount() == null) ? 0 : getSuccCount().hashCode());
        result = prime * result + ((getFailCount() == null) ? 0 : getFailCount().hashCode());
        result = prime * result + ((getMergeCount() == null) ? 0 : getMergeCount().hashCode());
        result = prime * result + ((getPayStat() == null) ? 0 : getPayStat().hashCode());
        result = prime * result + ((getDataStatus() == null) ? 0 : getDataStatus().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getUploadRemark() == null) ? 0 : getUploadRemark().hashCode());
        return result;
    }
}