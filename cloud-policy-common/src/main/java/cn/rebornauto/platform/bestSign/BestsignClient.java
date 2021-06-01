package cn.rebornauto.platform.bestSign;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import cn.rebornauto.platform.business.entity.CertificateStatus;
import cn.rebornauto.platform.business.entity.PersonalCertificateStatus;
import cn.rebornauto.platform.business.form.DaiZhengForm;
import cn.rebornauto.platform.business.vo.SignInfoVo;
import cn.rebornauto.platform.common.Const;
import cn.rebornauto.platform.upload.entity.UploadInfo;

import com.alibaba.fastjson.JSONObject;

public class BestsignClient {
	
    private static DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyyMMdd");

    
    
    /**
     * 个人用户注册
     * [注册，设置实名信息，申请数字证书，生成默认签名]
     * @throws Exception
     */
    public static void personalUserReg(BestsignOpenApiClient openApiClient,SignInfoVo signInfoVo,PersonalCertificateStatus personalCertificateStatus,Integer idType) throws Exception{
        //用户注册
        String account = signInfoVo.getAgentAccount(); //账号
        String name =signInfoVo.getAgentName(); //用户名称    
        //设置个人实名信息
        String identity = signInfoVo.getAgentIdcardno(); //证件号码      
        //注册返回异步申请证书signInfoVo务id
        String taskId = openApiClient.userPersonalReg(account, name,identity,idType);
        JSONObject userObj;
   		try {
   			//异步查询
   			userObj = openApiClient.applyCertStatus(account,taskId);
   			int errno = userObj.getIntValue("errno");
   			personalCertificateStatus.setErrno(errno+"");
   			personalCertificateStatus.setErrmsg(userObj.getString("errmsg"));
   			if(userObj.getIntValue("errno")==0){
   				JSONObject data = userObj.getJSONObject("data");
   				if (data != null) {
   					personalCertificateStatus.setStatus(data.getString("status"));
   				        if(data.getString("status").equals(Const.APPLY_CERT_STATUS1)){
   				        	personalCertificateStatus.setMessage(Const.APPLY_CERT_MESSAGE1);
   				        }
   				        if(data.getString("status").equals(Const.APPLY_CERT_STATUS2)){
   				        	personalCertificateStatus.setMessage(Const.APPLY_CERT_MESSAGE2);
   				        }
   				        if(data.getString("status").equals(Const.APPLY_CERT_STATUS3)){
   				        	personalCertificateStatus.setMessage(Const.APPLY_CERT_MESSAGE3);
   				        }
   				        if(data.getString("status").equals(Const.APPLY_CERT_STATUS4)){
   				        	personalCertificateStatus.setMessage(Const.APPLY_CERT_MESSAGE4);
   				        }
   				        if(data.getString("status").equals(Const.APPLY_CERT_STATUS5)){
   				        	personalCertificateStatus.setMessage(Const.APPLY_CERT_MESSAGE5);
   				        }
   				        if(data.getString("status").equals(Const.APPLY_CERT_STATUS6)){
   				        	personalCertificateStatus.setMessage(Const.APPLY_CERT_MESSAGE6);
   				        }
   				        if(data.getString("status").equals(Const.APPLY_CERT_STATUS0)){
   				        	personalCertificateStatus.setMessage(Const.APPLY_CERT_MESSAGE0);
   				        }
   				     personalCertificateStatus.setData(data.toJSONString());
   				}
   			}
   		} catch (Exception e) {
   			// TODO Auto-generated catch block
   			personalCertificateStatus.setErrno(Const.ERRNO);
   			personalCertificateStatus.setErrmsg(Const.ERRMSG);
   		}
   		personalCertificateStatus.setTaskid(taskId);
   		personalCertificateStatus.setAccount(account);       
    }
    
