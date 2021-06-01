package cn.rebornauto.platform.pay.pingan.request.base;

import java.io.Serializable;

import lombok.Data;

/** Title: 
 * Description: 
 * Company: 
 * @author kgc
 * @date Apr 28, 2019 12:00:19 AM
 */
@Data
public class BaseRequestPingAn implements Serializable{

	/**
	 * 交易流水号(系统流水号，规范：用户短号（6位）+日期（6位）+随机编号（10位）例：C256341801183669951236)
	 */
	private String CnsmrSeqNo;
	/**
	 * 商户号(签约客户号)
	 */
	private String MrchCode;
}
