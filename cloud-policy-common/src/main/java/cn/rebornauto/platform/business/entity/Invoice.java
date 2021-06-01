package cn.rebornauto.platform.business.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "`t_invoice`")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Invoice implements Serializable {
    /**
     * id
     */
    @Id
    @Column(name = "`id`")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 订单号
     */
    @Column(name = "`order_id`")
    private Integer orderId;

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
     * 代理人佣金
     */
    @Column(name = "`agent_commission`")
    private BigDecimal agentCommission;

    /**
     * 实际付款
     */
    @Column(name = "`real_payment`")
    private BigDecimal realPayment;

    /**
     * 充值状态 1未到账，2已到账
     */
    @Column(name = "`recharge`")
    private Integer recharge;

    /**
     * 开票状态 1未开票，2已开票
     */
    @Column(name = "`invoice_status`")
    private Integer invoiceStatus;

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
     * 开票金额
     */
    @Column(name = "`invoice_amount`")
    private BigDecimal invoiceAmount;

    /**
     * 发票抬头
     */
    @Column(name = "`invoice_title`")
    private String invoiceTitle;

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
     * 收件人
     */
    @Column(name = "`receiver`")
    private String receiver;

    /**
     * 联系电话
     */
    @Column(name = "`mobile`")
    private String mobile;

    /**
     * 地址
     */
    @Column(name = "`address`")
    private String address;

    /**
     * 发票图片地址
     */
    @Column(name = "`invoice_pic_url`")
    private String invoicePicUrl;

    /**
     * 发票状态   1待开票   2已开票
     */
    @Column(name = "`out_invoice_status`")
    private Integer outInvoiceStatus;

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
        sb.append(", orderId=").append(orderId);
        sb.append(", invoiceTime=").append(invoiceTime);
        sb.append(", customerId=").append(customerId);
        sb.append(", agentCommission=").append(agentCommission);
        sb.append(", realPayment=").append(realPayment);
        sb.append(", recharge=").append(recharge);
        sb.append(", invoiceStatus=").append(invoiceStatus);
        sb.append(", expressCompany=").append(expressCompany);
        sb.append(", expressNo=").append(expressNo);
        sb.append(", invoiceAmount=").append(invoiceAmount);
        sb.append(", invoiceTitle=").append(invoiceTitle);
        sb.append(", taxpayerType=").append(taxpayerType);
        sb.append(", taxpayerNumber=").append(taxpayerNumber);
        sb.append(", regAddress=").append(regAddress);
        sb.append(", openBank=").append(openBank);
        sb.append(", openBankNo=").append(openBankNo);
        sb.append(", invoiceContent=").append(invoiceContent);
        sb.append(", receiver=").append(receiver);
        sb.append(", mobile=").append(mobile);
        sb.append(", address=").append(address);
        sb.append(", invoicePicUrl=").append(invoicePicUrl);
        sb.append(", outInvoiceStatus=").append(outInvoiceStatus);
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
        Invoice other = (Invoice) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId()))
            && (this.getInvoiceTime() == null ? other.getInvoiceTime() == null : this.getInvoiceTime().equals(other.getInvoiceTime()))
            && (this.getCustomerId() == null ? other.getCustomerId() == null : this.getCustomerId().equals(other.getCustomerId()))
            && (this.getAgentCommission() == null ? other.getAgentCommission() == null : this.getAgentCommission().equals(other.getAgentCommission()))
            && (this.getRealPayment() == null ? other.getRealPayment() == null : this.getRealPayment().equals(other.getRealPayment()))
            && (this.getRecharge() == null ? other.getRecharge() == null : this.getRecharge().equals(other.getRecharge()))
            && (this.getInvoiceStatus() == null ? other.getInvoiceStatus() == null : this.getInvoiceStatus().equals(other.getInvoiceStatus()))
            && (this.getExpressCompany() == null ? other.getExpressCompany() == null : this.getExpressCompany().equals(other.getExpressCompany()))
            && (this.getExpressNo() == null ? other.getExpressNo() == null : this.getExpressNo().equals(other.getExpressNo()))
            && (this.getInvoiceAmount() == null ? other.getInvoiceAmount() == null : this.getInvoiceAmount().equals(other.getInvoiceAmount()))
            && (this.getInvoiceTitle() == null ? other.getInvoiceTitle() == null : this.getInvoiceTitle().equals(other.getInvoiceTitle()))
            && (this.getTaxpayerType() == null ? other.getTaxpayerType() == null : this.getTaxpayerType().equals(other.getTaxpayerType()))
            && (this.getTaxpayerNumber() == null ? other.getTaxpayerNumber() == null : this.getTaxpayerNumber().equals(other.getTaxpayerNumber()))
            && (this.getRegAddress() == null ? other.getRegAddress() == null : this.getRegAddress().equals(other.getRegAddress()))
            && (this.getOpenBank() == null ? other.getOpenBank() == null : this.getOpenBank().equals(other.getOpenBank()))
            && (this.getOpenBankNo() == null ? other.getOpenBankNo() == null : this.getOpenBankNo().equals(other.getOpenBankNo()))
            && (this.getInvoiceContent() == null ? other.getInvoiceContent() == null : this.getInvoiceContent().equals(other.getInvoiceContent()))
            && (this.getReceiver() == null ? other.getReceiver() == null : this.getReceiver().equals(other.getReceiver()))
            && (this.getMobile() == null ? other.getMobile() == null : this.getMobile().equals(other.getMobile()))
            && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
            && (this.getInvoicePicUrl() == null ? other.getInvoicePicUrl() == null : this.getInvoicePicUrl().equals(other.getInvoicePicUrl()))
            && (this.getOutInvoiceStatus() == null ? other.getOutInvoiceStatus() == null : this.getOutInvoiceStatus().equals(other.getOutInvoiceStatus()))
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
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        result = prime * result + ((getInvoiceTime() == null) ? 0 : getInvoiceTime().hashCode());
        result = prime * result + ((getCustomerId() == null) ? 0 : getCustomerId().hashCode());
        result = prime * result + ((getAgentCommission() == null) ? 0 : getAgentCommission().hashCode());
        result = prime * result + ((getRealPayment() == null) ? 0 : getRealPayment().hashCode());
        result = prime * result + ((getRecharge() == null) ? 0 : getRecharge().hashCode());
        result = prime * result + ((getInvoiceStatus() == null) ? 0 : getInvoiceStatus().hashCode());
        result = prime * result + ((getExpressCompany() == null) ? 0 : getExpressCompany().hashCode());
        result = prime * result + ((getExpressNo() == null) ? 0 : getExpressNo().hashCode());
        result = prime * result + ((getInvoiceAmount() == null) ? 0 : getInvoiceAmount().hashCode());
        result = prime * result + ((getInvoiceTitle() == null) ? 0 : getInvoiceTitle().hashCode());
        result = prime * result + ((getTaxpayerType() == null) ? 0 : getTaxpayerType().hashCode());
        result = prime * result + ((getTaxpayerNumber() == null) ? 0 : getTaxpayerNumber().hashCode());
        result = prime * result + ((getRegAddress() == null) ? 0 : getRegAddress().hashCode());
        result = prime * result + ((getOpenBank() == null) ? 0 : getOpenBank().hashCode());
        result = prime * result + ((getOpenBankNo() == null) ? 0 : getOpenBankNo().hashCode());
        result = prime * result + ((getInvoiceContent() == null) ? 0 : getInvoiceContent().hashCode());
        result = prime * result + ((getReceiver() == null) ? 0 : getReceiver().hashCode());
        result = prime * result + ((getMobile() == null) ? 0 : getMobile().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        result = prime * result + ((getInvoicePicUrl() == null) ? 0 : getInvoicePicUrl().hashCode());
        result = prime * result + ((getOutInvoiceStatus() == null) ? 0 : getOutInvoiceStatus().hashCode());
        result = prime * result + ((getDataStatus() == null) ? 0 : getDataStatus().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreateoper() == null) ? 0 : getCreateoper().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getModifyoper() == null) ? 0 : getModifyoper().hashCode());
        result = prime * result + ((getModifytime() == null) ? 0 : getModifytime().hashCode());
        return result;
    }
}