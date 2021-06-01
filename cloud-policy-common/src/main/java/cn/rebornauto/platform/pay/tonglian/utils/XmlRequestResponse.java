package cn.rebornauto.platform.pay.tonglian.utils;
/** Title: 报文请求及返回内容
 * Description: 
 * Company: 
 * @author kgc
 * @date Apr 7, 2018 2:35:41 PM
 */
public class XmlRequestResponse {

	private String xmlRequest;
	
	private String xmlResponse;

	public String getXmlRequest() {
		return xmlRequest;
	}

	public void setXmlRequest(String xmlRequest) {
		this.xmlRequest = xmlRequest;
	}

	public String getXmlResponse() {
		return xmlResponse;
	}

	public void setXmlResponse(String xmlResponse) {
		this.xmlResponse = xmlResponse;
	}

	@Override
	public String toString() {
		return "XmlRequestResponse [xmlRequest=" + xmlRequest
				+ ", xmlResponse=" + xmlResponse + "]";
	}
	
	
}
