package cn.rebornauto.platform.pay.pingan.response.base;

import java.io.Serializable;

import lombok.Data;

/** Title: 
 * Description: 
 * Company: 
 * @author kgc
 * @date Apr 28, 2019 12:00:19 AM
 */
@Data
public class BaseResponsePingAn implements Serializable{

	/**
	 * 返回码
	 */
	private String TxnReturnCode ;
	/**
	 * 返回信息
	 */
	private String TxnReturnMsg;
	/**
	 * 交易流水号
	 */
	private String CnsmrSeqNo;
}
