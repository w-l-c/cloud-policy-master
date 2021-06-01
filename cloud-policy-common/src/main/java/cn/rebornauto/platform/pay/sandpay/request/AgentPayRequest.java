package cn.rebornauto.platform.pay.sandpay.request;

import java.io.Serializable;

import cn.rebornauto.platform.pay.sandpay.dsf.demo.SandBase;
import cn.rebornauto.platform.pay.sandpay.enums.SandAccAttrEnums;
import cn.rebornauto.platform.pay.sandpay.enums.SandAccTypeEnums;
import lombok.Data;

/** Title: 实时代付  请求
 * Description: 
 * Company: 
 * @author kgc
 * @date Mar 27, 2020 3:35:48 PM
 */
@Data
public class AgentPayRequest implements Serializable{
	private static final long serialVersionUID = -4692988550450844046L;
	/**
	 * 版本号
	 * 固定填写01
	 */
	private String version = SandBase.version;
	/**
	 * 产品ID
	 * 代付对私：00000004
	 * 代付对公：00000003
	 */
	private String productId = SandBase.PRODUCTID_AGENTPAY_TOC;
	/**
	 * 交易时间
	 * 合作商户发起时间 
	 * 格式：yyyymmddhhMMss
	 */
	private String tranTime = SandBase.getCurrentTime();
	/**
	 * 订单号
	 * 合作商户唯一，建议订单号中包含日期
	 */
	private String orderCode = SandBase.getOrderCode();
	/**
	 * 订单超时时间
	 * 不填默认24小时
	 */
	private String timeOut = SandBase.getNextDayTime();
	/**
	 * 金额
	 * 精确到分，不足12位前面补0
	 */
	private String tranAmt;
	/**
	 * 币种
	 * 固定156
	 */
	private String currencyCode = SandBase.CURRENCY_CODE;
	/**
	 * 账户属性
	 * 0-对私
	 * 1-对公
	 */
	private String accAttr = SandAccAttrEnums.ACC_ATTR_0.getCode();
	/**
	 * 账号类型
	 * 3-公司账户 
	 * 4-银行卡
	 */
	private String accType = SandAccTypeEnums.ACC_TYPE_4.getCode();
	/**
	 * 收款人账户号
	 */
	private String accNo;
	/**
	 * 收款人账户名
	 */
	private String accName;
	/**
	 * 收款人开户省份编码
	 */
	private String provNo = "";
	/**
	 * 收款人开会城市编码
	 */
	private String cityNo = "";
	/**
	 * 收款账户开户行名称
	 * 对公代付必填（产品ID为0000003时） 
	 */
	private String bankName = "";
	/**
	 * 收款人账户联行号
	 * 对公代付必填（产品ID为0000003时）
	 */
	private String bankType;
	/**
	 * 摘要
	 */
	private String remark;
	/**
	 * 付款模式
	 * 固定填1
	 */
	private String payMode;
	/**
	 * 渠道类型
	 * 固定填写07
	 */
	private String channelType = "";
	/**
	 * 业务扩展参数
	 */
	private String extendParams;
	/**
	 * 请求方保留域
	 * 如需发送交易结果至收款方，则必填，值为收款方的短信通知内容
	 */
	private String reqReserved = "";
	/**
	 * 代付结果通知地址
	 * 如需发送交易结果至某地址，则必填
	 */
	private String noticeUrl;
	/**
	 * 手机号
	 * 如需发送交易结果至收款方，则必填
	 */
	private String phone;
	/**
	 * 扩展域
	 */
	private String extend = "";
	
}
