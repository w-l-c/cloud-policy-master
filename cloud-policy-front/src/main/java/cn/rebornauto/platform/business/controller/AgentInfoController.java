package cn.rebornauto.platform.business.controller;
import cn.rebornauto.platform.business.form.AgentCustomerForm;
import cn.rebornauto.platform.business.form.AgentInfoForm;
import cn.rebornauto.platform.business.query.AgentCustomerQuery;
import cn.rebornauto.platform.business.query.AgentInfoQuery;
import cn.rebornauto.platform.business.service.AgentInfoService;
import cn.rebornauto.platform.business.service.BusiLogService;
import cn.rebornauto.platform.business.vo.GzhAgentCustomerVo;
import cn.rebornauto.platform.business.vo.GzhAgentInfoVo;
import cn.rebornauto.platform.business.vo.GzhUserInfoVo;
import cn.rebornauto.platform.common.Const;
import cn.rebornauto.platform.common.controller.BaseController;
import cn.rebornauto.platform.common.data.request.Request;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.common.data.view.TableBody;
import cn.rebornauto.platform.configuration.WxProperties;
import cn.rebornauto.platform.sys.entity.SysUser;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 
 * <p>Title: AgentInfoController</p>  
 * <p>Description:</p>  
 * @author zjl  
 * @date 2019年4月30日
 */
@RestController
@RequestMapping("/gzh/agentInfo")
public class AgentInfoController extends BaseController {
	@Autowired
	private AgentInfoService agentInfoService;
	
    @Autowired
    private WxProperties wxProperties;
		
    @Autowired
    private   BusiLogService  busiLogService;
    /**
     * GZH根据openid查用户的信息
     * @param openid
     * @param httpServletRequest
     * @return
     */
	@GetMapping("/userInfo")
	@RequiresPermissions("gzh:agentInfo:userInfo")
	public Response userInfo(@RequestParam(value ="openid", defaultValue ="-1") String openid,HttpServletRequest httpServletRequest) {	
		 //验证openid	
		ServletContext application = httpServletRequest.getSession().getServletContext();
		if(application.getAttribute(wxProperties.getApplicationWxOpenid()+openid)==null){
			return Response.factory().code(-1).message("未授权");	
		}
		GzhUserInfoVo agentInfoVo = agentInfoService.selectUserInfoByOpenid(openid);				
		return Response.ok().body(agentInfoVo);
	}
		
	
    /**
     * GZH根据openid查询代理人的信息
     * @param openid
     * @param httpServletRequest
     * @return
     */
	@GetMapping("/info")
	@RequiresPermissions("gzh:agentInfo:info")
	public Response info(@RequestParam(value ="openid", defaultValue ="-1") String openid,HttpServletRequest httpServletRequest) {	
		  //验证openid	
		ServletContext application = httpServletRequest.getSession().getServletContext();
		if(application.getAttribute(wxProperties.getApplicationWxOpenid()+openid)==null){
			return Response.factory().code(-1).message("未授权");	
		}
		GzhAgentInfoVo agentInfoVo = agentInfoService.selectAgentInfoByOpenid(openid);				
		return Response.ok().body(agentInfoVo);
	}
	/**
	 * GZH录入代理人的信息
	 * @param request
	 * @param httpServletRequest
	 * @return
	 * @throws Exception 
	 */
	@PostMapping("/add")
	@RequiresPermissions("gzh:agentInfo:add")
	public Response add(@RequestBody @Validated Request<AgentInfoForm, AgentInfoQuery> request,HttpServletRequest httpServletRequest) throws Exception{	
		AgentInfoForm form = request.getForm();
	  	//验证openid
		ServletContext application = httpServletRequest.getSession().getServletContext();
		if(application.getAttribute(wxProperties.getApplicationWxOpenid()+form.getOpenid())==null){
			return Response.factory().code(-1).message("未授权");	
		}
		if (!StringUtils.isEmpty(form.getAgentMobile())) {
			if (!Pattern.matches("^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$",form.getAgentMobile())) {
				return Response.factory().code(-1).message("手机号格式错误");
			}
		}
		agentInfoService.addAgentInfo(form);
		SysUser user=new SysUser();
		user.setCustomerId(form.getCustomerId());
		 //业务日记
		busiLogService.add(httpServletRequest, Const.busi_log_busi_type_2, Const.busi_log_option_type_xinzeng,"录入代理人的信息成功",user);
		return Response.ok().message("录入代理人的信息成功");
	}
	
