package cn.rebornauto.platform.payment.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

import cn.rebornauto.platform.business.dao.CustomerQuotaMapper;
import cn.rebornauto.platform.business.dao.CustomerTotalQuotaMapper;
import cn.rebornauto.platform.business.dao.DaiZhengInfoMapper;
import cn.rebornauto.platform.business.dao.OrderMapper;
import cn.rebornauto.platform.business.dao.SandPayResultDemoMapper;
import cn.rebornauto.platform.business.dao.TransactionFlowMapper;
import cn.rebornauto.platform.business.entity.AgentQuota;
import cn.rebornauto.platform.business.entity.CollectionRepaymentSignVO;
import cn.rebornauto.platform.business.entity.SandPayResultDemo;
import cn.rebornauto.platform.business.entity.TransactionFlow;
import cn.rebornauto.platform.business.service.AgentQuotaService;
import cn.rebornauto.platform.business.service.BusiLogService;
import cn.rebornauto.platform.business.service.CollectionRepaymentSignService;
import cn.rebornauto.platform.business.service.DaiZhengInfoService;
import cn.rebornauto.platform.business.service.PaymentChannelConfigService;
import cn.rebornauto.platform.business.service.SysDicService;
import cn.rebornauto.platform.business.service.TransactionFlowService;
import cn.rebornauto.platform.common.Const;
import cn.rebornauto.platform.common.util.DateUtils;
import cn.rebornauto.platform.common.util.LogUtil;
import cn.rebornauto.platform.common.util.StringUtil;
import cn.rebornauto.platform.pay.sandpay.dsf.demo.SandBase;
import cn.rebornauto.platform.pay.sandpay.entity.SandInfo;
import cn.rebornauto.platform.pay.sandpay.enums.SandInterfaceEnums;
import cn.rebornauto.platform.pay.sandpay.enums.SandResultFlagEnums;
import cn.rebornauto.platform.pay.sandpay.request.QueryOrderRequest;
import cn.rebornauto.platform.pay.tonglian.Enum.PayStatusEnum;
import cn.rebornauto.platform.pay.tonglian.utils.DateUtil;
import cn.rebornauto.platform.payment.service.SandPayService;
import cn.rebornauto.platform.payment.service.impl.tonglianpay.BaseHandler;
import cn.rebornauto.platform.sys.entity.SysUser;

/** Title: 
 * Description: 
 * Company: 
 * @author kgc
 * @date Apr 1, 2020 12:01:03 PM
 */
@Service
public class SandPayServiceImpl implements SandPayService{
	
	private static final Logger logger = LoggerFactory.getLogger(SandPayServiceImpl.class);


	@Autowired
    CollectionRepaymentSignService collectionRepaymentSignService;
    
    @Autowired
    TransactionFlowService transactionFlowService;
    
    @Autowired
    TransactionFlowMapper transactionFlowMapper;
    
    @Autowired
    SysDicService sysDicService;
    
    @Autowired
    PaymentChannelConfigService paymentChannelConfigService;
    
    @Autowired
    DaiZhengInfoService daiZhengInfoService;
    
    @Autowired
    BaseHandler baseHandler;
    
    @Autowired
    OrderMapper orderMapper;
    
    @Autowired
    BusiLogService busiLogService;
    
    @Autowired
    AgentQuotaService agentQuotaService;
    
    @Autowired
    CustomerQuotaMapper customerQuotaMapper;
    
    @Autowired
    CustomerTotalQuotaMapper customerTotalQuotaMapper;
    
    @Autowired
    DaiZhengInfoMapper daiZhengInfoMapper;
    
