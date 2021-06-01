package cn.rebornauto.platform.business.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/** Title: 
 * Description: 
 * Company: 
 * @author kgc
 * @date Mar 20, 2020 10:16:59 AM
 */
@Data
public class AgentQuotaVO implements Serializable{

	private static final long serialVersionUID = -4093196282538419698L;

	private String openBankNo;
	
	private String agentIdcardno;
	
	private BigDecimal amount;
	
	private String yearmonth;
	
	private String agentName;
}
