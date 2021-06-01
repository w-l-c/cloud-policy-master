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
import java.time.LocalDateTime;

@Table(name = "`t_agent_info`")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgentInfo implements Serializable {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 国籍  1中国大陆   2中国香港/澳门  3其他国家或地区
     */
    @Column(name = "`nationality_id`")
    private Integer nationalityId;

    /**
     * 代理人姓名
     */
    @Column(name = "`agent_name`")
    private String agentName;

    /**
     * 代理人openid，用于便于已经在客户中注册过的代理人，信息反显直接提交绑定另外的客户
     */
    @Column(name = "`openid`")
    private String openid;

    /**
     * 证件类型   4身份证
     */
    @Column(name = "`id_type`")
    private Integer idType;

    /**
     * 证件号码
     */
    @Column(name = "`agent_idcardno`")
    private String agentIdcardno;

    /**
     * 正面证件图片
     */
    @Column(name = "`front_idcard_pic_url`")
    private String frontIdcardPicUrl;

    /**
     * 反面证件图片
     */
    @Column(name = "`back_idcard_pic_url`")
    private String backIdcardPicUrl;

    /**
     * 头像图片
     */
    @Column(name = "`header_pic_url`")
    private String headerPicUrl;

    /**
     * 手机号
     */
    @Column(name = "`agent_mobile`")
    private String agentMobile;

    /**
     * 邮箱
     */
    @Column(name = "`agent_email`")
    private String agentEmail;

    /**
     * 省
     */
    @Column(name = "`agent_province`")
    private String agentProvince;

    /**
     * 市
     */
    @Column(name = "`agent_city`")
    private String agentCity;

    /**
     * 地址
     */
    @Column(name = "`agent_address`")
    private String agentAddress;

    /**
     * 代理人二维码图片显示相对路径
     */
    @Column(name = "`agent_qr_code_pic_url`")
    private String agentQrCodePicUrl;

    /**
     * 代理人二维码扫描接口url，含后端加密信息，根据每个代理人信息唯一生成
     */
    @Column(name = "`agent_qr_code_img_post_url`")
    private String agentQrCodeImgPostUrl;

    /**
     * 尚尚签个人证书账号
     */
    @Column(name = "`account`")
    private String account;

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
        sb.append(", nationalityId=").append(nationalityId);
        sb.append(", agentName=").append(agentName);
        sb.append(", openid=").append(openid);
        sb.append(", idType=").append(idType);
        sb.append(", agentIdcardno=").append(agentIdcardno);
        sb.append(", frontIdcardPicUrl=").append(frontIdcardPicUrl);
        sb.append(", backIdcardPicUrl=").append(backIdcardPicUrl);
        sb.append(", headerPicUrl=").append(headerPicUrl);
        sb.append(", agentMobile=").append(agentMobile);
        sb.append(", agentEmail=").append(agentEmail);
        sb.append(", agentProvince=").append(agentProvince);
        sb.append(", agentCity=").append(agentCity);
        sb.append(", agentAddress=").append(agentAddress);
        sb.append(", agentQrCodePicUrl=").append(agentQrCodePicUrl);
        sb.append(", agentQrCodeImgPostUrl=").append(agentQrCodeImgPostUrl);
        sb.append(", account=").append(account);
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
        AgentInfo other = (AgentInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getNationalityId() == null ? other.getNationalityId() == null : this.getNationalityId().equals(other.getNationalityId()))
            && (this.getAgentName() == null ? other.getAgentName() == null : this.getAgentName().equals(other.getAgentName()))
            && (this.getOpenid() == null ? other.getOpenid() == null : this.getOpenid().equals(other.getOpenid()))
            && (this.getIdType() == null ? other.getIdType() == null : this.getIdType().equals(other.getIdType()))
            && (this.getAgentIdcardno() == null ? other.getAgentIdcardno() == null : this.getAgentIdcardno().equals(other.getAgentIdcardno()))
            && (this.getFrontIdcardPicUrl() == null ? other.getFrontIdcardPicUrl() == null : this.getFrontIdcardPicUrl().equals(other.getFrontIdcardPicUrl()))
            && (this.getBackIdcardPicUrl() == null ? other.getBackIdcardPicUrl() == null : this.getBackIdcardPicUrl().equals(other.getBackIdcardPicUrl()))
            && (this.getHeaderPicUrl() == null ? other.getHeaderPicUrl() == null : this.getHeaderPicUrl().equals(other.getHeaderPicUrl()))
            && (this.getAgentMobile() == null ? other.getAgentMobile() == null : this.getAgentMobile().equals(other.getAgentMobile()))
            && (this.getAgentEmail() == null ? other.getAgentEmail() == null : this.getAgentEmail().equals(other.getAgentEmail()))
            && (this.getAgentProvince() == null ? other.getAgentProvince() == null : this.getAgentProvince().equals(other.getAgentProvince()))
            && (this.getAgentCity() == null ? other.getAgentCity() == null : this.getAgentCity().equals(other.getAgentCity()))
            && (this.getAgentAddress() == null ? other.getAgentAddress() == null : this.getAgentAddress().equals(other.getAgentAddress()))
            && (this.getAgentQrCodePicUrl() == null ? other.getAgentQrCodePicUrl() == null : this.getAgentQrCodePicUrl().equals(other.getAgentQrCodePicUrl()))
            && (this.getAgentQrCodeImgPostUrl() == null ? other.getAgentQrCodeImgPostUrl() == null : this.getAgentQrCodeImgPostUrl().equals(other.getAgentQrCodeImgPostUrl()))
            && (this.getAccount() == null ? other.getAccount() == null : this.getAccount().equals(other.getAccount()))
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
        result = prime * result + ((getNationalityId() == null) ? 0 : getNationalityId().hashCode());
        result = prime * result + ((getAgentName() == null) ? 0 : getAgentName().hashCode());
        result = prime * result + ((getOpenid() == null) ? 0 : getOpenid().hashCode());
        result = prime * result + ((getIdType() == null) ? 0 : getIdType().hashCode());
        result = prime * result + ((getAgentIdcardno() == null) ? 0 : getAgentIdcardno().hashCode());
        result = prime * result + ((getFrontIdcardPicUrl() == null) ? 0 : getFrontIdcardPicUrl().hashCode());
        result = prime * result + ((getBackIdcardPicUrl() == null) ? 0 : getBackIdcardPicUrl().hashCode());
        result = prime * result + ((getHeaderPicUrl() == null) ? 0 : getHeaderPicUrl().hashCode());
        result = prime * result + ((getAgentMobile() == null) ? 0 : getAgentMobile().hashCode());
        result = prime * result + ((getAgentEmail() == null) ? 0 : getAgentEmail().hashCode());
        result = prime * result + ((getAgentProvince() == null) ? 0 : getAgentProvince().hashCode());
        result = prime * result + ((getAgentCity() == null) ? 0 : getAgentCity().hashCode());
        result = prime * result + ((getAgentAddress() == null) ? 0 : getAgentAddress().hashCode());
        result = prime * result + ((getAgentQrCodePicUrl() == null) ? 0 : getAgentQrCodePicUrl().hashCode());
        result = prime * result + ((getAgentQrCodeImgPostUrl() == null) ? 0 : getAgentQrCodeImgPostUrl().hashCode());
        result = prime * result + ((getAccount() == null) ? 0 : getAccount().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreateoper() == null) ? 0 : getCreateoper().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getModifyoper() == null) ? 0 : getModifyoper().hashCode());
        result = prime * result + ((getModifytime() == null) ? 0 : getModifytime().hashCode());
        return result;
    }
}