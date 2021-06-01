/**
 *项目：xmlInterf
 *通联支付网络有限公司
 * 作者：张广海
 * 日期：Jan 2, 2013
 * 功能说明：系统对接demo
 */
package cn.rebornauto.platform.pay.tonglian.allinpay.xmltrans.demo;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import cn.rebornauto.platform.pay.tonglian.allinpay.XmlTools;
import cn.rebornauto.platform.pay.tonglian.allinpay.xmltrans.service.TranxServiceImpl;

//import com.allinpay.xmltrans.service.TranxServiceImpl;

/**
 * 特别申明，该demo仅供学习参考，未经过严格测试，不作为通联发布版本，其中有可能存在bug，
 * 请严格参照对接文档的格式要求，以及返回码说明进行系统 对接
 */
public class TranxMain {

	/**
	 * 外网ip；113.108.182.3 
	 * 端口：8083（http）
	 * 端口：443（https）
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		String testTranURL1 = "http://172.16.1.11/aipg/query";//通联测试环境，内网（与查询有关的使用）
		//String testTranURL="https://113.108.182.3/aipg/ProcessServlet"; //通联测试环境，外网（商户测试使用）
		String testTranURL="https://113.108.182.3/debugaipg/ProcessServlet"; //通联测试环境，外网（商户测试使用）
		String testTranURL11="https://172.16.1.11/aipg/ProcessServlet"; //通联测试环境，内网（通联内部技术使用）
		String tranURL="http://tlt.allinpay.com/aipg/ProcessServlet";//通联生产环境（商户上线时使用）
		String testURLOfBill="https://113.108.182.3/aipg/GetConFile.do?SETTDAY=@xxx&REQTIME=@yyy&MERID=@zzz&SIGN=@sss&CONTFEE=1"; //
		String URLOfBill="https://tlt.allinpay.com/aipg/GetConFile.do?SETTDAY=@xxx&REQTIME=@yyy&MERID=@zzz&SIGN=@sss&CONTFEE=1"; //
		String testURLOfBill1="https://172.16.1.11/aipg/GetConFile.do?SETTDAY=@xxx&REQTIME=@yyy&MERID=@zzz&SIGN=@sss&CONTFEE=1";
		boolean isfront=false;//是否发送至前置机（由前置机进行签名）如不特别说明，商户技术不要设置为true
		String reqsn="1404826943281";//交易流水号(交易结果查询时，待查询的原支付交易流水号)
		String trx_code,busicode;//100001批量代收 100002批量代付 100011单笔实时代收 100014单笔实时代付
		TranxServiceImpl tranxService=new TranxServiceImpl();
		String testURLftp="https://172.16.1.11/aipg/UploadUserFile";
		/**
		 * 测试的时候不用修改以下业务代码，但上生产环境的时候，必须使用业务人员提供的业务代码，否则返回“未开通业务类型”
		 * 另外，特别说明：如果生产环境对接的时候返回”未开通产品“那么说明该商户开通的接口与目前测试的接口不一样，需要找业务确认
		 * 代收是批量代收接口的简称，代付 是批量代付接口的简称，
		 * 对接报文中，info下面的用户名一般是：商户号+04，比如商户号为：200604000000445，那么对接用户一般为：20060400000044504
		 */
		//trx_code="100001";
		//trx_code="REFUND";
		trx_code="100014";
		//trx_code="REFUND";
		if("100011".equals(trx_code) || "100001".equals(trx_code))//收款的时候，填写收款的业务代码
			busicode="14902";
		else
			busicode="09001";
		//设置安全提供者,注意，这一步尤为重要
		BouncyCastleProvider provider = new BouncyCastleProvider();
		XmlTools.initProvider(provider);
		
		//tranxService.getAccountInfo(testTranURL,isfront);
		//tranxService.batchTranx(testTranURL,trx_code,busicode, isfront);
//		tranxService.downBills(testTranURL, isfront);
		//签约结果通知
		//tranxService.signNotice(NOTICEURL, isfront);
		//简单对账文件下载
		//tranxService.downSimpleBills(testURLOfBill, true);
		//交易查询
	   //tranxService.queryTradeNew(testTranURL, "200604000000445-1510738899631", isfront,"","");
		//单笔退款，一般busicode取09200
		//busicode="09200";
		//tranxService.refundTranx(testTranURL, trx_code, busicode, isfront);
	
		
		//单笔实时代收
	tranxService.singleTranx(testTranURL, trx_code, busicode,isfront);

		/*for(int a=0;a<2;a++){
			tranxService.singleTranx(testTranURL,trx_code, busicode,isfront);
			Thread.sleep(3000);
		}*/
		
		 
		//商户虚拟户历史余额查询
		//tranxService.merAcctBalance(testTranURL,isfront,"20140805","20140806");
		
		//单笔实时身份验证
		//tranxService.singleAcctVerify(testTranURL,isfront);
		
		//批量身份验证
//		tranxService.batchAcctVerify(testTranURL, isfront);
		//账户余额查询
	//tranxService.acctBalance(testTranURL1, isfront, "20140805", "20140806");
		
		//国民身份验证
		//tranxService.idVerify(testTranURL, isfront);

		//国民身份验证查询
	//	tranxService.idVerifyQ(testTranURL, isfront);
		
		//实名付申请
	//	tranxService.rnpA(testTranURL, isfront);
		
		//实名付短信重发
//		tranxService.rnpR(testTranURL, isfront);
		
		//实名付确认
//		tranxService.rnpC(tranURL, isfront);
		
		//实名付结果查询
//	tranxService.rnpQ(tranURL, isfront);
		
		//内部转账
		//tranxService.acctTransferReq(testTranURL, isfront);
		//tranxService.putmoney(testTranURL, isfront);
		//ftp+xml
		
	//tranxService.ftpxml(testURLftp, isfront);
		
	}
	

}
