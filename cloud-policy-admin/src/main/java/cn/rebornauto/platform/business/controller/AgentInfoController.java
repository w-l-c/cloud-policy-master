package cn.rebornauto.platform.business.controller;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.rebornauto.platform.business.form.AgentInfoForm;
import cn.rebornauto.platform.business.query.AgentInfoQuery;
import cn.rebornauto.platform.business.query.PaymentStatisticsQuery;
import cn.rebornauto.platform.business.service.AgentInfoService;
import cn.rebornauto.platform.business.service.BusiLogService;
import cn.rebornauto.platform.business.service.PaymentChannelConfigService;
import cn.rebornauto.platform.business.service.SysDicService;
import cn.rebornauto.platform.business.vo.AgentVo;
import cn.rebornauto.platform.business.vo.PaymentStatisticsVo;
import cn.rebornauto.platform.common.Const;
import cn.rebornauto.platform.common.controller.BaseController;
import cn.rebornauto.platform.common.controller.ExportExcel;
import cn.rebornauto.platform.common.data.request.Form;
import cn.rebornauto.platform.common.data.request.Pagination;
import cn.rebornauto.platform.common.data.request.Request;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.common.data.view.ResponseCode;
import cn.rebornauto.platform.common.data.view.TableBody;
import cn.rebornauto.platform.common.util.TuoMinUtil;
import cn.rebornauto.platform.pay.tonglian.entity.TongLianInfo;
import cn.rebornauto.platform.sys.entity.SysUser;


/**
 * ???????????????
 * <p>Title: AgentInfoController</p>  
 * <p>Description:</p>  
 * @author zjl  
 * @date 2019???5???5???
 */
@RestController
@RequestMapping("/agentInfo")
public class AgentInfoController extends BaseController {
	@Autowired
	private AgentInfoService agentInfoService;
	
	@Autowired
	private PaymentChannelConfigService paymentChannelConfigService;
	
	@Autowired
	private SysDicService  sysDicService;
	