    /**
     * GZH根据代理人id查询保险公司(客户)以及代征主体的信息列表
     * @param id
     * @param openid
     * @param httpServletRequest
     * @return
     */
	@GetMapping("/signList")
	@RequiresPermissions("gzh:agentInfo:signList")
	public Response signList(@RequestParam(value ="id", defaultValue ="-1") int id,@RequestParam(value ="openid", defaultValue ="-1") String openid,HttpServletRequest httpServletRequest) {		
	    //验证openid	
		ServletContext application = httpServletRequest.getSession().getServletContext();
		if(application.getAttribute(wxProperties.getApplicationWxOpenid()+openid)==null){
			return Response.factory().code(-1).message("未授权");	
		}
		TableBody body = TableBody.factory();
		List<GzhAgentCustomerVo> list = agentInfoService.selectAgentCustomerListByAgentId(id);				
		body.setList(list);
		return Response.ok().body(body);
	}	
	
	/**
	 * GZH代理人签约客户
	 * @param request
	 * @param httpServletRequest
	 * @return
	 * @throws Exception 
	 */
	   @PostMapping("/sign")
	  @RequiresPermissions("gzh:agentInfo:sign")
	  public Response sign(@RequestBody @Validated Request<AgentCustomerForm, AgentCustomerQuery> request,HttpServletRequest httpServletRequest) throws Exception{	
		   AgentCustomerForm form = request.getForm();
	       //验证openid	
		  ServletContext application =
		  httpServletRequest.getSession().getServletContext();
		  if(application.getAttribute(wxProperties.getApplicationWxOpenid()+form.
		  getOpenid())==null){ return Response.factory().code(-1).message("未授权"); }
        	String manualSigningUrl = agentInfoService.agentSignCustomer(form);
    		return Response.ok().body(manualSigningUrl);
	}
	
    /**
     * GZH根据代理人保险公司(客户)关系表id 以及代征主体的信息
     * @param id
     * @param openid
     * @param httpServletRequest
     * @return
     */
	@GetMapping("/signInfo")
	@RequiresPermissions("gzh:agentInfo:signInfo")
	public Response signInfo(@RequestParam(value ="id", defaultValue ="-1") int id,@RequestParam(value ="openid", defaultValue ="-1") String openid,HttpServletRequest httpServletRequest) {		
	    //验证openid	
		ServletContext application = httpServletRequest.getSession().getServletContext();
		if(application.getAttribute(wxProperties.getApplicationWxOpenid()+openid)==null){
			return Response.factory().code(-1).message("未授权");	
		}
    	GzhAgentCustomerVo gzhAgentCustomerVo = agentInfoService.selectAgentCustomerInfoById(id);					
		return Response.ok().body(gzhAgentCustomerVo);
	}	

	/**
	 * 上上签回调
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/callback", method = RequestMethod.GET)
	protected final ModelAndView callback(HttpServletRequest httpServletRequest) throws Exception {
		String contractId = httpServletRequest.getParameter("contractId");
		if (StringUtils.hasText(contractId)) {
			ServletContext application = httpServletRequest.getSession().getServletContext();
			String openid=agentInfoService.callbackSign(contractId);
			String headimgurl = (String) application.getAttribute(wxProperties.getApplicationWxOpenid()+openid+"headerPicUrl");
		    return new ModelAndView("redirect:/personalCenter/router?openid="+openid+"&headerPicUrl="+headimgurl);
		}
	    return new ModelAndView("/404");
	}
}
