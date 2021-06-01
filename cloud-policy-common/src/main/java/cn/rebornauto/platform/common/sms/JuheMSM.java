package cn.rebornauto.platform.common.sms;

import net.sf.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
 
/**
*短信API服务调用示例代码 － 聚合数据
*在线接口文档：http://www.juhe.cn/docs/54
**/
 
public class JuheMSM{
    public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
 
    //配置您申请的KEY
    public static final String APPKEY ="57034fafc2312d56ba7663614de8c40f";
 
    //1.屏蔽词检查测
    public static void getRequest1(){
        String result =null;
        String url ="http://v.juhe.cn/sms/black";//请求接口地址
        Map params = new HashMap();//请求参数
            params.put("word","");//需要检测的短信内容，需要UTF8 URLENCODE
            params.put("key",APPKEY);//应用APPKEY(应用详细页查询)
 
        try {
            result =net(url, params, "GET");
            JSONObject object = JSONObject.fromObject(result);
            if(object.getInt("error_code")==0){
                System.out.println(object.get("result"));
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    //2.发送短信
    public static String getRequest2(String mobile,String tpl_id,String tpl_value){
        String result =null;
        String url ="http://v.juhe.cn/sms/send";//请求接口地址
        Map params = new HashMap();//请求参数
            params.put("mobile",mobile);//接收短信的手机号码
            params.put("tpl_id",tpl_id);//短信模板ID，请参考个人中心短信模板设置
            params.put("tpl_value",tpl_value);//变量名和变量值对。如果你的变量名或者变量值中带有#&=中的任意一个特殊符号，请先分别进行urlencode编码后再传递，<a href="http://www.juhe.cn/news/index/id/50" target="_blank">详细说明></a>
            params.put("key",APPKEY);//应用APPKEY(应用详细页查询)
            params.put("dtype","json");//返回数据的格式,xml或json，默认json
 
        try {
            result =net(url, params, "GET");
            JSONObject object = JSONObject.fromObject(result);
            if(object.getInt("error_code")==0){
                System.out.println(object.get("result"));
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
		
    }
    
    /**
     * 发送短信工具
     * @param mobile
     * @param tpl_id
     * @param tpl_value
     * @param url
     * @param appKey
     * @return
     */
    public static String  sendSms(String mobile,String tpl_id,String tpl_value,String url,String appKey){
        String result =null;
//        String url ="http://v.juhe.cn/sms/send";//请求接口地址
        Map params = new HashMap();//请求参数
            params.put("mobile",mobile);//接收短信的手机号码
            params.put("tpl_id",tpl_id);//短信模板ID，请参考个人中心短信模板设置
            params.put("tpl_value",tpl_value);//变量名和变量值对。如果你的变量名或者变量值中带有#&=中的任意一个特殊符号，请先分别进行urlencode编码后再传递，<a href="http://www.juhe.cn/news/index/id/50" target="_blank">详细说明></a>
            params.put("key",appKey);//应用APPKEY(应用详细页查询)
            params.put("dtype","json");//返回数据的格式,xml或json，默认json
 
        try {
            result =net(url, params, "GET");
//            JSONObject object = JSONObject.fromObject(result);
//            if(object.getInt("error_code")==0){
//                System.out.println(object.get("result"));
//            }else{
//                System.out.println(object.get("error_code")+":"+object.get("reason"));
//            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
		
    }
 
 
 
    public static void main(String[] args) {
	    	String mobile ="13391391217";
	    	String tpl_id= "160147";
//	    	String value = "您的验证码是#code#=。如非本人操作，请忽略本短信";
            StringBuffer sb = new StringBuffer();
            sb.append("#code#=").append(2234);
	    	String tpl_value="";
	    	try {
	    		tpl_value = URLEncoder.encode(sb.toString(), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	    	System.out.println(tpl_value);
	    	getRequest2(mobile, tpl_id, tpl_value);
    }
 
    /**
     *
     * @param strUrl 请求地址
     * @param params 请求参数
     * @param method 请求方法
     * @return  网络请求字符串
     * @throws Exception
     */
    public static String net(String strUrl, Map params,String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if(method==null || method.equals("GET")){
                strUrl = strUrl+"?"+urlencode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if(method==null || method.equals("GET")){
                conn.setRequestMethod("GET");
            }else{
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params!= null && method.equals("POST")) {
                try {
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                        out.writeBytes(urlencode(params));
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }
 
    //将map型转为请求参数型
    public static String urlencode(Map<String,Object>data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}