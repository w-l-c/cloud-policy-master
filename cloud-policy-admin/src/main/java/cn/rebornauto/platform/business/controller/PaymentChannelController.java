package cn.rebornauto.platform.business.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.rebornauto.platform.business.dao.MakeInvoiceMapper;
import cn.rebornauto.platform.business.dao.SandPayResultDemoMapper;
import cn.rebornauto.platform.business.entity.SandPayResultDemo;
import cn.rebornauto.platform.business.form.CustomerInfoForm;
import cn.rebornauto.platform.business.form.OrderForm;
import cn.rebornauto.platform.business.form.PaymentChannelForm;
import cn.rebornauto.platform.business.form.TestForm;
import cn.rebornauto.platform.business.query.CustomerInfoQuery;
import cn.rebornauto.platform.business.query.PayQuery;
import cn.rebornauto.platform.business.query.PaymentChannelQuery;
import cn.rebornauto.platform.business.service.BusiLogService;
import cn.rebornauto.platform.business.service.DaiZhengInfoService;
import cn.rebornauto.platform.business.service.PaymentChannelConfigService;
import cn.rebornauto.platform.business.service.PaymentChannelService;
import cn.rebornauto.platform.business.service.SysConfigService;
import cn.rebornauto.platform.business.service.SysDicService;
import cn.rebornauto.platform.common.Const;
import cn.rebornauto.platform.common.controller.BaseController;
import cn.rebornauto.platform.common.data.request.Pagination;
import cn.rebornauto.platform.common.data.request.Request;
import cn.rebornauto.platform.common.data.view.PaymentChannelConfigVO;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.common.data.view.ResponseCode;
import cn.rebornauto.platform.common.data.view.TableBody;
import cn.rebornauto.platform.common.util.BigDecimalUtil;
import cn.rebornauto.platform.common.util.StringUtil;
import cn.rebornauto.platform.pay.sandpay.dsf.demo.SandBase;
import cn.rebornauto.platform.pay.sandpay.entity.SandInfo;
import cn.rebornauto.platform.pay.sandpay.enums.SandInterfaceEnums;
import cn.rebornauto.platform.pay.sandpay.request.AgentPayRequest;
import cn.rebornauto.platform.pay.sandpay.request.QueryOrderRequest;
import cn.rebornauto.platform.pay.sandpay.response.QueryOrderResponse;
import cn.rebornauto.platform.payment.service.impl.tonglianpay.BaseHandler;
import cn.rebornauto.platform.sys.entity.SysUser;


/** Title: 支付渠道管理
 * Description: 
 * Company: 
 * @author kgc
 * @date Apr 27, 2019 11:49:49 PM
 */
@RestController
@RequestMapping("/paymentChannel")
public class PaymentChannelController  extends BaseController {
	
	public static  Logger logger = LoggerFactory.getLogger(PaymentChannelController.class);

	@Autowired
	PaymentChannelService paymentChannelService;
	
	@Autowired
	SysConfigService sysConfigService;
	
	@Autowired
	PaymentChannelConfigService paymentChannelConfigService;
	
	@Autowired
	MakeInvoiceMapper makeInvoiceMapper;
	
	@Autowired
	SysDicService sysDicService;
	
	@Autowired
	BaseHandler baseHandler;
	
	@Autowired
	DaiZhengInfoService daiZhengInfoService;
	
	@Autowired
	SandPayResultDemoMapper sandPayResultDemoMapper;
	
