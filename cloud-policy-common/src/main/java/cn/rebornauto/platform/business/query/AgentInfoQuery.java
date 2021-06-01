package cn.rebornauto.platform.business.query;

import cn.rebornauto.platform.common.data.request.Query;
import lombok.Data;


@Data
public class AgentInfoQuery extends Query {
	
	private String  openid;
	
	private String    customerId;

	private String    agentName;
	
	private String    agentIdcardno;
	
	private String    createtime;
	
	private String    startTime;
	
	private String    endTime;
	
	private Integer    authStatus;
	
	private Integer    signStatus;
	
	private Integer    dataStatus;

    public void setCreatetime(String createtime) {
	        this.createtime = createtime;
	        if (createtime != null && !"".equals(createtime)) {
	            String[] split = createtime.split("~");
	            this.startTime = split[0];
	            this.endTime = split[1];
	        }
	   }
}
