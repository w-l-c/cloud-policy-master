package cn.rebornauto.platform.payment.service.impl.tonglianpay;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rebornauto.platform.business.dao.AgentBankNoInfoMapper;
import cn.rebornauto.platform.business.dao.AgentInfoMapper;
import cn.rebornauto.platform.business.dao.AgentQuotaMapper;
import cn.rebornauto.platform.business.dao.CustomerQuotaMapper;
import cn.rebornauto.platform.business.dao.CustomerTotalQuotaMapper;
import cn.rebornauto.platform.business.dao.DaiZhengInfoMapper;
import cn.rebornauto.platform.business.dao.OrderDetailMapper;
import cn.rebornauto.platform.business.dao.OrderMapper;
import cn.rebornauto.platform.business.dao.TransactionFlowMapper;
import cn.rebornauto.platform.business.entity.AgentBankNoInfo;
import cn.rebornauto.platform.business.entity.AgentQuota;
import cn.rebornauto.platform.business.entity.DaiZhengInfo;
import cn.rebornauto.platform.business.entity.OrderDetailMerge;
import cn.rebornauto.platform.business.entity.TransactionFlow;
import cn.rebornauto.platform.business.form.OrderForm;
import cn.rebornauto.platform.business.service.AgentBankNoInfoService;
import cn.rebornauto.platform.business.service.AgentQuotaService;
import cn.rebornauto.platform.business.service.BusiLogService;
import cn.rebornauto.platform.business.service.PaymentChannelConfigService;
import cn.rebornauto.platform.business.service.SysConfigService;
import cn.rebornauto.platform.common.Const;
import cn.rebornauto.platform.common.util.DateUtils;
import cn.rebornauto.platform.common.util.RandomUtil;
import cn.rebornauto.platform.pay.sandpay.entity.SandInfo;
import cn.rebornauto.platform.pay.tonglian.Enum.FtTypeEnum;
import cn.rebornauto.platform.pay.tonglian.Enum.PayStatusEnum;
import cn.rebornauto.platform.pay.tonglian.entity.TongLianInfo;
import cn.rebornauto.platform.pay.tonglian.model.SingleTranReq;
import cn.rebornauto.platform.pay.tonglian.utils.DateUtil;
import cn.rebornauto.platform.sys.entity.SysUser;


/**
 * @author ligewei
 * @date 2017???11???27???
 */
@Service
public class PaymentHandler extends BaseHandler{

    @Autowired
    TransactionFlowMapper transactionFlowMapper;
    
    @Autowired
    CustomerQuotaMapper customerQuotaMapper;
    
    @Autowired
    CustomerTotalQuotaMapper customerTotalQuotaMapper;
    
    @Autowired
    AgentInfoMapper agentInfoMapper;
    
    @Autowired
    OrderDetailMapper orderDetailMapper;
    
    @Autowired
    PaymentChannelConfigService paymentChannelConfigService;
    
    @Autowired
    DaiZhengInfoMapper daiZhengInfoMapper;
    
    @Autowired
    OrderMapper orderMapper;
    
    @Autowired
    AgentBankNoInfoService agentBankNoInfoService;
    
    @Autowired
    BusiLogService busiLogService;
    
    @Autowired
    AgentQuotaService agentQuotaService;
    
    @Autowired
    SysConfigService sysConfigService;
    
    @Autowired
    AgentQuotaMapper agentQuotaMapper;
    
    @Autowired
    AgentBankNoInfoMapper agentBankNoInfoMapper;

    /**
     * ??????????????????????????????
     * @param orderId  ??????id
     * @param ftTypeId ????????????id
     * @return
     */
    public String payForLoan(Integer orderId,Integer ftTypeId,Integer borrowId,String formatDay,String userName,int paymentChannelId,OrderDetailMerge mergeVo,String customerId) {
        SingleTranReq singleTranReq = null;
        TransactionFlow model = null;
        //????????????6????????????
        String transactionFlowId = DateUtil.getAllTimes()+RandomUtil.randomStr(6);
        
        //???????????????id
        AgentBankNoInfo queryAgentInfo = new AgentBankNoInfo();
        queryAgentInfo.setAgentOpenBankNo(mergeVo.getOpenBankNo());
        queryAgentInfo.setDataStatus(Const.DATA_STATUS_1);
        AgentBankNoInfo agentBankNoInfo = agentBankNoInfoMapper.selectOne(queryAgentInfo);
        

        singleTranReq = wrapSingleTranReq(transactionFlowId, agentBankNoInfo.getAgentOpenBankCode(), mergeVo.getAgentName(), mergeVo.getOpenBankNo(), "", mergeVo.getAmount(), "0","","",mergeVo.getIdcardno());
        model = wrapTransactionFlowModel(singleTranReq, ftTypeId,userName,mergeVo,"",paymentChannelId);
        
        model.setCustomerId(customerId);
        model.setAgentId(agentBankNoInfo.getAgentId());
        //????????????????????????
        transactionFlowMapper.insertSelective(model);
        return model.getTransactionFlowId();
    }

