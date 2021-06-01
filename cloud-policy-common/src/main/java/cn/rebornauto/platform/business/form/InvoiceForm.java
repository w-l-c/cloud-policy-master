package cn.rebornauto.platform.business.form;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import cn.rebornauto.platform.common.data.request.Form;
import lombok.Data;

@Data
public class InvoiceForm extends Form {
    private LocalDateTime invoiceTime;
    private String expressCompany;
    private String expressNo;
    private String[] invoicePicUrl;
    
    private String customerId;
    private Integer applyNum;
    private BigDecimal applyAmount;
    private String invoiceNo;
    private Integer invoiceType;
    private String remark;
}