	@Autowired
	BusiLogService busiLogService;
	
	
	/**
	 * 订单查询接口
	 * @param param
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/sandQueryOrder",method = RequestMethod.GET)
    public QueryOrderResponse queryOrder(HttpServletRequest req,HttpServletResponse response)throws Exception{
		logger.info(SandInterfaceEnums.QUERY_ORDER.getMsg());
		
		QueryOrderResponse queryOrderResponse = new QueryOrderResponse();
		
		//支付环境开关 测试或者生产
        String sysPaySwitch = sysDicService.selectSysPaySwitch();
		
        SandInfo sandInfo = paymentChannelConfigService.getSandInfo(Const.SYS_PAYMENT_CHANNEL_SAND, sysPaySwitch);
		
//        sandInfo.setSandsdkSandCertPath("/Users/si/Desktop/sand_sc/sand.cer");
//        sandInfo.setSandsdkSignCertPath("/Users/si/Desktop/sand_sc/10130872.pfx");
//        sandInfo.setSandsdkUrl("https://caspay.sandpay.com.cn/agent-main/openapi/");
        
        // 查询订单的交易时间
        String tranTime = SandBase.getCurrentTime();
        
        // 要查询的订单号 
//        String orderCode = "202004021703052";
        String orderCode = req.getParameter("orderCode");
        QueryOrderRequest request = new QueryOrderRequest(tranTime, orderCode);
        
        JSONObject jsonRequest = (JSONObject) JSONObject.toJSON(request);
		
		String reqAddr = SandInterfaceEnums.QUERY_ORDER.getCode();//接口报文规范中获取
		
		JSONObject resp = SandBase.requestServer(jsonRequest.toJSONString(), reqAddr, SandBase.ORDER_QUERY, sandInfo);
		
		System.out.println("resp:"+resp);	
		if(resp!=null) {
			System.out.println("响应码：["+resp.getString("respCode")+"]");	
			logger.info("响应码：["+resp.getString("respCode")+"]");
			System.out.println("响应描述：["+resp.getString("respDesc")+"]");
			logger.info("响应描述：["+resp.getString("respDesc")+"]");
			System.out.println("处理状态：["+resp.getString("resultFlag")+"]");
			logger.info("处理状态：["+resp.getString("resultFlag")+"]");
			
			queryOrderResponse = JSON.toJavaObject(resp,QueryOrderResponse.class);
		}else {
			System.out.println("服务器请求异常！！！");
			logger.error("服务器请求异常！！！");	
		}
		
		return queryOrderResponse;
	}
	
	/**
	 * 订单支付接口
	 * @param param
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/sandAgentPay",method = RequestMethod.POST)
    public void agentPay(@RequestBody TestForm testForm,HttpServletResponse response)throws Exception{
		logger.info(SandInterfaceEnums.AGENT_PAY.getMsg());
		String accName = testForm.getAccName();
		String accNo = testForm.getAccNo();
		BigDecimal tranAmt = testForm.getTranAmt();
		if(StringUtils.isBlank(accName)
			||StringUtils.isBlank(accNo)
			||null==tranAmt
			||tranAmt.compareTo(BigDecimal.ZERO)==0) {
			StringUtil.writeStr2Res("银行账号、姓名、金额等不能为空!", response);
		}else {
			
			//支付环境开关 测试或者生产
	        String sysPaySwitch = sysDicService.selectSysPaySwitch();
			
	        SandInfo sandInfo = paymentChannelConfigService.getSandInfo(Const.SYS_PAYMENT_CHANNEL_SAND, sysPaySwitch);
			
			String reqAddr = SandInterfaceEnums.AGENT_PAY.getCode();//接口报文规范中获取
			
			tranAmt = tranAmt.multiply(new BigDecimal("100"));
			
			
			AgentPayRequest request = new AgentPayRequest();
			request.setTranAmt(BigDecimalUtil.formatTo12Str(tranAmt));//金额
			request.setAccNo(accNo);//收款人账户号
			request.setAccName(accName);//收款人账户名
//			request.setBankType("123456123456");//收款人账户联行号
			request.setRemark("代付");//摘要
	        
			JSONObject resp = new JSONObject();
	        JSONObject jsonRequest = (JSONObject) JSONObject.toJSON(request);
	        if (Const.SYS_PAY_SWITCH_TEST.equalsIgnoreCase(sysPaySwitch)) {
    			SandPayResultDemo query = new SandPayResultDemo();
    			query.setType("agentPayResult");
    			SandPayResultDemo record = sandPayResultDemoMapper.selectOne(query);
    			String json = record.getJson();
    	        resp = JSONObject.parseObject(json);
    		} else if (Const.SYS_PAY_SWITCH_ONLINE.equalsIgnoreCase(sysPaySwitch)) {
    			resp = SandBase.requestServer(jsonRequest.toJSONString(), reqAddr, SandBase.AGENT_PAY, sandInfo);
    		}
			
			
			System.out.println("resp:"+resp);	
			if(resp!=null) {
				logger.info("响应码：["+resp.getString("respCode")+"]");	
				logger.info("响应描述：["+resp.getString("respDesc")+"]");
				logger.info("处理状态：["+resp.getString("resultFlag")+"]");
			}else {
				logger.error("服务器请求异常！！！");	
			}	
			
			String returnJson = JSON.toJSONString(resp);  
			logger.info("返回:\r\n"+StringUtil.formatJson(returnJson));
	    	StringUtil.writeStr2Res(StringUtil.formatJson(returnJson), response);
		}
	}
	
	
	/**
	 * 支付公司帐务明细及余额查询
	 * @param qDate
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryBalanceAndDetail",method = RequestMethod.POST)
	public Response queryBalanceAndDetail(@RequestBody Request<OrderForm, PayQuery> request) throws Exception {
		PayQuery query = request.getQuery();
		if(StringUtils.isNotBlank(query.getDate())) {
			if(query.getPaymentChannelId()==Const.SYS_PAYMENT_CHANNEL_TONGLIAN) {
				return paymentChannelService.tongLianBalanceAndDetail(query.getDate());
			}else if(query.getPaymentChannelId()==Const.SYS_PAYMENT_CHANNEL_SAND) {
				return paymentChannelService.sandBalanceAndDetail(query.getDate());
			}else {
				return Response.error().message("找不到支付通道!");
			}
		}else {
			return Response.error().message("日期不能为空!");
		}
	}
	
	
	/**
	 * 列表查询
	 * @param request
	 * @return
	 */
	@PostMapping("/list")
    @RequiresPermissions("paymentChannel:list")
    public Response list(@RequestBody Request<PaymentChannelForm, PaymentChannelQuery> request) {
        Pagination pagination = request.getPagination();
        PaymentChannelQuery query = request.getQuery();
        
        TableBody body = TableBody.factory();
        
        int rowcount = paymentChannelService.count(query);
        pagination.setTotal(rowcount);
        body.setPagination(pagination);
        List<PaymentChannelConfigVO> list = paymentChannelService.list(query,pagination, currentUser());
        body.setList(list);
        return Response.ok().body(body);
    }
	
	
	/**
	 * 新增和修改  渠道配置
	 * @param request
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/edit")
	@RequiresPermissions("paymentChannel:edit")
	public Response edit(	@RequestBody @Validated Request<PaymentChannelForm, PaymentChannelQuery> request,HttpServletRequest httpServletRequest) throws Exception {
		PaymentChannelForm form =request.getForm();	
		setCurrUser(form);
		Response result = paymentChannelService.edit(form);
		if (result.getCode()!=ResponseCode.SUCCESS.value()) {
			return Response.error().message(result.getMessage());
	 	}
		return list(request);
	}
}
