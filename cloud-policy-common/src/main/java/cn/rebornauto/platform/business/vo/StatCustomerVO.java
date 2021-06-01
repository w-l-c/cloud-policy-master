package cn.rebornauto.platform.business.vo;

import java.math.BigDecimal;

import lombok.Data;

/** Title: 展示vo
 * Description: 
 * Company: 
 * @author kgc
 * @date Apr 2, 2020 10:59:24 AM
 */
@Data
public class StatCustomerVO {
	/**
     *  商户号
     */
    private String customerId;
	/**
     *  公司名称
     */
    private String customerName;
    /**
     *  实际充值金额
     */
    private BigDecimal realPayment = BigDecimal.ZERO;
    /**
     *  实际充值金额
     */
    private String realPaymentFin = "0";
    /**
     *  系统充值金额
     */
    private BigDecimal agentCommission = BigDecimal.ZERO;
    /**
     *  系统充值金额
     */
    private String agentCommissionFin = "0";
    /**
     *  发放金额
     */
    private BigDecimal payAmount = BigDecimal.ZERO;
    /**
     *  发放金额
     */
    private String payAmountFin = "0";
    /**
     *  发放人数
     */
    private Integer payCount = 0;
    /**
     *  发放平均值
     */
    private BigDecimal avgAmount = BigDecimal.ZERO;
    /**
     *  发放平均值
     */
    private String avgAmountFin = "0";
    /**
     *  余额
     */
    private BigDecimal leftAmount = BigDecimal.ZERO;
    /**
     *  余额
     */
    private String leftAmountFin = "0";
    /**
     *  已开发票
     */
    private BigDecimal applyAmount = BigDecimal.ZERO;
    /**
     *  已开发票
     */
    private String applyAmountFin = "0";
}
