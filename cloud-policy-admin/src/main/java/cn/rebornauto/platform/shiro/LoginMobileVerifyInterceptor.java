package cn.rebornauto.platform.shiro;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.rebornauto.platform.common.util.WebUtil;
 
/**
 * 拦截器
 */
public class LoginMobileVerifyInterceptor implements HandlerInterceptor{
	 //在请求处理之前进行调用（Controller方法调用之前
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
//        System.out.printf("preHandle被调用\n");
        boolean loginFlag = WebUtil.getCookieVerify(httpServletRequest.getSession(),httpServletRequest);
        System.out.println("短信验证状态:"+loginFlag);
        if (!loginFlag) {
        	System.out.println("没有短信验证通过");
        	// 拦截至登陆页面
        	httpServletRequest.getRequestDispatcher("/401").forward(httpServletRequest, httpServletResponse);
            // false为不通过
			return false;
		}
        System.out.println("短信验证通过cookie存在");
        return true;   
    }
 
    //请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
//        System.out.println("postHandle被调用\n");
    }
 
    //在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
//        System.out.println("afterCompletion被调用\n");
    }
}
