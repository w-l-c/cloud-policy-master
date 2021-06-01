package cn.rebornauto.platform.business.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "`t_bankcard_verify4_allinpay`")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankcardVerify4Allinpay implements Serializable {
    /**
     * 主键
     */
    @Id
    @Column(name = "`id`")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 银行名
     */
    @Column(name = "`bankname`")
    private String bankname;

    /**
     * 通联银行code
     */
    @Column(name = "`bankcode`")
    private String bankcode;

    /**
     * 银行卡
     */
    @Column(name = "`bankcard`")
    private String bankcard;

    /**
     * 身份证
     */
    @Column(name = "`idcard`")
    private String idcard;

    /**
     * 姓名
     */
    @Column(name = "`realname`")
    private String realname;

    /**
     * 手机号
     */
    @Column(name = "`mobile`")
    private String mobile;

    /**
     * 验证状态：200银行卡号校验一致,其他看提示
     */
    @Column(name = "`verifystatus`")
    private Integer verifystatus;

    /**
     * 验证提示
     */
    @Column(name = "`verifymsg`")
    private String verifymsg;

    /**
     * 备注
     */
    @Column(name = "`remark`")
    private String remark;

    /**
     * 创建时间
     */
    @Column(name = "`gmt_create`")
    private LocalDateTime gmtCreate;

    /**
     * 更新时间
     */
    @Column(name = "`gmt_modified`")
    private LocalDateTime gmtModified;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", bankname=").append(bankname);
        sb.append(", bankcode=").append(bankcode);
        sb.append(", bankcard=").append(bankcard);
        sb.append(", idcard=").append(idcard);
        sb.append(", realname=").append(realname);
        sb.append(", mobile=").append(mobile);
        sb.append(", verifystatus=").append(verifystatus);
        sb.append(", verifymsg=").append(verifymsg);
        sb.append(", remark=").append(remark);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtModified=").append(gmtModified);
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
        BankcardVerify4Allinpay other = (BankcardVerify4Allinpay) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getBankname() == null ? other.getBankname() == null : this.getBankname().equals(other.getBankname()))
            && (this.getBankcode() == null ? other.getBankcode() == null : this.getBankcode().equals(other.getBankcode()))
            && (this.getBankcard() == null ? other.getBankcard() == null : this.getBankcard().equals(other.getBankcard()))
            && (this.getIdcard() == null ? other.getIdcard() == null : this.getIdcard().equals(other.getIdcard()))
            && (this.getRealname() == null ? other.getRealname() == null : this.getRealname().equals(other.getRealname()))
            && (this.getMobile() == null ? other.getMobile() == null : this.getMobile().equals(other.getMobile()))
            && (this.getVerifystatus() == null ? other.getVerifystatus() == null : this.getVerifystatus().equals(other.getVerifystatus()))
            && (this.getVerifymsg() == null ? other.getVerifymsg() == null : this.getVerifymsg().equals(other.getVerifymsg()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getBankname() == null) ? 0 : getBankname().hashCode());
        result = prime * result + ((getBankcode() == null) ? 0 : getBankcode().hashCode());
        result = prime * result + ((getBankcard() == null) ? 0 : getBankcard().hashCode());
        result = prime * result + ((getIdcard() == null) ? 0 : getIdcard().hashCode());
        result = prime * result + ((getRealname() == null) ? 0 : getRealname().hashCode());
        result = prime * result + ((getMobile() == null) ? 0 : getMobile().hashCode());
        result = prime * result + ((getVerifystatus() == null) ? 0 : getVerifystatus().hashCode());
        result = prime * result + ((getVerifymsg() == null) ? 0 : getVerifymsg().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
        result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
        return result;
    }
}