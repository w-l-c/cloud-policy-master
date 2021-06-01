package cn.rebornauto.platform.business.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.rebornauto.platform.business.form.InvoiceForm;
import cn.rebornauto.platform.business.query.InvoiceQuery;
import cn.rebornauto.platform.business.service.BusiLogService;
import cn.rebornauto.platform.business.service.MakeInvoiceDetailService;
import cn.rebornauto.platform.business.service.MakeInvoiceService;
import cn.rebornauto.platform.business.vo.MakeInvoiceDetailVO;
import cn.rebornauto.platform.common.Const;
import cn.rebornauto.platform.common.controller.BaseController;
import cn.rebornauto.platform.common.data.request.Pagination;
import cn.rebornauto.platform.common.data.request.Request;
import cn.rebornauto.platform.common.data.view.EnumsBody;
import cn.rebornauto.platform.common.data.view.MakeInvoiceTableBody;
import cn.rebornauto.platform.common.data.view.OptionVO;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.common.data.view.ResponseCode;
import cn.rebornauto.platform.payment.service.OrderService;
import cn.rebornauto.platform.sys.entity.SysEnums;
import cn.rebornauto.platform.sys.entity.SysUser;
import cn.rebornauto.platform.sys.service.SysEnumsService;


/**
 * <p>Title: 开票管理</p>
 * <p>Description: </p>
 * <p>Company: </p>
 * @author kgc
 * @date Mar 17, 2020 3:19:57 PM
 */
@RestController
@RequestMapping("/makeInvoice")
public class MakeInvoiceController  extends BaseController {

	@Autowired
    BusiLogService busiLogService;
	
	@Autowired
	MakeInvoiceDetailService makeInvoiceDetailService;
	
	@Autowired
	MakeInvoiceService makeInvoiceService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	SysEnumsService sysEnumsService;
	
	/**
	 * 列表查询
	 * @param request
	 * @return
	 */
	@PostMapping("/list")
    @RequiresPermissions("makeInvoice:list")
    public Response list(@RequestBody Request<InvoiceForm, InvoiceQuery> request) {
        Pagination pagination = request.getPagination();
        InvoiceQuery query = request.getQuery();
        
        MakeInvoiceTableBody body = MakeInvoiceTableBody.factory();
        //获取操作人信息
        SysUser sysUser = currentUser();
        
        //可开票总额
        BigDecimal invoiceAmount = BigDecimal.ZERO;
        //判断是否为平台角色
        boolean administrator = isAdministrator(sysUser.getCustomerId());
        if (!administrator) {
            //不是平台角色需添加客户角色查询限制
            query.setCustomerId(sysUser.getCustomerId());
            
            //判断是否开票主表存在数据，如果不存在，默认插入一条可开票额度的数据
            invoiceAmount = makeInvoiceService.checkDataUnExistToCreate(sysUser.getCustomerId(),sysUser.getNickname());
        }
        
        int rowcount = makeInvoiceDetailService.count(query);
        pagination.setTotal(rowcount);
        body.setPagination(pagination);
        List<MakeInvoiceDetailVO> list = makeInvoiceDetailService.list(query,pagination, currentUser());
        body.setList(list);
        body.setInvoiceAmount(invoiceAmount);
        return Response.ok().body(body);
    }
	
	/**
     * 开票申请
     * @return
     */
    @GetMapping("/apply")
    @ResponseBody
    public Response apply() {
    	SysUser user = currentUser();
    	MakeInvoiceDetailVO vo = makeInvoiceDetailService.apply(user);
        
        return Response.ok().body(vo);
    }
    
    /**
     * 开票申请保存
     * @param request
     * @return
     * @throws Exception 
     */
    @PostMapping("/applySave")
    @RequiresPermissions("makeInvoice:applySave")
    public Response applySave(@RequestBody Request<InvoiceForm, InvoiceQuery> request,HttpServletRequest req) throws Exception {
    	InvoiceForm form = request.getForm();
        setCurrUser(form);
        SysUser user = currentUser();
        Response resp = makeInvoiceDetailService.applySave(form,user);
        if(resp.getCode()!=ResponseCode.SUCCESS.value()) {
        	//操作日志
	        busiLogService.add(req, Const.busi_log_busi_type_10, Const.busi_log_option_type_fail,"开票申请保存失败", user);
        	return resp;
        }else {
        	//操作日志
	        busiLogService.add(req, Const.busi_log_busi_type_10, Const.busi_log_option_type_success,"开票申请保存成功", user);
        }
        return list(request);
    }
    
