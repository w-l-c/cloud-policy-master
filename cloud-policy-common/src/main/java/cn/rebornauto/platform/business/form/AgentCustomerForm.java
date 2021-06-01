package cn.rebornauto.platform.business.form;

import lombok.Data;
import cn.rebornauto.platform.common.data.request.Form;
@Data
public class AgentCustomerForm extends  Form {
	
	private String openid;
	
	private String pdfFillValuePaht;

	private int type;

}
