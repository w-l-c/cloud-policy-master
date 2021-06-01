package cn.rebornauto.platform.weixin.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.rebornauto.platform.business.query.AgentInfoQuery;
import cn.rebornauto.platform.business.service.AgentInfoService;
import cn.rebornauto.platform.business.service.SysConfigService;
import cn.rebornauto.platform.business.service.SysDicService;
import cn.rebornauto.platform.business.service.WxCustomerService;
import cn.rebornauto.platform.business.vo.WxCustomerVo;
import cn.rebornauto.platform.common.Const;
import cn.rebornauto.platform.configuration.WxProperties;
import cn.rebornauto.platform.wx.api.OauthAPI;
import cn.rebornauto.platform.wx.entity.AuthorizeInfo;
import cn.rebornauto.platform.wx.entity.WxApiConf;
import cn.rebornauto.platform.wx.response.OauthGetTokenResponse;
import cn.rebornauto.platform.wx.service.WxApiConfService;
import cn.rebornauto.platform.wx.util.HttpUtil;
import cn.rebornauto.platform.wx.util.MyX509TrustManager;
import cn.rebornauto.platform.wx.util.Token;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
/**
 * 微信公众号授权
 * <p>Title: OauthController</p>  
 * <p>Description:</p>  
 * @author zjl  
 * @date 2019年5月5日
 */
@Controller
@RequestMapping(value = "/api/weixin/oauth")
public class OauthController {
	private static final Logger logger = LoggerFactory.getLogger(OauthController.class);

	private final static String customerId = "customerId";

	 private long weixinTokenStartTime;
	 
	@Autowired
	private OauthAPI oauthAPI;

	@Autowired
	private	WxProperties  wxProperties;
	
	@Autowired
	private AgentInfoService agentInfoService;

    @Autowired
    private WxApiConfService wxApiConfService;
    
    @Autowired
    private   SysConfigService  sysConfigService;
    
    @Autowired
    private   SysDicService  sysDicService;
    
    @Autowired
    WxCustomerService  wxCustomerService;
	/**
     *GZH授权成功回调页面(扫码)
     * @param httpServletRequest
     * @return
     */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	protected final String author(HttpServletRequest httpServletRequest) {
	    	System.out.println("weixin端扫码入口:------------------------------------------");	
		 String code = httpServletRequest.getParameter("code");	
			System.out.println("code:------------------------------------------"+code);
		if (StringUtils.hasText(code)) {
			AuthorizeInfo authorizeInfo = sysConfigService.getAuthorizeInfo(sysDicService.selectSysPaySwitch());
			System.out.println("authorizeInfo-------------------------------------------"+authorizeInfo);
			WxApiConf wxapiconf = wxApiConfService.apiConf(authorizeInfo.getAppid());
			// 获取用户的openid
			System.out.println("wxapiconf:---------------------------------------"+wxapiconf);
			OauthGetTokenResponse token = oauthAPI.getToken(wxapiconf,code);
		  	System.out.println("token:------------------------------------------"+token);
		  	String openid = token.getOpenid();	
		  	System.out.println("openid:------------------------------------------"+openid);
			if (StringUtils.hasText(openid)) {	
				//获取用户头像
				String headimgurl = oauthAPI.getUserInfo(token.getAccessToken(),openid).getHeadimgurl();
				AgentInfoQuery query = new AgentInfoQuery();
				//放入二维码中的客户id
				getAgentInfoQuery(httpServletRequest, query);
				query.setOpenid(openid);
				ServletContext application = httpServletRequest.getSession().getServletContext();
				application.setAttribute(wxProperties.getApplicationWxOpenid()+openid,openid);
				application.setAttribute(wxProperties.getApplicationWxOpenid()+openid+"headerPicUrl",headimgurl);
				//判断根据customerId和openid判断返回页 0返回注册页面 1返回用户信息页面 2返回错误信息页面
			    Byte type = agentInfoService.getTypeByQuery(query);	
			    //判断是否关注			
			   boolean checkFans = checkFans(getToken(wxapiconf).getAccessToken(),openid);
			   System.out.println("checkFans:------------------------------------------"+checkFans);
				if(checkFans){
				     if(type.equals(Const.TYPE_LOGON)){						    	                          
					     return "redirect:/certificationAuthorityImg/router?openid="+openid+"&customerId="+query.getCustomerId()+"&headerPicUrl="+headimgurl;				    
				    }else if(type.equals(Const.TYPE_USER)){
						 return "redirect:/personalCenter/router?openid="+openid+"&headerPicUrl="+headimgurl;				    
				   }else{ 
						  return "redirect:/certificationTips";				
					}	         				
				}else{
					WxCustomerVo wxCustomerVO=wxCustomerService.selectByQuery(query);	
					if(wxCustomerVO==null){
					       	wxCustomerService.insertSelective(query);
					}else{
						if(!wxCustomerVO.getCustomerId().equals(query.getCustomerId())){
							wxCustomerService.insertSelective(query);
						}
					}
					return authorizeInfo.getPublicNumber();				
				}	       
			}
		}
		return "/404";
	}