    /**
     * 查看明细
     * @return
     */
    @GetMapping("/detail")
    @ResponseBody
    public Response detail(String id) {
    	SysUser user = currentUser();
    	String customerId = user.getCustomerId();
    	MakeInvoiceDetailVO detail = makeInvoiceDetailService.detail(id);
    	//同一个客户的单子才能查看，admin除外
    	if(!isAdministrator(customerId)) {
    		if(!detail.getCustomerId().equalsIgnoreCase(customerId)) {
        		return Response.factory().code(-1).message("无权查看!");
        	}
    	}
        return Response.ok().body(detail);
    }
    
    /**
     * 确认开票
     * @param request
     * @return
     * @throws Exception 
     */
    @PostMapping("/examine")
    @RequiresPermissions("makeInvoice:examine")
    public Response examine(@RequestBody Request<InvoiceForm, InvoiceQuery> request,HttpServletRequest req) throws Exception {
    	InvoiceForm form = request.getForm();
        setCurrUser(form);
        SysUser user = currentUser();
        Response resp = makeInvoiceDetailService.examine(form,user);
        if(resp.getCode()!=ResponseCode.SUCCESS.value()) {
        	//操作日志
	        busiLogService.add(req, Const.busi_log_busi_type_10, Const.busi_log_option_type_fail,"确认开票失败", user);
        	return resp;
        }else {
        	//操作日志
	        busiLogService.add(req, Const.busi_log_busi_type_10, Const.busi_log_option_type_success,"确认开票成功", user);
        }
        return list(request);
    }
    
    /**
     * 确认寄送（弹窗输入快递公司和快递单号）
     * @return
     */
    @GetMapping("/send")
    @ResponseBody
    public Response send(String id) {
    	//获取快递公司列表
    	List<SysEnums> enumList = sysEnumsService.getEnumsByCategory("expressCompany");
    	EnumsBody body = EnumsBody.factory();
    	if(null!=enumList && enumList.size()>0) {
    		for (int i = 0; i < enumList.size(); i++) {
        		OptionVO vo = new OptionVO();
            	vo.setKey(enumList.get(i).getEnumvalue());
            	vo.setValue(enumList.get(i).getLiteral());
                body.getList().add(vo);
    		}
    	}
        return Response.ok().body(body.getList());
    }
    
    /**
     * 寄送保存
     * @param request
     * @return
     * @throws Exception 
     */
    @PostMapping("/sendSave")
    @RequiresPermissions("makeInvoice:sendSave")
    public Response sendSave(@RequestBody Request<InvoiceForm, InvoiceQuery> request,HttpServletRequest req) throws Exception {
    	InvoiceForm form = request.getForm();
        setCurrUser(form);
        SysUser user = currentUser();
        Response resp = makeInvoiceDetailService.sendSave(user,form);
        if(resp.getCode()!=ResponseCode.SUCCESS.value()) {
        	busiLogService.add(req, Const.busi_log_busi_type_10, Const.busi_log_option_type_fail,"寄送失败", user);
        	return resp;
        }else {
        	//操作日志
	        busiLogService.add(req, Const.busi_log_busi_type_10, Const.busi_log_option_type_success,"寄送成功", user);
        }
        return list(request);
    }
    
    /**
     * 发票类型
     * @return
     */
    @GetMapping("/invoiceType")
    @ResponseBody
    public Response invoiceType() {
    	//获取快递公司列表
    	List<SysEnums> enumList = sysEnumsService.getEnumsByCategory("invoiceType");
    	EnumsBody body = EnumsBody.factory();
    	if(null!=enumList && enumList.size()>0) {
    		for (int i = 0; i < enumList.size(); i++) {
        		OptionVO vo = new OptionVO();
            	vo.setKey(enumList.get(i).getEnumvalue());
            	vo.setValue(enumList.get(i).getLiteral());
                body.getList().add(vo);
    		}
    	}
        return Response.ok().body(body.getList());
    }
}
