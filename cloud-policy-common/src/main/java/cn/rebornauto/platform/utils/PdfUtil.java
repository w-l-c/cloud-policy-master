package cn.rebornauto.platform.utils;


import cn.rebornauto.platform.bestSign.BestsignClient;
import cn.rebornauto.platform.bestSign.entity.BestSignInfo;
import cn.rebornauto.platform.bestSign.entity.GxjjhzhbxyInfo;
import cn.rebornauto.platform.bestSign.entity.XmsmqswjwtsInfo;
import cn.rebornauto.platform.bestSign.utils.PdfTemplate;
import cn.rebornauto.platform.business.vo.SignInfoVo;
import cn.rebornauto.platform.common.Const;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;

public class PdfUtil {
	
    private Logger logger = LoggerFactory.getLogger(this.getClass());


   /**
    * 共享经济合作伙伴协议合同填值
    * @param bestSignInfo
    * @param shangShangQianInfo
    * @param form
    * @param signInfoVo
    * @return
    */
	public  String gxjjhzhbxyFillValue(BestSignInfo bestSignInfo,GxjjhzhbxyInfo shangShangQianInfo,SignInfoVo signInfoVo,long currentTimeMillis) {	
	    	//签约时间
             LocalDateTime localDateTime =  LocalDateTime.now();
           //合同编号
	        String contractNumber="yb"+currentTimeMillis;
            signInfoVo.setSignTime(localDateTime);
            signInfoVo.setContractNumber(contractNumber);
            //填完值合同的名称
            String name = contractNumber+shangShangQianInfo.getGxjjhzhbxyName();    
		    // 新生成pdf的位置
		    String  gxjjhzhbxyPath= bestSignInfo.getPdfPath()+"/"+name;	    
		    // 模板的文件
			String modelGxjjhzhbxyPath =bestSignInfo.getModelPdfPath() +"/"+shangShangQianInfo.getModelGxjjhzhbxyName();
			try {
				HashMap<String, String> hashMap = new HashMap<>();
				if(signInfoVo.getDaiZhengId()==Const.DAI_ZHENG_ID_1) {
//					hashMap.put("contractNumber", contractNumber);
//					hashMap.put("signTime", new SimpleDateFormat("yyyy年MM月dd日").format(new Date()));
//					hashMap.put("daiZhengLinkMan", signInfoVo.getDaiZhengLinkMan());
//					hashMap.put("daiZhengLinkMobile", signInfoVo.getDaiZhengLinkMobile());
//					hashMap.put("daiZhengLinkEmail", signInfoVo.getDaiZhengLinkEmail());
//					hashMap.put("agentName", signInfoVo.getAgentName());
//					hashMap.put("agentAddress", signInfoVo.getAgentAddress());
//					hashMap.put("agentIdcardno", signInfoVo.getAgentIdcardno());
//					hashMap.put("agentMobile", signInfoVo.getAgentMobile());
//					hashMap.put("agentEmail", signInfoVo.getAgentEmail());
					LocalDateTime startDate = LocalDateTime.now();					
					hashMap.put("signArea","上海");
					hashMap.put("contractNumber",contractNumber);
					hashMap.put("agentName", signInfoVo.getAgentName());
					hashMap.put("agentAddress", signInfoVo.getAgentAddress());
					hashMap.put("agentIdcardno", signInfoVo.getAgentIdcardno());
					hashMap.put("agentMobile", signInfoVo.getAgentMobile());
					hashMap.put("agentEmail", signInfoVo.getAgentEmail());
					hashMap.put("syy", startDate.getYear()+"");
					hashMap.put("smm",startDate.getMonthValue()+"");
					hashMap.put("sdd", startDate.getDayOfMonth()+"");
					//两年后日期
					LocalDateTime endDate = startDate.plusYears(2);
					hashMap.put("eyy", endDate.getYear()+"");
					hashMap.put("emm",endDate.getMonthValue()+"");
					hashMap.put("edd", endDate.getDayOfMonth()+"");
				}else if(signInfoVo.getDaiZhengId()==Const.DAI_ZHENG_ID_2) {
					LocalDateTime startDate = LocalDateTime.now();					
					hashMap.put("signArea","上海");
					hashMap.put("contractNumber",contractNumber);
					hashMap.put("agentName", signInfoVo.getAgentName());
					hashMap.put("agentAddress", signInfoVo.getAgentAddress());
					hashMap.put("agentIdcardno", signInfoVo.getAgentIdcardno());
					hashMap.put("agentMobile", signInfoVo.getAgentMobile());
					hashMap.put("agentEmail", signInfoVo.getAgentEmail());
					hashMap.put("syy", startDate.getYear()+"");
					hashMap.put("smm",startDate.getMonthValue()+"");
					hashMap.put("sdd", startDate.getDayOfMonth()+"");
					//两年后日期
					LocalDateTime endDate = startDate.plusYears(2);
					hashMap.put("eyy", endDate.getYear()+"");
					hashMap.put("emm",endDate.getMonthValue()+"");
					hashMap.put("edd", endDate.getDayOfMonth()+"");
				}
				
				PdfTemplate.doSomeThingTest(gxjjhzhbxyPath, hashMap, modelGxjjhzhbxyPath,bestSignInfo.getSimsun());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error(e.toString());
				 gxjjhzhbxyPath="500";
			}
		    return gxjjhzhbxyPath;
	}