    /**
     * ??????
     * @param transactionFlowId
     * @param ftTypeId
     * @param userName
     * @param userOpenName
     * @param formatDay
     * @param payType
     * @param sysPaySwitch
     * @return
     * @throws Exception
     */
    public String payForSecond(OrderForm form,String transactionFlowId,Integer ftTypeId,String userName,String userOpenName,String formatDay,Integer payType,String sysPaySwitch,HttpServletRequest req,SysUser currentUser)throws Exception {
        TransactionFlow queryTransactionFlow = new TransactionFlow();
        queryTransactionFlow.setTransactionFlowId(transactionFlowId);
        TransactionFlow model = transactionFlowMapper.selectOne(queryTransactionFlow);
        
        //????????????
    	String openBankNo = form.getOpenBankNo();
        SingleTranReq singleTranReq = new SingleTranReq();
        //???????????????transflow???????????????????????????????????????
        if(FtTypeEnum.RIGHT_LOAN.getCode().intValue()==ftTypeId.intValue()) {
        	//????????????
        	String bankCode = "";
        	if(StringUtils.isNotBlank(openBankNo)) {//??????????????????????????????
        		AgentBankNoInfo info = agentBankNoInfoService.selectAgentBankNoInfoByOpenBankNo(openBankNo);
        		openBankNo = info.getAgentOpenBankNo();
        		bankCode = info.getAgentOpenBankCode();
        		model.setOpenBankNo(openBankNo);
        		model.setBankCode(bankCode);
        	}else {
        		openBankNo = model.getOpenBankNo();
        		bankCode = model.getBankCode();
        	}
        	
            singleTranReq = wrapSingleTranReq(transactionFlowId, bankCode, model.getAgentName(), openBankNo, "", model.getAmount(), "0","","",model.getIdcardno());
        }
        DaiZhengInfo daiZhengInfo = daiZhengInfoMapper.selectByPrimaryKey(model.getDaiZhengId());
        
        //????????????
        Map<String, Object> result = new HashMap<String, Object>();
        if(null!=daiZhengInfo && StringUtils.isNotBlank(daiZhengInfo.getDaiZhengName())) {
        	//??????????????????
        	if(Const.SYS_PAYMENT_CHANNEL_TONGLIAN==model.getPaymentChannelId()) {
        		//????????????
        		result = tongLianPay(daiZhengInfo, model, sysPaySwitch, userName, ftTypeId, singleTranReq);
        	}else if(Const.SYS_PAYMENT_CHANNEL_SAND==model.getPaymentChannelId()){
        		//????????????
        		result = sandPay(daiZhengInfo, model, sysPaySwitch, singleTranReq,userName);
        	}
        }
        
        String status = result.get("status").toString();
        Date completeTime = (Date) result.get("completeTime");
        model.setPayStatus(Integer.parseInt(status));
        model.setResult((null==result.get("msg"))?"":result.get("msg").toString());
        model.setCompleteTime(DateUtils.dateToLocalDateTime(completeTime));
        //????????????????????????
        transactionFlowMapper.updateByPrimaryKeySelective(model);
        
        //????????????????????????????????????????????????
        if(FtTypeEnum.RIGHT_LOAN.getCode().equals(ftTypeId)) {
	        if(null!=status && status.equalsIgnoreCase(PayStatusEnum.SUCCESS.getCode())) {
	      		//????????????????????????
	      		orderMapper.updateSuccCountByOrderId(model.getOrderId());
	      		//????????????????????????
	      		orderMapper.updatePayStatByOrderId(model.getOrderId());
	      		//??????????????????
	      		orderMapper.updateOrderStatusByOrderId(model.getOrderId());
	      		//????????????
		        busiLogService.add(req, Const.busi_log_busi_type_5, Const.busi_log_option_type_success,"????????????[?????????:"+model.getOrderId()+",??????:"+openBankNo+",????????????:"+model.getAmount()+"]", currentUser);
				
	        }else if(null!=status && status.equalsIgnoreCase(PayStatusEnum.ERROR.getCode())){
	        	//1.????????????????????????
        		AgentQuota record = new AgentQuota();
        		record.setAgentIdcardno(model.getIdcardno());
	        	record.setAgentOpenBankNo(openBankNo);
	        	record.setLoanAmount(model.getAmount());
	        	record.setModifyoper(currentUser.getNickname());
	        	record.setYearmonth(DateUtil.format(new Date(), "yyyyMM"));
	        	agentQuotaMapper.cancelAgentQuotaLoanAmount(record);//????????????????????????
	        	
	        	//2.??????????????????
	      		customerQuotaMapper.cancelCustomerQuotaByCustomerId(model.getCustomerId(), model.getAmount());
	      		
	      		//3.?????????????????????
	      		customerTotalQuotaMapper.cancelCustomerTotalQuotaByCustomerId(model.getAmount());
	      		
	      		//4.????????????????????????
	      		daiZhengInfoMapper.cancelDaiZhengQuotaById(model.getDaiZhengId(),model.getAmount());
	        	//????????????????????????
	      		orderMapper.updateFailCountByOrderId(model.getOrderId());
	      		//????????????
		        busiLogService.add(req, Const.busi_log_busi_type_5, Const.busi_log_option_type_fail,"????????????[?????????:"+model.getOrderId()+",??????:"+openBankNo+","+model.getResult()+"]", currentUser);
				
	        }
        }
        return status;
    }


