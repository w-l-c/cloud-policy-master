package cn.rebornauto.platform.bestSign;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import cn.rebornauto.platform.utils.HttpClientSender;
import cn.rebornauto.platform.utils.RSAUtil;

import java.io.IOException;

/**
 * 上上签混合云SDK客户端
 */
public class BestsignOpenApiClient {
	

	private String developerId;

	private String privateKey;

	private String serverHost;
	
	private static String urlSignParams = "?developerId=%s&rtick=%s&signType=rsa&sign=%s";

	public BestsignOpenApiClient(String developerId, String privateKey,
                                 String serverHost) {
		this.developerId = developerId;
		this.privateKey = privateKey;
		this.serverHost = serverHost;
	}

	// ***************常规接口start*****************************************************
	/**
	 * 1.1 个人用户注册
	 * 
	 * @param account
	 *            用户账号
	 * @param name
	 *            姓名
	 * @param mail
	 *            用来接收通知邮件的电子邮箱
	 * @param mobile
	 *            用来接收通知短信的手机号码
	 * @param identity
	 *            证件号码
	 * @param identityType
	 *            枚举值：0-身份证，目前仅支持身份证
	 * @param contactMail
	 *            电子邮箱
	 * @param contactMobile
	 *            手机号码
	 * @param province
	 *            省份
	 * @param city
	 *            城市
	 * @param address
	 *            地址
	 * @return 异步申请任务单号
	 * @throws IOException
	 */
	public String userPersonalReg(String account,String name,String identity,Integer idType) throws Exception {
		String host = this.serverHost;
		String method = "/user/reg/";

		// 组装请求参数，作为requestbody
		JSONObject requestBody = new JSONObject();
		requestBody.put("account", account);
		requestBody.put("name", name);
		requestBody.put("userType", "1");
/*		requestBody.put("mail", mail);
		requestBody.put("mobile", mobile);*/

		JSONObject credential = new JSONObject();
		credential.put("identity", identity);
		if(idType.intValue()==4) {
			credential.put("identityType", "0");
		}else {
			credential.put("identityType", "1");
		}
		
/*		credential.put("contactMail", contactMail);
		credential.put("contactMobile", contactMobile);
		credential.put("province", province);
    	credential.put("city", city);
		credential.put("address", address);*/
		requestBody.put("credential", credential);
		requestBody.put("applyCert", "1");

		System.out.println(requestBody.toJSONString());
		// 生成一个时间戳参数
		String rtick = RSAUtil.getRtick();
		// 计算参数签名
		String paramsSign = RSAUtil.calcRsaSign(this.developerId,
				this.privateKey, host, method, rtick, null,
				requestBody.toJSONString());
		// 签名参数追加为url参数
		String urlParams = String.format(urlSignParams, this.developerId,
				rtick, paramsSign);
		// 发送请求
		String responseBody = HttpClientSender.sendHttpPost(host, method,
				urlParams, requestBody.toJSONString());
		// 返回结果解析
		JSONObject userObj = JSON.parseObject(responseBody);
		// 返回errno为0，表示成功，其他表示失败
		if (userObj.getIntValue("errno") == 0) {
			JSONObject data = userObj.getJSONObject("data");
			if (data != null) {
				return data.getString("taskId");
			}
			return null;
		} else {
			throw new Exception(userObj.getIntValue("errno") + ":"
					+ userObj.getString("errmsg"));
		}
	}
	
	// ***************常规接口start*****************************************************

