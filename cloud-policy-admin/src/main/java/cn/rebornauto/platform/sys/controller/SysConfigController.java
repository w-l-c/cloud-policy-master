package cn.rebornauto.platform.sys.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.rebornauto.platform.business.service.SysConfigService;
import cn.rebornauto.platform.business.service.SysDicService;
import cn.rebornauto.platform.common.Const;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.sys.entity.SysConfig;

/** Title: 
 * Description: 
 * Company: 
 * @author kgc
 * @date May 12, 2019 10:29:50 AM
 */
@RestController
@RequestMapping("/config")
public class SysConfigController {
	
	@Autowired
	SysConfigService sysConfigService;
	
	@Autowired
	SysDicService sysDicService;

	/**
	 * 清配置表缓存
	 * @param request
	 * @param key
	 * @return
	 */
	@RequestMapping(value = "/cache/clear", method = RequestMethod.GET, params = {"key"})
    @ResponseBody
    public Response clearCache(HttpServletRequest request, @RequestParam(required = true) String key) {
        String value = sysConfigService.clearCache(key);
        return Response.ok().body(value);
    }
	
	/**
	 * 获取支付id
	 * @param request
	 * @param key
	 * @return
	 */
	@RequestMapping(value = "/getPayChannel", method = RequestMethod.GET)
    @ResponseBody
    public Response getPaymentChannelId(HttpServletRequest request) {
		//支付环境开关 测试或者生产
        String sysPaySwitch = sysDicService.selectSysPaySwitch();
		
        //支付
        String key = "paymentChannel";
		String value = sysConfigService.findValueByKey(key,sysPaySwitch);
		if(Const.SYS_PAYMENT_CHANNEL_TONGLIAN == Integer.parseInt(value)) {
			return Response.ok().body("目前 通联支付");
		}else if(Const.SYS_PAYMENT_CHANNEL_SAND == Integer.parseInt(value)) {
			return Response.ok().body("目前 杉徳支付");
		}else {
			return Response.ok();
		}
    }
	
	
	/**
	 * 获取支付id
	 * @param request
	 * @param key
	 * @return
	 */
	@RequestMapping(value = "/changePayChannel", method = RequestMethod.GET)
    @ResponseBody
    public Response changePaymentChannelId(HttpServletRequest request) {
		//支付环境开关 测试或者生产
        String sysPaySwitch = sysDicService.selectSysPaySwitch();
        String key = request.getParameter("key");
        //支付
        String payType = "paymentChannel";
        
        SysConfig record = new SysConfig();
        record.setValue(key);
        Response resp = sysConfigService.changePaymentChannelId(record, sysPaySwitch, payType);
        return resp;
    }
	
	
}