    /**
     * 企业用户注册
     * [注册，设置实名信息，申请数字证书，生成默认签名]
     * @throws Exception
     */
    public static  CertificateStatus companyUserReg(BestsignOpenApiClient openApiClient,DaiZhengForm form,CertificateStatus certificateStatus) throws Exception {
        //用户注册
        String account = form.getAccount(); //账号
        String name =  form.getDaiZhengName(); //用户名
        //设置个人实名信息
        String regCode = form.getRegCode(); //工商注册号
        String orgCode = form.getOrgCode(); //组织机构代码
        String taxCode = form.getTaxCode(); //税务登记证号
        String legalPerson =form.getLegalPerson(); //法定代表人姓名
        String legalPersonIdentityType = form.getLegalPersonIdentityType(); //法定代表人证件号
        String legalPersonIdentity = form.getLegalPersonIdentity(); //法定代表人证件号     
        String contactMobile = form.getDaiZhengLinkMobile(); //联系手机
        //注册返回异步申请证书任务id
        String taskId = openApiClient.companyUserReg(account, name,"", "", regCode, orgCode,taxCode,legalPerson,legalPersonIdentityType,legalPersonIdentity,"", "", contactMobile, "", "", "");
        JSONObject userObj;
   		try {
   			userObj = openApiClient.applyCertStatus(account,taskId);
   			int errno = userObj.getIntValue("errno");
   			certificateStatus.setErrno(errno+"");
   			certificateStatus.setErrmsg(userObj.getString("errmsg"));
   			if(userObj.getIntValue("errno")==0){
   				JSONObject data = userObj.getJSONObject("data");
   				if (data != null) {
   					 certificateStatus.setStatus(data.getString("status"));
   				        if(data.getString("status").equals(Const.APPLY_CERT_STATUS1)){
   				        	certificateStatus.setMessage(Const.APPLY_CERT_MESSAGE1);
   				        }
   				        if(data.getString("status").equals(Const.APPLY_CERT_STATUS2)){
   				        	certificateStatus.setMessage(Const.APPLY_CERT_MESSAGE2);
   				        }
   				        if(data.getString("status").equals(Const.APPLY_CERT_STATUS3)){
   				        	certificateStatus.setMessage(Const.APPLY_CERT_MESSAGE3);
   				        }
   				        if(data.getString("status").equals(Const.APPLY_CERT_STATUS4)){
   				        	certificateStatus.setMessage(Const.APPLY_CERT_MESSAGE4);
   				        }
   				        if(data.getString("status").equals(Const.APPLY_CERT_STATUS5)){
   				        	certificateStatus.setMessage(Const.APPLY_CERT_MESSAGE5);
   				        }
   				        if(data.getString("status").equals(Const.APPLY_CERT_STATUS6)){
   				        	certificateStatus.setMessage(Const.APPLY_CERT_MESSAGE6);
   				        }
   				        if(data.getString("status").equals(Const.APPLY_CERT_STATUS0)){
   				        	certificateStatus.setMessage(Const.APPLY_CERT_MESSAGE0);
   				        }
   				        certificateStatus.setData(data.toJSONString());
   				}
   			}
   		} catch (Exception e) {
   			// TODO Auto-generated catch block
   			certificateStatus.setErrno(Const.ERRNO);
   			certificateStatus.setErrmsg(Const.ERRMSG);
   		}
           certificateStatus.setTaskid(taskId);
           certificateStatus.setAccount(account);       
   		return certificateStatus;
    }

