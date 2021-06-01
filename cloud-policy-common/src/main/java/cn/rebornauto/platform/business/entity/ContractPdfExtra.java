package cn.rebornauto.platform.business.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "`t_contract_pdf_extra`")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractPdfExtra implements Serializable {
    /**
     * 主键ID
     */
    @Id
    @Column(name = "`id`")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 代理人id
     */
    @Column(name = "`agent_id`")
    private Integer agentId;

    /**
     * 代征主体id
     */
    @Column(name = "`dai_zheng_id`")
    private Integer daiZhengId;

    /**
     * 签约时间
     */
    @Column(name = "`sign_time`")
    private LocalDateTime signTime;

    /**
     * 合同编号
     */
    @Column(name = "`contract_number`")
    private String contractNumber;

    /**
     * 上上签合同编号
     */
    @Column(name = "`contract_id`")
    private String contractId;

    /**
     * 手动签署页面的url,保存7天
     */
    @Column(name = "`manual_signing_url`")
    private String manualSigningUrl;

    /**
     * 手动签署页面的url存储日期
     */
    @Column(name = "`manual_signing_time`")
    private LocalDateTime manualSigningTime;

    /**
     * 合同保存路径
     */
    @Column(name = "`url`")
    private String url;

    /**
     * 合同签署状态(1:未签约 2:已签约 3:签约失败 4::签约待审核 5:签约审核拒绝)
     */
    @Column(name = "`status`")
    private Integer status;

    /**
     * 合同类型 1:共享经济合作伙伴协议 2:厦门思明区税务局委托书
     */
    @Column(name = "`type`")
    private Integer type;

    /**
     * 数据状态   1有效   -1无效
     */
    @Column(name = "`data_status`")
    private Integer dataStatus;

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
        sb.append(", agentId=").append(agentId);
        sb.append(", daiZhengId=").append(daiZhengId);
        sb.append(", signTime=").append(signTime);
        sb.append(", contractNumber=").append(contractNumber);
        sb.append(", contractId=").append(contractId);
        sb.append(", manualSigningUrl=").append(manualSigningUrl);
        sb.append(", manualSigningTime=").append(manualSigningTime);
        sb.append(", url=").append(url);
        sb.append(", status=").append(status);
        sb.append(", type=").append(type);
        sb.append(", dataStatus=").append(dataStatus);
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
        ContractPdfExtra other = (ContractPdfExtra) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getAgentId() == null ? other.getAgentId() == null : this.getAgentId().equals(other.getAgentId()))
            && (this.getDaiZhengId() == null ? other.getDaiZhengId() == null : this.getDaiZhengId().equals(other.getDaiZhengId()))
            && (this.getSignTime() == null ? other.getSignTime() == null : this.getSignTime().equals(other.getSignTime()))
            && (this.getContractNumber() == null ? other.getContractNumber() == null : this.getContractNumber().equals(other.getContractNumber()))
            && (this.getContractId() == null ? other.getContractId() == null : this.getContractId().equals(other.getContractId()))
            && (this.getManualSigningUrl() == null ? other.getManualSigningUrl() == null : this.getManualSigningUrl().equals(other.getManualSigningUrl()))
            && (this.getManualSigningTime() == null ? other.getManualSigningTime() == null : this.getManualSigningTime().equals(other.getManualSigningTime()))
            && (this.getUrl() == null ? other.getUrl() == null : this.getUrl().equals(other.getUrl()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getDataStatus() == null ? other.getDataStatus() == null : this.getDataStatus().equals(other.getDataStatus()))
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
        result = prime * result + ((getAgentId() == null) ? 0 : getAgentId().hashCode());
        result = prime * result + ((getDaiZhengId() == null) ? 0 : getDaiZhengId().hashCode());
        result = prime * result + ((getSignTime() == null) ? 0 : getSignTime().hashCode());
        result = prime * result + ((getContractNumber() == null) ? 0 : getContractNumber().hashCode());
        result = prime * result + ((getContractId() == null) ? 0 : getContractId().hashCode());
        result = prime * result + ((getManualSigningUrl() == null) ? 0 : getManualSigningUrl().hashCode());
        result = prime * result + ((getManualSigningTime() == null) ? 0 : getManualSigningTime().hashCode());
        result = prime * result + ((getUrl() == null) ? 0 : getUrl().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getDataStatus() == null) ? 0 : getDataStatus().hashCode());
        result = prime * result + ((getCreateoper() == null) ? 0 : getCreateoper().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getModifyoper() == null) ? 0 : getModifyoper().hashCode());
        result = prime * result + ((getModifytime() == null) ? 0 : getModifytime().hashCode());
        return result;
    }
}