package cn.rebornauto.platform.business.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.rebornauto.platform.business.entity.PostAddress;
import cn.rebornauto.platform.business.form.PostAddressForm;
import cn.rebornauto.platform.business.query.PostAddressQuery;
import cn.rebornauto.platform.business.service.BusiLogService;
import cn.rebornauto.platform.business.service.PostAddressService;
import cn.rebornauto.platform.common.Const;
import cn.rebornauto.platform.common.ReturnMsgConst;
import cn.rebornauto.platform.common.controller.BaseController;
import cn.rebornauto.platform.common.data.request.Request;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.common.data.view.ResponseCode;
import cn.rebornauto.platform.sys.entity.SysUser;
import cn.rebornauto.platform.sys.service.SysEnumsService;


/**
 * <p>Title: 邮寄地址管理</p>
 * <p>Description: </p>
 * <p>Company: </p>
 * @author kgc
 * @date Mar 17, 2020 3:19:57 PM
 */
@RestController
@RequestMapping("/postAddress")
public class PostAddressController  extends BaseController {

	@Autowired
    BusiLogService busiLogService;
	
	@Autowired
	SysEnumsService sysEnumsService;
	
	@Autowired
	PostAddressService postAddressService;
	
	/**
     * 查看明细
     * @return
     */
    @GetMapping("/edit")
    @ResponseBody
    public Response edit() {
    	SysUser user = currentUser();
    	String customerId = user.getCustomerId();
    	PostAddress detail = postAddressService.detail(customerId);
    	if(null==detail) {
    		detail = new PostAddress();
    	}
        return Response.ok().body(detail);
    }
	
    /**
     * 保存地址
     * @param request
     * @return
     * @throws Exception 
     */
    @PostMapping("/save")
    @RequiresPermissions("postAddress:save")
    public Response save(@RequestBody Request<PostAddressForm, PostAddressQuery> request,HttpServletRequest req) throws Exception {
    	PostAddressForm form = request.getForm();
    	SysUser user = currentUser();
      //同一个客户的单子才能查看，admin除外
    	if(!isAdministrator(user.getCustomerId())) {
    		Response resp = postAddressService.save(form,user);
            if(resp.getCode()!=ResponseCode.SUCCESS.value()) {
            	busiLogService.add(req, Const.busi_log_busi_type_11, Const.busi_log_option_type_fail,"邮寄地址保存失败", user);
            	return resp;
            }else {
            	//操作日志
    	        busiLogService.add(req, Const.busi_log_busi_type_11, Const.busi_log_option_type_success,"邮寄地址保存成功", user);
            }
    	}else {
    		return Response.error().message(ReturnMsgConst.RETURN_600039.getMsg());
    	}
        return edit();
    }
}