    /**
     * 个人异步申请状态查询
     * @return
     * @throws Exception
     */
    public static PersonalCertificateStatus applyCertStatus(BestsignOpenApiClient openApiClient,PersonalCertificateStatus certificateStatus ){
        String account = certificateStatus.getAccount();
        String taskId = certificateStatus.getTaskid();
        JSONObject userObj;
   		try {
   			userObj = openApiClient.applyCertStatus(account,taskId);
   			int errno = userObj.getIntValue("errno");
   			certificateStatus.setErrno(errno+"");
   			certificateStatus.setErrmsg(userObj.getString("errmsg"));
   			if(userObj.getIntValue("errno")==0){
   				JSONObject data = userObj.getJSONObject("data");
   				if (data != null) {
   					 certificateStatus.setStatus(data.getString("status"));
   				        if(data.getString("status").equals(Const.APPLY_CERT_STATUS1)){
   				        	certificateStatus.setMessage(Const.APPLY_CERT_MESSAGE1);
   				        }
   				        if(data.getString("status").equals(Const.APPLY_CERT_STATUS2)){
   				        	certificateStatus.setMessage(Const.APPLY_CERT_MESSAGE2);
   				        }
   				        if(data.getString("status").equals(Const.APPLY_CERT_STATUS3)){
   				        	certificateStatus.setMessage(Const.APPLY_CERT_MESSAGE3);
   				        }
   				        if(data.getString("status").equals(Const.APPLY_CERT_STATUS4)){
   				        	certificateStatus.setMessage(Const.APPLY_CERT_MESSAGE4);
   				        }
   				        if(data.getString("status").equals(Const.APPLY_CERT_STATUS5)){
   				        	certificateStatus.setMessage(Const.APPLY_CERT_MESSAGE5);
   				        }
   				        if(data.getString("status").equals(Const.APPLY_CERT_STATUS6)){
   				        	certificateStatus.setMessage(Const.APPLY_CERT_MESSAGE6);
   				        }
   				        if(data.getString("status").equals(Const.APPLY_CERT_STATUS0)){
   				        	certificateStatus.setMessage(Const.APPLY_CERT_MESSAGE0);
   				        }
   				        certificateStatus.setData(data.toJSONString());
   				}
   			}
   		} catch (Exception e) {
   			// TODO Auto-generated catch block
   			certificateStatus.setErrno(Const.ERRNO);
   			certificateStatus.setErrmsg(Const.ERRMSG);
   		}
           certificateStatus.setTaskid(taskId);
           certificateStatus.setAccount(account);       
   		return certificateStatus;
    }
    
    /**
     * 企业异步申请状态查询
     * @return
     * @throws Exception
     */
    public static CertificateStatus applyCertStatus(BestsignOpenApiClient openApiClient,CertificateStatus certificateStatus ){
        String account = certificateStatus.getAccount();
        String taskId = certificateStatus.getTaskid();
        JSONObject userObj;
   		try {
   			userObj = openApiClient.applyCertStatus(account,taskId);
   			int errno = userObj.getIntValue("errno");
   			certificateStatus.setErrno(errno+"");
   			certificateStatus.setErrmsg(userObj.getString("errmsg"));
   			if(userObj.getIntValue("errno")==0){
   				JSONObject data = userObj.getJSONObject("data");
   				if (data != null) {
   					 certificateStatus.setStatus(data.getString("status"));
   				        if(data.getString("status").equals(Const.APPLY_CERT_STATUS1)){
   				        	certificateStatus.setMessage(Const.APPLY_CERT_MESSAGE1);
   				        }
   				        if(data.getString("status").equals(Const.APPLY_CERT_STATUS2)){
   				        	certificateStatus.setMessage(Const.APPLY_CERT_MESSAGE2);
   				        }
   				        if(data.getString("status").equals(Const.APPLY_CERT_STATUS3)){
   				        	certificateStatus.setMessage(Const.APPLY_CERT_MESSAGE3);
   				        }
   				        if(data.getString("status").equals(Const.APPLY_CERT_STATUS4)){
   				        	certificateStatus.setMessage(Const.APPLY_CERT_MESSAGE4);
   				        }
   				        if(data.getString("status").equals(Const.APPLY_CERT_STATUS5)){
   				        	certificateStatus.setMessage(Const.APPLY_CERT_MESSAGE5);
   				        }
   				        if(data.getString("status").equals(Const.APPLY_CERT_STATUS6)){
   				        	certificateStatus.setMessage(Const.APPLY_CERT_MESSAGE6);
   				        }
   				        if(data.getString("status").equals(Const.APPLY_CERT_STATUS0)){
   				        	certificateStatus.setMessage(Const.APPLY_CERT_MESSAGE0);
   				        }
   				        certificateStatus.setData(data.toJSONString());
   				}
   			}
   		} catch (Exception e) {
   			// TODO Auto-generated catch block
   			certificateStatus.setErrno(Const.ERRNO);
   			certificateStatus.setErrmsg(Const.ERRMSG);
   		}
           certificateStatus.setTaskid(taskId);
           certificateStatus.setAccount(account);       
   		return certificateStatus;
    }