	/**
	 * 1.1 企业用户注册
	 * @param account
	 * @param name
	 * @param mail
	 * @param mobile
	 * @param regCode
	 * @param orgCode
	 * @param taxCode
	 * @param legalPerson
	 * @param legalPersonIdentity
	 * @param legalPersonMobile
	 * @param contactMail
	 * @param contactMobile
	 * @param province
	 * @param city
	 * @param address
	 * @return
	 * @throws Exception
	 */
	public String companyUserReg(String account, String name, String mail,
			String mobile, String regCode, String orgCode,String taxCode, String legalPerson,String legalPersonIdentityType,String legalPersonIdentity, String legalPersonMobile,
			String contactMail, String contactMobile, String province,
			String city, String address) throws Exception {
		String host = this.serverHost;
		String method = "/user/reg/";

		// 组装请求参数，作为requestbody
		JSONObject requestBody = new JSONObject();
		requestBody.put("account", account);
		requestBody.put("name", name);
		requestBody.put("userType", "2");
		//requestBody.put("mail", mail);
		//requestBody.put("mobile", mobile);

		JSONObject credential = new JSONObject();
		credential.put("regCode", regCode);
		credential.put("orgCode", orgCode);
		credential.put("taxCode", taxCode);
		credential.put("legalPerson", legalPerson);
		credential.put("legalPersonIdentity", legalPersonIdentity);
		credential.put("legalPersonIdentityType",legalPersonIdentityType);
		//credential.put("legalPersonMobile", legalPersonMobile);
		credential.put("contactMail", contactMail);
		//credential.put("contactMobile", contactMobile);
		//credential.put("province", province);
		//credential.put("city", city);
		//credential.put("address", address);
		requestBody.put("credential", credential);
		requestBody.put("applyCert", "1");

		// 生成一个时间戳参数
		String rtick = RSAUtil.getRtick();
		// 计算参数签名
		String paramsSign = RSAUtil.calcRsaSign(this.developerId,
				this.privateKey, host, method, rtick, null,
				requestBody.toJSONString());
		// 签名参数追加为url参数
		String urlParams = String.format(urlSignParams, this.developerId,
				rtick, paramsSign);
		// 发送请求
		String responseBody = HttpClientSender.sendHttpPost(host, method,
				urlParams, requestBody.toJSONString());
		// 返回结果解析
 		JSONObject userObj = JSON.parseObject(responseBody);
		// 返回errno为0，表示成功，其他表示失败
		if (userObj.getIntValue("errno") == 0) {
			JSONObject data = userObj.getJSONObject("data");
			if (data != null) {
				return data.getString("taskId");
			}
			return null;
		} else {
			throw new Exception(userObj.getIntValue("errno") + ":"
					+ userObj.getString("errmsg"));
		}
	}
	// ***************常规接口start*****************************************************

	/**
	 * 1.11 异步申请状态查询
	 * @param account
	 * @param taskId
	 * @return
	 * @throws Exception
	 */
	public JSONObject applyCertStatus(String account,String taskId) throws Exception {
		String host = this.serverHost;
		String method = "/user/async/applyCert/status/";

		// 组装请求参数，作为requestbody
		JSONObject requestBody = new JSONObject();
		requestBody.put("account", account);
		requestBody.put("taskId",taskId);

		// 生成一个时间戳参数
		String rtick = RSAUtil.getRtick();
		// 计算参数签名
		String paramsSign = RSAUtil.calcRsaSign(this.developerId,
				this.privateKey, host, method, rtick, null,
				requestBody.toJSONString());
		// 签名参数追加为url参数
		String urlParams = String.format(urlSignParams, this.developerId,
				rtick, paramsSign);
		// 发送请求
		String responseBody = HttpClientSender.sendHttpPost(host, method,
				urlParams, requestBody.toJSONString());
		// 返回结果解析
		return JSON.parseObject(responseBody);
	}
	/**
	 * 1.11 上上签接口公共方法
	 */
	public JSONObject bestSignApiPost(String method,JSONObject requestBody) throws Exception {
		String host = this.serverHost;
		// 生成一个时间戳参数
		String rtick = RSAUtil.getRtick();
		// 计算参数签名
		String paramsSign = RSAUtil.calcRsaSign(this.developerId,
				this.privateKey, host, method, rtick, null,
				requestBody.toJSONString());
		// 签名参数追加为url参数
		String urlParams = String.format(urlSignParams, this.developerId,
				rtick, paramsSign);
		// 发送请求
		String responseBody = HttpClientSender.sendHttpPost(host, method,
				urlParams, requestBody.toJSONString());
		// 返回结果解析
 		JSONObject userObj = JSON.parseObject(responseBody);
		// 返回errno为0，表示成功，其他表示失败
		if (userObj.getIntValue("errno") == 0) {
			JSONObject data = userObj.getJSONObject("data");
			if (data != null) {
				return data;
			}
			return null;
		} else {
			throw new Exception(userObj.getIntValue("errno") + ":"
					+ userObj.getString("errmsg"));
		}
	}
	// ***************常规接口start*****************************************************

