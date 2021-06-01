/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014
 * Project : sandpay-dsf-demo
 * $Id$
 * $Revision$
 * Last Changed by pxl at 2018-4-25 下午6:18:37
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
 * 交易：对账单申请<br>
 * 日期： 2018-04<br>
 * 版本： 1.0.0 
 * 说明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己需要，按照技术文档编写。该代码仅供参考。<br>
 */
public class ClearFileContextDemo {

	public static  Logger logger = LoggerFactory.getLogger(ClearFileContextDemo.class);
	
	
	JSONObject request = new JSONObject();
	
	/** 
	*  组织请求报文          
	*/
	private void setRequest() {	
		request.put("version", SandBase.version);					  // 版本号      
		request.put("clearDate", SandBase.getCurrentDate());          // 对账日期    
		request.put("busiType", "2");                                 // 业务类型    1-代付业务 2-代收业务
		request.put("fileType", "1");                                 // 文件返回类型   1-文件下载链接(默认)
	}
	
	
	public static void main(String[] args) throws Exception {
		
		ClearFileContextDemo demo = new ClearFileContextDemo();
		String reqAddr="/getClearFileContent";   //接口报文规范中获取
		
		//设置报文
		demo.setRequest();
		
		
		JSONObject resp = SandBase.requestServer(demo.request.toJSONString(), reqAddr, SandBase.CLEAR_FILE_CONTEXT,null);
		
		if(resp!=null) {
			logger.info("响应码：["+resp.getString("respCode")+"]");	
			logger.info("响应描述：["+resp.getString("respDesc")+"]");
			logger.info("内容(文件下载链接)：["+resp.getString("content")+"]");
		}else {
			logger.error("服务器请求异常！！！");	
		}	

	}

}
