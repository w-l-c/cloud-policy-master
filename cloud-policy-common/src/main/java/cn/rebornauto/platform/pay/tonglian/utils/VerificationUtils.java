package cn.rebornauto.platform.pay.tonglian.utils;

/**
 *项目：xmlInterf
 *通联支付网络有限公司
 * 作者：张广海
 * 日期：Jan 2, 2013
 * 功能说明：系统对接xml 测试demo
 */

import java.text.SimpleDateFormat;

import cn.rebornauto.platform.common.util.LogUtil;
import cn.rebornauto.platform.pay.tonglian.aipg.common.AipgReq;
import cn.rebornauto.platform.pay.tonglian.aipg.common.InfoReq;
import cn.rebornauto.platform.pay.tonglian.aipg.singleacctvalid.ValidR;
import cn.rebornauto.platform.pay.tonglian.allinpay.XmlTools;
import cn.rebornauto.platform.pay.tonglian.allinpay.xmltrans.pojo.TranxCon;
import cn.rebornauto.platform.pay.tonglian.entity.TongLianInfo;


/**
 */
public class VerificationUtils {
	TranxCon tranxContants=new TranxCon();
	SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

	/**
     * 组装报文头部
     * @param trxcod
     * @return
     *日期：Sep 9, 2012
     */
      private static InfoReq makeReq(String trxcod,TongLianInfo tongLianInfo)
       {
        InfoReq info=new InfoReq();
        info.setTRX_CODE(trxcod);
        info.setREQ_SN(tongLianInfo.getMerchantId()+"-"+String.valueOf(System.currentTimeMillis()));
        info.setUSER_NAME(tongLianInfo.getUserName());
        info.setUSER_PASS(tongLianInfo.getPassword());
        info.setLEVEL("5");
        info.setDATA_TYPE("2");
        info.setVERSION("03");
        if("300000".equals(trxcod)||"300001".equals(trxcod)||"300003".equals(trxcod)||"REFUND".equals(trxcod)){
            info.setMERCHANT_ID(tongLianInfo.getMerchantId());
        }
        return info;
      }
	
	   private static String sendXml(String xml,TongLianInfo tongLianInfo,boolean isFront) throws  Exception{
		   LogUtil.debug("======================发送报文======================：\n"+xml);
	        String resp=XmlTools.send(tongLianInfo.getUrl(),xml);
	        boolean flag= verifyMsg(resp, tongLianInfo.getTltcerPath(),isFront);
	        if(flag){
	        	LogUtil.debug("======================响应内容验证通过:响应内容======================：\n"+resp) ;
	            return resp;
	        }else{
	        	LogUtil.debug("======================响应内容验证不通过:响应内容======================：\n"+resp) ;
	            throw new RuntimeException("验签失败");
	        }
	    }

	  /**
     * 签名并且发送给通联
     * @param xml
     * @param flag
     * @param url
     * @return
     */
    private static String sendToTlt(String xml,boolean flag,TongLianInfo tongLianInfo) {
        try{
            if(!flag){
                xml=signMsg(xml,tongLianInfo);
            }else{
                xml=xml.replaceAll("<SIGNED_MSG></SIGNED_MSG>", "");
            }
            return sendXml(xml,tongLianInfo,flag);
        }catch(Exception e){
           throw new RuntimeException(e);
        }
       // return "请求链接中断，如果是支付请求，请做交易结果查询，以确认该笔交易是否已被通联受理，避免重复交易";
    }
	
    /**
     * 报文签名
     * @param xml
     * @return
     *日期：Sep 9, 2012
     * @throws Exception
     */
    private static String signMsg(String xml,TongLianInfo tongLianInfo) throws Exception{
        xml=XmlTools.signMsg(xml, tongLianInfo.getPfxPath(),tongLianInfo.getPfxPassword(), false);
        return xml;
    }

    /**
     * 验证签名
     * @param msg
     * @return
     *日期：Sep 9, 2012
     * @throws Exception
     */
    private static boolean verifyMsg(String msg,String cer,boolean isFront) throws Exception{
        boolean flag=XmlTools.verifySign(msg, cer, false,isFront);
        return flag;
    }
	
	   /**
     * 日期：Sep 4, 2012
     * 功能：单笔实时身份验证
     * @throws Exception
     */
    public static String singleAcctVerify(String trxcod,ValidR valid,TongLianInfo tongLianInfo) throws Exception {
        String xml="";
        AipgReq aipgReq=new AipgReq();
		InfoReq info=makeReq(trxcod,tongLianInfo);//211003三要素，211003四要素
        aipgReq.setINFO(info);
        aipgReq.addTrx(valid);
        xml= XmlTools.buildXml(aipgReq,true);
        String resp = sendToTlt(xml,false,tongLianInfo);
        return resp;
    }
}