	/**
	 * 1.5、查询企业用户证件信息
	 * @param account
	 * @return
	 * @throws Exception
	 */
	public String applyCertStatus(String account) throws Exception {
		String host = this.serverHost;
		String method = "/user/async/applyCert/status/";

		// 组装请求参数，作为requestbody
		JSONObject requestBody = new JSONObject();
		requestBody.put("account", account);

		// 生成一个时间戳参数
		String rtick = RSAUtil.getRtick();
		// 计算参数签名
		String paramsSign = RSAUtil.calcRsaSign(this.developerId,
				this.privateKey, host, method, rtick, null,
				requestBody.toJSONString());
		// 签名参数追加为url参数
		String urlParams = String.format(urlSignParams, this.developerId,
				rtick, paramsSign);
		// 发送请求
		String responseBody = HttpClientSender.sendHttpPost(host, method,
				urlParams, requestBody.toJSONString());
		// 返回结果解析
 		JSONObject userObj = JSON.parseObject(responseBody);
		// 返回errno为0，表示成功，其他表示失败
		if (userObj.getIntValue("errno") == 0) {
			JSONObject data = userObj.getJSONObject("data");
			if (data != null) {
				return data.toJSONString();
			}
			return null;
		} else {
			throw new Exception(userObj.getIntValue("errno") + ":"
					+ userObj.getString("errmsg"));
		}
	}

	/**
	 * 2.3 下载用户默认签名
	 * 
	 * @param account
	 *            用户账号
	 * @param imageName
	 *            签名图片名称 传空或default为默认签名图片。 企业用户传自定义印章名称则下载指定的图片。
	 * @return
	 * @throws Exception
	 */
	public byte[] signatureImageDownload(String account, String imageName)
			throws Exception {
		String host = this.serverHost;
		String method = "/storage/signatureImage/user/download/";

		// 组装url参数
		String urlParams = "account=" + account + "&imageName=" + imageName;

		// 生成一个时间戳参数
		String rtick = RSAUtil.getRtick();
		// 计算参数签名
		String paramsSign = RSAUtil.calcRsaSign(this.developerId,
				this.privateKey, host, method, rtick, urlParams, null);
		// 签名参数追加为url参数
		urlParams = String.format(urlSignParams, this.developerId, rtick,
				paramsSign) + "&" + urlParams;
		// 发送请求
		byte[] responseBody = HttpClientSender.sendHttpGet(host, method,
				urlParams);
		// 返回结果解析
		return responseBody;
	}

	/**
	 * 4.9、下载合同文件
	 */
    public byte[] downloadContract(String contractId)
            throws Exception {
        String host = this.serverHost;
        String method = "/contract/download/";

        // 组装url参数
        String urlParams = "contractId=" + contractId;

		// 生成一个时间戳参数
		String rtick = RSAUtil.getRtick();
		// 计算参数签名
		String paramsSign = RSAUtil.calcRsaSign(this.developerId,
				this.privateKey, host, method, rtick, urlParams, null);
		// 签名参数追加为url参数
		urlParams = String.format(urlSignParams, this.developerId, rtick,
				paramsSign) + "&" + urlParams;
		// 发送请求
		byte[] responseBody = HttpClientSender.sendHttpGet(host, method,
				urlParams);
		// 返回结果解析
		return responseBody;
	}
}
