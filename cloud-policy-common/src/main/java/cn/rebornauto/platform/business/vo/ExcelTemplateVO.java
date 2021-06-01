package cn.rebornauto.platform.business.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/** Title: 
 * Description: 
 * Company: 
 * @author kgc
 * @date May 10, 2019 11:51:50 AM
 */
@Data
public class ExcelTemplateVO implements Serializable{

	private static final long serialVersionUID = 1591627359170665981L;

	private String idcardno;
	
	private String openBankNo;
	
	private String agentName;
	
	private String policyNo;
	
	private BigDecimal policyAmount;
	
	private BigDecimal agentCommission;
	
	private String outtime;
	/**
	 * 被保险人 
	 */
	private String policyPerson;
	/**
	 * 备注1
	 */
	private String remark;
	/**
	 * 备注2
	 */
	private String remark2;
	/**
	 * 备注3
	 */
	private String remark3;

}
