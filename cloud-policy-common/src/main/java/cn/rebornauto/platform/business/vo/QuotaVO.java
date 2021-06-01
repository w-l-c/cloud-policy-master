package cn.rebornauto.platform.business.vo;

import java.math.BigDecimal;
import java.util.Map;

import lombok.Data;

/** Title: 
 * Description: 
 * Company: 
 * @author kgc
 * @date Mar 21, 2020 3:17:10 PM
 */
@Data
public class QuotaVO {

	/**
	 * 需要支付总金额
	 */
	private BigDecimal payAmount;
	
	/**
	 * 每个代理人总金额
	 */
	private Map<String, BigDecimal> agentAmountMap;
	/**
	 * 客户id
	 */
	private String customerId;
	/**
	 * 代征id
	 */
	private Integer daiZhengId;
}
