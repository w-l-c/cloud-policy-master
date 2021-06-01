package cn.rebornauto.platform.business.vo;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class CustomerQuotaVo {
    private Integer id;
    /**
     * 账户余额（元）
     */
    private BigDecimal customerBalance;

    /**
     * 冻结金额（元）
     */
    private BigDecimal frozenAmount;

    /**
     * 待审核充值金额（元）
     */
    private BigDecimal pendingApprAmount;

    /**
     * 累计付款金额（元）
     */
    private BigDecimal loanAmount;

    /**
     * 累计充值金额（元）
     */
    private BigDecimal rechargeAmount;
}