	public String xmsmqswjwts(BestSignInfo bestSignInfo, XmsmqswjwtsInfo shangShangQianInfo, SignInfoVo signInfoVo){
		//签约时间
		LocalDateTime now =  LocalDateTime.now();
		//填完值合同的名称
		String name = System.currentTimeMillis()+shangShangQianInfo.getXmsmqswjwtsName();

		String xmsmqswjwtsPath=bestSignInfo.getPdfPath()+"/"+name;
		// 模板的文件
		String modelXmsmqswjwtsPath =bestSignInfo.getModelPdfPath() +"/"+shangShangQianInfo.getModelXmsmqswjwtsName();

		try {
			HashMap<String, String> hashMap = new HashMap<>();
			String agentAddress = signInfoVo.getAgentAddress();
			if(signInfoVo.getAgentProvince()!=null){
				if (signInfoVo.getAgentProvince().equals(signInfoVo.getAgentCity()))	{
					agentAddress = signInfoVo.getAgentProvince()+signInfoVo.getAgentAddress();
				}else{
					agentAddress = signInfoVo.getAgentProvince()+signInfoVo.getAgentCity()+signInfoVo.getAgentAddress();
				}
			}
			hashMap.put("agentAddress", agentAddress);
			hashMap.put("agentIdcardno", signInfoVo.getAgentIdcardno());
			hashMap.put("agentMobile", signInfoVo.getAgentMobile());
			hashMap.put("agentEmail", signInfoVo.getAgentEmail());
			hashMap.put("sYy", now.getYear() + "");
			hashMap.put("sMm", now.getMonthValue() + "");
			hashMap.put("sDd", now.getDayOfMonth() + "");

			PdfTemplate.doSomeThingTest(xmsmqswjwtsPath, hashMap, modelXmsmqswjwtsPath, bestSignInfo.getSimsun());
		}catch (Exception e){
			logger.error(e.toString());
			xmsmqswjwtsPath="500";
		}
		return xmsmqswjwtsPath;
	}

	
public static void main(String[] args) {
	BestSignInfo bestSignInfo = new BestSignInfo();
	bestSignInfo.setSimsun("D:/pdf/simsun.ttc,1");
	bestSignInfo.setPdfPath("D:/pdf");
	bestSignInfo.setModelPdfPath("D:/pdf");
	XmsmqswjwtsInfo shangShangQianInfo = new XmsmqswjwtsInfo();
	shangShangQianInfo.setXmsmqswjwtsName("tjxmsmqswjwts.pdf");
	shangShangQianInfo.setModelXmsmqswjwtsName("modelxmsmqswjwts.pdf");
	SignInfoVo signInfoVo= new SignInfoVo();
	signInfoVo.setAgentAddress("一二三四五六七八九零");
	signInfoVo.setDaiZhengId(1);
	PdfUtil pdfUtil = new PdfUtil();
	pdfUtil.xmsmqswjwts(bestSignInfo,shangShangQianInfo,signInfoVo)
;
}
	/**
	 * 封装上传pdf所需字段
	 * @param account
	 * @param filePath
	 * @param fileName
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public  static com.alibaba.fastjson.JSONObject wrapUploadRequestBody(String account,String filePath, String fileName, String pageSize)throws Exception{
		byte [] bdata = BestsignClient.inputStream2ByteArray(filePath);
		String fmd5 = DigestUtils.md5Hex(bdata);
		com.alibaba.fastjson.JSONObject requestBody = new com.alibaba.fastjson.JSONObject();
		requestBody.put("account",account);
		requestBody.put("fmd5",fmd5);
		requestBody.put("ftype","PDF");
		requestBody.put("fname",fileName);
		requestBody.put("fpages",pageSize);
		requestBody.put("fdata", Base64.getEncoder().encodeToString(bdata));
		requestBody.put("title",fileName);
		requestBody.put("description",fileName);
		long time = System.currentTimeMillis();
		time = time + 60*60*1000*24*7;
		String timeStr = time + "";
		timeStr = timeStr.substring(0,timeStr.length()-3);
		requestBody.put("expireTime",timeStr);
		return requestBody;
	}
	
	
	/**
	 * 封装上传pdf所需字段
	 * @param account
	 * @param bdata
	 * @param fileName
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public  static com.alibaba.fastjson.JSONObject wrapUploadRequestBody(String account,byte [] bdata, String fileName, String pageSize)throws Exception{
		String fmd5 = DigestUtils.md5Hex(bdata);
		com.alibaba.fastjson.JSONObject requestBody = new com.alibaba.fastjson.JSONObject();
		requestBody.put("account",account);
		requestBody.put("fmd5",fmd5);
		requestBody.put("ftype","PDF");
		requestBody.put("fname",fileName);
		requestBody.put("fpages",pageSize);
		requestBody.put("fdata", Base64.getEncoder().encodeToString(bdata));
		requestBody.put("title",fileName);
		requestBody.put("description",fileName);
		long time = System.currentTimeMillis();
		time = time + 60*60*1000;
		String timeStr = time + "";
		timeStr = timeStr.substring(0,timeStr.length()-3);
		System.out.println(timeStr);
		requestBody.put("expireTime",timeStr);
		return requestBody;
	}
	
	/**
	 * 封装企业签署pdf所需字段
	 * @param contractId
	 * @param signer
	 * @param x
	 * @param y
	 * @param pageNum
	 * @param signatureImageName
	 * @return
	 * @throws Exception
	 */
	public static com.alibaba.fastjson.JSONObject wrapCertRequestBody(String contractId,String signer,String x,String y,String pageNum,String signatureImageName)throws Exception {
		com.alibaba.fastjson.JSONObject requestBody = new com.alibaba.fastjson.JSONObject();
		requestBody.put("contractId",contractId);
		requestBody.put("signer",signer);
	    requestBody.put("signatureImageName",signatureImageName);
		JSONArray signaturePositions = new JSONArray();
		com.alibaba.fastjson.JSONObject signaturePosition = new com.alibaba.fastjson.JSONObject();
		signaturePosition.put("x",x);
		signaturePosition.put("y",y);
		signaturePosition.put("pageNum",pageNum);
		signaturePositions.add(signaturePosition);
		requestBody.put("signaturePositions",signaturePositions);
		return requestBody;
	}
	
	
	/**
	 * 封装个人签署pdf所需字段
	 * @param contractId
	 * @param signer
	 * @param x
	 * @param y
	 * @param pageNum
	 * @param signatureImageName
	 * @return
	 * @throws Exception
	 */
	public static com.alibaba.fastjson.JSONObject wrapCertRequestBody(String contractId,String signer,String x,String y,String pageNum)throws Exception {
		com.alibaba.fastjson.JSONObject requestBody = new com.alibaba.fastjson.JSONObject();
		requestBody.put("contractId",contractId);
		requestBody.put("signer",signer);
	    requestBody.put("signatureImageWidth","70");
	    requestBody.put("signatureImageHeight","50");
		JSONArray signaturePositions = new JSONArray();
		com.alibaba.fastjson.JSONObject signaturePosition = new com.alibaba.fastjson.JSONObject();
		signaturePosition.put("x",x);
		signaturePosition.put("y",y);
		signaturePosition.put("pageNum",pageNum);
		signaturePositions.add(signaturePosition);
		requestBody.put("signaturePositions",signaturePositions);
		return requestBody;
	}

	public static com.alibaba.fastjson.JSONObject wrapSendRequestBody(String contractId, String signer, String x, String y, String pageNum,String returnUrl) {
		com.alibaba.fastjson.JSONObject requestBody = new com.alibaba.fastjson.JSONObject();
		requestBody.put("contractId",contractId);
		requestBody.put("signer",signer);
		requestBody.put("signatureImageWidth","70");
	    requestBody.put("signatureImageHeight","50");
		JSONArray signaturePositions = new JSONArray();
		com.alibaba.fastjson.JSONObject signaturePosition = new com.alibaba.fastjson.JSONObject();
		signaturePosition.put("x",x);
		signaturePosition.put("y",y);
		signaturePosition.put("pageNum",pageNum);
		signaturePositions.add(signaturePosition);
		requestBody.put("signaturePositions",signaturePositions);
		requestBody.put("returnUrl",returnUrl);
		requestBody.put("isDrawSignatureImage","1");
		//requestBody.put("returnUrl","https://www.baidu.com/");
		// TODO Auto-generated method stub
		return requestBody;
	}
}