   @Autowired
   private   BusiLogService  busiLogService;
    
   
	/**
	 * ???????????????????????????
	 * @param request
	 * @return
	 */
	@PostMapping("/list")
	@RequiresPermissions("agentInfo:list")
	public Response list(@RequestBody Request<AgentInfoForm, AgentInfoQuery> request) {			
		Pagination pagination = request.getPagination();
		TableBody body = TableBody.factory();
		body.setPagination(pagination);
		AgentInfoQuery query = request.getQuery();
		AgentInfoForm form =request.getForm();	
	    setCurrUser(form);
		if (StringUtils.hasText(form.getUserCustomerId())) {
			// ??????????????????????????????true:????????? false:??????????????????
			if (!isAdministrator(form.getUserCustomerId())) {
				// ??????customerId ????????????
				query.setCustomerId(form.getUserCustomerId());
			}
		} else {
			query.setCustomerId(Const.NOTFOUND);
		}
		long rowcount = agentInfoService.countByQuery(query);
		// ???????????????
		pagination.setTotal(rowcount);
		// ??????????????????
		List<AgentVo> list = agentInfoService.pageQuery(pagination, query);	
		
		//?????????????????????
		if (!isAdministrator(form.getUserCustomerId())) {
			if(null!=list && list.size()>0) {
				for (int i = 0; i < list.size(); i++) {
					//???????????????
					if(org.apache.commons.lang.StringUtils.isNotBlank(list.get(i).getAgentIdcardno()) && list.get(i).getAgentIdcardno().length()==18) {
						list.get(i).setAgentIdcardno(TuoMinUtil.idcardEncrypt(list.get(i).getAgentIdcardno()));
					}else {//????????????
						list.get(i).setAgentIdcardno(TuoMinUtil.idPassport(list.get(i).getAgentIdcardno()));
					}
					//???????????????
					list.get(i).setAgentMobile(TuoMinUtil.mobileEncrypt(list.get(i).getAgentMobile()));
				}
			}
		}
		body.setList(list);
		return Response.ok().body(body);
	}
	
	
	/**
	 * 
	 *?????????????????????
	 * @return
	 */
	@PostMapping("/edit")
	@RequiresPermissions("agentInfo:edit")
	public Response update(	@RequestBody @Validated Request<AgentInfoForm, AgentInfoQuery> request,HttpServletRequest req) {
		AgentInfoForm form =request.getForm();	
		setCurrUser(form);
	   //??????????????????????????????true:????????? false:??????????????????
	   if(!isAdministrator(form.getUserCustomerId())){
	    if(!form.getCustomerId().equals(form.getUserCustomerId())){
			return Response.factory().code(-1).message("????????????");
	       }
	 	}
    
		int code = agentInfoService.edit(form);
		if (code < 1) {
			return Response.factory().code(-1).message("??????????????????");
		}
        //????????????
		busiLogService.add(req, Const.busi_log_busi_type_2, Const.busi_log_option_type_xiugai,"???????????????????????????", currentUser());
		return list(request);
	}
	
	
	/**
	 *????????????????????????,??????3??????
	 * @param request
	 * @return
	 */
	@PostMapping("/addBankCardNo")
	@RequiresPermissions("agentInfo:addBankCardNo")
	public Response add(@RequestBody @Validated Request<AgentInfoForm, AgentInfoQuery> request,HttpServletRequest req) {			
		AgentInfoForm form = request.getForm();
		setCurrUser(form);
		   //??????????????????????????????true:????????? false:??????????????????
		   if(!isAdministrator(form.getUserCustomerId())){
			    if(!form.getCustomerId().equals(form.getUserCustomerId())){
					return Response.factory().code(-1).message("????????????");
			   }
		}
		//???????????? online/????????????test  ??????
		String sysPaySwitch = sysDicService.selectSysPaySwitch();                      
		//????????????????????????
	    TongLianInfo tongLianInfo=paymentChannelConfigService.getTongLianInfo(Const.SYS_PAYMENT_CHANNEL_TONGLIAN,sysPaySwitch);  
	    //???????????????????????????????????????
		Response response = agentInfoService.addBankCardNo(form,tongLianInfo);
		if(response.getCode()!=200){
			return response;
		}
	      //????????????
		busiLogService.add(req, Const.busi_log_busi_type_2, Const.busi_log_option_type_xinzeng,"????????????????????????????????????", currentUser());
		return list(request);
	}
	
	
    /**
	 *????????????
	 * @return
	 */
	@RequestMapping("/allAuth")
	@RequiresPermissions("agentInfo:allAuth")
	public Response allAuth(@RequestBody @Validated Request<AgentInfoForm, AgentInfoQuery> request,HttpServletRequest req) {
		AgentInfoForm form =request.getForm();	
		setCurrUser(form);
	    agentInfoService.allAuth(form);
		busiLogService.add(req, Const.busi_log_busi_type_2, Const.busi_log_option_type_tongguo,"?????????????????????", currentUser());
		return list(request);
	}

