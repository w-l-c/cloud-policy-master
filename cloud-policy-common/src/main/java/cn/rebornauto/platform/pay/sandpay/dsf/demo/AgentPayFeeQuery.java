/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014
 * Project : sandpay-dsf-demo
 * $Id$
 * $Revision$
 * Last Changed by pxl at 2018-4-25 下午6:05:55
 * $URL$
 * 
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * pxl         2018-4-25        Initailized
 */
package cn.rebornauto.platform.pay.sandpay.dsf.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**
 * 产品：杉德代收付产品<br>
 * 交易：代付手续费查询<br>
 * 日期： 2018-04<br>
 * 版本： 1.0.0 
 * 说明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己需要，按照技术文档编写。该代码仅供参考。<br>
 */
public class AgentPayFeeQuery {

	public static  Logger logger = LoggerFactory.getLogger(AgentPayFeeQuery.class);
	
	
	JSONObject request = new JSONObject();
	
	/** 
	* 组织请求报文     
	*/
	private void setRequest() {
		
		request.put("version", SandBase.version);                        // 版本号      
		request.put("productId", SandBase.PRODUCTID_AGENTPAY_TOC);        // 产品ID      
		request.put("tranTime", SandBase.getCurrentTime());               // 交易时间    
		request.put("orderCode", SandBase.getOrderCode());                // 订单号      
		request.put("tranAmt", "000000000012");                           // 金额        
		request.put("currencyCode", SandBase.CURRENCY_CODE);              // 币种        
		request.put("accAttr", "0");                                      // 账户属性     0-对私   1-对公
		request.put("accType", "4");                                      // 账号类型    3-公司账户  4-银行卡
		request.put("accNo", "6216261000000000018");                      // 收款人账户号
		request.put("channelType", "07");                                 // 渠道类型     07-互联网  08-移动端
		request.put("extend", "");                                        // 扩展域      
		request.put("areaFlag", "");                                      // 区域标识 
	}
	
	
	
	public static void main(String[] args) throws Exception {
		
		AgentPayFeeQuery demo = new AgentPayFeeQuery();
		String reqAddr="/queryAgentpayFee";   //接口报文规范中获取
		//设置报文
		demo.setRequest();
		
		
		JSONObject resp = SandBase.requestServer(demo.request.toJSONString(), reqAddr, SandBase.AGENT_PAY_FEE_QUERY, null);
		
		if(resp!=null) {
			logger.info("响应码：["+resp.getString("respCode")+"]");	
			logger.info("响应描述：["+resp.getString("respDesc")+"]");
			logger.info("手续费：["+resp.getString("tranFee")+"]");
		}else {
			logger.error("服务器请求异常！！！");	
		}	

	}



}
