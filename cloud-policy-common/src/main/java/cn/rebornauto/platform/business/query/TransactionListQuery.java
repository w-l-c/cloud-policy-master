package cn.rebornauto.platform.business.query;

import lombok.Data;
import cn.rebornauto.platform.common.data.request.Query;

/** Title: 支付明细
 * Description: 
 * Company: 
 * @author kgc
 * @date Apr 30, 2019 2:10:14 PM
 */
@Data
public class TransactionListQuery extends Query{

	/**
	 *  客户编号 
	 */
	private String customerId;
	/**
	 * 审批状态
	 */
	private String checkStatus;
	/**
	 *  订单编号 
	 */
	private Integer orderId;
	/**
	 * 交易状态
	 */
	private Integer payStatus;
	/**
	 * 代理人姓名
	 */
	private String agentName;
	/**
	 * 银行卡号
	 */
	private String openBankNo;
	/**
	 * 批次号
	 */
	private String batchNo;
	/**
	 * 支付时间
	 */
	private String completeTime;
	/**
	 * 起始时间
	 */
	private String startTime;
	/**
	 * 结束时间
	 */
	private String endTime;
	public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
        if (!"".equals(completeTime) && completeTime != null) {
            String[] split = completeTime.split("~");
            if(split.length == 1) {
            	this.startTime = split[0];
                this.endTime = split[0];
            }else {
            	this.startTime = split[0];
                this.endTime = split[1];
            }
        }
    }
}
