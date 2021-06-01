package cn.rebornauto.platform.shiro;

import cn.rebornauto.platform.common.Const;
import cn.rebornauto.platform.common.data.view.Response;
import org.apache.shiro.web.filter.PathMatchingFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 原本在此处验证验证码
 * 但通过request Payload此处或者不方便
 */
public class ValidateCodeFilter extends PathMatchingFilter {

    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest req = (HttpServletRequest) request;
        Object safecode = req.getSession().getAttribute(Const.SafeCode_SessionName);
       // return safecode != null;
        return true;
    }
}
