package cn.rebornauto.platform.business.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "`t_customer_info`")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerInfo implements Serializable {
    /**
     * 客户编号   根据8开头规则生成自增4位数字
     */
    @Id
    @Column(name = "`id`")
    @GeneratedValue(generator = "JDBC")
    private String id;

    
    /**
     * 客户名称
     */
    @Column(name = "`customer_name`")
    private String customerName;
    
    /**
     * 客户简称
     */
    @Column(name = "`customer_nickname`")
    private String customerNickname;

    /**
     * 服务费率
     */
    @Column(name = "`service_rate`")
    private BigDecimal serviceRate;

    /**
     * 发票抬头
     */
    @Column(name = "`invoice_title`")
    private String invoiceTitle;

    /**
     * 发票内容
     */
    @Column(name = "`invoice_content`")
    private String invoiceContent;

    /**
     * 纳税人类型   1一般纳税人   2小规模纳税人
     */
    @Column(name = "`taxpayer_type`")
    private Integer taxpayerType;

    /**
     * 纳税人识别号
     */
    @Column(name = "`taxpayer_number`")
    private String taxpayerNumber;

    /**
     * 单位注册地址
     */
    @Column(name = "`customer_reg_address`")
    private String customerRegAddress;

    /**
     * 开户名
     */
    @Column(name = "`customer_open_name`")
    private String customerOpenName;

    /**
     * 开户行名称
     */
    @Column(name = "`customer_open_bank_name`")
    private String customerOpenBankName;

    /**
     * 开户行代码
     */
    @Column(name = "`customer_open_bank_code`")
    private String customerOpenBankCode;

    /**
     * 银行卡号
     */
    @Column(name = "`customer_open_bank_no`")
    private String customerOpenBankNo;

    /**
     * 入驻时间
     */
    @Column(name = "`entertime`")
    private LocalDateTime entertime;

    /**
     * 收件人
     */
    @Column(name = "`receiver`")
    private String receiver;

    /**
     * 联系电话
     */
    @Column(name = "`customer_mobile`")
    private String customerMobile;

    /**
     * 地址
     */
    @Column(name = "`customer_address`")
    private String customerAddress;

    /**
     * 联系人
     */
    @Column(name = "`customer_link_man`")
    private String customerLinkMan;

    /**
     * 联系邮箱
     */
    @Column(name = "`customer_link_email`")
    private String customerLinkEmail;

    /**
     * 二维码图片显示相对路径
     */
    @Column(name = "`qr_code_img_pic_url`")
    private String qrCodeImgPicUrl;

    /**
     * 二维码扫描接口url，含后端加密信息，根据每个客户信息唯一生成
     */
    @Column(name = "`qr_code_img_post_url`")
    private String qrCodeImgPostUrl;

    /**
     * 二维码logo图片显示相对路径
     */
    @Column(name = "`qr_code_logo_img_pic_url`")
    private String qrCodeLogoImgPicUrl;

    /**
     * 代征主体 id
     */
    @Column(name = "`dai_zheng_id`")
    private Integer daiZhengId;

    /**
     * 数据状态   1有效   -1无效
     */
    @Column(name = "`data_status`")
    private Integer dataStatus;
    
    
    /**
     * 初始化代理人客户签约是否认证：1未认证 、2 已认证
     */
    @Column(name = "`is_auth`")
    private Integer isAuth;

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
    
    /**
     * 电话号码
     */
    @Column(name = "`tel`")
    private String tel;
    
    /**
     * 支行代码
     */
    @Column(name = "`customer_sub_open_bank_name`")
    private String customerSubOpenBankName;
    
    /**
     * 发票样张
     */
    @Column(name = "`invoice_demo_url`")
    private String invoiceDemoUrl;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", customerName=").append(customerName);
        sb.append(", serviceRate=").append(serviceRate);
        sb.append(", invoiceTitle=").append(invoiceTitle);
        sb.append(", invoiceContent=").append(invoiceContent);
        sb.append(", taxpayerType=").append(taxpayerType);
        sb.append(", taxpayerNumber=").append(taxpayerNumber);
        sb.append(", customerRegAddress=").append(customerRegAddress);
        sb.append(", customerOpenName=").append(customerOpenName);
        sb.append(", customerOpenBankName=").append(customerOpenBankName);
        sb.append(", customerOpenBankCode=").append(customerOpenBankCode);
        sb.append(", customerOpenBankNo=").append(customerOpenBankNo);
        sb.append(", entertime=").append(entertime);
        sb.append(", receiver=").append(receiver);
        sb.append(", customerMobile=").append(customerMobile);
        sb.append(", customerAddress=").append(customerAddress);
        sb.append(", customerLinkMan=").append(customerLinkMan);
        sb.append(", customerLinkEmail=").append(customerLinkEmail);
        sb.append(", qrCodeImgPicUrl=").append(qrCodeImgPicUrl);
        sb.append(", qrCodeImgPostUrl=").append(qrCodeImgPostUrl);
        sb.append(", qrCodeLogoImgPicUrl=").append(qrCodeLogoImgPicUrl);
        sb.append(", daiZhengId=").append(daiZhengId);
        sb.append(", dataStatus=").append(dataStatus);
        sb.append(", isAuth=").append(isAuth);

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
        CustomerInfo other = (CustomerInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCustomerName() == null ? other.getCustomerName() == null : this.getCustomerName().equals(other.getCustomerName()))
            && (this.getServiceRate() == null ? other.getServiceRate() == null : this.getServiceRate().equals(other.getServiceRate()))
            && (this.getInvoiceTitle() == null ? other.getInvoiceTitle() == null : this.getInvoiceTitle().equals(other.getInvoiceTitle()))
            && (this.getInvoiceContent() == null ? other.getInvoiceContent() == null : this.getInvoiceContent().equals(other.getInvoiceContent()))
            && (this.getTaxpayerType() == null ? other.getTaxpayerType() == null : this.getTaxpayerType().equals(other.getTaxpayerType()))
            && (this.getTaxpayerNumber() == null ? other.getTaxpayerNumber() == null : this.getTaxpayerNumber().equals(other.getTaxpayerNumber()))
            && (this.getCustomerRegAddress() == null ? other.getCustomerRegAddress() == null : this.getCustomerRegAddress().equals(other.getCustomerRegAddress()))
            && (this.getCustomerOpenName() == null ? other.getCustomerOpenName() == null : this.getCustomerOpenName().equals(other.getCustomerOpenName()))
            && (this.getCustomerOpenBankName() == null ? other.getCustomerOpenBankName() == null : this.getCustomerOpenBankName().equals(other.getCustomerOpenBankName()))
            && (this.getCustomerOpenBankCode() == null ? other.getCustomerOpenBankCode() == null : this.getCustomerOpenBankCode().equals(other.getCustomerOpenBankCode()))
            && (this.getCustomerOpenBankNo() == null ? other.getCustomerOpenBankNo() == null : this.getCustomerOpenBankNo().equals(other.getCustomerOpenBankNo()))
            && (this.getEntertime() == null ? other.getEntertime() == null : this.getEntertime().equals(other.getEntertime()))
            && (this.getReceiver() == null ? other.getReceiver() == null : this.getReceiver().equals(other.getReceiver()))
            && (this.getCustomerMobile() == null ? other.getCustomerMobile() == null : this.getCustomerMobile().equals(other.getCustomerMobile()))
            && (this.getCustomerAddress() == null ? other.getCustomerAddress() == null : this.getCustomerAddress().equals(other.getCustomerAddress()))
            && (this.getCustomerLinkMan() == null ? other.getCustomerLinkMan() == null : this.getCustomerLinkMan().equals(other.getCustomerLinkMan()))
            && (this.getCustomerLinkEmail() == null ? other.getCustomerLinkEmail() == null : this.getCustomerLinkEmail().equals(other.getCustomerLinkEmail()))
            && (this.getQrCodeImgPicUrl() == null ? other.getQrCodeImgPicUrl() == null : this.getQrCodeImgPicUrl().equals(other.getQrCodeImgPicUrl()))
            && (this.getQrCodeImgPostUrl() == null ? other.getQrCodeImgPostUrl() == null : this.getQrCodeImgPostUrl().equals(other.getQrCodeImgPostUrl()))
            && (this.getQrCodeLogoImgPicUrl() == null ? other.getQrCodeLogoImgPicUrl() == null : this.getQrCodeLogoImgPicUrl().equals(other.getQrCodeLogoImgPicUrl()))
            && (this.getDaiZhengId() == null ? other.getDaiZhengId() == null : this.getDaiZhengId().equals(other.getDaiZhengId()))
            && (this.getDataStatus() == null ? other.getDataStatus() == null : this.getDataStatus().equals(other.getDataStatus()))
            && (this.getIsAuth() == null ? other.getIsAuth() == null : this.getIsAuth().equals(other.getIsAuth()))  
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
        result = prime * result + ((getCustomerName() == null) ? 0 : getCustomerName().hashCode());
        result = prime * result + ((getServiceRate() == null) ? 0 : getServiceRate().hashCode());
        result = prime * result + ((getInvoiceTitle() == null) ? 0 : getInvoiceTitle().hashCode());
        result = prime * result + ((getInvoiceContent() == null) ? 0 : getInvoiceContent().hashCode());
        result = prime * result + ((getTaxpayerType() == null) ? 0 : getTaxpayerType().hashCode());
        result = prime * result + ((getTaxpayerNumber() == null) ? 0 : getTaxpayerNumber().hashCode());
        result = prime * result + ((getCustomerRegAddress() == null) ? 0 : getCustomerRegAddress().hashCode());
        result = prime * result + ((getCustomerOpenName() == null) ? 0 : getCustomerOpenName().hashCode());
        result = prime * result + ((getCustomerOpenBankName() == null) ? 0 : getCustomerOpenBankName().hashCode());
        result = prime * result + ((getCustomerOpenBankCode() == null) ? 0 : getCustomerOpenBankCode().hashCode());
        result = prime * result + ((getCustomerOpenBankNo() == null) ? 0 : getCustomerOpenBankNo().hashCode());
        result = prime * result + ((getEntertime() == null) ? 0 : getEntertime().hashCode());
        result = prime * result + ((getReceiver() == null) ? 0 : getReceiver().hashCode());
        result = prime * result + ((getCustomerMobile() == null) ? 0 : getCustomerMobile().hashCode());
        result = prime * result + ((getCustomerAddress() == null) ? 0 : getCustomerAddress().hashCode());
        result = prime * result + ((getCustomerLinkMan() == null) ? 0 : getCustomerLinkMan().hashCode());
        result = prime * result + ((getCustomerLinkEmail() == null) ? 0 : getCustomerLinkEmail().hashCode());
        result = prime * result + ((getQrCodeImgPicUrl() == null) ? 0 : getQrCodeImgPicUrl().hashCode());
        result = prime * result + ((getQrCodeImgPostUrl() == null) ? 0 : getQrCodeImgPostUrl().hashCode());
        result = prime * result + ((getQrCodeLogoImgPicUrl() == null) ? 0 : getQrCodeLogoImgPicUrl().hashCode());
        result = prime * result + ((getDaiZhengId() == null) ? 0 : getDaiZhengId().hashCode());
        result = prime * result + ((getDataStatus() == null) ? 0 : getDataStatus().hashCode());
        result = prime * result + ((getIsAuth() == null) ? 0 : getIsAuth().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreateoper() == null) ? 0 : getCreateoper().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getModifyoper() == null) ? 0 : getModifyoper().hashCode());
        result = prime * result + ((getModifytime() == null) ? 0 : getModifytime().hashCode());
        return result;
    }
}