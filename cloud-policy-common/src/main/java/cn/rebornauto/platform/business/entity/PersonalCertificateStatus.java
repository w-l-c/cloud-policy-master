package cn.rebornauto.platform.business.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "`t_personal_certificate_status`")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonalCertificateStatus implements Serializable {
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
     * 需要查询证书申请状态的用户唯一标识
     */
    @Column(name = "`account`")
    private String account;

    /**
     * 任务单号由异步申请数字证书接口返回得到，taskId在24小时内可用于查询
     */
    @Column(name = "`taskId`")
    private String taskid;

    /**
     * 接口处理花费的时间
     */
    @Column(name = "`cost`")
    private String cost;

    /**
     * 错误码, 如果为0表示成功没有错误
     */
    @Column(name = "`errno`")
    private String errno;

    /**
     * 错误描述，结合errno对照附录
     */
    @Column(name = "`errmsg`")
    private String errmsg;

    /**
     * 证书申请状态1：新申请2：申请中3：超时4：申请失败5：成功-1：无效的申请（数据库无此值）0：taskId不存在或已过期
     */
    @Column(name = "`status`")
    private String status;

    /**
     * 状态描述
     */
    @Column(name = "`message`")
    private String message;

    /**
     * 输出数据 （如果有的话，具体内容和每一个具体的接口相关）
     */
    @Column(name = "`data`")
    private String data;

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
        sb.append(", account=").append(account);
        sb.append(", taskid=").append(taskid);
        sb.append(", cost=").append(cost);
        sb.append(", errno=").append(errno);
        sb.append(", errmsg=").append(errmsg);
        sb.append(", status=").append(status);
        sb.append(", message=").append(message);
        sb.append(", data=").append(data);
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
        PersonalCertificateStatus other = (PersonalCertificateStatus) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getAgentId() == null ? other.getAgentId() == null : this.getAgentId().equals(other.getAgentId()))
            && (this.getAccount() == null ? other.getAccount() == null : this.getAccount().equals(other.getAccount()))
            && (this.getTaskid() == null ? other.getTaskid() == null : this.getTaskid().equals(other.getTaskid()))
            && (this.getCost() == null ? other.getCost() == null : this.getCost().equals(other.getCost()))
            && (this.getErrno() == null ? other.getErrno() == null : this.getErrno().equals(other.getErrno()))
            && (this.getErrmsg() == null ? other.getErrmsg() == null : this.getErrmsg().equals(other.getErrmsg()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getMessage() == null ? other.getMessage() == null : this.getMessage().equals(other.getMessage()))
            && (this.getData() == null ? other.getData() == null : this.getData().equals(other.getData()))
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
        result = prime * result + ((getAccount() == null) ? 0 : getAccount().hashCode());
        result = prime * result + ((getTaskid() == null) ? 0 : getTaskid().hashCode());
        result = prime * result + ((getCost() == null) ? 0 : getCost().hashCode());
        result = prime * result + ((getErrno() == null) ? 0 : getErrno().hashCode());
        result = prime * result + ((getErrmsg() == null) ? 0 : getErrmsg().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getMessage() == null) ? 0 : getMessage().hashCode());
        result = prime * result + ((getData() == null) ? 0 : getData().hashCode());
        result = prime * result + ((getCreateoper() == null) ? 0 : getCreateoper().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getModifyoper() == null) ? 0 : getModifyoper().hashCode());
        result = prime * result + ((getModifytime() == null) ? 0 : getModifytime().hashCode());
        return result;
    }
}