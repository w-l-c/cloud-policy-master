package cn.rebornauto.platform.business.query;

import cn.rebornauto.platform.common.data.request.Query;
import lombok.Data;

/** Title: 
 * Description: 
 * Company: 
 * @author kgc
 * @date Apr 14, 2020 2:37:26 PM
 */
@Data
public class PayQuery  extends Query{

	/**
	 * 查询日期
	 */
	private String date;
	/**
	 * 支付通道id
	 */
	private int paymentChannelId;
}