	/**
	 * ????????????
	 */
	@RequestMapping("/paymentStatistics")
	@RequiresPermissions("agentInfo:paymentStatistics")
	public Response paymentStatistics(@RequestBody @Validated Request<Form, PaymentStatisticsQuery> request, HttpServletRequest req) {
		Pagination pagination = request.getPagination();
		TableBody body = TableBody.factory();
		body.setPagination(pagination);
		PaymentStatisticsQuery query = request.getQuery();
		if (!StringUtils.hasText(query.getMonth())) {
			query.setMonth(new SimpleDateFormat("yyyyMM").format(new Date()));
		}
		Form form = request.getForm();
		setCurrUser(form);
		if (StringUtils.hasText(form.getUserCustomerId())) {
			// ??????????????????????????????true:????????? false:??????????????????
			if (!isAdministrator(form.getUserCustomerId())) {
				// ??????customerId ????????????
				query.setCustomerId(form.getUserCustomerId());
			}
		} else {
			query.setCustomerId(Const.NOTFOUND);
		}
		long rowcount = agentInfoService.countByQueryPaymentStatistics(query);
		// ???????????????
		pagination.setTotal(rowcount);
		List<PaymentStatisticsVo> list = agentInfoService.queryPaymentStatistics(pagination, query);
		
		//?????????????????????
		if (!isAdministrator(form.getUserCustomerId())) {
			if(null!=list && list.size()>0) {
				for (int i = 0; i < list.size(); i++) {
					//???????????????
					if(org.apache.commons.lang.StringUtils.isNotBlank(list.get(i).getIdcardno()) && list.get(i).getIdcardno().length()==18) {
						list.get(i).setIdcardno(TuoMinUtil.idcardEncrypt(list.get(i).getIdcardno()));
					}else {//????????????
						list.get(i).setIdcardno(TuoMinUtil.idPassport(list.get(i).getIdcardno()));
					}
					//???????????????
					list.get(i).setPhone(TuoMinUtil.mobileEncrypt(list.get(i).getPhone()));
				}
			}
		}
		body.setList(list);
		return Response.ok().body(body);
	}
	/**
	 * ??????????????????
	 */
	@GetMapping("/paymentStatisticsExport")
	public String paymentStatisticsExport(HttpServletResponse response, HttpServletRequest request){
		PaymentStatisticsQuery query = new PaymentStatisticsQuery();
		try {
			response.setCharacterEncoding("UTF-8");
			String filedisplay = "???????????????.xls";
			filedisplay = URLEncoder.encode(filedisplay, "UTF-8");
			response.addHeader("Content-Disposition", "attachment;filename="+ filedisplay);
			response.setContentType("application/x-download");
			List<Object[]> dataList=new ArrayList<Object[]>();

			query.setMonth(request.getParameter("month"));
			query.setAgentName(request.getParameter("agentName"));
			if (!StringUtils.hasText(query.getMonth())) {
				query.setMonth(new SimpleDateFormat("yyyyMM").format(new Date()));
			}
			SysUser sysUser = currentUser();
			if (StringUtils.hasText(sysUser.getCustomerId())) {
				// ??????????????????????????????true:????????? false:??????????????????
				if (!isAdministrator(sysUser.getCustomerId())) {
					// ??????customerId ????????????
					query.setCustomerId(sysUser.getCustomerId());
				}
			} else {
				query.setCustomerId(Const.NOTFOUND);
			}
			List<PaymentStatisticsVo> list = agentInfoService.queryPaymentStatisticsForExport(query);
			for (PaymentStatisticsVo paymentStatisticsVo : list) {
				// ??????????????????????????????true:????????? false:??????????????????
				if (!isAdministrator(sysUser.getCustomerId())) {
					//???????????????
					if(org.apache.commons.lang.StringUtils.isNotBlank(paymentStatisticsVo.getIdcardno()) && paymentStatisticsVo.getIdcardno().length()==18) {
						paymentStatisticsVo.setIdcardno(TuoMinUtil.idcardEncrypt(paymentStatisticsVo.getIdcardno()));
					}else {//????????????
						paymentStatisticsVo.setIdcardno(TuoMinUtil.idPassport(paymentStatisticsVo.getIdcardno()));
					}
					//???????????????
					paymentStatisticsVo.setPhone(TuoMinUtil.mobileEncrypt(paymentStatisticsVo.getPhone()));
				}
				Object[] datas=new Object[]{
						paymentStatisticsVo.getMonth()
						,paymentStatisticsVo.getAgentName()
						,paymentStatisticsVo.getIdcardno()
						,paymentStatisticsVo.getPhone()
						,paymentStatisticsVo.getPaymentSum()};
				dataList.add(datas);
			}
			String [] rowName = new String[] {
					"??????",
					"??????",
					"????????????",
					"?????????",
					"???????????????",

			};
			ExportExcel exportExcel = new ExportExcel(response,"???????????????",rowName,dataList);
			exportExcel.exportWithoutNo();
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return null;
	}
	
	/**
	 * ???????????????????????????
	 * @param request
	 * @param req
	 * @return
	 * @throws Exception 
	 */
	@PostMapping("/addAgentInfoSingle")
	@RequiresPermissions("agentInfo:addAgentInfoSingle")
	public Response addAgentInfo(@RequestBody @Validated Request<AgentInfoForm, AgentInfoQuery> request,HttpServletRequest req) throws Exception {
		AgentInfoForm form =request.getForm();	
		setCurrUser(form);
		SysUser user = currentUser();
    
		Response resp = agentInfoService.addAgentInfoSingle(form,user);
		if(resp.getCode()!=ResponseCode.SUCCESS.value()) {
			busiLogService.add(req, Const.busi_log_busi_type_2, Const.busi_log_option_type_xiugai,"???????????????????????????!", currentUser());
        	return resp;
		}else {
			busiLogService.add(req, Const.busi_log_busi_type_2, Const.busi_log_option_type_xiugai,"???????????????????????????!", currentUser());
		}
		return list(request);
	}
	
}
