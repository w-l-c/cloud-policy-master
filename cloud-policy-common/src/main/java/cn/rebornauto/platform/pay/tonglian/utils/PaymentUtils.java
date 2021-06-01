package cn.rebornauto.platform.pay.tonglian.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.io.IOUtils;

import cn.rebornauto.platform.common.util.LogUtil;
import cn.rebornauto.platform.pay.tonglian.aipg.common.InfoReq;
import cn.rebornauto.platform.pay.tonglian.allinpay.XmlTools;
import cn.rebornauto.platform.pay.tonglian.entity.TongLianInfo;

/**
 * @autor ligewei
 * @date 2017年11月15日
 */
public class PaymentUtils {

    private static SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
    /**
     * 组装报文头部
     * @param trxcod
     * @return
     *日期：Sep 9, 2012
     */
    public static InfoReq makeReq(String trxcod,TongLianInfo tongLianInfo)
    {
        InfoReq info=new InfoReq();
        info.setTRX_CODE(trxcod);
        info.setREQ_SN(tongLianInfo.getMerchantId()+"-"+String.valueOf(System.currentTimeMillis())+"-"+ (int)(Math.random()*1000));
        info.setUSER_NAME(tongLianInfo.getUserName());
        info.setUSER_PASS(tongLianInfo.getPassword());
        info.setLEVEL("5");
        info.setDATA_TYPE("2");
        info.setVERSION("04");
        if("300000".equals(trxcod)||"300001".equals(trxcod)||"300003".equals(trxcod)||"REFUND".equals(trxcod)){
            info.setMERCHANT_ID(tongLianInfo.getMerchantId());
        }
        return info;
    }

    public static String sendXml(String xml,String url,boolean isFront,TongLianInfo tongLianInfo) throws UnsupportedEncodingException, Exception{
    	LogUtil.debug("======================发送报文======================：\n"+xml);
        String resp=XmlTools.send(url,xml);
    	LogUtil.debug("======================响应内容======================：\n"+resp);
        boolean flag= verifyMsg(resp, tongLianInfo.getTltcerPath(),isFront);
        if(flag){
        	LogUtil.debug("响应内容验证通过");
        }else{
        	LogUtil.debug("响应内容验证不通过");
        }
        return resp;
    }
    
    public static String sendToTlt(String xml,boolean flag,String url,TongLianInfo tongLianInfo)throws Exception {
        if(!flag){
            xml=signMsg(xml,tongLianInfo);
        }else{
            xml=xml.replaceAll("<SIGNED_MSG></SIGNED_MSG>", "");
        }
        return sendXml(xml,url,flag,tongLianInfo);

    }
    /**
     * 报文签名
     * @param xml
     * @return
     *日期：Sep 9, 2012
     * @throws Exception
     */
    public static String signMsg(String xml,TongLianInfo tongLianInfo) throws Exception{
    	System.out.println("PfxPath:"+tongLianInfo.getPfxPath());
    	System.out.println("PfxPassword:"+tongLianInfo.getPfxPassword());
        xml= XmlTools.signMsg(xml, tongLianInfo.getPfxPath(),tongLianInfo.getPfxPassword(), false);
        return xml;
    }

    /**
     * 验证签名
     * @param msg
     * @return
     *日期：Sep 9, 2012
     * @throws Exception
     */
    public static boolean verifyMsg(String msg,String cer,boolean isFront) throws Exception{
        boolean flag=XmlTools.verifySign(msg, cer, false,isFront);
        System.out.println("验签结果["+flag+"]") ;
        return flag;
    }

    /**
     * 解析对账文件并保存在项目内
     * @param resp
     * @throws Exception
     */
    public static void writeBill(String resp) throws Exception {

        int iStart = resp.indexOf("<CONTENT>");
        if(iStart==-1) throw new Exception("XML报文中不存在<CONTENT>");
        int end = resp.indexOf("</CONTENT>");
        if(end==-1) throw new Exception("XML报文中不存在</CONTENT>");
        String billContext = resp.substring(iStart + 9, end);

        //写文件
        FileOutputStream sos=null;
        sos=new FileOutputStream(new File("bills/bill.gz"));
        Base64InputStream b64is=new Base64InputStream(IOUtils.toInputStream(billContext),false);
        IOUtils.copy(b64is, sos);
        IOUtils.closeQuietly(b64is);
        //解压
        ZipInputStream zin=new ZipInputStream(new FileInputStream(new File("bills/bill.gz")));
        ZipEntry zipEntry=null;
        while ((zipEntry = zin.getNextEntry()) != null) {
            String entryName = zipEntry.getName().toLowerCase();
            FileOutputStream os = new FileOutputStream("bills/"+entryName);
            // Transfer bytes from the ZIP file to the output file
            byte[] buf = new byte[1024];
            int len;
            while ((len = zin.read(buf)) > 0) {
                os.write(buf, 0, len);
            }
            os.close();
            zin.closeEntry();
        }
    }

}
