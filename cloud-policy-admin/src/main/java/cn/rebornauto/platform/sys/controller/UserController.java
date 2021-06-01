package cn.rebornauto.platform.sys.controller;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cn.rebornauto.platform.business.service.SysConfigService;
import cn.rebornauto.platform.common.Const;
import cn.rebornauto.platform.common.SysConfigKey;
import cn.rebornauto.platform.common.controller.BaseController;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.common.util.WebUtil;
import cn.rebornauto.platform.common.utils.json.JsonNameFilter;
import cn.rebornauto.platform.common.utils.json.JsonNullValueFilter;
import cn.rebornauto.platform.shiro.PasswordHander;
import cn.rebornauto.platform.sms.entity.SmsQueue;
import cn.rebornauto.platform.sms.service.SmsQueueService;
import cn.rebornauto.platform.sys.entity.SysMenu;
import cn.rebornauto.platform.sys.entity.SysUser;
import cn.rebornauto.platform.sys.form.LoginForm;
import cn.rebornauto.platform.sys.form.SysUserForm;
import cn.rebornauto.platform.sys.form.SysUserSmsForm;
import cn.rebornauto.platform.sys.service.SysMenuService;
import cn.rebornauto.platform.sys.service.SysUserService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

@RestController
public class UserController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private static final String KEY_PRE = "sms:";

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysMenuService sysMenuService;
    
    @Autowired
    SmsQueueService smsQueueService;
    
    @Autowired
    SysConfigService sysConfigService;

    @RequestMapping(value = "/sys/doLogin", method = RequestMethod.POST)
    //@BusLogger(bussessid = 1, remark = "用户登录")
    public Response doLogin(HttpSession session, @RequestBody @Validated LoginForm loginForm,HttpServletRequest request,HttpServletResponse response) {
        String ip = getIP(request);
    	Object safecode = session.getAttribute(Const.SafeCode_SessionName);
        if (safecode==null || !safecode.toString().equalsIgnoreCase(loginForm.getCode())) {
        	logger.info(ip+"===登录失败===验证码错误" + loginForm+"\t"+safecode);
           return Response.factory().code(100).message("验证码错误");
        }
        session.removeAttribute(Const.SafeCode_SessionName);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token =
                new UsernamePasswordToken(loginForm.getUsername(), loginForm.getPassword());

        token.setRememberMe("1".equals(loginForm.getRememberMe()));
        try {
            //shiro登录
            subject.login(token);
            SysUser user0 = (SysUser) subject.getPrincipal();
            SysUser user_r = sysUserService.findById_Relative(user0.getId());
            logger.info(ip+"===登录成功===" + loginForm+"\t"+safecode);
            session.setAttribute(Const.SESSION_USER,user_r);
            String loginVerify = sysConfigService.findValueByKey(SysConfigKey.SmsLoginVerify);
            if ("true".equals(loginVerify)) { //需要通过短信验证码登录
            	//验证通过添加cookie
                if (!WebUtil.getCookieVerify(session,request)) {
                	String phones = user_r.getPhones();
                	List<String> mobiles = Arrays.asList(phones.split(","));
                	user_r.setMobiles(mobiles);
                	return Response.ok().code(202).body(user_r);
                }else {
                	return Response.ok().body(user_r);
                }
            }else {
            	WebUtil.setCookieVerify(session,response);
            	return Response.ok().body(user_r);
            }
        } catch (AuthenticationException e) {
        	logger.info(ip+"===登录失败===用户名或者密码错误" + loginForm+"\t"+safecode);
            return Response.factory().code(101).message("用户名或者密码错误");
        } catch (Exception e2) {
        	logger.info(ip+"===登录失败===数据异常" + loginForm+"\t"+safecode);
            return Response.factory().code(102).message("数据异常");
        }
    }

    @RequestMapping("/sys/doLogout")
    //@BusLogger(bussessid = 2, remark = "用户退出登录")
    public Response doLogout() {
        return Response.ok();
    }

    @RequestMapping("/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Response Unauthentication() {
        return new Response(401, "未登录");
    }

    @RequestMapping("/403")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Response Unauthorized() {
        return new Response(403, "无权访问");
    }

    @RequestMapping(value = "/sys/currentUser", method = RequestMethod.GET)
    public Response menus() {
        int userId = getUserId();
        SysUser user_r = sysUserService.findById_Relative(userId);
        if(StringUtils.isEmpty(user_r.getAvatar())){
          user_r.setAvatar("https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png");
        }
        List<SysMenu> but = sysMenuService.findButtonByUserId(user_r);
        List<SysMenu> nav = sysMenuService.findMenusByUserId(user_r);
        SimplePropertyPreFilter menuspp =
                new SimplePropertyPreFilter(SysMenu.class, "id", "menuname", "router", "children", "icon", "menutype");
        JSONObject data = new JSONObject();
        data.put("user", user_r);
        data.put("menu", nav);
        data.put("btn", but);
        String names = "menuname>name;router>path";
        String str = JSON.toJSONString(data,
                new SerializeFilter[]{menuspp, new JsonNameFilter(names), new JsonNullValueFilter("")});
        return Response.ok().body(JSON.parse(str));
    }

    @RequestMapping(value = "/sys/modify/password", method = RequestMethod.POST)
    public Response updatePassword(@RequestBody  SysUserForm userForm) {
        logger.info("修改密码{}",userForm);
        int userId = getUserId();
        String account = getUserAccount();
        //只能修改自己的密码
        userForm.setId(userId);
        userForm.setPassword(PasswordHander.encodedPassword(userForm.getPassword(),account));
        userForm.setNewpassword(PasswordHander.encodedPassword(userForm.getNewpassword(),account));
        int z = sysUserService.updatePassword(userForm);
        if(z==1){
            return Response.ok();
        }else{
            return Response.factory().code(z).message("修改失败");
        }
    }
    
    /**
     * 获取短信验证码
     * @param session
     * @param form
     * @param request
     * @return
     */
    @RequestMapping(value = "/sys/getCaptcha", method = RequestMethod.POST)
    public Response getCaptcha(HttpSession session, @RequestBody SysUserSmsForm form,HttpServletRequest request) {
    	setCurrUser(form);
    	String ip = getIP(request);
    	String referer = request.getHeader("Referer");
    	String mobile = form.getMobile();
    	logger.info("mobile>>>>>"+mobile);
    	if (WebUtil.isMobileNO(mobile) && referer != null) {
            String code = WebUtil.rand(4);
            logger.info("发送短信：" + mobile + "\t" + code + "\t" + ip + "\t" + referer);
            SmsQueue queue = new SmsQueue();
            queue.setStatus(Const.SMS_0);
            queue.setMobile(mobile);
            session.setAttribute(KEY_PRE + mobile, code);
            queue.setGmtCreate(LocalDateTime.now());
            //验证码内容：【师域云保】您的验证码是2234。如非本人操作，请忽略本短信
            queue.setContent(code);
            queue.setSourceIp(ip);
            queue.setSourceType(Const.Source_Type);
            queue.setTemplateId(Const.Template_LoginVerify);
            queue.setChannel("登录手机验证码");
            smsQueueService.save(queue);
            return Response.ok();
        }
        return Response.error().message("非法操作!");
    }
    
    /**
     * 验证手机验证码
     * @param session
     * @param form
     * @param request
     * @return
     */
    @RequestMapping(value = "/sys/doLoginMobile", method = RequestMethod.POST)
    public Response doLoginMobile(HttpSession session, @RequestBody SysUserSmsForm form,HttpServletRequest request,HttpServletResponse response) {
    	setCurrUser(form);
    	String mobile = form.getMobile();
    	String smscode = form.getSmscode();
    	logger.info("mobile>>>>>"+mobile+" smscode>>>"+smscode);
    	Object obj = session.getAttribute(KEY_PRE + mobile);
        if (obj != null) {
            String verifyServer = obj.toString();
            if (smscode.equalsIgnoreCase(verifyServer)) {
            	WebUtil.setCookieVerify(session,response);
                return Response.ok();
            }
        }
        return Response.factory().code(100).message("验证码输入错误!");
    }
}