    @Autowired
    SandPayResultDemoMapper sandPayResultDemoMapper;
    /**
     * 查询订单及更新
     */
	public void queryOrderAndUpdate(HttpServletRequest req) {
		//支付环境开关 测试或者生产
        String sysPaySwitch = sysDicService.selectSysPaySwitch();
        
        //杉徳支付
        SandInfo sandInfo = paymentChannelConfigService.getSandInfo(Const.SYS_PAYMENT_CHANNEL_SAND, sysPaySwitch);
        
    	//查询半小时内杉徳支付处理中数据
    	List<CollectionRepaymentSignVO> inProcessedList = collectionRepaymentSignService.selectInProcessedFromSandPay();
    	if(null == inProcessedList || inProcessedList.size()==0) {
    		logger.info("未找到半小时内杉徳支付处理中数据!");
    	}else {
    		//循环处理杉徳支付  半小时内  处理中订单
    		for (int i = 0; i < inProcessedList.size(); i++) {
    			// 要查询的订单号 
                String orderCode = inProcessedList.get(i).getRequestSn();
                
                // 查询订单的交易时间 
                String tranTime = DateUtils.localDateTimeToStringYYMMDDHHMMSS(inProcessedList.get(i).getPostTime());
                LogUtil.debug("开始查询交易结果,requestSn:" + orderCode+",tranTime:"+tranTime);
                
                synchronized (orderCode) {
                	Map<String, Object> result = new HashMap<>();
                    TransactionFlow transactionFlow = transactionFlowService.selectOne(inProcessedList.get(i).getTransactionFlowId());
                    
                    //设置定时任务人员
                    SysUser currentUser = new SysUser();
                    currentUser.setCustomerId(transactionFlow.getCustomerId());
                    currentUser.setNickname("杉徳支付定时任务");
                    try {
                        QueryOrderRequest request = new QueryOrderRequest(tranTime, orderCode);
                        
                        JSONObject jsonRequest = (JSONObject) JSONObject.toJSON(request);
                		
                		String reqAddr = SandInterfaceEnums.QUERY_ORDER.getCode();//接口报文规范中获取
                		
                		String reqData = jsonRequest.toJSONString();
                		
                		JSONObject resp = new JSONObject();
                		if (Const.SYS_PAY_SWITCH_TEST.equalsIgnoreCase(sysPaySwitch)) {
                			LogUtil.info("挡板请求数据：\n"+StringUtil.formatJson(reqData));	
                			SandPayResultDemo query = new SandPayResultDemo();
                			query.setType("queryOrderResult");
                			SandPayResultDemo record = sandPayResultDemoMapper.selectOne(query);
                			String json = record.getJson();
                	        resp = JSONObject.parseObject(json);
                			LogUtil.info("挡板返回数据：\n"+StringUtil.formatJson(resp.toJSONString()));	
                		} else if (Const.SYS_PAY_SWITCH_ONLINE.equalsIgnoreCase(sysPaySwitch)) {
                			LogUtil.info("杉徳生产请求数据：\n"+StringUtil.formatJson(reqData));
                			resp = SandBase.requestServer(jsonRequest.toJSONString(), reqAddr, SandBase.ORDER_QUERY, sandInfo);
                			LogUtil.info("杉徳生产返回数据：\n"+StringUtil.formatJson(resp.toJSONString()));
                		}
                		
                		System.out.println("resp:"+resp);	
                		if(resp!=null) {
                			String respCode = resp.getString("respCode");
            				String respDesc = resp.getString("respDesc");
            				String resultFlag = resp.getString("resultFlag");
            				String origRespCode = resp.getString("origRespCode");
            				String origRespDesc = resp.getString("origRespDesc");
            				
            				LogUtil.info("请求响应码：[" + respCode + "]");
            				LogUtil.info("请求响应描述：[" + respDesc + "]");
            				LogUtil.info("处理状态：[" + resultFlag + "]");
            				LogUtil.info("业务响应码：[" + origRespCode + "]");
            				LogUtil.info("业务响应描述：[" + origRespDesc + "]");

            				if (resultFlag.equalsIgnoreCase(SandResultFlagEnums.RESULT_FLAG_0.getCode())) {
            					result.put("status", PayStatusEnum.SUCCESS.getCode());
            					result.put("msg", origRespDesc);
            				} else if (resultFlag.equalsIgnoreCase(SandResultFlagEnums.RESULT_FLAG_1.getCode())) {
            					result.put("status", PayStatusEnum.ERROR.getCode());
            					result.put("msg", origRespDesc);
            				} else {
            					result.put("status", PayStatusEnum.IN_PROCESSED.getCode());
            					result.put("msg", origRespDesc);
            				}

            				Date responseTime = new Date();
            				result.put("completeTime", responseTime);
            				result.put("liquidTime", responseTime);
            				String status = result.get("status").toString();
            				
            				CollectionRepaymentSignVO vo = inProcessedList.get(i);
            				//更新杉徳支付数据
            				sandUpdateOrder(req, status, vo, request, transactionFlow, origRespCode, origRespDesc, responseTime, currentUser);
                		}else {
                			LogUtil.error("服务器请求异常！！！");	
                		}
                    } catch (Exception e) {
                        LogUtil.error("----------请求订单查询接口失败----------"+e.getMessage());
                    }
                }
			}
    	}
	}
	
