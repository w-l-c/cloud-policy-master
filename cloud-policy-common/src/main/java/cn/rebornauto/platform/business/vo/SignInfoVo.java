package cn.rebornauto.platform.business.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SignInfoVo {
	private Integer id;
	private Integer agentId;
	private Integer signStatus;
	private String agentName;
	private String agentProvince;
	private String agentCity;
	private String agentAddress;
	private String agentIdcardno;
	private String agentMobile;
	private String agentAccount;
	private String agentEmail;
	private Integer daiZhengId;	
	private String daiZhengName;
	private String daiZhengAddress;
	private String daiZhengLinkMan;
	private String daiZhengLinkMobile;
	private String daiZhengLinkEmail;
	private String daiZhengAccount;
	private String imageName;
	private String contractNumber;
	private LocalDateTime signTime;
}
