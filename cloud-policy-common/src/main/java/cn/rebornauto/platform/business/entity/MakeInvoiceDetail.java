package cn.rebornauto.platform.business.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "`t_make_invoice_detail`")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MakeInvoiceDetail implements Serializable {
    /**
     * 发票申请编号
     */
    @Id
    @Column(name = "`id`")
    @GeneratedValue(generator = "JDBC")
    private Integer id;
    
    /**
     * 发票编号
     */
    @Column(name = "`invoice_no`")
    private String invoiceNo;

    /**
     * 已申请发票(张)
     */
    @Column(name = "`apply_num`")
    private Integer applyNum;

    /**
     * 开票时间
     */
    @Column(name = "`invoice_time`")
    private LocalDateTime invoiceTime;

    /**
     * 客户编号
     */
    @Column(name = "`customer_id`")
    private String customerId;

    /**
     * 商户别名
     */
    @Column(name = "`customer_name`")
    private String customerName;

    /**
     * 开票金额(元)
     */
    @Column(name = "`apply_amount`")
    private BigDecimal applyAmount;

    /**
     * 可开票金额(元)
     */
    @Column(name = "`invoice_amount`")
    private BigDecimal invoiceAmount;

    /**
     * 快递公司名
     */
    @Column(name = "`express_company`")
    private String expressCompany;

    /**
     * 快递单号
     */
    @Column(name = "`express_no`")
    private String expressNo;

    /**
     * 发票抬头
     */
    @Column(name = "`invoice_title`")
    private String invoiceTitle;

    /**
     * 发票类型
     */
    @Column(name = "`invoice_type`")
    private Integer invoiceType;
    
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
    @Column(name = "`reg_address`")
    private String regAddress;

    /**
     * 开户行
     */
    @Column(name = "`open_bank`")
    private String openBank;

    /**
     * 银行卡号
     */
    @Column(name = "`open_bank_no`")
    private String openBankNo;

    /**
     * 发票内容
     */
    @Column(name = "`invoice_content`")
    private String invoiceContent;

    /**
     * 详细地址
     */
    @Column(name = "`address`")
    private String address;

    /**
     * 收件人姓名
     */
    @Column(name = "`receiver`")
    private String receiver;

    /**
     * 联系电话
     */
    @Column(name = "`mobile`")
    private String mobile;

    /**
     * 发票图片地址
     */
    @Column(name = "`invoice_pic_url`")
    private String invoicePicUrl;

    /**
     * 开票状态   1待开票   2已开票
     */
    @Column(name = "`make_invoice_status`")
    private Integer makeInvoiceStatus;

    /**
     * 邮寄状态   1待寄送   2已寄送
     */
    @Column(name = "`post_status`")
    private Integer postStatus;

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
     * 申请人
     */
    @Column(name = "`apply_oper`")
    private String applyOper;

    /**
     * 申请时间
     */
    @Column(name = "`apply_time`")
    private LocalDateTime applyTime;

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
     * 本金金额(元)
     */
    @Column(name = "`capital_amount`")
    private BigDecimal capitalAmount;
    
    /**
     * 服务费率
     */
    @Column(name = "`customer_service_rate`")
    private BigDecimal customerServiceRate;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", applyNum=").append(applyNum);
        sb.append(", invoiceTime=").append(invoiceTime);
        sb.append(", customerId=").append(customerId);
        sb.append(", customerName=").append(customerName);
        sb.append(", applyAmount=").append(applyAmount);
        sb.append(", invoiceAmount=").append(invoiceAmount);
        sb.append(", expressCompany=").append(expressCompany);
        sb.append(", expressNo=").append(expressNo);
        sb.append(", invoiceTitle=").append(invoiceTitle);
        sb.append(", taxpayerType=").append(taxpayerType);
        sb.append(", taxpayerNumber=").append(taxpayerNumber);
        sb.append(", regAddress=").append(regAddress);
        sb.append(", openBank=").append(openBank);
        sb.append(", openBankNo=").append(openBankNo);
        sb.append(", invoiceContent=").append(invoiceContent);
        sb.append(", address=").append(address);
        sb.append(", receiver=").append(receiver);
        sb.append(", mobile=").append(mobile);
        sb.append(", invoicePicUrl=").append(invoicePicUrl);
        sb.append(", makeInvoiceStatus=").append(makeInvoiceStatus);
        sb.append(", postStatus=").append(postStatus);
        sb.append(", dataStatus=").append(dataStatus);
        sb.append(", remark=").append(remark);
        sb.append(", applyOper=").append(applyOper);
        sb.append(", applyTime=").append(applyTime);
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
        MakeInvoiceDetail other = (MakeInvoiceDetail) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getApplyNum() == null ? other.getApplyNum() == null : this.getApplyNum().equals(other.getApplyNum()))
            && (this.getInvoiceTime() == null ? other.getInvoiceTime() == null : this.getInvoiceTime().equals(other.getInvoiceTime()))
            && (this.getCustomerId() == null ? other.getCustomerId() == null : this.getCustomerId().equals(other.getCustomerId()))
            && (this.getCustomerName() == null ? other.getCustomerName() == null : this.getCustomerName().equals(other.getCustomerName()))
            && (this.getApplyAmount() == null ? other.getApplyAmount() == null : this.getApplyAmount().equals(other.getApplyAmount()))
            && (this.getInvoiceAmount() == null ? other.getInvoiceAmount() == null : this.getInvoiceAmount().equals(other.getInvoiceAmount()))
            && (this.getExpressCompany() == null ? other.getExpressCompany() == null : this.getExpressCompany().equals(other.getExpressCompany()))
            && (this.getExpressNo() == null ? other.getExpressNo() == null : this.getExpressNo().equals(other.getExpressNo()))
            && (this.getInvoiceTitle() == null ? other.getInvoiceTitle() == null : this.getInvoiceTitle().equals(other.getInvoiceTitle()))
            && (this.getTaxpayerType() == null ? other.getTaxpayerType() == null : this.getTaxpayerType().equals(other.getTaxpayerType()))
            && (this.getTaxpayerNumber() == null ? other.getTaxpayerNumber() == null : this.getTaxpayerNumber().equals(other.getTaxpayerNumber()))
            && (this.getRegAddress() == null ? other.getRegAddress() == null : this.getRegAddress().equals(other.getRegAddress()))
            && (this.getOpenBank() == null ? other.getOpenBank() == null : this.getOpenBank().equals(other.getOpenBank()))
            && (this.getOpenBankNo() == null ? other.getOpenBankNo() == null : this.getOpenBankNo().equals(other.getOpenBankNo()))
            && (this.getInvoiceContent() == null ? other.getInvoiceContent() == null : this.getInvoiceContent().equals(other.getInvoiceContent()))
            && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
            && (this.getReceiver() == null ? other.getReceiver() == null : this.getReceiver().equals(other.getReceiver()))
            && (this.getMobile() == null ? other.getMobile() == null : this.getMobile().equals(other.getMobile()))
            && (this.getInvoicePicUrl() == null ? other.getInvoicePicUrl() == null : this.getInvoicePicUrl().equals(other.getInvoicePicUrl()))
            && (this.getMakeInvoiceStatus() == null ? other.getMakeInvoiceStatus() == null : this.getMakeInvoiceStatus().equals(other.getMakeInvoiceStatus()))
            && (this.getPostStatus() == null ? other.getPostStatus() == null : this.getPostStatus().equals(other.getPostStatus()))
            && (this.getDataStatus() == null ? other.getDataStatus() == null : this.getDataStatus().equals(other.getDataStatus()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getApplyOper() == null ? other.getApplyOper() == null : this.getApplyOper().equals(other.getApplyOper()))
            && (this.getApplyTime() == null ? other.getApplyTime() == null : this.getApplyTime().equals(other.getApplyTime()))
            && (this.getModifyoper() == null ? other.getModifyoper() == null : this.getModifyoper().equals(other.getModifyoper()))
            && (this.getModifytime() == null ? other.getModifytime() == null : this.getModifytime().equals(other.getModifytime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getApplyNum() == null) ? 0 : getApplyNum().hashCode());
        result = prime * result + ((getInvoiceTime() == null) ? 0 : getInvoiceTime().hashCode());
        result = prime * result + ((getCustomerId() == null) ? 0 : getCustomerId().hashCode());
        result = prime * result + ((getCustomerName() == null) ? 0 : getCustomerName().hashCode());
        result = prime * result + ((getApplyAmount() == null) ? 0 : getApplyAmount().hashCode());
        result = prime * result + ((getInvoiceAmount() == null) ? 0 : getInvoiceAmount().hashCode());
        result = prime * result + ((getExpressCompany() == null) ? 0 : getExpressCompany().hashCode());
        result = prime * result + ((getExpressNo() == null) ? 0 : getExpressNo().hashCode());
        result = prime * result + ((getInvoiceTitle() == null) ? 0 : getInvoiceTitle().hashCode());
        result = prime * result + ((getTaxpayerType() == null) ? 0 : getTaxpayerType().hashCode());
        result = prime * result + ((getTaxpayerNumber() == null) ? 0 : getTaxpayerNumber().hashCode());
        result = prime * result + ((getRegAddress() == null) ? 0 : getRegAddress().hashCode());
        result = prime * result + ((getOpenBank() == null) ? 0 : getOpenBank().hashCode());
        result = prime * result + ((getOpenBankNo() == null) ? 0 : getOpenBankNo().hashCode());
        result = prime * result + ((getInvoiceContent() == null) ? 0 : getInvoiceContent().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        result = prime * result + ((getReceiver() == null) ? 0 : getReceiver().hashCode());
        result = prime * result + ((getMobile() == null) ? 0 : getMobile().hashCode());
        result = prime * result + ((getInvoicePicUrl() == null) ? 0 : getInvoicePicUrl().hashCode());
        result = prime * result + ((getMakeInvoiceStatus() == null) ? 0 : getMakeInvoiceStatus().hashCode());
        result = prime * result + ((getPostStatus() == null) ? 0 : getPostStatus().hashCode());
        result = prime * result + ((getDataStatus() == null) ? 0 : getDataStatus().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getApplyOper() == null) ? 0 : getApplyOper().hashCode());
        result = prime * result + ((getApplyTime() == null) ? 0 : getApplyTime().hashCode());
        result = prime * result + ((getModifyoper() == null) ? 0 : getModifyoper().hashCode());
        result = prime * result + ((getModifytime() == null) ? 0 : getModifytime().hashCode());
        return result;
    }
}