	/**
	 * ?????????????????????
	 */
	public void addTempQuota(TransactionFlow transactionFlow,SysUser currentUser) {
		//1.???????????????????????????
		AgentQuota record = new AgentQuota();
		record.setAgentIdcardno(getAgentIdcard(transactionFlow.getOpenBankNo()));
    	record.setLoanAmount(transactionFlow.getAmount());
    	record.setModifyoper(currentUser.getNickname());
    	record.setYearmonth(DateUtil.format(new Date(), "yyyyMM"));
    	agentQuotaService.addAgentQuotaLoanAmount(record);//????????????????????????????????????????????????????????????
		
    	//2.??????????????????
  		customerQuotaMapper.addCustomerQuotaByCustomerId(transactionFlow.getCustomerId(), transactionFlow.getAmount());
  		
  		//3.?????????????????????
  		customerTotalQuotaMapper.addCustomerTotalQuotaByCustomerId(transactionFlow.getAmount());
  		
  		//4.????????????????????????
  		daiZhengInfoMapper.addDaiZhengQuotaById(transactionFlow.getDaiZhengId(),transactionFlow.getAmount());
	}
    public String getAgentIdcard (String openBankNo) {
		return agentInfoMapper.selectAgentIdcardnoByOpenBankNo(openBankNo);
	}
    
    
    /**
     * ????????????
     * @param daiZhengInfo
     * @param model
     * @param sysPaySwitch
     * @param userName
     * @param ftTypeId
     * @param singleTranReq
     * @return
     */
    public Map<String, Object> tongLianPay(DaiZhengInfo daiZhengInfo,TransactionFlow model,String sysPaySwitch,String userName, Integer ftTypeId,SingleTranReq singleTranReq) {
    	Map<String, Object> result = new HashMap<String, Object>();
		TongLianInfo tongLianInfo = paymentChannelConfigService.getTongLianInfo(model.getPaymentChannelId(), sysPaySwitch);
		if(null!=tongLianInfo) {
			result = singlePaymentTongLian(singleTranReq, userName,ftTypeId,model.getAgentName(),tongLianInfo,daiZhengInfo.getDaiZhengName());
		}
		return result;
    }
    
    /**
     * ????????????
     * @param daiZhengInfo
     * @param model
     * @param sysPaySwitch
     * @param singleTranReq
     * @param userName
     * @return
     */
    public Map<String, Object> sandPay(DaiZhengInfo daiZhengInfo,TransactionFlow model,String sysPaySwitch,SingleTranReq singleTranReq,String userName) {
    	Map<String, Object> result = new HashMap<String, Object>();
		SandInfo sandInfo = paymentChannelConfigService.getSandInfo(Const.SYS_PAYMENT_CHANNEL_SAND, sysPaySwitch);
		if(null!=sandInfo) {
			result = singlePaymentSand(singleTranReq, userName,sandInfo,sysPaySwitch);
		}
		return result;
    }
    
}
