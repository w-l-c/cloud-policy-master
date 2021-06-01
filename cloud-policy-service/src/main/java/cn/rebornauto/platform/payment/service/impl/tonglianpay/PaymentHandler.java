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
 * @date 2017年11月27日
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
     * 代付生成业务支付流水
     * @param orderId  订单id
     * @param ftTypeId 业务类型id
     * @return
     */
    public String payForLoan(Integer orderId,Integer ftTypeId,Integer borrowId,String formatDay,String userName,int paymentChannelId,OrderDetailMerge mergeVo,String customerId) {
        SingleTranReq singleTranReq = null;
        TransactionFlow model = null;
        //时间戳＋6位随机数
        String transactionFlowId = DateUtil.getAllTimes()+RandomUtil.randomStr(6);
        
        //放入代理人id
        AgentBankNoInfo queryAgentInfo = new AgentBankNoInfo();
        queryAgentInfo.setAgentOpenBankNo(mergeVo.getOpenBankNo());
        queryAgentInfo.setDataStatus(Const.DATA_STATUS_1);
        AgentBankNoInfo agentBankNoInfo = agentBankNoInfoMapper.selectOne(queryAgentInfo);
        

        singleTranReq = wrapSingleTranReq(transactionFlowId, agentBankNoInfo.getAgentOpenBankCode(), mergeVo.getAgentName(), mergeVo.getOpenBankNo(), "", mergeVo.getAmount(), "0","","",mergeVo.getIdcardno());
        model = wrapTransactionFlowModel(singleTranReq, ftTypeId,userName,mergeVo,"",paymentChannelId);
        
        model.setCustomerId(customerId);
        model.setAgentId(agentBankNoInfo.getAgentId());
        //插入交易流水记录
        transactionFlowMapper.insertSelective(model);
        return model.getTransactionFlowId();
    }

    /**
     * 代付
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
        
        //银行卡号
    	String openBankNo = form.getOpenBankNo();
        SingleTranReq singleTranReq = new SingleTranReq();
        //保理融资用transflow表数据，读取配置信息不会变
        if(FtTypeEnum.RIGHT_LOAN.getCode().intValue()==ftTypeId.intValue()) {
        	//银行代码
        	String bankCode = "";
        	if(StringUtils.isNotBlank(openBankNo)) {//再次支付重新选择卡号
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
        
        //单笔支付
        Map<String, Object> result = new HashMap<String, Object>();
        if(null!=daiZhengInfo && StringUtils.isNotBlank(daiZhengInfo.getDaiZhengName())) {
        	//支付通道选择
        	if(Const.SYS_PAYMENT_CHANNEL_TONGLIAN==model.getPaymentChannelId()) {
        		//通联支付
        		result = tongLianPay(daiZhengInfo, model, sysPaySwitch, userName, ftTypeId, singleTranReq);
        	}else if(Const.SYS_PAYMENT_CHANNEL_SAND==model.getPaymentChannelId()){
        		//杉徳支付
        		result = sandPay(daiZhengInfo, model, sysPaySwitch, singleTranReq,userName);
        	}
        }
        
        String status = result.get("status").toString();
        Date completeTime = (Date) result.get("completeTime");
        model.setPayStatus(Integer.parseInt(status));
        model.setResult((null==result.get("msg"))?"":result.get("msg").toString());
        model.setCompleteTime(DateUtils.dateToLocalDateTime(completeTime));
        //修改交易流水状态
        transactionFlowMapper.updateByPrimaryKeySelective(model);
        
        //主债权放款成功，才更新代理商额度
        if(FtTypeEnum.RIGHT_LOAN.getCode().equals(ftTypeId)) {
	        if(null!=status && status.equalsIgnoreCase(PayStatusEnum.SUCCESS.getCode())) {
	      		//更改订单表成功数
	      		orderMapper.updateSuccCountByOrderId(model.getOrderId());
	      		//更改订单表统计数
	      		orderMapper.updatePayStatByOrderId(model.getOrderId());
	      		//更新订单状态
	      		orderMapper.updateOrderStatusByOrderId(model.getOrderId());
	      		//操作日志
		        busiLogService.add(req, Const.busi_log_busi_type_5, Const.busi_log_option_type_success,"放款成功[订单号:"+model.getOrderId()+",卡号:"+openBankNo+",放款金额:"+model.getAmount()+"]", currentUser);
				
	        }else if(null!=status && status.equalsIgnoreCase(PayStatusEnum.ERROR.getCode())){
	        	//1.代理人月额度释放
        		AgentQuota record = new AgentQuota();
        		record.setAgentIdcardno(model.getIdcardno());
	        	record.setAgentOpenBankNo(openBankNo);
	        	record.setLoanAmount(model.getAmount());
	        	record.setModifyoper(currentUser.getNickname());
	        	record.setYearmonth(DateUtil.format(new Date(), "yyyyMM"));
	        	agentQuotaMapper.cancelAgentQuotaLoanAmount(record);//放款失败释放额度
	        	
	        	//2.客户余额释放
	      		customerQuotaMapper.cancelCustomerQuotaByCustomerId(model.getCustomerId(), model.getAmount());
	      		
	      		//3.客户总余额释放
	      		customerTotalQuotaMapper.cancelCustomerTotalQuotaByCustomerId(model.getAmount());
	      		
	      		//4.代征主体额度释放
	      		daiZhengInfoMapper.cancelDaiZhengQuotaById(model.getDaiZhengId(),model.getAmount());
	        	//更改订单表失败数
	      		orderMapper.updateFailCountByOrderId(model.getOrderId());
	      		//操作日志
		        busiLogService.add(req, Const.busi_log_busi_type_5, Const.busi_log_option_type_fail,"放款失败[订单号:"+model.getOrderId()+",卡号:"+openBankNo+","+model.getResult()+"]", currentUser);
				
	        }
        }
        return status;
    }


	/**
	 * 临时先增加额度
	 */
	public void addTempQuota(TransactionFlow transactionFlow,SysUser currentUser) {
		//1.增加代理人当月额度
		AgentQuota record = new AgentQuota();
		record.setAgentIdcardno(getAgentIdcard(transactionFlow.getOpenBankNo()));
    	record.setLoanAmount(transactionFlow.getAmount());
    	record.setModifyoper(currentUser.getNickname());
    	record.setYearmonth(DateUtil.format(new Date(), "yyyyMM"));
    	agentQuotaService.addAgentQuotaLoanAmount(record);//暂时先增加到放款额度，如果放款失败了释放
		
    	//2.客户余额变更
  		customerQuotaMapper.addCustomerQuotaByCustomerId(transactionFlow.getCustomerId(), transactionFlow.getAmount());
  		
  		//3.客户总余额变更
  		customerTotalQuotaMapper.addCustomerTotalQuotaByCustomerId(transactionFlow.getAmount());
  		
  		//4.代征主体额度变更
  		daiZhengInfoMapper.addDaiZhengQuotaById(transactionFlow.getDaiZhengId(),transactionFlow.getAmount());
	}
    public String getAgentIdcard (String openBankNo) {
		return agentInfoMapper.selectAgentIdcardnoByOpenBankNo(openBankNo);
	}
    
    
    /**
     * 通联支付
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
     * 杉徳支付
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
