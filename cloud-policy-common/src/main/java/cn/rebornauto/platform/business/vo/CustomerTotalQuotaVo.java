package cn.rebornauto.platform.business.vo;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class CustomerTotalQuotaVo {
    private Integer id;
    /**
     * 账户总余额（元）
     */
    private BigDecimal customerBalance;
    /**
     * 可用总余额（元）
     */
    private BigDecimal availableBalance;

    /**
     * 冻结总金额（元）
     */
    private BigDecimal frozenAmount;

    /**
     * 待审核充值总金额（元）
     */
    private BigDecimal pendingApprAmount;

    /**
     * 累计付款总金额（元）
     */
    private BigDecimal loanAmount;

    /**
     * 累计充值总金额（元）
     */
    private BigDecimal rechargeAmount;
    private String customerName;
    private BigDecimal serviceRate;
    private Integer dataStatus;
    private String customerId;
    
    
    /**
     * 账户总余额（元） 千分位
     */
    private String customerBalanceFin;
    /**
     * 可用总余额（元） 千分位
     */
    private String availableBalanceFin;

    /**
     * 冻结总金额（元） 千分位
     */
    private String frozenAmountFin;

    /**
     * 待审核充值总金额（元） 千分位
     */
    private String pendingApprAmountFin;

    /**
     * 累计付款总金额（元） 千分位
     */
    private String loanAmountFin;

    /**
     * 累计充值总金额（元） 千分位
     */
    private String rechargeAmountFin;
}
