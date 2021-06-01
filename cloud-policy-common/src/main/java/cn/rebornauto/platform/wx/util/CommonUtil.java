package cn.rebornauto.platform.wx.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.ConnectException;
import java.net.URL;





/**
* 类名: CommonUtil </br>
* 描述: 通用工具类  </br>
* 开发人员： souvc </br>
* 创建时间：  2015-11-27 </br>
* 发布版本：V1.0  </br>
 */
public class CommonUtil {
    private static Logger log = LoggerFactory.getLogger(CommonUtil.class);


    // 凭证获取（GET）                                                                                                                                                            
    public final static String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx584cdfb684629f5a&secret=f8e1c45cced6119a218daf0057f8ef32";  

    
    
    /**
     * 发送https请求
     * 
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr 提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
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
            log.error("连接超时：{}", ce);
        } catch (Exception e) {
            log.error("https请求异常：{}", e);
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
    public static Token getToken() {
        Token token = null;
        String requestUrl = token_url;
        // 发起GET请求获取凭证
        JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);

        if (null != jsonObject) {
            try {
                token = new Token();
                token.setAccessToken(jsonObject.getString("access_token"));
                token.setExpiresIn(jsonObject.getInt("expires_in"));
            } catch (JSONException e) {
                token = null;
                // 获取token失败
                log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return token;
    }
    
    /**
     * URL编码（utf-8）
     * 
     * @param source
     * @return
     */
    public static String urlEncodeUTF8(String source) {
        String result = source;
        try {
            result = java.net.URLEncoder.encode(source, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * 根据内容类型判断文件扩展名
     * 
     * @param contentType 内容类型
     * @return
     */
    public static String getFileExt(String contentType) {
        String fileExt = "";
        if ("image/jpeg".equals(contentType))
            fileExt = ".jpg";
        else if ("audio/mpeg".equals(contentType))
            fileExt = ".mp3";
        else if ("audio/amr".equals(contentType))
            fileExt = ".amr";
        else if ("video/mp4".equals(contentType))
            fileExt = ".mp4";
        else if ("video/mpeg4".equals(contentType))
            fileExt = ".mp4";
        return fileExt;
    }
   
    
    
    /**
	 * 
	 * 校验是否关注过公众号
	 * 
	 * @param access_token
	 * @param openid
	 * @return
	 */
	public static boolean checkFans(String access_token, String openid) {
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
	public static void main(String[] args) {
		boolean checkFans = checkFans(getToken().getAccessToken(),"oxsoU1Zwk8Y6Fx3lL06FKkVYSU-Y");
		System.out.println(checkFans);

	}
	
	}
