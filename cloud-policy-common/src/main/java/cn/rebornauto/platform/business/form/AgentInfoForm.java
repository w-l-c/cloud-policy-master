package cn.rebornauto.platform.business.form;

import lombok.Data;
import cn.rebornauto.platform.common.data.request.Form;


@Data
public class AgentInfoForm extends  Form {
	
	private Integer  agentId;

	//GZH front
	private Integer  nationalityId;
	/**
	 * 证件类型
	 */
	private Integer  idType;
	/**
	 * 代理人名称
	 */
	private String agentName;
	/**
	 * 代理人手机号
	 */
	private String agentMobile;
	/**
	 * 代理人身份证号
	 */
	private String agentIdcardno;
	/**
	 * 代理人地址(省)
	 */
	private String agentProvince;
	/**
	 * 代理人地址(市)
	 */
	private String agentCity;
	/**
	 * 代理人地址(详细地址)
	 */
	private String agentAddress;
	/**
	 * 代理人银行卡号
	 */
	private String agentOpenBankNo;
	/**
	 * 代理人银行代码
	 */
	private String agentOpenBankCode;
	/**
	 * 身份证正面
	 */
	private String frontIdcardPicUrl;
	/**
	 * 身份证反面
	 */
	private String backIdcardPicUrl;
	/**
	 * 合同pdf
	 */
	private String contractPicUrl;

	
	private String openid;
	
	//HD admin
	private Integer  signStatus;
	
	private Integer  authStatus;

	private Integer  dataStatus;
	
	private String customerId;

	private String ip;

	
}
