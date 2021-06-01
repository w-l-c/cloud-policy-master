package cn.rebornauto.platform.common.handler.intercept;

import cn.rebornauto.platform.common.controller.BaseController;
import cn.rebornauto.platform.sys.entity.SysLogs;
import cn.rebornauto.platform.sys.service.SysLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class LogHandlerInterceptorAdapter extends HandlerInterceptorAdapter {

    @Autowired
    private SysLogsService sysLogsService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            SysLogs logs = new SysLogs();
            logs.setCreatetime(new Date());
            int bussessid = 0;
            String remark = "";
            if (handler instanceof HandlerMethod) {
                HandlerMethod hm = (HandlerMethod) handler;
                BusLogger buslogger = hm.getMethodAnnotation(BusLogger.class);
                if (buslogger != null) {
                    bussessid = buslogger.bussessid();
                    remark = buslogger.remark();
                    Integer uid = BaseController.getUserId();
                    logs.setUserid(uid==null?0:uid);
                    logs.setUsername(BaseController.getUserAccount()+"");
                    logs.setBussessid(bussessid);
                    logs.setLogtype(request.getRequestURI());
                    logs.setRemark(remark);
                    //request参数
                    logs.setParameters(getParameter(request));

                    logs.setSourceip(BaseController.getIP(request));
                    logs.setUseragent(request.getHeader("User-Agent"));
                    sysLogsService.save(logs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private String getParameter(HttpServletRequest request) {
        try {
            StringBuilder r = new StringBuilder();
            Map<String, String[]> map = request.getParameterMap();
            if (map != null && map.size() > 0) {
                Set<Map.Entry<String, String[]>> set = map.entrySet();
                for (Map.Entry<String, String[]> e : set) {
                    String k = e.getKey();
                    List<String> val = Arrays.asList(e.getValue());
                    r.append(k + "=" + val).append("&");
                }
            }
            ServletInputStream is = null;
            if (is != null) {
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line = null;
                while ((line = br.readLine()) != null) {
                    r.append(line);
                }
            }
            return r.toString();
        } catch (IOException e) {
        }
        return "";
    }
}
