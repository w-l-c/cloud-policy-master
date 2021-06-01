package cn.rebornauto.platform.pay.pingan.request;

import lombok.Data;
import cn.rebornauto.platform.pay.pingan.request.base.BaseRequestPingAn;

/** Title: 
 * Description: 
 * Company: 
 * @author kgc
 * @date Apr 28, 2019 12:05:07 AM
 */
@Data
public class SingleDfTranDealRequest extends BaseRequestPingAn{

	/**
	 * 业务种类号(固定：100157)
	 */
	private String BussTypeNo;
	/**
	 * 协议编号(可通过柜面查询，签约的协议号)
	 */
	private String AgreeNo;
	/**
	 * 收付类型(2-下拨)
	 */
	private String InoutFlag;
	/**
	 * 转出账号(商户签约账号)
	 */
	private String FromAcctNo;
	/**
	 * 转出户名
	 */
	private String FromClientName;
	/**
	 * 转入账号(客户账号)
	 */
	private String ToAcctNo;
	/**
	 * 转入户名
	 */
	private String ToClientName;
	/**
	 * 币种(RMB)
	 */
	private String Ccy;
	/**
	 * 交易金额(元为单位)
	 */
	private String TranAmt;
	/**
	 * 用途(备注信息)
	 */
	private String Usage;
	/**
	 * 转出账号联行号
	 */
	private String OutAcctNoInterbankId;
	/**
	 * 收款人联行号(输入规则，详见下面说明)
	 */
	private String PayeeInterbankId;
	/**
	 * 代收付代码(00000)
	 */
	private String CollPayCode;
	/**
	 * 手机号码
	 */
	private String MobilePhone;
	/**
	 * 证件类型(1,身份证)
	 */
	private String GlobalType;
	/**
	 * 证件号码
	 */
	private String GlobalId;
	/**
	 * 保留域
	 */
	private String ReservedMsg;
	/**
	 * 开户行省份(输入规则，详见下面说明)
	 */
	private String OpenAcctBranchProvince;
	/**
	 * 开户行城市(输入规则，详见下面说明)
	 */
	private String OpenAcctBranchCity;
	/**
	 * 收款人开户行名称(输入规则，详见下面说明)
	 */
	private String PayeeAcctOpenBranchName;
	/**
	 * 单位结算卡账户名称(单位结算卡时完整账户户名或营业执照号为二选一必输)
	 */
	private String CompanySettleCardAcctName;
	/**
	 * 营业执照注册号(单位结算卡时完整账户户名或营业执照号为二选一必输)
	 */
	private String BusinessLicenceRegistrationNo;
	/**
	 * 交易场景类型
	 */
	private String TranSceneType;
	/**
	 * 交易摘要(交易订单信息等内容)
	 */
	private String TranSummary;
	/**
	 * 原订单号(原订单交易请求流水号，如果该笔支付是针对某一笔失败的支付交易的重发，必须传该字段。如果多次对同一笔失败交易发起重发，该流号必须保持为最原始的流水号)
	 */
	private String OldOrderNo;

	/***
	 * 关于PayeeInterbankId、OpenAcctBranchProvince、OpenAcctBranchCity、PayeeAcctOpenBranchName字段送值说明：						
	 * "1、如果收款帐号为平安银行帐号，以上字段可以不送
		2、如果收款帐号非平安银行帐号，则遵循如下规则：
			1）单位签约，未开通智能路由，则以上字段均无需输入（是否开通智能路由可通过柜面查询）
			2）如果开通了智能路由，输入PayeeInterbankId（收款人联行号），则OpenAcctBranchProvince、OpenAcctBranchCity、PayeeAcctOpenBranchName (收款人开户省、市、开户行名称)可以不输入；
			如果PayeeInterbankId（收款人联行号）没有输入，则OpenAcctBranchProvince、OpenAcctBranchCity、PayeeAcctOpenBranchName (收款人开户省、市、开户行名称)必输"						
	 */
	
}