     /**
      * 个人中心
      * @param httpServletRequest
      * @return
      */
	@RequestMapping(value = "/core", method = RequestMethod.GET)
	protected final String core(HttpServletRequest httpServletRequest) {		
		String code = httpServletRequest.getParameter("code");	
		if (StringUtils.hasText(code)) {
			AuthorizeInfo authorizeInfo = sysConfigService.getAuthorizeInfo(sysDicService.selectSysPaySwitch());
			WxApiConf wxapiconf = wxApiConfService.apiConf(authorizeInfo.getAppid());
			// 获取用户的openid
			OauthGetTokenResponse token = oauthAPI.getToken(wxapiconf,code);
		  	String openid = token.getOpenid();	
			if (StringUtils.hasText(openid)) {	
				//获取用户头像
				String headimgurl = oauthAPI.getUserInfo(token.getAccessToken(),openid).getHeadimgurl();
				AgentInfoQuery query = new AgentInfoQuery();
				query.setOpenid(openid);
				ServletContext application = httpServletRequest.getSession().getServletContext();
				application.setAttribute(wxProperties.getApplicationWxOpenid()+openid,openid);
				application.setAttribute(wxProperties.getApplicationWxOpenid()+openid+"headerPicUrl",headimgurl);
        		WxCustomerVo wxCustomerVO=wxCustomerService.selectByQuery(query);	
        		//判断是否扫过码，客户信息是否存在
                if(wxCustomerVO!=null){
                	query.setCustomerId(wxCustomerVO.getCustomerId());
                 }
                //判断根据customerId和openid判断返回页 0返回注册页面 1返回用户信息页面 2返回错误信息页面
			    Byte type = agentInfoService.getTypeByQuery(query);	
			     if(type.equals(Const.TYPE_LOGON)){			    	   
				     return "redirect:/certificationAuthorityImg/router?openid="+openid+"&customerId="+query.getCustomerId()+"&headerPicUrl="+headimgurl;				    
			    }else if(type.equals(Const.TYPE_USER)){
					 return "redirect:/personalCenter/router?openid="+openid+"&headerPicUrl="+headimgurl;				    
			   }else{ 
					  return "redirect:/certificationTips";				
			 }	
			}
		}
		return "/404";
	}

	/**
	 * 获取二维码代理公司ID
	 * @param request
	 * @param query
	 */
	private void getAgentInfoQuery(HttpServletRequest request, AgentInfoQuery query) {
		// TODO Auto-generated method stub
		if (query == null) {
			return;
		}
		String getCustomerId = request.getParameter(customerId);
		if (StringUtils.hasText(getCustomerId)) {
			query.setCustomerId(getCustomerId);
		}
	}
	
	
	   /**
     * 发送https请求
     * 
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr 提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
	private  JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);
            
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);

            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
        	logger.error("连接超时：{}", ce);
        } catch (Exception e) {
        	logger.error("https请求异常：{}", e);
        }
        return jsonObject;
    }

    /**
     * 获取接口访问凭证
     * @param apiConf 
     * @param wxApiConfService 
     * 
     * @param appid 凭证
     * @param appsecret 密钥
     * @return
     */
	private Token getToken(WxApiConf wxapiconf) {
		Token token = null;
		JSONObject jsonObject=null;
		long now = System.currentTimeMillis();
		// 从数据库中获取
		if (wxapiconf == null) {
			return null;
		}
		Date st = wxapiconf.getAccessTokenStarttime();
		if (st != null) {
			weixinTokenStartTime = st.getTime();	 
		}
		long time = now - weixinTokenStartTime;
	     /*
         * 判断优先顺序：
         * 1.官方给出的超时时间是7200秒，这里用6000秒来做，防止出现已经过期的情况
         * 2.刷新标识判断，如果已经在刷新了，则也直接跳过，避免多次重复刷新，如果没有在刷新，则开始刷新
         */
		if (time > 6000000) {
			   String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+wxapiconf.getAppid()+"&secret="+wxapiconf.getAppsecret();  
			// 发起GET请求获取凭证
			jsonObject = httpsRequest(token_url, "GET", null);
		}else{
			   token = new Token();
               token.setAccessToken(wxapiconf.getAccessToken());
		}
        if (null != jsonObject) {
            try {
                token = new Token();
                token.setAccessToken(jsonObject.getString("access_token"));
                wxapiconf.setAccessToken(jsonObject.getString("access_token"));
                wxapiconf.setAccessTokenStarttime(new Date(now));
                wxApiConfService.update(wxapiconf);
            } catch (JSONException e) {
                token = null;
                // 获取token失败
                logger.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return token;
    }
    


      
    /**
	 * 
	 * 校验是否关注过公众号
	 * 
	 * @param access_token
	 * @param openid
	 * @return
	 */
	private  boolean checkFans(String access_token, String openid) {
		int isFans = 0;
		String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+ access_token + "&openid=" + openid + "&lang=zh_CN";		
		try {
			String content = HttpUtil.doGet(url);
			if (content.indexOf("errcode") == -1) {
				JsonElement jelement = new JsonParser().parse(content);
				isFans = Integer.parseInt(jelement.getAsJsonObject().get("subscribe").getAsString());					
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isFans == 1 ? true : false;
	}
	
}
