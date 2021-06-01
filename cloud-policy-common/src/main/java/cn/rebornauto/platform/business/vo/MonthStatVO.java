package cn.rebornauto.platform.business.vo;

import java.time.LocalDateTime;

import lombok.Data;

/** Title: 月度统计
 * Description: 
 * Company: 
 * @author kgc
 * @date Apr 2, 2020 10:59:24 AM
 */
@Data
public class MonthStatVO {
	/**
     *  客户公司
     */
    private String customerName;
    /**
     *  付款时间
     */
    private LocalDateTime completeTime;
    /**
     *  姓名
     */
    private String agentName;
    /**
     *  身份证件类型
     */
    private String idType;
    /**
     *  身份证件号码
     */
    private String agentIdcardno;
    /**
     *  国籍（地区）
     */
    private String cardType;
    /**
     *  联系电话
     */
    private String agentMobile;
    /**
     *  生产经营地行政区划
     */
    private String daiZheng;
    /**
     *  应税收入
     */
    private String amount;
    /**
     *  应税所得率
     */
    private String taxableIncomeRate;
    /**
     *  计税依据
     */
    private String taxYiju;
    /**
     *  税率
     */
    private String taxRate;
    /**
     *  速算扣除数
     */
    private String qcNumber;
    /**
     *  应纳税额
     */
    private String taxPayable;
    /**
     *  累计已缴纳税额
     */
    private String accumulatedTaxPaid;
    /**
     *  本期应补退税额
     */
    private String taxRefundable;
}