    /**
     * 上上签接口样板
     * @return
     * @throws Exception
     */
    public static String bestSignApi(BestsignOpenApiClient openApiClient )throws Exception{
        String method = "/storage/contract/lock/";
        JSONObject requestBody = new JSONObject();
        requestBody.put("contractId","154529351701000002");
        openApiClient.bestSignApiPost(method,requestBody);
        return null;
    }
   /**
    * 上传印章
    * @param openApiClient
    * @return
    * @throws Exception
    */
    public static String uploadCert(BestsignOpenApiClient openApiClient,UploadInfo uploadInfo,DaiZhengForm form)throws Exception{ 
    	String  imageName = form.getImageName();
        //本地服务器印章图片的名称
        //String name = LocalDate.now().format(formatters)+imageName+".png";    
        //本地服务器印章的路径
       // String sealImgPicPath= form.getSealImgPicPath()+name;
        //把带域名的印章图片下载到本地服务器
        //downloadPicture(uploadInfo.getDomain()+form.getSealImgPicUrl(),sealImgPicPath);
        String method = "/signatureImage/user/upload/";
        JSONObject requestBody = new JSONObject();
        requestBody.put("account",form.getAccount());
        requestBody.put("imageData",inputStream2ByteArray(form.getSealImgPicUrl()));
        requestBody.put("imageName",imageName);
        openApiClient.bestSignApiPost(method,requestBody);
        //上传印章成功删除本地服务器印章
        //deletePicture(sealImgPicPath);
        return null;
    }

    /**
     * 上传合同
     * @return
     * @throws Exception
     */
    public static String uploadContract(JSONObject requestBody,BestsignOpenApiClient openApiClient )throws Exception{
        String method = "/storage/contract/upload/";
        JSONObject data = openApiClient.bestSignApiPost(method,requestBody);
        return data.getString("contractId");
    }
    /**
     * 签署合同
     * @return
     * @throws Exception
     */
    public static void cert(JSONObject requestBody,BestsignOpenApiClient openApiClient )throws Exception{
        String method = "/storage/contract/sign/cert/";
        openApiClient.bestSignApiPost(method,requestBody);
    }

    public static String send(JSONObject requestBody,BestsignOpenApiClient openApiClient )throws Exception{
        String method = "/contract/send/";
       return  openApiClient.bestSignApiPost(method,requestBody).getString("url");	
    }
    /**
     * 锁定合同
     * @return
     * @throws Exception
     */
    public static void lock(String contractId,BestsignOpenApiClient openApiClient )throws Exception{
        String method = "/storage/contract/lock/";
        JSONObject requestBody = new JSONObject();
        requestBody.put("contractId",contractId);
        openApiClient.bestSignApiPost(method,requestBody);
    }

    /**
     * 下载合同到指定位置
     * @param contractId
     * @param filePath
     * @param fileName
     * @throws Exception
     */
    public static  byte[]  downloadContract(String contractId,String filePath,String fileName,BestsignOpenApiClient openApiClient )throws Exception{
        byte[] bytes = openApiClient.downloadContract(contractId);
        byte2File(bytes,filePath,fileName);
        return bytes;
    }
    
    public static  byte[]  downloadContract(String contractId,BestsignOpenApiClient openApiClient )throws Exception{
        return openApiClient.downloadContract(contractId);
       }


