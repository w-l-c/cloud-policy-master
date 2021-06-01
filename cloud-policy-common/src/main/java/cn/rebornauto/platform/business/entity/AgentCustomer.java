package cn.rebornauto.platform.business.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "`t_agent_customer`")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgentCustomer implements Serializable {
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
     * 代理人id
     */
    @Column(name = "`agent_id`")
    private Integer agentId;

    /**
     * 推荐人编号，就是其他代理人id
     */
    @Column(name = "`recommender_id`")
    private Integer recommenderId;

    /**
     * 认证状态   1未认证、2已认证、3已拒绝
     */
    @Column(name = "`auth_status`")
    private Integer authStatus;

    /**
     * 签约状态   1未签约、2已签约
     */
    @Column(name = "`sign_status`")
    private Integer signStatus;

    /**
     * 数据状态   1有效   -1无效
     */
    @Column(name = "`data_status`")
    private Integer dataStatus;
    
    
    /**
     * 合同编号
     */
    @Column(name = "`contract_number`")
    private String contractNumber;

    
    /**
     * 签约时间
     */
    @Column(name = "`sign_time`")
    private LocalDateTime signTime;
    
    
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
        sb.append(", customerId=").append(customerId);
        sb.append(", agentId=").append(agentId);
        sb.append(", recommenderId=").append(recommenderId);
        sb.append(", authStatus=").append(authStatus);
        sb.append(", signStatus=").append(signStatus);
        sb.append(", dataStatus=").append(dataStatus);
        sb.append(", contractNumber=").append(contractNumber);
        sb.append(", signTime=").append(signTime);
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
        AgentCustomer other = (AgentCustomer) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCustomerId() == null ? other.getCustomerId() == null : this.getCustomerId().equals(other.getCustomerId()))
            && (this.getAgentId() == null ? other.getAgentId() == null : this.getAgentId().equals(other.getAgentId()))
            && (this.getRecommenderId() == null ? other.getRecommenderId() == null : this.getRecommenderId().equals(other.getRecommenderId()))
            && (this.getAuthStatus() == null ? other.getAuthStatus() == null : this.getAuthStatus().equals(other.getAuthStatus()))
            && (this.getSignStatus() == null ? other.getSignStatus() == null : this.getSignStatus().equals(other.getSignStatus()))
            && (this.getDataStatus() == null ? other.getDataStatus() == null : this.getDataStatus().equals(other.getDataStatus()))
            && (this.getContractNumber() == null ? other.getContractNumber() == null : this.getContractNumber().equals(other.getContractNumber()))
            && (this.getSignTime() == null ? other.getSignTime() == null : this.getSignTime().equals(other.getSignTime()))   
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
        result = prime * result + ((getCustomerId() == null) ? 0 : getCustomerId().hashCode());
        result = prime * result + ((getAgentId() == null) ? 0 : getAgentId().hashCode());
        result = prime * result + ((getRecommenderId() == null) ? 0 : getRecommenderId().hashCode());
        result = prime * result + ((getAuthStatus() == null) ? 0 : getAuthStatus().hashCode());
        result = prime * result + ((getSignStatus() == null) ? 0 : getSignStatus().hashCode());
        result = prime * result + ((getDataStatus() == null) ? 0 : getDataStatus().hashCode());
        result = prime * result + ((getContractNumber() == null) ? 0 : getContractNumber().hashCode());
        result = prime * result + ((getSignTime() == null) ? 0 : getSignTime().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreateoper() == null) ? 0 : getCreateoper().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getModifyoper() == null) ? 0 : getModifyoper().hashCode());
        result = prime * result + ((getModifytime() == null) ? 0 : getModifytime().hashCode());
        return result;
    }
}