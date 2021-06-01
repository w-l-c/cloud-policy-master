package cn.rebornauto.platform.business.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

@Data
public class OrderDetailVO implements Serializable {
    private Integer id;

    /**
     * 订单号
     */
    private Integer orderId;

    /**
     * 代征主体id
     */
    private Integer daiZhengId;

    /**
     * 支付通道id
     */
    private Integer paymentChannelId;

    /**
     * 保单号
     */
    private String policyNo;

    /**
     * 保费
     */
    private BigDecimal policyAmount;

    /**
     * 佣金
     */
    private BigDecimal amount;

    /**
     * 代理人id,通过身份证匹配到代理人id，便于将来关联代理人查询
     */
    private Integer agentId;

    /**
     * 姓名
     */
    private String agentName;

    /**
     * 身份证
     */
    private String idcardno;

    /**
     * 银行卡号
     */
    private String openBankNo;

    /**
     * 出单时间
     */
    private LocalDateTime outtime;

    /**
     * 来源   单次上传  或者  xxx.xls
     */
    private String source;

    /**
     * 数据状态   1有效   -1无效
     */
    private Integer dataStatus;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private String createoper;

    /**
     * 注册时间
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createtime;

    /**
     * 修改人
     */
    private String modifyoper;

    /**
     * 修改时间
     */
    private LocalDateTime modifytime;
    
    /**
     * 代理人银行卡号
     */
    private String agentOpenBankCode;
    /**
     * 客户id
     */
    private String customerId;
    /**
     * 总金额
     */
    private BigDecimal totalAmountQuery;
    /**
     * 总笔数
     */
    private Integer totalCountQuery;
    
    private String policyPerson;
    
    private Integer checkStatus;
    
    private String mergeId;
    
    /**
     * 保费千分位
     */
    private String policyAmountFin;

    /**
     * 佣金千分位
     */
    private String amountFin;
    /**
     * 总金额千分位
     */
    private String totalAmountQueryFin;
    /**
     * 支付状态
     */
    private Integer payStatus;
    /**
     * 支付状态结果
     */
    private String payStatusResult;
    /**
     * 支付完成时间
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime completeTime;
    /**
     * 支付结果
     */
    private String result;
    
    /**
     * 备注2
     */
    private String remark2;
    
    /**
     * 备注3
     */
    private String remark3;

    private static final long serialVersionUID = 1L;

}