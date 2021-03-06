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
     * ?????????????????????
     */
	public void queryOrderAndUpdate(HttpServletRequest req) {
		//?????????????????? ??????????????????
        String sysPaySwitch = sysDicService.selectSysPaySwitch();
        
        //????????????
        SandInfo sandInfo = paymentChannelConfigService.getSandInfo(Const.SYS_PAYMENT_CHANNEL_SAND, sysPaySwitch);
        
    	//?????????????????????????????????????????????
    	List<CollectionRepaymentSignVO> inProcessedList = collectionRepaymentSignService.selectInProcessedFromSandPay();
    	if(null == inProcessedList || inProcessedList.size()==0) {
    		logger.info("????????????????????????????????????????????????!");
    	}else {
    		//????????????????????????  ????????????  ???????????????
    		for (int i = 0; i < inProcessedList.size(); i++) {
    			// ????????????????????? 
                String orderCode = inProcessedList.get(i).getRequestSn();
                
                // ??????????????????????????? 
                String tranTime = DateUtils.localDateTimeToStringYYMMDDHHMMSS(inProcessedList.get(i).getPostTime());
                LogUtil.debug("????????????????????????,requestSn:" + orderCode+",tranTime:"+tranTime);
                
                synchronized (orderCode) {
                	Map<String, Object> result = new HashMap<>();
                    TransactionFlow transactionFlow = transactionFlowService.selectOne(inProcessedList.get(i).getTransactionFlowId());
                    
                    //????????????????????????
                    SysUser currentUser = new SysUser();
                    currentUser.setCustomerId(transactionFlow.getCustomerId());
                    currentUser.setNickname("????????????????????????");
                    try {
                        QueryOrderRequest request = new QueryOrderRequest(tranTime, orderCode);
                        
                        JSONObject jsonRequest = (JSONObject) JSONObject.toJSON(request);
                		
                		String reqAddr = SandInterfaceEnums.QUERY_ORDER.getCode();//???????????????????????????
                		
                		String reqData = jsonRequest.toJSONString();
                		
                		JSONObject resp = new JSONObject();
                		if (Const.SYS_PAY_SWITCH_TEST.equalsIgnoreCase(sysPaySwitch)) {
                			LogUtil.info("?????????????????????\n"+StringUtil.formatJson(reqData));	
                			SandPayResultDemo query = new SandPayResultDemo();
                			query.setType("queryOrderResult");
                			SandPayResultDemo record = sandPayResultDemoMapper.selectOne(query);
                			String json = record.getJson();
                	        resp = JSONObject.parseObject(json);
                			LogUtil.info("?????????????????????\n"+StringUtil.formatJson(resp.toJSONString()));	
                		} else if (Const.SYS_PAY_SWITCH_ONLINE.equalsIgnoreCase(sysPaySwitch)) {
                			LogUtil.info("???????????????????????????\n"+StringUtil.formatJson(reqData));
                			resp = SandBase.requestServer(jsonRequest.toJSONString(), reqAddr, SandBase.ORDER_QUERY, sandInfo);
                			LogUtil.info("???????????????????????????\n"+StringUtil.formatJson(resp.toJSONString()));
                		}
                		
                		System.out.println("resp:"+resp);	
                		if(resp!=null) {
                			String respCode = resp.getString("respCode");
            				String respDesc = resp.getString("respDesc");
            				String resultFlag = resp.getString("resultFlag");
            				String origRespCode = resp.getString("origRespCode");
            				String origRespDesc = resp.getString("origRespDesc");
            				
            				LogUtil.info("??????????????????[" + respCode + "]");
            				LogUtil.info("?????????????????????[" + respDesc + "]");
            				LogUtil.info("???????????????[" + resultFlag + "]");
            				LogUtil.info("??????????????????[" + origRespCode + "]");
            				LogUtil.info("?????????????????????[" + origRespDesc + "]");

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
            				//????????????????????????
            				sandUpdateOrder(req, status, vo, request, transactionFlow, origRespCode, origRespDesc, responseTime, currentUser);
                		}else {
                			LogUtil.error("??????????????????????????????");	
                		}
                    } catch (Exception e) {
                        LogUtil.error("----------??????????????????????????????----------"+e.getMessage());
                    }
                }
			}
    	}
	}
	
	/**
	 * ??????????????????
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
			//?????????????????????
			baseHandler.updateCollectionRepaymentSignStatus(vo.getId(), request.getOrderCode(),
					respCode, respCode, respDesc, responseTime, PayStatusEnum.SUCCESS.getCode(), new Date());
			//????????????????????????
      		orderMapper.updateSuccCountByOrderId(transactionFlow.getOrderId());
      		
      		//????????????????????????
      		orderMapper.updatePayStatByOrderId(transactionFlow.getOrderId());
      		
      		//??????????????????
      		orderMapper.updateOrderStatusByOrderId(transactionFlow.getOrderId());
      		
      		//?????????????????????
      		TransactionFlow tranRecord = new TransactionFlow();
      		tranRecord.setId(transactionFlow.getId());
            tranRecord.setPayStatus(Integer.parseInt(status));
            tranRecord.setResult(respDesc);
            tranRecord.setCompleteTime(DateUtils.dateToLocalDateTime(responseTime));
            tranRecord.setModifytime(DateUtils.dateToLocalDateTime(responseTime));
            transactionFlowMapper.updateByPrimaryKeySelective(tranRecord);
            
      		//????????????
            busiLogService.add(req, Const.busi_log_busi_type_9, Const.busi_log_option_type_success,"????????????????????????[orderId:"+transactionFlow.getOrderId()+",???????????????:"+transactionFlow.getAgentName()+",??????:"+transactionFlow.getOpenBankNo()+",amount:"+transactionFlow.getAmount()+"]", currentUser);
    		
            
		} else if (PayStatusEnum.ERROR.getCode().equals(status)) {
			//?????????????????????
			baseHandler.updateCollectionRepaymentSignStatus(vo.getId(), request.getOrderCode(),
					respCode, respCode, respDesc, responseTime, PayStatusEnum.ERROR.getCode(), new Date());
			//1.????????????????????????
    		AgentQuota record = new AgentQuota();
        	record.setAgentOpenBankNo(transactionFlow.getOpenBankNo());
        	record.setLoanAmount(transactionFlow.getAmount());
        	record.setYearmonth(DateUtil.format(new Date(), "yyyyMM"));
        	agentQuotaService.cancelAgentQuotaLoanAmount(record);//????????????????????????
        	
        	//2.??????????????????
      		customerQuotaMapper.cancelCustomerQuotaByCustomerId(transactionFlow.getCustomerId(), transactionFlow.getAmount());
      		
      		//3.?????????????????????
      		customerTotalQuotaMapper.cancelCustomerTotalQuotaByCustomerId(transactionFlow.getAmount());
      		
      		//4.????????????????????????
      		daiZhengInfoMapper.cancelDaiZhengQuotaById(transactionFlow.getDaiZhengId(),transactionFlow.getAmount());
    		//????????????????????????
      		orderMapper.updateFailCountByOrderId(transactionFlow.getOrderId());
      		
      		//?????????????????????
            TransactionFlow tranRecord = new TransactionFlow();
            tranRecord.setId(transactionFlow.getId());
            tranRecord.setPayStatus(Integer.parseInt(status));
            tranRecord.setResult(respDesc);
            tranRecord.setCompleteTime(DateUtils.dateToLocalDateTime(responseTime));
            tranRecord.setModifytime(DateUtils.dateToLocalDateTime(responseTime));
            transactionFlowMapper.updateByPrimaryKeySelective(tranRecord);
            
      		//????????????
            busiLogService.add(req, Const.busi_log_busi_type_9, Const.busi_log_option_type_fail," ????????????????????????", currentUser);
		}
	}
}
