package cn.rebornauto.platform.business.controller;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.rebornauto.platform.business.entity.CustomerInfo;
import cn.rebornauto.platform.business.form.RechargeForm;
import cn.rebornauto.platform.business.query.RechargeQuery;
import cn.rebornauto.platform.business.service.BusiLogService;
import cn.rebornauto.platform.business.service.RechargeService;
import cn.rebornauto.platform.business.vo.RechargeVo;
import cn.rebornauto.platform.common.Const;
import cn.rebornauto.platform.common.controller.BaseController;
import cn.rebornauto.platform.common.controller.ExportExcel;
import cn.rebornauto.platform.common.data.request.Pagination;
import cn.rebornauto.platform.common.data.request.Request;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.common.data.view.ResponseCode;
import cn.rebornauto.platform.common.data.view.TableBody;
import cn.rebornauto.platform.common.enums.RechargeEnum;
import cn.rebornauto.platform.common.util.DateUtils;
import cn.rebornauto.platform.common.util.LogUtil;
import cn.rebornauto.platform.pay.tonglian.utils.DateUtil;
import cn.rebornauto.platform.sys.entity.SysUser;

@RestController
@RequestMapping("/recharge")
public class RechargeController extends BaseController {
    @Autowired
    private RechargeService rechargeService;
    @Autowired
    private BusiLogService busiLogService;
    @PostMapping("/add")
    @RequiresPermissions("recharge:add")
    public Response add (@RequestBody Request<RechargeForm, RechargeQuery> request, HttpServletRequest req) {
        RechargeForm form = request.getForm();
        String ip = getIP(req);
        form.setIp(ip);
        setCurrUser(form);
        Response resp = rechargeService.save(form);
        if(resp.getCode()!=ResponseCode.SUCCESS.value()) {
        	return Response.error().message(resp.getMessage());
        }
        return list(request);
    }
    @PostMapping("/edit")
    @RequiresPermissions("recharge:edit")
    public Response edit (@RequestBody Request<RechargeForm, RechargeQuery> request, HttpServletRequest req) {
        RechargeForm form = request.getForm();
        String ip = getIP(req);
        form.setIp(ip);
        setCurrUser(form);
        Response resp = rechargeService.edit(form);
        if(resp.getCode()!=ResponseCode.SUCCESS.value()) {
        	return Response.error().message(resp.getMessage());
        }
        return list(request);
    }

    @PostMapping("/submitCheck")
    @RequiresPermissions("recharge:submitCheck")
    public Response submitCheck(@RequestBody Request<RechargeForm, RechargeQuery> request, HttpServletRequest req) {
        RechargeForm form = request.getForm();
        String ip = getIP(req);
        form.setIp(ip);
        setCurrUser(form);
        Response resp = rechargeService.submitCheck(form);
        if(resp.getCode()!=ResponseCode.SUCCESS.value()) {
        	return Response.error().message(resp.getMessage());
        }
        busiLogService.add(req, Const.busi_log_busi_type_4, Const.busi_log_option_type_shenqing,"充值审核", currentUser());
        return list(request);
    }
    @PostMapping("/checkSuccess")
    @RequiresPermissions("recharge:checkSuccess")
    public Response checkSuccess(@RequestBody Request<RechargeForm, RechargeQuery> request, HttpServletRequest req) {
        RechargeForm form = request.getForm();
        setCurrUser(form);
        SysUser user = currentUser();
        Response resp = rechargeService.checkSuccess(form,user);
        if(resp.getCode()!=ResponseCode.SUCCESS.value()) {
        	return Response.error().message(resp.getMessage());
        }
        busiLogService.add(req, Const.busi_log_busi_type_4, Const.busi_log_option_type_success,"充值审核成功", currentUser());
        return checkList(request);
    }
    @PostMapping("/checkFail")
    @RequiresPermissions("recharge:checkFail")
    public Response checkFail(@RequestBody Request<RechargeForm, RechargeQuery> request, HttpServletRequest req) {
        RechargeForm form = request.getForm();
        setCurrUser(form);
        Response resp = rechargeService.checkFail(form);
        if(resp.getCode()!=ResponseCode.SUCCESS.value()) {
        	return Response.error().message(resp.getMessage());
        }
        busiLogService.add(req, Const.busi_log_busi_type_4, Const.busi_log_option_type_fail,"充值审核驳回", currentUser());
        return checkList(request);
    }

