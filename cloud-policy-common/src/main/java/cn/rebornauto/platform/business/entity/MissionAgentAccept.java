package cn.rebornauto.platform.business.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "`t_mission_agent_accept`")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MissionAgentAccept implements Serializable {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 任务编号
     */
    @Column(name = "`mission_no`")
    private String missionNo;

    /**
     * 代理人id
     */
    @Column(name = "`agent_id`")
    private Integer agentId;

    /**
     * 完成时间
     */
    @Column(name = "`completetime`")
    private LocalDateTime completetime;

    /**
     * 劳务报酬
     */
    @Column(name = "`amount`")
    private BigDecimal amount;

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
        sb.append(", missionNo=").append(missionNo);
        sb.append(", agentId=").append(agentId);
        sb.append(", completetime=").append(completetime);
        sb.append(", amount=").append(amount);
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
        MissionAgentAccept other = (MissionAgentAccept) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMissionNo() == null ? other.getMissionNo() == null : this.getMissionNo().equals(other.getMissionNo()))
            && (this.getAgentId() == null ? other.getAgentId() == null : this.getAgentId().equals(other.getAgentId()))
            && (this.getCompletetime() == null ? other.getCompletetime() == null : this.getCompletetime().equals(other.getCompletetime()))
            && (this.getAmount() == null ? other.getAmount() == null : this.getAmount().equals(other.getAmount()))
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
        result = prime * result + ((getMissionNo() == null) ? 0 : getMissionNo().hashCode());
        result = prime * result + ((getAgentId() == null) ? 0 : getAgentId().hashCode());
        result = prime * result + ((getCompletetime() == null) ? 0 : getCompletetime().hashCode());
        result = prime * result + ((getAmount() == null) ? 0 : getAmount().hashCode());
        result = prime * result + ((getDataStatus() == null) ? 0 : getDataStatus().hashCode());
        result = prime * result + ((getCreateoper() == null) ? 0 : getCreateoper().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getModifyoper() == null) ? 0 : getModifyoper().hashCode());
        result = prime * result + ((getModifytime() == null) ? 0 : getModifytime().hashCode());
        return result;
    }
}