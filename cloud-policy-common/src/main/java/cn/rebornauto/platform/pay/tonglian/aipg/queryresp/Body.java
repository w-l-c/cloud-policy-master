package cn.rebornauto.platform.pay.tonglian.aipg.queryresp;

import java.util.ArrayList;
import java.util.List;

import cn.rebornauto.platform.pay.tonglian.aipg.queryreq.Query_Trans;

public class Body {
	private Query_Trans QUERY_TRANS;
	private List details = new ArrayList( );

	public Query_Trans getQUERY_TRANS()
	{
		return QUERY_TRANS;
	}
	public void setQUERY_TRANS(Query_Trans qUERYTRANS)
	{
		QUERY_TRANS = qUERYTRANS;
	}
	public List getDetails() {
		return details;
	}
	public void setDetails(List details) {
		this.details = details;
	}
	
	public void addDetail(Ret_Detail detail) {
		details.add(detail);
	}
}
