package cn.rebornauto.platform.business.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "`t_dai_zheng_info`")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DaiZhengInfo implements Serializable {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 代征主体名称
     */
    @Column(name = "`dai_zheng_name`")
    private String daiZhengName;

    /**
     * 公司名称
     */
    @Column(name = "`dai_zheng_company_name`")
    private String daiZhengCompanyName;
    
    
    /**
     * 公司简称
     */
    @Column(name = "`dai_zheng_simple_company_name`")
    private String daiZhengSimpleCompanyName;
    
    /**
     * 城市code
     */
    @Column(name = "`city_adcode`")
    private String cityAdcode;
    
    /**
     * 省code
     */
    @Column(name = "`province_adcode`")
    private String provinceAdcode;

    /**
     * 公司地址
     */
    @Column(name = "`dai_zheng_address`")
    private String daiZhengAddress;

    /**
     * logo
     */
    @Column(name = "`logo`")
    private String logo;

    /**
     * 联系人
     */
    @Column(name = "`dai_zheng_link_man`")
    private String daiZhengLinkMan;

    /**
     * 联系电话
     */
    @Column(name = "`dai_zheng_link_mobile`")
    private String daiZhengLinkMobile;

    /**
     * 联系邮箱
     */
    @Column(name = "`dai_zheng_link_email`")
    private String daiZhengLinkEmail;

    /**
     * 个税
     */
    @Column(name = "`personal_tax`")
    private BigDecimal personalTax;

    /**
     * 增值税
     */
    @Column(name = "`value_added_tax`")
    private BigDecimal valueAddedTax;

    /**
     * 附加税
     */
    @Column(name = "`extra_tax`")
    private BigDecimal extraTax;

    /**
     * 开户行
     */
    @Column(name = "`dai_zheng_open_bank`")
    private String daiZhengOpenBank;

    /**
     * 账户名称
     */
    @Column(name = "`dai_zheng_open_name`")
    private String daiZhengOpenName;

    /**
     * 银行卡号
     */
    @Column(name = "`dai_zheng_open_bank_no`")
    private String daiZhengOpenBankNo;

    /**
     * 累计充值金额（充值进来的费用总和）
     */
    @Column(name = "`recharge_amount`")
    private BigDecimal rechargeAmount;

    /**
     * 付款总额
     */
    @Column(name = "`pay_amount`")
    private BigDecimal payAmount;

    /**
     * 账户余额
     */
    @Column(name = "`left_amount`")
    private BigDecimal leftAmount;
    
    /**
     * 企业印章名称
     */
    @Column(name = "`image_name`")
    private String imageName;
    
    /**
     * 企业印章图片
     */
    @Column(name = "`seal_img_pic_url`")
    private String sealImgPicUrl;

    /**
     * 签章账号
     */
    @Column(name = "`account`")
    private String account;

    /**
     * 工商注册号
     */
    @Column(name = "`reg_code`")
    private String regCode;

    /**
     * 组织机构代码
     */
    @Column(name = "`org_code`")
    private String orgCode;

    /**
     * 税务登记证号
     */
    @Column(name = "`tax_code`")
    private String taxCode;

    /**
     * 法定代表人姓名
     */
    @Column(name = "`legal_person`")
    private String legalPerson;

    /**
     * 法定代表人证件类型 0-居民身份证 1-护照 B-港澳居民往来内地通行证 C-台湾居民来往大陆通行证  E-户口簿  F-临时居民身份证
     */
    @Column(name = "`legal_person_identity_type`")
    private String legalPersonIdentityType;

    /**
     * 法定代表人证件号 
     */
    @Column(name = "`legal_person_identity`")
    private String legalPersonIdentity;

    /**
     * 用户类型 1个人，2企业
     */
    @Column(name = "`user_type`")
    private String userType;

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
        sb.append(", daiZhengName=").append(daiZhengName);
        sb.append(", daiZhengCompanyName=").append(daiZhengCompanyName);
        sb.append(", daiZhengSimpleCompanyName=").append(daiZhengSimpleCompanyName);
        sb.append(", cityAdcode=").append(cityAdcode);
        sb.append(", provinceAdcode=").append(provinceAdcode);
        sb.append(", daiZhengAddress=").append(daiZhengAddress);
        sb.append(", logo=").append(logo);
        sb.append(", daiZhengLinkMan=").append(daiZhengLinkMan);
        sb.append(", daiZhengLinkMobile=").append(daiZhengLinkMobile);
        sb.append(", daiZhengLinkEmail=").append(daiZhengLinkEmail);
        sb.append(", personalTax=").append(personalTax);
        sb.append(", valueAddedTax=").append(valueAddedTax);
        sb.append(", extraTax=").append(extraTax);
        sb.append(", daiZhengOpenBank=").append(daiZhengOpenBank);
        sb.append(", daiZhengOpenName=").append(daiZhengOpenName);
        sb.append(", daiZhengOpenBankNo=").append(daiZhengOpenBankNo);
        sb.append(", rechargeAmount=").append(rechargeAmount);
        sb.append(", payAmount=").append(payAmount);
        sb.append(", leftAmount=").append(leftAmount);
        sb.append(", imageName=").append(imageName);
        sb.append(", sealImgPicUrl=").append(sealImgPicUrl);
        sb.append(", account=").append(account);
        sb.append(", regCode=").append(regCode);
        sb.append(", orgCode=").append(orgCode);
        sb.append(", taxCode=").append(taxCode);
        sb.append(", legalPerson=").append(legalPerson);
        sb.append(", legalPersonIdentityType=").append(legalPersonIdentityType);
        sb.append(", legalPersonIdentity=").append(legalPersonIdentity);
        sb.append(", userType=").append(userType);
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
        DaiZhengInfo other = (DaiZhengInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getDaiZhengName() == null ? other.getDaiZhengName() == null : this.getDaiZhengName().equals(other.getDaiZhengName()))
            && (this.getDaiZhengCompanyName() == null ? other.getDaiZhengCompanyName() == null : this.getDaiZhengCompanyName().equals(other.getDaiZhengCompanyName()))        
            && (this.getDaiZhengSimpleCompanyName() == null ? other.getDaiZhengSimpleCompanyName() == null : this.getDaiZhengSimpleCompanyName().equals(other.getDaiZhengSimpleCompanyName()))
            && (this.getCityAdcode() == null ? other.getCityAdcode() == null : this.getCityAdcode().equals(other.getCityAdcode()))
            && (this.getProvinceAdcode() == null ? other.getProvinceAdcode() == null : this.getProvinceAdcode().equals(other.getProvinceAdcode()))
            && (this.getDaiZhengAddress() == null ? other.getDaiZhengAddress() == null : this.getDaiZhengAddress().equals(other.getDaiZhengAddress()))
            && (this.getLogo() == null ? other.getLogo() == null : this.getLogo().equals(other.getLogo()))
            && (this.getDaiZhengLinkMan() == null ? other.getDaiZhengLinkMan() == null : this.getDaiZhengLinkMan().equals(other.getDaiZhengLinkMan()))
            && (this.getDaiZhengLinkMobile() == null ? other.getDaiZhengLinkMobile() == null : this.getDaiZhengLinkMobile().equals(other.getDaiZhengLinkMobile()))
            && (this.getDaiZhengLinkEmail() == null ? other.getDaiZhengLinkEmail() == null : this.getDaiZhengLinkEmail().equals(other.getDaiZhengLinkEmail()))
            && (this.getPersonalTax() == null ? other.getPersonalTax() == null : this.getPersonalTax().equals(other.getPersonalTax()))
            && (this.getValueAddedTax() == null ? other.getValueAddedTax() == null : this.getValueAddedTax().equals(other.getValueAddedTax()))
            && (this.getExtraTax() == null ? other.getExtraTax() == null : this.getExtraTax().equals(other.getExtraTax()))
            && (this.getDaiZhengOpenBank() == null ? other.getDaiZhengOpenBank() == null : this.getDaiZhengOpenBank().equals(other.getDaiZhengOpenBank()))
            && (this.getDaiZhengOpenName() == null ? other.getDaiZhengOpenName() == null : this.getDaiZhengOpenName().equals(other.getDaiZhengOpenName()))
            && (this.getDaiZhengOpenBankNo() == null ? other.getDaiZhengOpenBankNo() == null : this.getDaiZhengOpenBankNo().equals(other.getDaiZhengOpenBankNo()))
            && (this.getRechargeAmount() == null ? other.getRechargeAmount() == null : this.getRechargeAmount().equals(other.getRechargeAmount()))
            && (this.getPayAmount() == null ? other.getPayAmount() == null : this.getPayAmount().equals(other.getPayAmount()))
            && (this.getLeftAmount() == null ? other.getLeftAmount() == null : this.getLeftAmount().equals(other.getLeftAmount()))
            && (this.getImageName() == null ? other.getImageName() == null : this.getImageName().equals(other.getImageName()))
            && (this.getSealImgPicUrl() == null ? other.getSealImgPicUrl() == null : this.getSealImgPicUrl().equals(other.getSealImgPicUrl()))
            && (this.getAccount() == null ? other.getAccount() == null : this.getAccount().equals(other.getAccount()))
            && (this.getRegCode() == null ? other.getRegCode() == null : this.getRegCode().equals(other.getRegCode()))
            && (this.getOrgCode() == null ? other.getOrgCode() == null : this.getOrgCode().equals(other.getOrgCode()))
            && (this.getTaxCode() == null ? other.getTaxCode() == null : this.getTaxCode().equals(other.getTaxCode()))
            && (this.getLegalPerson() == null ? other.getLegalPerson() == null : this.getLegalPerson().equals(other.getLegalPerson()))
            && (this.getLegalPersonIdentityType() == null ? other.getLegalPersonIdentityType() == null : this.getLegalPersonIdentityType().equals(other.getLegalPersonIdentityType()))
            && (this.getLegalPersonIdentity() == null ? other.getLegalPersonIdentity() == null : this.getLegalPersonIdentity().equals(other.getLegalPersonIdentity()))
            && (this.getUserType() == null ? other.getUserType() == null : this.getUserType().equals(other.getUserType()))
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
        result = prime * result + ((getDaiZhengName() == null) ? 0 : getDaiZhengName().hashCode());
        result = prime * result + ((getDaiZhengCompanyName() == null) ? 0 : getDaiZhengCompanyName().hashCode());
        result = prime * result + ((getDaiZhengSimpleCompanyName() == null) ? 0 : getDaiZhengSimpleCompanyName().hashCode());
        result = prime * result + ((getCityAdcode() == null) ? 0 : getCityAdcode().hashCode());
        result = prime * result + ((getProvinceAdcode() == null) ? 0 : getProvinceAdcode().hashCode());
        result = prime * result + ((getDaiZhengAddress() == null) ? 0 : getDaiZhengAddress().hashCode());
        result = prime * result + ((getLogo() == null) ? 0 : getLogo().hashCode());
        result = prime * result + ((getDaiZhengLinkMan() == null) ? 0 : getDaiZhengLinkMan().hashCode());
        result = prime * result + ((getDaiZhengLinkMobile() == null) ? 0 : getDaiZhengLinkMobile().hashCode());
        result = prime * result + ((getDaiZhengLinkEmail() == null) ? 0 : getDaiZhengLinkEmail().hashCode());
        result = prime * result + ((getPersonalTax() == null) ? 0 : getPersonalTax().hashCode());
        result = prime * result + ((getValueAddedTax() == null) ? 0 : getValueAddedTax().hashCode());
        result = prime * result + ((getExtraTax() == null) ? 0 : getExtraTax().hashCode());
        result = prime * result + ((getDaiZhengOpenBank() == null) ? 0 : getDaiZhengOpenBank().hashCode());
        result = prime * result + ((getDaiZhengOpenName() == null) ? 0 : getDaiZhengOpenName().hashCode());
        result = prime * result + ((getDaiZhengOpenBankNo() == null) ? 0 : getDaiZhengOpenBankNo().hashCode());
        result = prime * result + ((getRechargeAmount() == null) ? 0 : getRechargeAmount().hashCode());
        result = prime * result + ((getPayAmount() == null) ? 0 : getPayAmount().hashCode());
        result = prime * result + ((getLeftAmount() == null) ? 0 : getLeftAmount().hashCode());
        result = prime * result + ((getImageName() == null) ? 0 : getImageName().hashCode());
        result = prime * result + ((getSealImgPicUrl() == null) ? 0 : getSealImgPicUrl().hashCode());
        result = prime * result + ((getAccount() == null) ? 0 : getAccount().hashCode());
        result = prime * result + ((getRegCode() == null) ? 0 : getRegCode().hashCode());
        result = prime * result + ((getOrgCode() == null) ? 0 : getOrgCode().hashCode());
        result = prime * result + ((getTaxCode() == null) ? 0 : getTaxCode().hashCode());
        result = prime * result + ((getLegalPerson() == null) ? 0 : getLegalPerson().hashCode());
        result = prime * result + ((getLegalPersonIdentityType() == null) ? 0 : getLegalPersonIdentityType().hashCode());
        result = prime * result + ((getLegalPersonIdentity() == null) ? 0 : getLegalPersonIdentity().hashCode());
        result = prime * result + ((getUserType() == null) ? 0 : getUserType().hashCode());
        result = prime * result + ((getDataStatus() == null) ? 0 : getDataStatus().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreateoper() == null) ? 0 : getCreateoper().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getModifyoper() == null) ? 0 : getModifyoper().hashCode());
        result = prime * result + ((getModifytime() == null) ? 0 : getModifytime().hashCode());
        return result;
    }
}