package cn.rebornauto.platform.sms.controller;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tk.mybatis.mapper.entity.Example;
import cn.rebornauto.platform.common.controller.BaseController;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.common.data.view.TableBody;
import cn.rebornauto.platform.pay.tonglian.utils.DateUtil;
import cn.rebornauto.platform.sms.entity.SmsQueue;
import cn.rebornauto.platform.sms.service.SmsQueueService;


/**
 * <p>Title: 短信查询</p>
 * <p>Description: </p>
 * <p>Company: </p>
 * @author kgc
 * @date May 29, 2019 10:49:49 AM
 */
@RestController
@RequestMapping("/sms")
public class SmsController extends BaseController {
	@Autowired
	private SmsQueueService smsQueueService;
	
    
   
	/**
	 * 查询手机号发送短信信息
	 * @param request
	 * @param mobile
	 * @return
	 */
	@GetMapping("/list/{mobile}")
	public Response list(@PathVariable("mobile")String mobile) {	
		Example smsExample = new Example(SmsQueue.class);
    	smsExample.createCriteria().andEqualTo("mobile", mobile)
    	.andGreaterThanOrEqualTo("gmtCreate", DateUtil.format(new Date(), "yyyy-MM-dd"));
    	smsExample.setOrderByClause("id desc");
		List<SmsQueue> list = smsQueueService.selectByExample(smsExample);	
		TableBody body = TableBody.factory();
		body.setList(list);
		return Response.ok().body(body);
	}
	
}
