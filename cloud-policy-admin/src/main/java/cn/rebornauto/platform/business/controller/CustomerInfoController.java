package cn.rebornauto.platform.business.controller;
import cn.rebornauto.platform.business.form.CustomerInfoForm;
import cn.rebornauto.platform.business.query.CustomerInfoQuery;
import cn.rebornauto.platform.business.service.BusiLogService;
import cn.rebornauto.platform.business.service.CustomerInfoService;
import cn.rebornauto.platform.business.vo.CustomerVo;
import cn.rebornauto.platform.common.Const;
import cn.rebornauto.platform.common.controller.BaseController;
import cn.rebornauto.platform.common.data.request.Pagination;
import cn.rebornauto.platform.common.data.request.Request;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.common.data.view.TableBody;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import java.util.List;


/**
 * 客户管理菜单
 * <p>Title: CustomerInfoController</p>  
 * <p>Description:</p>  
 * @author zjl  
 * @date 2019年5月5日
 */
@RestController
@RequestMapping("/customerInfo")
public class CustomerInfoController extends BaseController {
	
	@Autowired
	private CustomerInfoService customerInfoService;
	
   @Autowired
	 private   BusiLogService  busiLogService;

	/**
	 * 客户管理列表
	 * 
	 * @param request
	 * @return
	 */
	@PostMapping("/list")
	@RequiresPermissions("customerInfo:list")
	public Response list(@RequestBody Request<CustomerInfoForm, CustomerInfoQuery> request) {			
		Pagination pagination = request.getPagination();
		CustomerInfoQuery query = request.getQuery();
		TableBody body = TableBody.factory();
		body.setPagination(pagination);	
		 CustomerInfoForm form =request.getForm();	
		 setCurrUser(form);	 
		if (StringUtils.hasText(form.getUserCustomerId())) {
			// 判断是否是管理员账号true:管理员 false:其他客户账号
			if (!isAdministrator(form.getUserCustomerId())) {
				if (StringUtils.hasText(query.getId())) {
					if (form.getUserCustomerId().equals(query.getId())) {
						query.setId(form.getUserCustomerId());
					} else {
						query.setId(Const.NOTFOUND);
					}
				} else {
					// 获取customerId 数据隔离
					query.setId(form.getUserCustomerId());
				}
			}
		} else {
			query.setId(Const.NOTFOUND);
		}
		// 获取总条数
		long rowcount = customerInfoService.countByQuery(query);
		pagination.setTotal(rowcount);
		// 获取分页数据
		List<CustomerVo> list = customerInfoService.pageQuery(pagination, query);				
		body.setList(list);
		return Response.ok().body(body);
	}

	/**
	 * 录入客户信息
	 * @param request
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/add")
	@RequiresPermissions("customerInfo:add")
	public Response add(	@RequestBody @Validated Request<CustomerInfoForm, CustomerInfoQuery> request,HttpServletRequest httpServletRequest) throws Exception {
		CustomerInfoForm form =request.getForm();	
		setCurrUser(form);
		 //判断是否是管理员账号true:管理员 false:其他客户账号
		if(!isAdministrator(form.getUserCustomerId())){			
			return Response.factory().code(-1).message("超出权限");
		}
		//设置客户头像项目路径
        customerInfoService.add(form);
        //业务日记
		busiLogService.add(httpServletRequest, Const.busi_log_busi_type_1, Const.busi_log_option_type_xinzeng," 录入客户信息成功", currentUser());
		return list(request);
	}
	
	/**
	 * 修改客户信息
	 * @param request
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/edit")
	@RequiresPermissions("customerInfo:edit")
	public Response update(	@RequestBody @Validated Request<CustomerInfoForm, CustomerInfoQuery> request,HttpServletRequest httpServletRequest) throws Exception {
		CustomerInfoForm form =request.getForm();	
		setCurrUser(form);
		 //判断是否是管理员账号true:管理员 false:其他客户账号
		if(!isAdministrator(form.getUserCustomerId())){			
			return Response.factory().code(-1).message("超出权限");
		}
		int code = customerInfoService.edit(form);
		if (code < 1) {
			return Response.factory().code(-1).message("客户不存在");
	 	}else if(code==500){
	 		return Response.factory().code(-1).message("不可调整服务费，存在未开票金额!");
	 	}
		
       //业务日记
	   busiLogService.add(httpServletRequest, Const.busi_log_busi_type_1, Const.busi_log_option_type_xiugai,"修改客户信息成功", currentUser());
		return list(request);
	}
}