    @PostMapping("/queryCustomerInfo")
    public Response queryCustomerInfo (@RequestBody JSONObject json) {
        Map map = new HashMap();
        JSONObject query = json.getJSONObject("query");
        String customerId = query.getString("customerId");
        CustomerInfo customerInfo = rechargeService.getCustomerInfo(customerId);
        map.put("customer",customerInfo);
        return Response.ok().body(map);
    }
    @PostMapping("/checkList")
    @RequiresPermissions("recharge:checkList")
    public Response checkList(@RequestBody Request<RechargeForm, RechargeQuery> request) {
        Pagination pagination = request.getPagination();
        RechargeQuery query = request.getQuery();
        //获取操作人信息
        SysUser sysUser = currentUser();
        //判断是否为平台角色
        boolean administrator = isAdministrator(sysUser.getCustomerId());
        if (!administrator) {
            return Response.factory().code(-1).message("超出权限");
        }
        TableBody body = TableBody.factory();
        body.setPagination(pagination);
        int rowcount = rechargeService.count4CheckList(query);
        pagination.setTotal(rowcount);
        List<RechargeVo> list = rechargeService.page4CheckList(query, pagination);
        body.setList(list);
        return Response.ok().body(body);
    }
    @PostMapping("/list")
    @RequiresPermissions("recharge:list")
    public Response list(@RequestBody Request<RechargeForm, RechargeQuery> request) {
        Pagination pagination = request.getPagination();
        RechargeQuery query = request.getQuery();
        //获取操作人信息
        SysUser sysUser = currentUser();
        //判断是否为平台角色
        boolean administrator = isAdministrator(sysUser.getCustomerId());
        if (!administrator) {
            //不是平台角色需添加客户角色查询限制
            query.setCustomerId(sysUser.getCustomerId());
        }
        TableBody body = TableBody.factory();
        body.setPagination(pagination);
        long rowcount = rechargeService.count(query,sysUser);
        pagination.setTotal(rowcount);
        List<RechargeVo> list = rechargeService.page(query, sysUser, pagination);
        body.setList(list);
        return Response.ok().body(body);
    }
    
    /**
	 * 充值成功数据导出
	 * @param response
	 * @param request
	 * @return
	 */
	@SuppressWarnings("all")
    @GetMapping("/exportRechargeData")
    public String exportRechargeData(HttpServletResponse response, HttpServletRequest request){
		String customerId = request.getParameter("customerId");
		String customerName = request.getParameter("customerName");
    	String arrivetime = request.getParameter("arrivetime");
    	RechargeQuery query = new RechargeQuery();
    	query.setArriveTime(arrivetime);
    	if(StringUtils.isBlank(arrivetime)) {
    		LogUtil.info("日期不能为空!");
    		return "日期不能为空!";
    	}else{
    		if(DateUtil.getDaySub(query.getStartTime(), query.getEndTime())>31) {
    			LogUtil.info("日期范围不能超过一个月!");
        		return "日期范围不能超过一个月!";
    		}
    	}
    	
    	String title = "充值成功数据";
    	
        LogUtil.info("导出 "+title);
        try{
            response.setCharacterEncoding("UTF-8");
            String filedisplay = title+".xls";
            filedisplay = URLEncoder.encode(filedisplay, "UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename="+ filedisplay);
            response.setContentType("application/x-download");
            
            
            //获取操作人信息
            SysUser sysUser = currentUser();
            //判断是否为平台角色
            boolean administrator = isAdministrator(sysUser.getCustomerId());
            if (!administrator) {
                //不是平台角色需添加客户角色查询限制
                query.setCustomerId(sysUser.getCustomerId());
            }

            query.setRecharge(Const.recharge_3);
            List<RechargeVo> statList = rechargeService.selectRechargeExcelByQuery(query);
            
            List<Object[]> dataList=new ArrayList<Object[]>();
            for(int i=0;i<statList.size();i++){
            	String s1 = String.valueOf(statList.get(i).getId());
            	String s2= 	DateUtils.LocalDateTimeToString(statList.get(i).getApplytime());
            	String s3 = statList.get(i).getCustomerId();
            	String s4 = statList.get(i).getCustomerName();
            	String s5 = String.valueOf(statList.get(i).getServiceRate());
            	String s6 = String.valueOf(statList.get(i).getRealPaymentFin());
            	String s7 = String.valueOf(statList.get(i).getAgentCommissionFin());
            	String s8 = RechargeEnum.getName(statList.get(i).getRecharge());
            	String s9 = DateUtils.LocalDateTimeToString(statList.get(i).getArrivetime());
            	
                
                Object[] datas=new Object[]{s1,s2,s3,s4,s5,s6,s7,s8,s9};
                dataList.add(datas);
            }
            String [] rowName = new String[] {
            		"充值号",
            		"申请日期",
            		"客户编号",
                    "客户名称",
                    "服务费率(%)",
                    "实际充值金额",
                    "付款金额",
                    "充值状态",
                    "到账时间"
            };
            ExportExcel exportExcel = new ExportExcel(response,title,rowName,dataList);
            exportExcel.export();
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
        return null;
    }
}
