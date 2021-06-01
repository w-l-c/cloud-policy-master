package cn.rebornauto.platform.business.vo;

import lombok.Data;

@Data
public class GzhAgentInfoVo {

	private Integer  id;
	
	private String openid;

	private Integer  nationalityId;
	
	private Integer  idType;

	private String agentName;
	
	private String agentIdcardno;

/*	private String agentBankcardImgPicUrl;*/
	
	private String agentOpenBankNo;
	
	private String agentOpenBankName;

	private String agentOpenBankCode;

	private String agentMobile;

	private String frontIdcardPicUrl;

	private String backIdcardPicUrl;
	
	private String agentQrCodePicUrl;
	
	private String agentQrCodeImgPostUrl;	
	
	private String customerId;
	private String agentProvince;
	private String agentCity;
	private String agentAddress;

	private Integer  contractStatus;

}
