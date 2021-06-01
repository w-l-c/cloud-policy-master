package cn.rebornauto.platform.business.query;

import lombok.Data;
import cn.rebornauto.platform.common.data.request.Query;

/** Title: 财务付款
 * Description: 
 * Company: 
 * @author kgc
 * @date Apr 30, 2019 2:10:14 PM
 */
@Data
public class OrderQuery extends Query{

	/**
	 *  订单id 
	 */
	private Integer orderId;
	/**
	 *  客户编号 
	 */
	private String customerId;
	/**
	 * 申请时间
	 */
	private String createtime;
	/**
	 * 审批状态
	 */
	private String checkStatus;
	/**
	 * 起始时间
	 */
	private String startTime;
	/**
	 * 结束时间
	 */
	private String endTime;
	
	public void setCreatetime(String createtime) {
        this.createtime = createtime;
        if (!"".equals(createtime) && createtime != null) {
            String[] split = createtime.split("~");
            this.startTime = split[0];
            this.endTime = split[1];
        }
    }
	
	
}
