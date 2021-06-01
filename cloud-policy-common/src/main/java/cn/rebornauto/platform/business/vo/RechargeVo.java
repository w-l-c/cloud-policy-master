package cn.rebornauto.platform.business.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

@Data
public class RechargeVo {
    private Integer id;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime applytime;
    private String customerId;
    private String customerName;
    private BigDecimal serviceRate;
    private BigDecimal serviceAmount;
    private BigDecimal agentCommission;
    private BigDecimal realPayment;
    private Integer recharge;
    private String rechargeVoucherPicUrl;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime arrivetime;
    private Integer dataStatus;
    private Integer daiZhengId;
    
    /**
     * 财务千分位
     */
    private String agentCommissionFin;
    /**
     * 财务千分位
     */
    private String realPaymentFin;
    
    /**
     * 审核人
     */
    private String checkoper;

    /**
     * 审核时间
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime checktime;
    /**
     * 审核时效（分钟）
     */
    private Integer worktime;
}
