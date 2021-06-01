package cn.rebornauto.platform.business.form;

import java.math.BigDecimal;

import lombok.Data;

/** Title: 
 * Description: 
 * Company: 
 * @author kgc
 * @date Apr 3, 2020 9:02:14 AM
 */
@Data
public class TestForm {

	/**
	 * 订单
	 */
	private String orderCode;
	/**
	 * 银行卡号
	 */
	private String accNo;
	/**
	 * 姓名
	 */
	private String accName;
	/**
	 * 支付金额
	 */
	private BigDecimal tranAmt;
	
	
}
