package cn.rebornauto.platform.sms.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "`sms_queue`")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsQueue implements Serializable {
    /**
     * 主键id
     */
    @Id
    @Column(name = "`id`")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 手机号
     */
    @Column(name = "`mobile`")
    private String mobile;

    /**
     * 短信内容
     */
    @Column(name = "`content`")
    private String content;

    /**
     * 渠道模版id
     */
    @Column(name = "`template_id`")
    private String templateId;

    /**
     * 状态0:待发送1:已发送2:发送失败
     */
    @Column(name = "`status`")
    private Byte status;

    /**
     * 发送类型
     */
    @Column(name = "`source_type`")
    private Byte sourceType;

    /**
     * 发送IP
     */
    @Column(name = "`source_ip`")
    private String sourceIp;

    /**
     * 创建时间
     */
    @Column(name = "`gmt_create`")
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @Column(name = "`gmt_modified`")
    private LocalDateTime gmtModified;

    /**
     * 发送时间
     */
    @Column(name = "`gmt_send`")
    private LocalDateTime gmtSend;

    /**
     * 返回状态
     */
    @Column(name = "`ret_code`")
    private String retCode;

    /**
     * 返回信息
     */
    @Column(name = "`ret_message`")
    private String retMessage;

    /**
     * 验证码
     */
    @Column(name = "`channel`")
    private String channel;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", mobile=").append(mobile);
        sb.append(", content=").append(content);
        sb.append(", templateId=").append(templateId);
        sb.append(", status=").append(status);
        sb.append(", sourceType=").append(sourceType);
        sb.append(", sourceIp=").append(sourceIp);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", gmtSend=").append(gmtSend);
        sb.append(", retCode=").append(retCode);
        sb.append(", retMessage=").append(retMessage);
        sb.append(", channel=").append(channel);
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
        SmsQueue other = (SmsQueue) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMobile() == null ? other.getMobile() == null : this.getMobile().equals(other.getMobile()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getTemplateId() == null ? other.getTemplateId() == null : this.getTemplateId().equals(other.getTemplateId()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getSourceType() == null ? other.getSourceType() == null : this.getSourceType().equals(other.getSourceType()))
            && (this.getSourceIp() == null ? other.getSourceIp() == null : this.getSourceIp().equals(other.getSourceIp()))
            && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
            && (this.getGmtSend() == null ? other.getGmtSend() == null : this.getGmtSend().equals(other.getGmtSend()))
            && (this.getRetCode() == null ? other.getRetCode() == null : this.getRetCode().equals(other.getRetCode()))
            && (this.getRetMessage() == null ? other.getRetMessage() == null : this.getRetMessage().equals(other.getRetMessage()))
            && (this.getChannel() == null ? other.getChannel() == null : this.getChannel().equals(other.getChannel()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMobile() == null) ? 0 : getMobile().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getTemplateId() == null) ? 0 : getTemplateId().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getSourceType() == null) ? 0 : getSourceType().hashCode());
        result = prime * result + ((getSourceIp() == null) ? 0 : getSourceIp().hashCode());
        result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
        result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
        result = prime * result + ((getGmtSend() == null) ? 0 : getGmtSend().hashCode());
        result = prime * result + ((getRetCode() == null) ? 0 : getRetCode().hashCode());
        result = prime * result + ((getRetMessage() == null) ? 0 : getRetMessage().hashCode());
        result = prime * result + ((getChannel() == null) ? 0 : getChannel().hashCode());
        return result;
    }
}