package cn.rebornauto.platform.business.controller;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.rebornauto.platform.business.form.AgentBankNoInfoForm;
import cn.rebornauto.platform.business.query.AgentBankNoInfoQuery;
import cn.rebornauto.platform.business.service.AgentBankNoInfoService;
import cn.rebornauto.platform.business.service.BusiLogService;
import cn.rebornauto.platform.business.service.CustomerInfoService;
import cn.rebornauto.platform.business.service.IdcardZonenumInfoService;
import cn.rebornauto.platform.business.service.PaymentChannelConfigService;
import cn.rebornauto.platform.business.service.SysDicService;
import cn.rebornauto.platform.business.vo.AgentBankNoVo;
import cn.rebornauto.platform.common.Const;
import cn.rebornauto.platform.common.controller.BaseController;
import cn.rebornauto.platform.common.data.request.Query;
import cn.rebornauto.platform.common.data.request.Request;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.common.data.view.TableBody;
import cn.rebornauto.platform.configuration.WxProperties;
import cn.rebornauto.platform.pay.tonglian.entity.TongLianInfo;
import cn.rebornauto.platform.sms.service.SmsQueueService;
import cn.rebornauto.platform.sys.entity.SysUser;
import cn.rebornauto.platform.utils.IdcardUtils_new;
/**
 * 
 * <p>Title: AgentBankNoInfoController</p>  
 * <p>Description:</p>  
 * @author zjl  
 * @date 2019年5月1日
 */
@RestController
@RequestMapping("/gzh/agentBankNoInfo")
public class AgentBankNoInfoController extends BaseController {
	
    @Autowired
    SmsQueueService smsQueueService;
	
	@Autowired
	AgentBankNoInfoService  agentBankNoInfoService;
	
	@Autowired
	private CustomerInfoService customerInfoService;
	
	@Autowired
	private PaymentChannelConfigService paymentChannelConfigService;
	
	@Autowired
	private SysDicService  sysDicService;
	
	@Autowired
	private WxProperties  wxProperties;
	
	@Autowired
	private IdcardZonenumInfoService  idcardZonenumInfoService;
	
	 @Autowired
	  private   BusiLogService  busiLogService;
	
	
	/**
	 * GZH根据代理人id查询他的所有银行卡
	 * 
	 * @param id
	 * @param openid
	 * @param httpServletRequest
	 * @return
	 */
	@GetMapping("/list")
	@RequiresPermissions("gzh:agentBankNoInfo:list")
	public Response list(@RequestParam(value ="id", defaultValue ="-1") int id,@RequestParam(value ="openid", defaultValue ="-1") String openid,HttpServletRequest httpServletRequest) {				
		// 验证openid
		ServletContext application = httpServletRequest.getSession().getServletContext();			
		if (application.getAttribute(wxProperties.getApplicationWxOpenid()+openid) == null) {			
			return Response.factory().code(-1).message("未授权");
		}
		TableBody body = TableBody.factory();
		List<AgentBankNoVo> list = agentBankNoInfoService.selectAgentBankNoListByAgentId(id);				
		body.setList(list);
		return Response.ok().body(body);
	}	
	
	
	
	/**
	 * GZH根据代理人校验银行卡的信息
	 * 
	 * @param request
	 * @param httpServletRequest
	 * @return
	 */
	@PostMapping("/verification")
	@RequiresPermissions("gzh:agentBankNoInfo:verification")
	public Response verification(@RequestBody @Validated Request<AgentBankNoInfoForm, Query> request,HttpServletRequest httpServletRequest,HttpSession session) {			
		AgentBankNoInfoForm form = request.getForm();
		// 验证openid
		ServletContext application = httpServletRequest.getSession().getServletContext();
		if (application.getAttribute(wxProperties.getApplicationWxOpenid()+form.getOpenid()) == null) {			
			return Response.factory().code(-1).message("未授权");
		}	
		//线上环境 online/测试环境test  开关
		String sysPaySwitch = sysDicService.selectSysPaySwitch();                      
		//获取通联配置信息
	    TongLianInfo tongLianInfo=paymentChannelConfigService.getTongLianInfo(Const.SYS_PAYMENT_CHANNEL_TONGLIAN,sysPaySwitch);
	    //校验身份证
//       Map<Integer, String> zoneNum = idcardZonenumInfoService.selectZoneNums();
       if (StringUtils.isBlank(form.getAgentIdcardno())) {
//       if (!IdcardUtils_new.isIdcard(form.getAgentIdcardno(), zoneNum)) {
   		return Response.factory().code(1).message("证件格式不对");  	
       } 
		//校验三要素
  	    return agentBankNoInfoService.verification(form,tongLianInfo);
	}
	
	/**
	 * GZH根据代理人id录入银行卡的信息
	 * 
	 * @param request
	 * @param httpServletRequest
	 * @return
	 */
	@PostMapping("/add")
	@RequiresPermissions("gzh:agentBankNoInfo:add")
	public Response add(@RequestBody @Validated Request<AgentBankNoInfoForm, AgentBankNoInfoQuery> request,HttpServletRequest httpServletRequest) {			
		AgentBankNoInfoForm form = request.getForm();
		// 验证openid
		ServletContext application = httpServletRequest.getSession().getServletContext();
		if (application.getAttribute(wxProperties.getApplicationWxOpenid()+form.getOpenid()) == null) {			
			return Response.factory().code(-1).message("未授权");
		}	
		//线上环境 online/测试环境test  开关
		String sysPaySwitch = sysDicService.selectSysPaySwitch();                      
		//获取通联配置信息
	    TongLianInfo tongLianInfo=paymentChannelConfigService.getTongLianInfo(Const.SYS_PAYMENT_CHANNEL_TONGLIAN,sysPaySwitch);	    
	    //校验三要素，录入银行卡信息
	    Response response=agentBankNoInfoService.addAgentBankNoInfo(form,tongLianInfo);
		if(response.getCode()!=200){
			return response;
		}
		SysUser user=new SysUser();
		user.setCustomerId(form.getCustomerId());
		busiLogService.add(httpServletRequest, Const.busi_log_busi_type_2, Const.busi_log_option_type_xinzeng,"录入代理人银行卡的信息",user);
		return list(form.getId(),form.getOpenid(),httpServletRequest);
	}

	
}
