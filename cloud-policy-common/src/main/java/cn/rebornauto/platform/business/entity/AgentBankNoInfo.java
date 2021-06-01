package cn.rebornauto.platform.business.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "`t_agent_bank_no_info`")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgentBankNoInfo implements Serializable {
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
     * 代理人银行卡图片
     */
  /*  @Column(name = "`agent_bankcard_img_pic_url`")
    private String agentBankcardImgPicUrl;*/

    /**
     * 代理人银行卡号
     */
    @Column(name = "`agent_open_bank_no`")
    private String agentOpenBankNo;

    /**
     * 代理人开户行名称
     */
    @Column(name = "`agent_open_bank_name`")
    private String agentOpenBankName;

    /**
     * 代理人开户行代码
     */
    @Column(name = "`agent_open_bank_code`")
    private String agentOpenBankCode;

    /**
     * 代理人银行卡绑定手机号
     */
    @Column(name = "`agent_open_bank_mobile`")
    private String agentOpenBankMobile;

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
        sb.append(", agentId=").append(agentId);
      /*  sb.append(", agentBankcardImgPicUrl=").append(agentBankcardImgPicUrl);*/
        sb.append(", agentOpenBankNo=").append(agentOpenBankNo);
        sb.append(", agentOpenBankName=").append(agentOpenBankName);
        sb.append(", agentOpenBankCode=").append(agentOpenBankCode);
        sb.append(", agentOpenBankMobile=").append(agentOpenBankMobile);
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
        AgentBankNoInfo other = (AgentBankNoInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getAgentId() == null ? other.getAgentId() == null : this.getAgentId().equals(other.getAgentId()))
/*            && (this.getAgentBankcardImgPicUrl() == null ? other.getAgentBankcardImgPicUrl() == null : this.getAgentBankcardImgPicUrl().equals(other.getAgentBankcardImgPicUrl()))
*/            && (this.getAgentOpenBankNo() == null ? other.getAgentOpenBankNo() == null : this.getAgentOpenBankNo().equals(other.getAgentOpenBankNo()))
            && (this.getAgentOpenBankName() == null ? other.getAgentOpenBankName() == null : this.getAgentOpenBankName().equals(other.getAgentOpenBankName()))
            && (this.getAgentOpenBankCode() == null ? other.getAgentOpenBankCode() == null : this.getAgentOpenBankCode().equals(other.getAgentOpenBankCode()))
            && (this.getAgentOpenBankMobile() == null ? other.getAgentOpenBankMobile() == null : this.getAgentOpenBankMobile().equals(other.getAgentOpenBankMobile()))
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
        result = prime * result + ((getAgentId() == null) ? 0 : getAgentId().hashCode());
/*        result = prime * result + ((getAgentBankcardImgPicUrl() == null) ? 0 : getAgentBankcardImgPicUrl().hashCode());
*/        result = prime * result + ((getAgentOpenBankNo() == null) ? 0 : getAgentOpenBankNo().hashCode());
        result = prime * result + ((getAgentOpenBankName() == null) ? 0 : getAgentOpenBankName().hashCode());
        result = prime * result + ((getAgentOpenBankCode() == null) ? 0 : getAgentOpenBankCode().hashCode());
        result = prime * result + ((getAgentOpenBankMobile() == null) ? 0 : getAgentOpenBankMobile().hashCode());
        result = prime * result + ((getDataStatus() == null) ? 0 : getDataStatus().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreateoper() == null) ? 0 : getCreateoper().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getModifyoper() == null) ? 0 : getModifyoper().hashCode());
        result = prime * result + ((getModifytime() == null) ? 0 : getModifytime().hashCode());
        return result;
    }
}