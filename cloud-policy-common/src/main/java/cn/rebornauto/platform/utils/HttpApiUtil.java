package cn.rebornauto.platform.utils;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpApiUtil {

    private static RequestConfig requestConfig = RequestConfig.custom()
            .setConnectTimeout(20000).setSocketTimeout(22000).build();
    private static CloseableHttpClient httpClient = HttpClientBuilder.create().build();

    private static String execute(HttpRequestBase requestBase) throws Exception {
        CloseableHttpResponse httpResponse = null;
        try {
            requestBase.setConfig(requestConfig);
            httpResponse = httpClient.execute(requestBase);
            HttpEntity responseEntity = httpResponse.getEntity();
            int code = httpResponse.getStatusLine().getStatusCode();
            System.out.println("http status code:"+code);
            if (responseEntity != null) {
                String content = EntityUtils.toString(responseEntity, "UTF-8");
                return content;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpResponse != null) {
                httpResponse.close();
            }
        }
        return null;
    }


    public static String postJson(String url, Map<String, ? extends Object> param) {
        try {
            HttpPost httpPost = new HttpPost(url);
            StringEntity entity = new StringEntity(JSON.toJSONString(param), ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            return execute(httpPost);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String postForm(String url, Map<String, ? extends Object> param) {
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            httpPost.setHeader("accept", "*/*");
            httpPost.setHeader("connection", "Keep-Alive");
            httpPost.setHeader("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            List<NameValuePair> nvp = new ArrayList<>();
            if(param!=null){
                param.forEach((k, v) -> {
                    nvp.add(new BasicNameValuePair(k, v + ""));
                });
            }
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nvp, "UTF-8");
            httpPost.setEntity(entity);
            return execute(httpPost);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return null;
    }

    public static String postFile(String url, Map<String, ? extends Object> param) {
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
            multipartEntityBuilder.setCharset(Charset.forName("UTF-8"));
            param.forEach((k, v) -> {
                if (v instanceof File) {
                    multipartEntityBuilder.addBinaryBody(k, (File) v);
                } else {
                    multipartEntityBuilder.addTextBody(k, v + "");
                }
            });
            HttpEntity entity = multipartEntityBuilder.build();
            httpPost.setEntity(entity);
            return execute(httpPost);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return null;
    }

    public static String get(String url, Map<String,? extends Object> param) {
        try {
            StringBuffer sbuffer = new StringBuffer(url);
            if (param != null && param.size() > 0) {
                String p = url.contains("?") ? "&" : "?";
                sbuffer.append(p);
                param.forEach((k, v) -> {
                    sbuffer.append(k + "=" + v + "&");
                });
            }
            HttpGet httpGet = new HttpGet(sbuffer.toString());
            return execute(httpGet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
