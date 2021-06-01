package cn.rebornauto.platform.business.query;

import cn.rebornauto.platform.common.data.request.Query;
import lombok.Data;

@Data
public class InvoiceQuery extends Query {
	
	/**
     *  开票编号
     */
    private String invoiceNo;
    /**
     *  客户编号
     */
    private String customerId;
    /**
     *  客户名称
     */
    private String customerName;
    /**
     * 邮寄状态
     */
    private Integer postStatus;
    /**
     * 开票状态
     */
    private Integer makeInvoiceStatus;
    /**
     *  开票日期
     */
    private String invoiceTime;

    private String startInvoiceTime;

    private String endInvoiceTime;

    public void setInvoiceTime(String invoiceTime) {
        this.invoiceTime = invoiceTime;
        if (invoiceTime != null && !"".equals(invoiceTime)) {
            String[] split = invoiceTime.split("~");
            this.startInvoiceTime = split[0];
            this.endInvoiceTime = split[1];
        }
    }
    
    /**
     *  申请开票日期
     */
    private String applyTime;

    private String startApplyTime;

    private String endApplyTime;

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
        if (applyTime != null && !"".equals(applyTime)) {
            String[] split = applyTime.split("~");
            this.startApplyTime = split[0];
            this.endApplyTime = split[1];
        }
    }
}
