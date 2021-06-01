package cn.rebornauto.platform.business.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentStatisticsVo {
//    private Integer id;
    private String month;
    private String agentName;
    private String idcardno;
    private String phone;
    private BigDecimal paymentSum;
}
