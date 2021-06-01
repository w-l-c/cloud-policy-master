package cn.rebornauto.platform.business.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AgentVo implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer agentId;
	private String customerId;
	private String agentName;
	private String agentIdcardno;
	private String frontIdcardPicUrl;
	private String backIdcardPicUrl;
	private String agentMobile;
	private String createtime;
	private Integer authStatus;
	private Integer signStatus;
	private Integer dataStatus;	
	private List<AgentBankNoVo> bankList;	
	private String contractUrl;
	private String customerName;
	private String xmsmqswjwtsUrl;
	private String gxjjhzhbxyUrl;
}
