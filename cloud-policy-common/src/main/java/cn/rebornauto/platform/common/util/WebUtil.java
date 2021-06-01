package cn.rebornauto.platform.common.util;


import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.rebornauto.platform.common.Const;
import cn.rebornauto.platform.pay.tonglian.utils.DateUtil;
import cn.rebornauto.platform.sys.entity.SysUser;



/**
 * Created by xcl on 2017/1/4.
 */
public class WebUtil {

    private static char[] ch = "0123456789".toCharArray(); // 随即产生的字符串 不包括 i l(小写L) o（小写O） 1（数字1）0(数字0)

    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1")) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    //e.printStackTrace();
                }
                ipAddress = inet.getHostAddress();
            }
        }

        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) { //"***.***.***.***".length() = 15
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    /**
     * 生成随机数
     *
     * @param len
     * @return
     */
    public static String rand(int len) {
        int length = ch.length; // 随即字符串的长度
        Random random = new Random();
        String sRand = "";
        for (int i = 0; i < len; i++) {
            // 随即生成0-9的数字
            String rand = new Character(ch[random.nextInt(length)]).toString();
            sRand += rand;
        }
        return sRand;
    }

    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^((1[3,4,5,6,7,8,9]))\\d{9}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 设置cookie
     *
     * @param name   cookie名字
     * @param value  cookie值
     * @param maxAge cookie生命周期  以秒为单位
     */
    public static void addCookie(HttpServletResponse res, String name, String value, String domain, int maxAge) {
        try {
            value = URLEncoder.encode("" + value, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Cookie cookie = new Cookie(name, value + "");
        cookie.setPath("/");
        if(domain!=null){
            cookie.setDomain(domain);
        }
        if (maxAge > 0) cookie.setMaxAge(maxAge);
        res.addCookie(cookie);
    }

    /**
     * 根据名字获取cookie
     *
     * @param name cookie名字
     * @return
     */
    public static Cookie getCookieByName(HttpServletRequest req, String name) {
        Map<String, Cookie> cookieMap = getCookieMap(req);
        if (cookieMap.containsKey(name)) {
            Cookie cookie = (Cookie) cookieMap.get(name);
            return cookie;
        } else {
            return null;
        }
    }

    /**
     * 将cookie封装到Map里面
     *
     * @return
     */
    private static Map<String, Cookie> getCookieMap(HttpServletRequest req) {
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = req.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }


    public static boolean deleteCookie(HttpServletRequest req, HttpServletResponse res, String cookieName) {
        if (cookieName != null) {
            Cookie cookie = getCookie(req, cookieName);
            if (cookie != null) {
                cookie.setMaxAge(0);//0，就立即删除
                cookie.setPath("/");//不要漏掉
                cookie.setDomain(req.getServerName());
                res.addCookie(cookie);
                return true;
            }
        }
        return false;
    }

    public static String getValue(HttpServletRequest req, String cookieName) {
        Cookie cookie = getCookie(req, cookieName);
        return getValue(cookie);
    }

    public static String getValue(Cookie cookie) {
        if (null == cookie) return null;
        try {
            return URLDecoder.decode(cookie.getValue(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Cookie getCookie(HttpServletRequest req, String cookieName) {
        Cookie[] cookies = req.getCookies();
        Cookie cookie = null;
        try {
            if (cookies != null && cookies.length > 0) {
                for (int i = 0; i < cookies.length; i++) {
                    cookie = cookies[i];
                    if (cookie.getName().equals(cookieName)) {
                        return cookie;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMdd");

    public static boolean getCookieVerify(HttpSession session,HttpServletRequest request){
    	try {
	        SysUser user = (SysUser) session.getAttribute(Const.SESSION_USER);
	        if(user==null){ return false;}
	        String uname = user.getUsername();
	        String mobile = user.getMobile();
	        String key = DateUtil.getDays()+uname;
	        String value = AESUtils.encode(DateUtil.getDays()+mobile);
	        String value2 = WebUtil.getValue(request,key);
	        if(value2==null || !value2.equals(value.toString())){
	            return false;
	        }
        } catch (Exception e) {
			e.printStackTrace();
		}
        return true;
    }
    public static void setCookieVerify(HttpSession session,HttpServletResponse response){
    	try {
	        SysUser user = (SysUser) session.getAttribute(Const.SESSION_USER);
	        String uname = user.getUsername();
	        String mobile = user.getMobile();
	        String key = DateUtil.getDays()+uname;
	        String value = AESUtils.encode(DateUtil.getDays()+mobile);
	        WebUtil.addCookie(response, key, value, null, 60 * 60 * 24);
    	} catch (Exception e) {
 			e.printStackTrace();
 		}
    }
}
