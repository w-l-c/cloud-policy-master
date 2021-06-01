package cn.rebornauto.platform.business.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class InvoiceVo {
    private Integer id;
    private Integer orderId;
    private LocalDateTime invoiceTime;
    private String customerId;
    private String customerName;
    private BigDecimal agentCommission;
    private BigDecimal realPayment;
    private Integer recharge;
    private Integer invoiceStatus;
    private String expressCompany;
    private String expressNo;
    private BigDecimal invoiceAmount;
    private String invoiceTitle;
    private Integer taxpayerType;
    private String taxpayerNumber;
    private String customerRegAddress;
    private String customerOpenBank;
    private String customerOpenBankNo;
    private String invoiceContent;
    private String receiver;
    private String customerMobile;
    private String customerAddress;
    private String invoicePicUrl;
    private Integer outInvoiceStatus;
    private Integer dataStatus;

}
