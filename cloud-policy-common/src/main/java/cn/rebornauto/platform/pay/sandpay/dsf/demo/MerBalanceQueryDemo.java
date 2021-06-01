/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014
 * Project : sandpay-dsf-demo
 * $Id$
 * $Revision$
 * Last Changed by pxl at 2018-4-25 下午7:24:10
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
 * 交易：商户余额查询<br>
 * 日期： 2018-04<br>
 * 版本： 1.0.0 
 * 说明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己需要，按照技术文档编写。该代码仅供参考。<br>
 */
public class MerBalanceQueryDemo {

	public static  Logger logger = LoggerFactory.getLogger(MerBalanceQueryDemo.class);

	JSONObject request = new JSONObject();
	
	/** 
	*    组织请求报文      
	*/
	void setRequest() {
		request.put("version", SandBase.version);
		request.put("productId", SandBase.PRODUCTID_AGENTPAY_TOC);
		request.put("tranTime", SandBase.getCurrentTime());
		request.put("orderCode", SandBase.getOrderCode());
		request.put("channelType", "");
		request.put("extend", "");
	}
	
	public static void main(String[] args) throws Exception {
		
		MerBalanceQueryDemo demo = new MerBalanceQueryDemo();
		String reqAddr="/queryBalance";   //接口报文规范中获取
		
		//设置报文
		demo.setRequest();
		
		JSONObject resp = SandBase.requestServer(demo.request.toJSONString(), reqAddr, SandBase.MER_BALANCE_QUERY, null);
		
		System.out.println("resp:"+resp);	
		if(resp!=null) {
			System.out.println("响应码：["+resp.getString("respCode")+"]");	
			logger.info("响应码：["+resp.getString("respCode")+"]");
			System.out.println("响应描述：["+resp.getString("respDesc")+"]");
			logger.info("响应描述：["+resp.getString("respDesc")+"]");
			System.out.println("余额：["+resp.getString("balance")+"]");
			logger.info("余额：["+resp.getString("balance")+"]");
		}else {
			System.out.println("服务器请求异常！！！");
			logger.error("服务器请求异常！！！");	
		}
	}


}
