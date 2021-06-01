package cn.rebornauto.platform.business.form;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;
import cn.rebornauto.platform.common.data.request.Form;

/** Title: 
 * Description: 
 * Company: 
 * @author kgc
 * @date Apr 30, 2019 2:10:14 PM
 */
@Data
public class TransactionFlowForm extends Form{

	private Integer orderId;
	/**
	 * 交易流水号
	 */
	private String transactionFlowId;
	
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private String responseTime;
	
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private String completeTime;
	
	private int status;
	
	private String code;
	
	private String msg;
	
}
