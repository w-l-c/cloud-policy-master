package cn.rebornauto.platform.pay.sandpay.request;

import java.io.Serializable;

import cn.rebornauto.platform.pay.sandpay.dsf.demo.SandBase;
import lombok.Data;

/** Title: 账户余额查询 请求
 * Description: 
 * Company: 
 * @author kgc
 * @date Mar 27, 2020 3:16:02 PM
 */
@Data
public class QueryBalanceRequest  implements Serializable{
	private static final long serialVersionUID = 3540468002590364142L;
	/**
	 * 版本号
	 * 固定填写01
	 */
	private String version;
	/**
	 * 产品ID
	 * 代付对私：00000004
	 * 代付对公：00000003
	 */
	private String productId;
	/**
	 * 交易时间
	 * 合作商户发起时间 
	 * 格式：yyyymmddhhMMss
	 */
	private String tranTime;
	/**
	 * 订单号
	 * 合作商户唯一，建议订单号中包含日期
	 */
	private String orderCode;
	/**
	 * 渠道类型
	 */
	private String channelType = "";
	/**
	 * 扩展域
	 */
	private String extend = "";
	
	public QueryBalanceRequest() {
		this.version = SandBase.version;
		this.productId = SandBase.PRODUCTID_AGENTPAY_TOC;
		this.tranTime = SandBase.getCurrentTime();
		this.orderCode = SandBase.getOrderCode();
		this.channelType = "";
		this.extend = "";
	}

	@Override
	public String toString() {
		return "QueryBalanceRequest [version=" + version + ", productId=" + productId + ", tranTime=" + tranTime
				+ ", orderCode=" + orderCode + ", channelType=" + channelType + ", extend=" + extend + "]";
	}
}
