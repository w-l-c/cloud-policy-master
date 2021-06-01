package cn.rebornauto.platform.common.controller;

import cn.rebornauto.platform.common.Const;
import cn.rebornauto.platform.common.SysConst;
import cn.rebornauto.platform.common.data.request.Form;
import cn.rebornauto.platform.sys.entity.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseController {

    protected static Logger logger = LoggerFactory.getLogger(BaseController.class);

    protected Map statusDic() {
        HashMap<String, String> status = new HashMap<>();
        status.put(Const.Status_Normal+"","正常");
        status.put(Const.Status_Del+"","已删除");
        return status;
    }

    protected Map enabledStatusDic() {
        HashMap<String, String> status = new HashMap<>();
        status.put(SysConst.Enable+"","启用");
        status.put(SysConst.Disable+"","禁用");
        return status;
    }

    public static String getIP(HttpServletRequest request) {
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

    public static SysUser currentUser() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            SysUser user = (SysUser) subject.getPrincipal();
            return user;
        }
        return null;
    }

    public static String getUserAccount() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            SysUser user = (SysUser) subject.getPrincipal();
            return user.getAccount();
        }
        return null;
    }
    public static String getNickName() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            SysUser user = (SysUser) subject.getPrincipal();
            return user.getNickname();
        }
        return null;
    }
    public  static Integer getUserId() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            SysUser user = (SysUser) subject.getPrincipal();
            return user.getId();
        }
        return null;
    }

    public void setCurrUser(Form form){
        SysUser user = currentUser();
        form.setCurrUserId(user.getId());
        form.setCurrUserName(user.getNickname());
        form.setUserCustomerId(user.getCustomerId());
    }
    
    protected Map dataStatusDic() {
        HashMap<String, String> status = new HashMap<>();
        status.put(Const.STATUS_1+"","有效");
        status.put(Const.STATUS__1+"","作废");
        return status;
    }
    //校验是否是管理员账号
    public boolean  isAdministrator(String userCustomerId){
    	if(Const.ADMINISTRATOR_CUSTOMER_ID.equals(userCustomerId)){
    		return true;	
    	}
		return false;	
    }
     
}
