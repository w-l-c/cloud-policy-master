package cn.rebornauto.platform.business.query;

import lombok.Data;
import cn.rebornauto.platform.common.data.request.Query;

/** Title: 支付流水明细
 * Description: 
 * Company: 
 * @author kgc
 * @date Apr 30, 2019 2:10:14 PM
 */
@Data
public class CollectionRepaymentSignQuery extends Query{

	/**
	 *  客户编号 
	 */
	private String transactionFlowId;
	/**
	 * 交易状态
	 */
	private Integer payStatus;
	/**
	 * 三方交易流水号
	 */
	private String requestSn;
	/**
	 * 代理人姓名
	 */
	private String agentName;
	/**
	 * 身份证号
	 */
	private String idcardno;
	/**
	 * 银行卡号
	 */
	private String openBankNo;
	/**
	 * 客户编号
	 */
	private String customerId;
	
}
