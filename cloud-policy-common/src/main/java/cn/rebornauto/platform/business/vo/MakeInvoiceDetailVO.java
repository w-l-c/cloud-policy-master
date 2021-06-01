package cn.rebornauto.platform.business.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
public class MakeInvoiceDetailVO implements Serializable {
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
     * 发票类型名称
     */
    @Column(name = "`invoice_type_name`")
    private String invoiceTypeName;

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
     * 服务费率
     */
    private BigDecimal serviceRate;
    /**
     * 开户行名称
     */
    private String customerOpenBankName;
    /**
     * 开户行行号
     */
    private String customerOpenBankNo;
    /**
     * 财务千分位
     */
    private String applyAmountFin;
    /**
     * 财务千分位
     */
    private String invoiceAmountFin;
    /**
     * 开票电话
     */
    private String tel;
    /**
     * 寄送日期
     */
    private LocalDateTime sendTime;
    /**
     * 本金金额(元)
     */
    private BigDecimal capitalAmount;
    /**
     * 服务费率
     */
    private BigDecimal customerServiceRate;
    /**
     * 单位注册地址
     */
    private String customerRegAddress;
    /**
     * 支行名称
     */
    private String customerSubOpenBankName;
    /**
     * 发票样张
     */
    private String invoiceDemoUrl;

    private static final long serialVersionUID = 1L;

}