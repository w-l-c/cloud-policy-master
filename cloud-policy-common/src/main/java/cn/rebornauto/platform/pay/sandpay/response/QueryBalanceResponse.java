package cn.rebornauto.platform.pay.sandpay.response;

import java.io.Serializable;

import lombok.Data;

/** Title: 账户余额查询  相应
 * Description: 
 * Company: 
 * @author kgc
 * @date Mar 27, 2020 3:16:20 PM
 */
@Data
public class QueryBalanceResponse implements Serializable{

	private static final long serialVersionUID = -7033641203942131373L;
	/**
	 * 响应码
	 */
	private String respCode;
	/**
	 * 响应描述
	 */
	private String respDesc;
	/**
	 * 交易时间(合作商户发起时间)
	 */
	private String tranTime;
	/**
	 * 余额
	 */
	private String balance = "0";
	/**
	 * 订单号
	 */
	private String orderCode;
	/**
	 * 可用额度
	 */
	private String creditAmt;
	/**
	 * 扩展域
	 */
	private String extend;
}
