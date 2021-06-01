package cn.rebornauto.platform.pay.sandpay.request;

import java.io.Serializable;

import cn.rebornauto.platform.pay.sandpay.dsf.demo.SandBase;
import lombok.Data;

/** Title: 订单查询 请求
 * Description: 
 * Company: 
 * @author kgc
 * @date Mar 27, 2020 3:16:02 PM
 */
@Data
public class QueryOrderRequest implements Serializable{

	private static final long serialVersionUID = -8565376055970818178L;
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
	 * 扩展域
	 */
	private String extend;
	
	
	public QueryOrderRequest(String tranTime, String orderCode) {
		this.version = SandBase.version;
		this.productId = SandBase.PRODUCTID_AGENTPAY_TOC;
		this.tranTime = tranTime;
		this.orderCode = orderCode;
		this.extend = "";
	}


	@Override
	public String toString() {
		return "QueryOrderRequest [version=" + version + ", productId=" + productId + ", tranTime=" + tranTime
				+ ", orderCode=" + orderCode + ", extend=" + extend + "]";
	}
}