	/**
	 * 更新支付信息
	 * @param req
	 * @param status
	 * @param vo
	 * @param request
	 * @param transactionFlow
	 * @param respCode
	 * @param respDesc
	 * @param responseTime
	 * @param currentUser
	 */
	@Transactional
	public void sandUpdateOrder(HttpServletRequest req,String status,CollectionRepaymentSignVO vo,QueryOrderRequest request
			,TransactionFlow transactionFlow,String respCode,String respDesc,Date responseTime,SysUser currentUser) {
		if (PayStatusEnum.SUCCESS.getCode().equals(status)) {
			//更新支付流水表
			baseHandler.updateCollectionRepaymentSignStatus(vo.getId(), request.getOrderCode(),
					respCode, respCode, respDesc, responseTime, PayStatusEnum.SUCCESS.getCode(), new Date());
			//更改订单表成功数
      		orderMapper.updateSuccCountByOrderId(transactionFlow.getOrderId());
      		
      		//更改订单表统计数
      		orderMapper.updatePayStatByOrderId(transactionFlow.getOrderId());
      		
      		//更新订单状态
      		orderMapper.updateOrderStatusByOrderId(transactionFlow.getOrderId());
      		
      		//更新业务流水表
      		TransactionFlow tranRecord = new TransactionFlow();
      		tranRecord.setId(transactionFlow.getId());
            tranRecord.setPayStatus(Integer.parseInt(status));
            tranRecord.setResult(respDesc);
            tranRecord.setCompleteTime(DateUtils.dateToLocalDateTime(responseTime));
            tranRecord.setModifytime(DateUtils.dateToLocalDateTime(responseTime));
            transactionFlowMapper.updateByPrimaryKeySelective(tranRecord);
            
      		//操作日志
            busiLogService.add(req, Const.busi_log_busi_type_9, Const.busi_log_option_type_success,"更新交易结果成功[orderId:"+transactionFlow.getOrderId()+",代理人姓名:"+transactionFlow.getAgentName()+",卡号:"+transactionFlow.getOpenBankNo()+",amount:"+transactionFlow.getAmount()+"]", currentUser);
    		
            
		} else if (PayStatusEnum.ERROR.getCode().equals(status)) {
			//更新支付流水表
			baseHandler.updateCollectionRepaymentSignStatus(vo.getId(), request.getOrderCode(),
					respCode, respCode, respDesc, responseTime, PayStatusEnum.ERROR.getCode(), new Date());
			//1.代理人月额度释放
    		AgentQuota record = new AgentQuota();
        	record.setAgentOpenBankNo(transactionFlow.getOpenBankNo());
        	record.setLoanAmount(transactionFlow.getAmount());
        	record.setYearmonth(DateUtil.format(new Date(), "yyyyMM"));
        	agentQuotaService.cancelAgentQuotaLoanAmount(record);//放款失败释放额度
        	
        	//2.客户余额释放
      		customerQuotaMapper.cancelCustomerQuotaByCustomerId(transactionFlow.getCustomerId(), transactionFlow.getAmount());
      		
      		//3.客户总余额释放
      		customerTotalQuotaMapper.cancelCustomerTotalQuotaByCustomerId(transactionFlow.getAmount());
      		
      		//4.代征主体额度释放
      		daiZhengInfoMapper.cancelDaiZhengQuotaById(transactionFlow.getDaiZhengId(),transactionFlow.getAmount());
    		//更改订单表失败数
      		orderMapper.updateFailCountByOrderId(transactionFlow.getOrderId());
      		
      		//更新业务流水表
            TransactionFlow tranRecord = new TransactionFlow();
            tranRecord.setId(transactionFlow.getId());
            tranRecord.setPayStatus(Integer.parseInt(status));
            tranRecord.setResult(respDesc);
            tranRecord.setCompleteTime(DateUtils.dateToLocalDateTime(responseTime));
            tranRecord.setModifytime(DateUtils.dateToLocalDateTime(responseTime));
            transactionFlowMapper.updateByPrimaryKeySelective(tranRecord);
            
      		//操作日志
            busiLogService.add(req, Const.busi_log_busi_type_9, Const.busi_log_option_type_fail," 更新交易结果失败", currentUser);
		}
	}
}
