package cn.rebornauto.platform.business.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "`t_contract_pdf_his`")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractPdfHis implements Serializable {
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
     * 代理人id
     */
    @Column(name = "`dai_zheng_id`")
    private Integer daiZhengId;
    
    
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
     * 共享经济合作伙伴协议合同文件编号
     */
    @Column(name = "`gxjjhzhbxy_id`")
    private String gxjjhzhbxyId;
    
    /**
     * 手动签署页面的url
     */
    @Column(name = "`manual_signing_url`")
    private String manualSigningUrl;
    
    
    /**
     * 手动签署页面的url存储日期
     */
    @Column(name = "`manual_signing_time`")
    private LocalDateTime manualSigningTime;
    
    /**
     *共享经济合作伙伴协议合同
     */
    @Column(name = "`gxjjhzhbxy`")
    private String gxjjhzhbxy;

    /**
     * 合同zip包路径
     */
    @Column(name = "`yb_zip`")
    private String ybZip;
    
    
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
        sb.append(", gxjjhzhbxyId=").append(gxjjhzhbxyId);
        sb.append(", agentId=").append(agentId);
        sb.append(", daiZhengId=").append(daiZhengId);
        sb.append(", contractNumber=").append(contractNumber);
        sb.append(", signTime=").append(signTime);
        sb.append(", manualSigningUrl=").append(manualSigningUrl);
        sb.append(", manualSigningTime=").append(manualSigningTime);
        sb.append(", gxjjhzhbxy=").append(gxjjhzhbxy);
        sb.append(", ybZip=").append(ybZip);
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
        ContractPdfHis other = (ContractPdfHis) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getGxjjhzhbxyId() == null ? other.getGxjjhzhbxyId() == null : this.getGxjjhzhbxyId().equals(other.getGxjjhzhbxyId()))
            && (this.getAgentId() == null ? other.getAgentId() == null : this.getAgentId().equals(other.getAgentId()))
            && (this.getDaiZhengId() == null ? other.getDaiZhengId() == null : this.getDaiZhengId().equals(other.getDaiZhengId()))
            && (this.getContractNumber() == null ? other.getContractNumber() == null : this.getContractNumber().equals(other.getContractNumber()))
            && (this.getSignTime() == null ? other.getSignTime() == null : this.getSignTime().equals(other.getSignTime()))   
            && (this.getManualSigningUrl() == null ? other.getManualSigningUrl() == null : this.getManualSigningUrl().equals(other.getManualSigningUrl()))
            && (this.getManualSigningTime() == null ? other.getManualSigningTime() == null : this.getManualSigningTime().equals(other.getManualSigningTime()))
            && (this.getGxjjhzhbxy() == null ? other.getGxjjhzhbxy() == null : this.getGxjjhzhbxy().equals(other.getGxjjhzhbxy()))
            && (this.getYbZip() == null ? other.getYbZip() == null : this.getYbZip().equals(other.getYbZip()))
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
        result = prime * result + ((getGxjjhzhbxyId() == null) ? 0 : getGxjjhzhbxyId().hashCode());
        result = prime * result + ((getAgentId() == null) ? 0 : getAgentId().hashCode());
        result = prime * result + ((getDaiZhengId() == null) ? 0 : getDaiZhengId().hashCode());
        result = prime * result + ((getContractNumber() == null) ? 0 : getContractNumber().hashCode());
        result = prime * result + ((getSignTime() == null) ? 0 : getSignTime().hashCode());
        result = prime * result + ((getManualSigningUrl() == null) ? 0 : getManualSigningUrl().hashCode());
        result = prime * result + ((getManualSigningTime() == null) ? 0 : getManualSigningTime().hashCode());
        result = prime * result + ((getGxjjhzhbxy() == null) ? 0 : getGxjjhzhbxy().hashCode());
        result = prime * result + ((getYbZip() == null) ? 0 : getYbZip().hashCode());
        result = prime * result + ((getDataStatus() == null) ? 0 : getDataStatus().hashCode());
        result = prime * result + ((getCreateoper() == null) ? 0 : getCreateoper().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getModifyoper() == null) ? 0 : getModifyoper().hashCode());
        result = prime * result + ((getModifytime() == null) ? 0 : getModifytime().hashCode());
        return result;
    }
}