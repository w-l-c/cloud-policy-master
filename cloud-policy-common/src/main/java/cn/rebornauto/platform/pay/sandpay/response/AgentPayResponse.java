package cn.rebornauto.platform.pay.sandpay.response;

import java.io.Serializable;

import lombok.Data;

/** Title: 实时代付  相应
 * Description: 
 * Company: 
 * @author kgc
 * @date Mar 27, 2020 3:16:20 PM
 */
@Data
public class AgentPayResponse implements Serializable{

	private static final long serialVersionUID = -8140733297850548899L;
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
	 * 订单号
	 */
	private String orderCode;
	/**
	 * 结果状态  
	 * 0-成功 1-失败 2-处理中
	 */
	private String resultFlag;
	/**
	 * 杉德系统流水号
	 */
	private String sandSerial;
	/**
	 * 交易日期
	 */
	private String tranDate;
	/**
	 * 手续费
	 */
	private String tranFee;
	/**
	 * 额外手续费
	 */
	private String extraFee;
	/**
	 * 节假日手续费
	 */
	private String holidayFee;
	/**
	 * 扩展域
	 */
	private String extend;
}
