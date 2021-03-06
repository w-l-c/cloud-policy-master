package cn.rebornauto.platform.pay.tonglian.aipg.payreq;



import cn.rebornauto.platform.pay.tonglian.aipg.common.InfoReq;
import cn.rebornauto.platform.pay.tonglian.aipg.common.XStreamEx;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class Process {
	
	public XStream getXStream( ) {
		XStream xstream = new XStreamEx(new DomDriver());
		xstream.alias("AIPG", AIPG.class);
		xstream.alias("INFO", InfoReq.class);
		xstream.alias("TRANS_SUM", Trans_Sum.class);
		
		xstream.alias("TRANS_DETAIL", Trans_Detail.class);
		xstream.aliasField("TRANS_DETAILS", Body.class, "details");
		return xstream;
	}
	
	public  AIPG parseXML(String strData) {		
		return (AIPG)getXStream().fromXML(strData);
	}
	
	public  String formXML(AIPG obj) {
		return getXStream().toXML(obj);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
	} 

}
