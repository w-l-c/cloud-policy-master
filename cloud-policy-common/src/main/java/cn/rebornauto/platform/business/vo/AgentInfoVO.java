package cn.rebornauto.platform.business.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class AgentInfoVO implements Serializable {
	/**
     * 代理人id
     */
    private Integer agentId;

    /**
     * 代理人名字
     */
    private String agentName;
    
    /**
     * 代理人银行卡号
     */
    private String agentOpenBankNo;

    /**
     * 代理人开户行名称
     */
    private String agentOpenBankName;

    /**
     * 代理人开户行代码
     */
    private String agentOpenBankCode;

    /**
     * 代理人身份证号
     */
    private String agentIdcardno;

    

    private static final long serialVersionUID = 1L;



	@Override
	public String toString() {
		return "AgentInfoVO [agentId=" + agentId + ", agentName=" + agentName
				+ ", agentOpenBankNo=" + agentOpenBankNo
				+ ", agentOpenBankName=" + agentOpenBankName
				+ ", agentOpenBankCode=" + agentOpenBankCode
				+ ", agentIdcardno=" + agentIdcardno + "]";
	}




}