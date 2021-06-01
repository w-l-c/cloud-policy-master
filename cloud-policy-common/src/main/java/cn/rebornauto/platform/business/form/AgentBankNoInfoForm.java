package cn.rebornauto.platform.business.form;

import lombok.Data;
import cn.rebornauto.platform.common.data.request.Form;


@Data
public class AgentBankNoInfoForm extends  Form {
	
	private String openid;
	private String agentName;
	private String agentIdcardno;
	//private String agentMobile;
	private String agentOpenBankNo;
	private String agentOpenBankCode;
	private String customerId;
	//private String smscode;
	private Integer idType;
}