    /**
     * 辅助方法，本地文件转为byte[]
     * @param filePath
     * @return
     */
    public static byte[] inputStream2ByteArray(String filePath) {
        byte[] data = null;
        InputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            in = new FileInputStream(filePath);
            out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024 * 4];
            int n = 0;
            while ((n = in.read(buffer)) != -1) {
                out.write(buffer, 0, n);
            }
            data = out.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
            } catch (Exception e) {
                //ignore
            }
        }
        return data;
    }

    /**
     * 辅助方法，byte数组保存为本地文件
     * @param buf
     * @param filePath
     * @param fileName
     */
    public static void byte2File(byte[] buf, String filePath, String fileName)
    {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try
        {
            File dir = new File(filePath);
            if (!dir.exists())
            {
                dir.mkdirs();
            }
            file = new File(filePath +"/"+ fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(buf);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (bos != null)
            {
                try
                {
                    bos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (fos != null)
            {
                try
                {
                    fos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

/*    public static void main(String [] args){
        try {
        	 BestsignOpenApiClient openApiClient= new BestsignOpenApiClient("1545203530012493573","MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKvRLsWksIo6JkgeMGvnUwXn95J72z5d5CgqanTL4Xcod+O3G9a/RFBsAea9WpUR7W3btZzPmA9RXZsBwWzJ2Om1Za1m5hjJG7Wh71odGAqXPVI6l2JzQZU1jcpja/h8Or07LaWkDbVzeVEAtKWgklfjaZeaBD/hObO8YA4mXGzJAgMBAAECgYAp3vFaAvduyBr6Kjk8ooPuBYikrwBP44utaiDFU8YfRsJowP/MxpG8U4XnGDAR8et9gTRyAXkaCocYNkZP36S3cI23+HlyHIVokvq1iwq326YbomU4eZrJRCLePSpp+8Fbxz4ZXmUQZbts5adALRSIFOfOkDjD9Hz78m03dEdovQJBAO4OWgw1jRGQPH/2DPKBD9WqMjybenZf6qzzzNuEPu/F571oR7ifrwiIFkC5xQHIzkbLi1lq1sVfYJHGatLBBo8CQQC4xKbAFS7MDb1g0beipKdmMZFPOLj2HPGTWtiNIt6fAzYjCE94NL2Vwjz+I2bPaHaHu7nlBR97uTviuScsc0MnAkEAyIeiZwJ9MLxVml0EDwuU8SGLa+50vF6T6tj8SjYeZhBJ5A2BzQ4GfaNVS3SxdY1X5d70JYCQJnbvfByuQ5aCFQJAMrU1lk259CJpMOMdZLOPhYpg+L72dATpf6lU+Rbyxe0ZtG+Zb8H5rdoK5yxMEOv4a7PnhqTrSQdDGiv6RkazzQJBAIg2qx2mAx1dc82uSt/y8MwYTCvWMoINvBv43Plr0+dhUDBKibyT+XpNSsKleZ3xb3O1nYtwZ1CeXxO5clxYU98=","https://openapi.bestsign.info/openapi/v2");
            companyUserReg(openApiClient);
        }catch (Exception e){
            e.printStackTrace();
        }
    }*/
  /**
   * 不需要盖章下载pdf
   * @param pdfUrl
   * @param filePath
   * @param fileName
   * @return
   * @throws IOException
   */
	public static byte[] upAndDownContract(String pdfUrl,
			String filePath, String fileName) throws IOException {
		// TODO Auto-generated method stub
		 byte[] bytes = inputStream2ByteArray(pdfUrl);
		 byte2File(bytes,filePath,fileName);
	     return bytes;
	}
	
	/**
	 * 根据图片的url下载图片到本地
	 * @param urlList
	 * @param path
	 */
	public static void downloadPicture(String urlList,String path) {
        URL url = null;
        try {
            url = new URL(urlList);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());
 
            FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
 
            byte[] buffer = new byte[1024];
            int length;
 
            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            fileOutputStream.write(output.toByteArray());
            dataInputStream.close();
            fileOutputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	
	/**
	 * 根据图片的path删除图片
	 * @param urlList
	 * @param path
	 */
	public static void deletePicture(String delectPicturePath) {
		File file = new File(delectPicturePath);//文件路径
		file.delete();
    }
}
