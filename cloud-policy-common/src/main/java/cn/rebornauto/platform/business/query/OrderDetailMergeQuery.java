package cn.rebornauto.platform.business.query;

import lombok.Data;
import cn.rebornauto.platform.common.data.request.Query;

/** Title: 订单合并明细
 * Description: 
 * Company: 
 * @author kgc
 * @date Apr 30, 2019 2:10:14 PM
 */
@Data
public class OrderDetailMergeQuery extends Query{

	/**
	 *  客户编号 
	 */
	private String customerId;
	/**
	 * 申请时间
	 */
//	private String applyTime;
	/**
	 * 审批状态
	 */
	private String checkStatus;
	/**
	 *  订单编号 
	 */
	private Integer orderId;
	
	
}
