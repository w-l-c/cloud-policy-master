package cn.rebornauto.platform.business.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class PersonalCertificateStatusVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer agentId;
	private String account;
	private String taskId;
	
}
