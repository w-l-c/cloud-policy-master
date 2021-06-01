package cn.rebornauto.platform.payment.service.impl.tonglianpay;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.rebornauto.platform.business.dao.CollectionRepaymentSignMapper;
import cn.rebornauto.platform.business.dao.SandPayResultDemoMapper;
import cn.rebornauto.platform.business.entity.CollectionRepaymentSign;
import cn.rebornauto.platform.business.entity.OrderDetailMerge;
import cn.rebornauto.platform.business.entity.SandPayResultDemo;
import cn.rebornauto.platform.business.entity.TransactionFlow;
import cn.rebornauto.platform.business.service.PaymentChannelConfigService;
import cn.rebornauto.platform.common.Const;
import cn.rebornauto.platform.common.ReturnMsgConst;
import cn.rebornauto.platform.common.util.BigDecimalUtil;
import cn.rebornauto.platform.common.util.DateUtils;
import cn.rebornauto.platform.common.util.LogUtil;
import cn.rebornauto.platform.common.util.StringUtil;
import cn.rebornauto.platform.pay.sandpay.dsf.demo.SandBase;
import cn.rebornauto.platform.pay.sandpay.entity.SandInfo;
import cn.rebornauto.platform.pay.sandpay.enums.SandInterfaceEnums;
import cn.rebornauto.platform.pay.sandpay.enums.SandResultFlagEnums;
import cn.rebornauto.platform.pay.sandpay.request.AgentPayRequest;
import cn.rebornauto.platform.pay.tonglian.Enum.FtTypeEnum;
import cn.rebornauto.platform.pay.tonglian.Enum.PayStatusEnum;
import cn.rebornauto.platform.pay.tonglian.Enum.TradeTypeEnum;
import cn.rebornauto.platform.pay.tonglian.aipg.acquery.AcNode;
import cn.rebornauto.platform.pay.tonglian.aipg.acquery.AcQueryReq;
import cn.rebornauto.platform.pay.tonglian.aipg.common.AipgReq;
import cn.rebornauto.platform.pay.tonglian.aipg.common.AipgRsp;
import cn.rebornauto.platform.pay.tonglian.aipg.common.InfoReq;
import cn.rebornauto.platform.pay.tonglian.aipg.common.XSUtil;
import cn.rebornauto.platform.pay.tonglian.aipg.etdtlquery.EtDtl;
import cn.rebornauto.platform.pay.tonglian.aipg.etdtlquery.EtQRsp;
import cn.rebornauto.platform.pay.tonglian.aipg.etquery.EtQueryReq;
import cn.rebornauto.platform.pay.tonglian.aipg.payreq.AIPG;
import cn.rebornauto.platform.pay.tonglian.aipg.payreq.Body;
import cn.rebornauto.platform.pay.tonglian.aipg.payreq.Trans_Detail;
import cn.rebornauto.platform.pay.tonglian.aipg.payreq.Trans_Sum;
import cn.rebornauto.platform.pay.tonglian.aipg.payresp.Process;
import cn.rebornauto.platform.pay.tonglian.aipg.payresp.Ret_Detail;
import cn.rebornauto.platform.pay.tonglian.aipg.rtreq.Fasttrx;
import cn.rebornauto.platform.pay.tonglian.aipg.rtreq.Trans;
import cn.rebornauto.platform.pay.tonglian.aipg.rtrsp.Fagraret;
import cn.rebornauto.platform.pay.tonglian.aipg.rtrsp.TransRet;
import cn.rebornauto.platform.pay.tonglian.aipg.singleacctvalid.ValidRet;
import cn.rebornauto.platform.pay.tonglian.allinpay.XmlTools;
import cn.rebornauto.platform.pay.tonglian.entity.TongLianInfo;
import cn.rebornauto.platform.pay.tonglian.model.SingleSignReq;
import cn.rebornauto.platform.pay.tonglian.model.SingleTranReq;
import cn.rebornauto.platform.pay.tonglian.utils.DateUtil;
import cn.rebornauto.platform.pay.tonglian.utils.PaymentUtils;
import cn.rebornauto.platform.pay.tonglian.utils.XmlRequestResponse;

/**
 * <p>Title: 基础数据处理</p>
 * <p>Description: </p>
 * <p>Company: </p>
 * @author kgc
 * @date Jan 7, 2018 10:19:49 PM
 */
@Service
public class BaseHandler {
	
	

	private static int CORE_POOL_SIZE = 30;//默认
	private static int MAX_POOL_SIZE = 300;//默认
	private static int KEEPALIVE_TIME = 2;//默认
	private final int QUEUE_CAPACITY = (CORE_POOL_SIZE + MAX_POOL_SIZE) / 2;
	private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
	private BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(QUEUE_CAPACITY);
	private RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy();
	
	
	private ThreadPoolExecutor payFixedThreadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEPALIVE_TIME, TIME_UNIT, workQueue, rejectedExecutionHandler);;
	
	private ThreadPoolExecutor signFixedThreadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEPALIVE_TIME, TIME_UNIT, workQueue, rejectedExecutionHandler);;
	
	@Autowired
	CollectionRepaymentSignMapper collectionRepaymentSignMapper;
	
	@Autowired
	PaymentChannelConfigService paymentChannelConfigService;
	
	@Autowired
	SandPayResultDemoMapper sandPayResultDemoMapper;
	
	
	public BaseHandler() {
		payFixedThreadPool.allowCoreThreadTimeOut(true);
		signFixedThreadPool.allowCoreThreadTimeOut(true);
	}
//    private static final ExecutorService payFixedThreadPool = Executors.newFixedThreadPool(50);
	
//    private static final ExecutorService signFixedThreadPool = Executors.newFixedThreadPool(50);
    
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseHandler.class);
    
    /**
     * 设置安全提供者
     * @throws Exception
     */
    public void initProvider() throws Exception {
        BouncyCastleProvider provider = new BouncyCastleProvider();
        XmlTools.initProvider(provider);
    }
    
//    public void checkOpenBank(Map<String,Object> map){
//        List<BankCodeInfo> bankCodeInfos = bankCodeInfoDao.selectBankCodeInfo();
//        if(map.get("openBank").toString().indexOf(" ")<0) {
//            for (BankCodeInfo bankCodeInfo : bankCodeInfos) {
//                if (bankCodeInfo.getBankName().equals(map.get("openBank").toString())) {
//                    map.put("openBankCode", bankCodeInfo.getBankCode());
//                    break;
//                }
//            }
//        }else{
//            String openbankName = map.get("openBank").toString().split(" ")[0];
//            for (BankCodeInfo bankCodeInfo : bankCodeInfos) {
//                if (bankCodeInfo.getBankName().equals(openbankName)) {
//                    map.put("openBankCode", bankCodeInfo.getBankCode());
//                    break;
//                }
//            }
//        }
//    }



    public SingleTranReq wrapSingleTranReq(String transactionFlowId, String bankCode, String openName, String openBankNo, String mobile, BigDecimal amount, String arat,String province,String city,String idcardno) {
        SingleTranReq singleTranReq = new SingleTranReq();
        singleTranReq.setTransactionFlowId(transactionFlowId);
        singleTranReq.setACCOUNT_NAME(openName);
        singleTranReq.setACCOUNT_NO(openBankNo);
        singleTranReq.setAMOUNT(amount.toString().replace(".",""));
        singleTranReq.setTEL(mobile);
        singleTranReq.setACCOUNT_PROP(arat);
        singleTranReq.setACCOUNT_TYPE("00");
        singleTranReq.setBANK_CODE(bankCode);
        singleTranReq.setSUBMIT_TIME(DateUtil.getAllTime());
        singleTranReq.setPROVINCE(province);
        singleTranReq.setCITY(city);
        singleTranReq.setID(idcardno);
        return singleTranReq;
    }
    
    public TransactionFlow wrapTransactionFlowModel(SingleTranReq model, Integer ftTypeId,String userName,OrderDetailMerge mergeVo,String username,int paymentChannelId) {
        TransactionFlow transactionFlow = new TransactionFlow();
        transactionFlow.setCreateoper(username);
        transactionFlow.setCreatetime(DateUtils.dateToLocalDateTime(DateUtil.parse(model.getSUBMIT_TIME(), "yyyyMMddHHmmss")));
        transactionFlow.setBankCode(model.getBANK_CODE());
        transactionFlow.setOpenBankNo(model.getACCOUNT_NO());
        transactionFlow.setAgentName(model.getACCOUNT_NAME());
//        transactionFlow.setAccountType(model.getACCOUNT_TYPE());
//        transactionFlow.setAccountProperty(model.getACCOUNT_PROP());
        transactionFlow.setAmount(new BigDecimal(model.getAMOUNT()).divide(new BigDecimal("100"),2,BigDecimal.ROUND_HALF_UP));
        transactionFlow.setTransactionFlowId(model.getTransactionFlowId());
        transactionFlow.setFtTypeId(ftTypeId);
        transactionFlow.setCreateoper(userName);
        transactionFlow.setPayStatus(Integer.parseInt(PayStatusEnum.IN_PROCESSED.getCode()));
        transactionFlow.setOrderId(mergeVo.getOrderId());
//        transactionFlow.setCustomerId(mergeVo.getCustomerId());
        transactionFlow.setPaymentChannelId(paymentChannelId);
        transactionFlow.setDaiZhengId(mergeVo.getDaiZhengId());
//        transactionFlow.setPolicyNo(mergeVo.getPolicyNo());
//        transactionFlow.setPolicyAmount(mergeVo.getPolicyAmount());
        transactionFlow.setAgentId(mergeVo.getAgentId());
//        transactionFlow.setOuttime(mergeVo.getOuttime());
//        transactionFlow.setSource(mergeVo.getSource());
        transactionFlow.setIdcardno(mergeVo.getIdcardno());
        transactionFlow.setMergeId(mergeVo.getMergeId());
        
        //放款
        if(paymentChannelId == Const.SYS_PAYMENT_CHANNEL_TONGLIAN) {
        	if(FtTypeEnum.RIGHT_LOAN.getCode().equals(ftTypeId)){
                transactionFlow.setTradeCode(TradeTypeEnum.SINGLE_REPAYMENT.getCode());
            }
        }else if(paymentChannelId == Const.SYS_PAYMENT_CHANNEL_SAND){
        	transactionFlow.setTradeCode(SandBase.PRODUCTID_AGENTPAY_TOC);
        }
        
        return transactionFlow;
    }

    
    public SingleSignReq wrapSingleSignReq(Map<String, Object> customer){
        SingleSignReq signReq = new SingleSignReq();
        signReq.setACCOUNT_NAME((String) customer.get("openName"));
        signReq.setID((String) customer.get("cardNo"));
        signReq.setBANK_CODE((String) customer.get("openBankCode"));
        signReq.setACCOUNT_NO((String) customer.get("openBankNo"));
        signReq.setTEL((String) customer.get("mobile"));
        return signReq;
    }
    
    
    
    
    
    protected CollectionRepaymentSign wrapCollectionRepaymentSign(Fasttrx fagra, InfoReq info,SingleTranReq model,String userName) {
        CollectionRepaymentSign collectionRepaymentSign = new CollectionRepaymentSign();
        collectionRepaymentSign.setRequestSn(info.getREQ_SN());
        collectionRepaymentSign.setTransactionFlowId(model.getTransactionFlowId());
        collectionRepaymentSign.setTradeCode(info.getTRX_CODE());
        collectionRepaymentSign.setPostTime(DateUtils.dateToLocalDateTime(new Date()));
        collectionRepaymentSign.setBankCode(model.getBANK_CODE());
        collectionRepaymentSign.setOpenBankNo(model.getACCOUNT_NO());
        collectionRepaymentSign.setAgentName(model.getACCOUNT_NAME());
//        collectionRepaymentSign.setAccountType(model.getACCOUNT_TYPE());
//        collectionRepaymentSign.setAccountProperty(model.getACCOUNT_PROP());
        collectionRepaymentSign.setAmount(new BigDecimal(model.getAMOUNT()).divide(new BigDecimal("100"),2,BigDecimal.ROUND_HALF_UP));
        collectionRepaymentSign.setCreateoper(userName);
//        collectionRepaymentSign.setProvince(model.getPROVINCE());
//        collectionRepaymentSign.setCity(model.getCITY());
        collectionRepaymentSign.setStatus(Integer.parseInt(PayStatusEnum.IN_PROCESSED.getCode()));
        collectionRepaymentSign.setIdcardno(model.getID());
        return collectionRepaymentSign;
    }
    
    /**
     * 
     * @param model
     * @param userName
     * @return
     */
    protected CollectionRepaymentSign wrapSand(AgentPayRequest request,SingleTranReq model,String userName) {
        CollectionRepaymentSign collectionRepaymentSign = new CollectionRepaymentSign();
        collectionRepaymentSign.setRequestSn(request.getOrderCode());
        collectionRepaymentSign.setTransactionFlowId(model.getTransactionFlowId());
        collectionRepaymentSign.setTradeCode(request.getProductId());
        collectionRepaymentSign.setPostTime(DateUtils.dateToLocalDateTime(DateUtil.parse(request.getTranTime(), "yyyyMMddHHmmss")));
        collectionRepaymentSign.setBankCode(model.getBANK_CODE());
        collectionRepaymentSign.setOpenBankNo(model.getACCOUNT_NO());
        collectionRepaymentSign.setAgentName(model.getACCOUNT_NAME());
        collectionRepaymentSign.setAmount(new BigDecimal(model.getAMOUNT()).divide(new BigDecimal("100"),2,BigDecimal.ROUND_HALF_UP));
        collectionRepaymentSign.setCreateoper(userName);
        collectionRepaymentSign.setStatus(Integer.parseInt(PayStatusEnum.IN_PROCESSED.getCode()));
        collectionRepaymentSign.setIdcardno(model.getID());
        return collectionRepaymentSign;
    }
    
    protected CollectionRepaymentSign wrapCollectionRepaymentSign(Trans trans, InfoReq info,SingleTranReq model,String userName) {
        CollectionRepaymentSign collectionRepaymentSign = new CollectionRepaymentSign();
        collectionRepaymentSign.setRequestSn(info.getREQ_SN());
        collectionRepaymentSign.setTransactionFlowId(model.getTransactionFlowId());
        collectionRepaymentSign.setTradeCode(info.getTRX_CODE());
        collectionRepaymentSign.setPostTime(DateUtils.dateToLocalDateTime(new Date()));
        collectionRepaymentSign.setBankCode(model.getBANK_CODE());
        collectionRepaymentSign.setOpenBankNo(model.getACCOUNT_NO());
        collectionRepaymentSign.setAgentName(model.getACCOUNT_NAME());
        collectionRepaymentSign.setAmount(new BigDecimal(model.getAMOUNT()).divide(new BigDecimal("100"),2,BigDecimal.ROUND_HALF_UP));
        collectionRepaymentSign.setCreateoper(userName);
        collectionRepaymentSign.setStatus(Integer.parseInt(PayStatusEnum.IN_PROCESSED.getCode()));
        collectionRepaymentSign.setIdcardno(model.getID());
        return collectionRepaymentSign;
    }
    
    
    protected CollectionRepaymentSign wrapCollectionRepaymentSign(SingleTranReq trans, InfoReq info,SingleTranReq model,String userName) {
        CollectionRepaymentSign collectionRepaymentSign = new CollectionRepaymentSign();
        collectionRepaymentSign.setRequestSn(info.getREQ_SN());
        collectionRepaymentSign.setTransactionFlowId(model.getTransactionFlowId());
        collectionRepaymentSign.setTradeCode(info.getTRX_CODE());
        collectionRepaymentSign.setPostTime(DateUtils.dateToLocalDateTime(new Date()));
        collectionRepaymentSign.setBankCode(model.getBANK_CODE());
        collectionRepaymentSign.setOpenBankNo(model.getACCOUNT_NO());
        collectionRepaymentSign.setAgentName(model.getACCOUNT_NAME());
        collectionRepaymentSign.setAmount(new BigDecimal(model.getAMOUNT()).divide(new BigDecimal("100"),2,BigDecimal.ROUND_HALF_UP));
        collectionRepaymentSign.setCreateoper(userName);
        collectionRepaymentSign.setStatus(Integer.parseInt(PayStatusEnum.IN_PROCESSED.getCode()));
        return collectionRepaymentSign;
    }

    public Integer updateCollectionRepaymentSignStatus(Integer id,String requestSn,String status1,String status2,String remark,Date completeTime,String status,Date liquidTime){
        CollectionRepaymentSign collectionRepaymentSign = new CollectionRepaymentSign();
        collectionRepaymentSign.setId(id);
        collectionRepaymentSign.setRequestSn(requestSn);
        collectionRepaymentSign.setCompleteTime(DateUtils.dateToLocalDateTime(completeTime));
        collectionRepaymentSign.setStatus1(status1);
        collectionRepaymentSign.setStatus2(status2);
        collectionRepaymentSign.setRemark(remark);
        collectionRepaymentSign.setStatus(Integer.parseInt(status));
//        collectionRepaymentSign.setPostTime(DateUtils.dateToLocalDateTime(liquidTime));
        return collectionRepaymentSignMapper.updateByPrimaryKeySelective(collectionRepaymentSign);
    }
    
    protected Map<String,Object> checkResponse(String trxcode,AipgRsp aipgrsp,Map<String,Object> result){
        LogUtil.info("请求接口成功,返回值：" + aipgrsp);
        //实时交易结果返回处理逻辑(包括单笔实时代收，单笔实时代付，单笔实时身份验证)
        if ("0000".equals(aipgrsp.getINFO().getRET_CODE())) {
            LogUtil.info("提交成功");
            if(TradeTypeEnum.SINGLE_REPAYMENT.getCode().equals(trxcode)) {
                TransRet ret = (TransRet) aipgrsp.getTrxData().get(0);
                LogUtil.info("交易结果：" + ret.getRET_CODE() + ":" + ret.getERR_MSG());
                if ("0000".equals(ret.getRET_CODE())) {
                    LogUtil.info("交易成功（最终结果）");
                    result.put("status", PayStatusEnum.SUCCESS.getCode());
                    result.put("liquidTime", DateUtil.parseSimpleDate(ret.getSETTLE_DAY()));
                    return result;
                } else {
                    LogUtil.info("交易失败（最终结果）");
                    LogUtil.info("交易失败原因：" + ret.getERR_MSG());
                    result.put("status", PayStatusEnum.ERROR.getCode());
                    return result;
                }
            }else if(TradeTypeEnum.SIGN.getCode().equals(trxcode)){
                ValidRet ret = (ValidRet) aipgrsp.getTrxData().get(0);
                LogUtil.info("交易结果：" + ret.getRET_CODE() + ":" + ret.getERR_MSG());
                if ("0000".equals(ret.getRET_CODE())) {
                    LogUtil.info("交易成功（最终结果）");
                    result.put("status", PayStatusEnum.SUCCESS.getCode());
                    result.put("msg", ret.getERR_MSG());
                    return result;
                } else {
                    LogUtil.info("交易失败（最终结果）");
                    LogUtil.info("交易失败原因：" + ret.getERR_MSG());
                    result.put("status", PayStatusEnum.ERROR.getCode());
                    result.put("msg", ret.getERR_MSG());
                    return result;
                }
            }else if(TradeTypeEnum.SINGLE_COLLECTION.getCode().equals(trxcode)) {
                TransRet ret = (TransRet) aipgrsp.getTrxData().get(0);
                LogUtil.info("交易结果：" + ret.getRET_CODE() + ":" + ret.getERR_MSG());
                if ("0000".equals(ret.getRET_CODE())) {
                    LogUtil.info("交易成功（最终结果）");
                    result.put("status", PayStatusEnum.SUCCESS.getCode());
                    result.put("liquidTime", DateUtil.parseSimpleDate(ret.getSETTLE_DAY()));
                    return result;
                } else {
                    LogUtil.info("交易失败（最终结果）");
                    LogUtil.info("交易失败原因：" + ret.getERR_MSG());
                    result.put("status", PayStatusEnum.ERROR.getCode());
                    return result;
                }
            }else if(TradeTypeEnum.QUICK_PAYMENT_SMS.getCode().equals(trxcode)) {
            	Fagraret ret = (Fagraret) aipgrsp.getTrxData().get(0);
                LogUtil.info("交易结果：" + ret.getRET_CODE() + ":" + ret.getERR_MSG());
                if ("0000".equals(ret.getRET_CODE())) {
                    LogUtil.info("交易成功（最终结果）");
                    result.put("status", PayStatusEnum.SUCCESS.getCode());
                    result.put("msg", ret.getERR_MSG());
                    return result;
                } else {
                    LogUtil.info("交易失败（最终结果）");
                    LogUtil.info("交易失败原因：" + ret.getERR_MSG());
                    result.put("status", PayStatusEnum.ERROR.getCode());
                    result.put("msg", ret.getERR_MSG());
                    return result;
                }
            }else if(TradeTypeEnum.QUICK_PAYMENT_SIGN.getCode().equals(trxcode)) {
            	Fagraret ret = (Fagraret) aipgrsp.getTrxData().get(0);
                LogUtil.info("交易结果：" + ret.getRET_CODE() + ":" + ret.getERR_MSG());
                if ("0000".equals(ret.getRET_CODE())) {
                    LogUtil.info("交易成功（最终结果）");
                    result.put("status", PayStatusEnum.SUCCESS.getCode());
                    result.put("agrmno", ret.getAGRMNO());
                    result.put("msg", ret.getERR_MSG());
                    return result;
                } else {
                    LogUtil.info("交易失败（最终结果）");
                    LogUtil.info("交易失败原因：" + ret.getERR_MSG());
                    result.put("status", PayStatusEnum.ERROR.getCode());
                    result.put("msg", ret.getERR_MSG());
                    return result;
                }
            }else if(TradeTypeEnum.QUICK_PAYMENT.getCode().equals(trxcode)) {
            	Fagraret ret = (Fagraret) aipgrsp.getTrxData().get(0);
                LogUtil.info("交易结果：" + ret.getRET_CODE() + ":" + ret.getERR_MSG());
                if ("0000".equals(ret.getRET_CODE())) {
                    LogUtil.info("交易成功（最终结果）");
                    result.put("status", PayStatusEnum.SUCCESS.getCode());
                    result.put("liquidTime", DateUtil.parseSimpleDate(ret.getSETTLE_DAY()));
                    result.put("msg", ret.getERR_MSG());
                    return result;
                } else {
                    LogUtil.info("交易失败（最终结果）");
                    LogUtil.info("交易失败原因：" + ret.getERR_MSG());
                    result.put("status", PayStatusEnum.ERROR.getCode());
                    result.put("msg", ret.getERR_MSG());
                    return result;
                }
            }else if(TradeTypeEnum.QUERY_MERCHANT_INFO.getCode().equals(trxcode)) {
            	AcNode ret = (AcNode) aipgrsp.getACQUERYREP().get(0);
                LogUtil.info("交易结果，余额：" + ret.getBALANCE() );
                result.put("status", PayStatusEnum.SUCCESS.getCode());
                result.put("balance", ret.getBALANCE());
                return result;
            }else if(TradeTypeEnum.QUERY_MERCHANT_RECHARGE_DETAIL.getCode().equals(trxcode)) {
            	EtQRsp ret = (EtQRsp) aipgrsp.getETQRSP();
            	List<EtDtl> list = ret.getDetails();
            	if(null!=list && list.size()>0) {
            		for (int i = 0; i < list.size(); i++) {
            			if(list.get(i).getCRDR().equalsIgnoreCase("2")) {//非充值的删除
            				list.remove(i);
            				i--;
            			}
    				}
            		result.put("sum", list.size());//充值数量
                	result.put("etDtlList", list);//充值列表
            	}
            	
                result.put("status", PayStatusEnum.SUCCESS.getCode());
                return result;
            }
        } else {
            if ("2000".equals(aipgrsp.getINFO().getRET_CODE())
                    || "2001".equals(aipgrsp.getINFO().getRET_CODE())
                    || "2003".equals(aipgrsp.getINFO().getRET_CODE())
                    || "2005".equals(aipgrsp.getINFO().getRET_CODE())
                    || "2007".equals(aipgrsp.getINFO().getRET_CODE())
                    || "2008".equals(aipgrsp.getINFO().getRET_CODE())) {
                LogUtil.info("交易处理中或者不确定状态，需要在稍后5分钟后进行交易结果查询（轮询）");
                result.put("status",PayStatusEnum.IN_PROCESSED.getCode());
                return result;
            } else if (aipgrsp.getINFO().getRET_CODE().startsWith("1")) {
                String errormsg = aipgrsp.getINFO().getERR_MSG() == null ? "连接异常，请重试" : aipgrsp.getINFO().getERR_MSG();
                LogUtil.info("交易请求失败，原因：" + errormsg);
                result.put("status",PayStatusEnum.ERROR.getCode());
                return result;
            } else {
                if(TradeTypeEnum.SINGLE_REPAYMENT.getCode().equals(trxcode)) {
                    String errorMsg = null;
                    if(aipgrsp.getTrxData()!=null) {
                        TransRet ret = (TransRet) aipgrsp.getTrxData().get(0);
                        errorMsg = ret.getERR_MSG();
                    }else {
                        errorMsg = aipgrsp.getINFO().getERR_MSG();
                    }
                    LogUtil.info("交易失败(最终结果)，失败原因：" + errorMsg);
                    result.put("status", PayStatusEnum.ERROR.getCode());
                    return result;
                }else if(TradeTypeEnum.SIGN.getCode().equals(trxcode)){
                    String errorMsg = null;
                    if(aipgrsp.getTrxData()!=null) {
                        ValidRet ret = (ValidRet) aipgrsp.getTrxData().get(0);
                        errorMsg = ret.getERR_MSG();
                    }else {
                        errorMsg = aipgrsp.getINFO().getERR_MSG();
                    }
                    LogUtil.info("交易失败(最终结果)，失败原因：" + errorMsg);
                    result.put("status", PayStatusEnum.ERROR.getCode());
                    return result;
                }else if(TradeTypeEnum.SINGLE_COLLECTION.getCode().equals(trxcode)) {
                    String errorMsg = null;
                    if(aipgrsp.getTrxData()!=null) {
                        TransRet ret = (TransRet) aipgrsp.getTrxData().get(0);
                        errorMsg = ret.getERR_MSG();
                    }else {
                        errorMsg = aipgrsp.getINFO().getERR_MSG();
                    }
                    LogUtil.info("交易失败(最终结果)，失败原因：" + errorMsg);
                    result.put("status", PayStatusEnum.ERROR.getCode());
                    return result;
                }else if(TradeTypeEnum.QUICK_PAYMENT_SMS.getCode().equals(trxcode)) {
                    String errorMsg = null;
                    if(aipgrsp.getTrxData()!=null) {
                    	Fagraret ret = (Fagraret) aipgrsp.getTrxData().get(0);
                        errorMsg = ret.getERR_MSG();
                    }else {
                        errorMsg = aipgrsp.getINFO().getERR_MSG();
                    }
                    LogUtil.info("交易失败(最终结果)，失败原因：" + errorMsg);
                    result.put("status", PayStatusEnum.ERROR.getCode());
                    result.put("msg", errorMsg);
                    return result;
                }else if(TradeTypeEnum.QUICK_PAYMENT_SIGN.getCode().equals(trxcode)) {
                    String errorMsg = null;
                    if(aipgrsp.getTrxData()!=null) {
                    	Fagraret ret = (Fagraret) aipgrsp.getTrxData().get(0);
                        errorMsg = ret.getERR_MSG();
                    }else {
                        errorMsg = aipgrsp.getINFO().getERR_MSG();
                    }
                    LogUtil.info("交易失败(最终结果)，失败原因：" + errorMsg);
                    result.put("status", PayStatusEnum.ERROR.getCode());
                    result.put("msg", errorMsg);
                    return result;
                }else if(TradeTypeEnum.QUICK_PAYMENT.getCode().equals(trxcode)) {
                    String errorMsg = null;
                    if(aipgrsp.getTrxData()!=null) {
                    	Fagraret ret = (Fagraret) aipgrsp.getTrxData().get(0);
                        errorMsg = ret.getERR_MSG();
                    }else {
                        errorMsg = aipgrsp.getINFO().getERR_MSG();
                    }
                    LogUtil.info("交易失败(最终结果)，失败原因：" + errorMsg);
                    result.put("status", PayStatusEnum.ERROR.getCode());
                    result.put("msg", errorMsg);
                    return result;
                }
            }
        }
        return result;
    }
    
    protected Map<String,Object> checkBatchResponse(String trxcode, cn.rebornauto.platform.pay.tonglian.aipg.payresp.AIPG aipgrsp, Map<String,Object> result){
        LogUtil.info("请求接口成功,返回值：" + aipgrsp);
        //实时交易结果返回处理逻辑(包括单笔实时代收，单笔实时代付，单笔实时身份验证)
        if ("0000".equals(aipgrsp.getINFO().getRET_CODE())||"4000".equals(aipgrsp.getINFO().getRET_CODE())) {
            LogUtil.info("提交成功");
            if(TradeTypeEnum.MULTI_REPAYMENT.getCode().equals(trxcode)) {
                Ret_Detail ret = (Ret_Detail) aipgrsp.getBODY().getDetails().get(0);
                LogUtil.info("交易结果：" + ret.getRET_CODE() + ":" + ret.getERR_MSG());
                if ("0000".equals(ret.getRET_CODE())||"4000".equals(ret.getRET_CODE())) {
                    LogUtil.info("提交成功（最终结果）");
                    result.put("status", PayStatusEnum.IN_PROCESSED.getCode());
                    return result;
                } else {
                    LogUtil.info("提交失败（最终结果）");
                    LogUtil.info("提交失败原因：" + ret.getERR_MSG());
                    result.put("status", PayStatusEnum.ERROR.getCode());
                    return result;
                }
            }
        } else {
            if ("2000".equals(aipgrsp.getINFO().getRET_CODE())
                    || "2001".equals(aipgrsp.getINFO().getRET_CODE())
                    || "2003".equals(aipgrsp.getINFO().getRET_CODE())
                    || "2005".equals(aipgrsp.getINFO().getRET_CODE())
                    || "2007".equals(aipgrsp.getINFO().getRET_CODE())
                    || "2008".equals(aipgrsp.getINFO().getRET_CODE())) {
                LogUtil.info("交易处理中或者不确定状态，需要在稍后5分钟后进行交易结果查询（轮询）");
                result.put("status",PayStatusEnum.IN_PROCESSED.getCode());
                return result;
            } else if ("1000".equals(aipgrsp.getINFO().getRET_CODE())
                ||"1108".equals(aipgrsp.getINFO().getRET_CODE())) {
                LogUtil.info("交易处理中或者不确定状态，需要在稍后5分钟后进行交易结果查询（轮询）");
                result.put("status",PayStatusEnum.IN_PROCESSED.getCode());
                return result;
            } else {
                if(TradeTypeEnum.MULTI_REPAYMENT.getCode().equals(trxcode)) {
                    String errorMsg = null;
                    if(CollectionUtils.isNotEmpty(aipgrsp.getBODY().getDetails())) {
                        TransRet ret = (TransRet) aipgrsp.getBODY().getDetails().get(0);
                        errorMsg = ret.getERR_MSG();
                    }else {
                        errorMsg = aipgrsp.getINFO().getERR_MSG();
                    }
                    LogUtil.info("交易失败(最终结果)，失败原因：" + errorMsg);
                    result.put("status", PayStatusEnum.ERROR.getCode());
                    return result;
                }
            }
        }
        return result;
    }
    
    
    
    /**
     * 通联单笔代付
     * @param model
     * @param userName
     * @param ftTypeId
     * @param userOpenName
     * @param tongLianInfo
     * @param daiZhengName
     * @return
     */
    public Map<String,Object> singlePaymentTongLian(SingleTranReq model,String userName,Integer ftTypeId,String userOpenName,TongLianInfo tongLianInfo,String daiZhengName) {
        Map<String,Object> result = new HashMap<>();
        try {
            initProvider();
            String xml = "";
            AipgReq aipg = new AipgReq();
            InfoReq info = PaymentUtils.makeReq(TradeTypeEnum.SINGLE_REPAYMENT.getCode(),tongLianInfo);
            aipg.setINFO(info);
            Trans trans = new Trans();
            BeanUtils.copyProperties(model, trans);
            trans.setMERCHANT_ID(tongLianInfo.getMerchantId());
            trans.setCURRENCY("CNY");
            trans.setBUSINESS_CODE(tongLianInfo.getBusinessCodeDf());
            //如果是主债券放款
            if(FtTypeEnum.RIGHT_LOAN.getCode().intValue()==ftTypeId.intValue()) {
                String remark = tongLianInfo.getRightLoanPrefix()+daiZhengName+"-"+userOpenName;
                trans.setSUMMARY(remark);
                trans.setREMARK(remark);
            }
//            trans.setAMOUNT("9000000");
//            trans.setTEL("13961414413");
//            trans.setPROVINCE("湖北");
//            trans.setCITY("武汉");
//            trans.setBANK_NAME("广东发展银行1111");
//            trans.setUNION_BANK("105651008112");
            
            aipg.addTrx(trans);
            CollectionRepaymentSign collectionRepaymentSign = wrapCollectionRepaymentSign(trans, info, model,userName);
            if (collectionRepaymentSignMapper.insert(collectionRepaymentSign) < 1) {
                throw new Exception("插入支付流水表异常");
            }
            LOGGER.info("----------去请求代付接口----------");
            LOGGER.info("service层请求代付接口参数：" + aipg);
            xml = XmlTools.buildXml(aipg, true);
            String responseStr = null;
            Date errorTime = new Date();
            try {
                responseStr = PaymentUtils.sendToTlt(xml, false, tongLianInfo.getUrl(),tongLianInfo);
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.info("-----------请求代付接口失败----------");
                LOGGER.info(e.getMessage());
                QueryTradeThread queryTradeThread = new QueryTradeThread(TradeTypeEnum.SINGLE_REPAYMENT.getCode(),aipg.getINFO().getREQ_SN(),tongLianInfo);
                payFixedThreadPool.execute(queryTradeThread);
                updateCollectionRepaymentSignStatus(collectionRepaymentSign.getId(),info.getREQ_SN(), "9999", "9999", "请求代付接口失败",errorTime,PayStatusEnum.IN_PROCESSED.getCode(),new Date());
                result.put("status",PayStatusEnum.IN_PROCESSED.getCode());
                result.put("msg","请求代付接口失败");
                result.put("liquidTime",errorTime);
                result.put("completeTime",errorTime);
                return result;
            }
            AipgRsp aipgrsp = null;
            String trxcode = null;
            //或者交易码
            if (responseStr.indexOf("<TRX_CODE>") != -1)
            {
                int end = responseStr.indexOf("</TRX_CODE>");
                int begin = end - 6;
                if (begin >= 0) trxcode = responseStr.substring(begin, end);
            }
            aipgrsp=XSUtil.parseRsp(responseStr);
            result =  checkResponse(trxcode,aipgrsp,result);

            Date responseTime = new Date();
            result.put("completeTime",responseTime);
            result.put("liquidTime",responseTime);
            result.put("msg",aipgrsp.getINFO().getERR_MSG());
            String status = result.get("status").toString();
            if(PayStatusEnum.SUCCESS.getCode().equals(status)){
                TransRet ret = (TransRet) aipgrsp.getTrxData().get(0);
                updateCollectionRepaymentSignStatus(collectionRepaymentSign.getId(),aipg.getINFO().getREQ_SN(), aipgrsp.getINFO().getRET_CODE(), ret.getRET_CODE(), ret.getERR_MSG(),responseTime,PayStatusEnum.SUCCESS.getCode(),DateUtil.parseSimpleDate(ret.getSETTLE_DAY()));
                result.put("liquidTime",DateUtil.parseSimpleDate(ret.getSETTLE_DAY()));
            }else if(PayStatusEnum.IN_PROCESSED.getCode().equals(status)){
                updateCollectionRepaymentSignStatus(collectionRepaymentSign.getId(),aipgrsp.getINFO().getREQ_SN(), aipgrsp.getINFO().getRET_CODE(), null, aipgrsp.getINFO().getERR_MSG() == null ? "连接异常，请重试" : aipgrsp.getINFO().getERR_MSG(),responseTime,PayStatusEnum.IN_PROCESSED.getCode(),new Date());
                QueryTradeThread queryTradeThread = new QueryTradeThread(TradeTypeEnum.SINGLE_REPAYMENT.getCode(),aipgrsp.getINFO().getREQ_SN(),tongLianInfo);
                payFixedThreadPool.execute(queryTradeThread);
            }else if(PayStatusEnum.ERROR.getCode().equals(status)){
                TransRet ret = null;
                String errorMsg = null;
                String retCode = null;
                if(aipgrsp.getTrxData()!=null) {
                    ret = (TransRet) aipgrsp.getTrxData().get(0);
                    errorMsg = ret.getERR_MSG();
                    retCode = ret.getRET_CODE();
                }else {
                    errorMsg = aipgrsp.getINFO().getERR_MSG();
                    retCode = aipgrsp.getINFO().getRET_CODE();
                }
                updateCollectionRepaymentSignStatus(collectionRepaymentSign.getId(),aipgrsp.getINFO().getREQ_SN(), aipgrsp.getINFO().getRET_CODE(), retCode, errorMsg,responseTime,PayStatusEnum.ERROR.getCode(),new Date());

            }
            return result;
        }catch (Exception e){
            result.put("status",PayStatusEnum.ERROR.getCode());
            result.put("completeTime",new Date());
            e.printStackTrace();
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
            LogUtil.debug("处理通联接口异常:"+sw.toString());
            return result;
        }
   }
    
    /**
     * 批量代付
     */
    public Map<String,Object> batchRepayment(SingleTranReq model,String userName,Integer ftTypeId,String userOpenName,TongLianInfo tongLianInfo) {
        Map<String,Object> result = new HashMap<>();
        try {
            initProvider();
            String xml = "";
            AIPG aipg = new AIPG();
            InfoReq info = PaymentUtils.makeReq(TradeTypeEnum.MULTI_REPAYMENT.getCode(), tongLianInfo);
            aipg.setINFO(info);
            Trans_Detail transDetail = new Trans_Detail();
            BeanUtils.copyProperties(model, transDetail);
            transDetail.setSN("1");
            transDetail.setCURRENCY("CNY");
            //如果是主债券放款
            if(FtTypeEnum.RIGHT_LOAN.getCode().intValue()==ftTypeId.intValue()) {
                String remark = tongLianInfo.getRightLoanPrefix()+userOpenName;
                transDetail.setSUMMARY(remark);
                transDetail.setREMARK(remark);
            }
            Body body = new Body();
            Trans_Sum transSum = new Trans_Sum();
            transSum.setMERCHANT_ID(tongLianInfo.getMerchantId());
            transSum.setBUSINESS_CODE(tongLianInfo.getBusinessCodeDf());
            transSum.setSUBMIT_TIME(model.getSUBMIT_TIME());
            transSum.setTOTAL_ITEM("1");
            transSum.setTOTAL_SUM(transDetail.getAMOUNT());
            body.setTRANS_SUM(transSum);
            body.addDetail(transDetail);
            aipg.setBODY(body);
            CollectionRepaymentSign collectionRepaymentSign = wrapCollectionRepaymentSign(model, info, model,userName);
            if (collectionRepaymentSignMapper.insert(collectionRepaymentSign) < 1) {
                throw new Exception("插入支付流水表异常");
            }
            LOGGER.info("----------去请求批量代付接口----------");
            LOGGER.info("service层请求批量代付接口参数：" + aipg);
            xml = XmlTools.buildXmlForBatch(aipg, true);
            String responseStr = null;
            Date errorTime = new Date();
            try {
                responseStr = PaymentUtils.sendToTlt(xml, false, tongLianInfo.getUrl(),tongLianInfo);
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.info("-----------请求批量代付接口失败----------");
                LOGGER.info(e.getMessage());
                BatchQueryTradeThread queryTradeThread = new BatchQueryTradeThread(TradeTypeEnum.MULTI_REPAYMENT.getCode(),aipg.getINFO().getREQ_SN(),tongLianInfo);
                payFixedThreadPool.execute(queryTradeThread);
                updateCollectionRepaymentSignStatus(collectionRepaymentSign.getId(),info.getREQ_SN(), "9999", "9999", "请求批量代付接口失败",errorTime,PayStatusEnum.IN_PROCESSED.getCode(),new Date());
                result.put("status",PayStatusEnum.IN_PROCESSED.getCode());
                result.put("msg","请求批量代付接口失败");
                result.put("liquidTime",errorTime);
                result.put("completeTime",errorTime);
                return result;
            }

            cn.rebornauto.platform.pay.tonglian.aipg.payresp.AIPG aipgrsp = new cn.rebornauto.platform.pay.tonglian.aipg.payresp.AIPG();
            String trxcode = null;
            //或者交易码
            if (responseStr.indexOf("<TRX_CODE>") != -1)
            {
                int end = responseStr.indexOf("</TRX_CODE>");
                int begin = end - 6;
                if (begin >= 0) trxcode = responseStr.substring(begin, end);
            }
            Process process =new Process();
            aipgrsp = process.parseXML(responseStr);
            result =  checkBatchResponse(trxcode,aipgrsp,result);
            Date responseTime = new Date();
            result.put("completeTime",responseTime);
            result.put("liquidTime",responseTime);
            result.put("msg",aipgrsp.getINFO().getERR_MSG());
            String status = result.get("status").toString();
            if(PayStatusEnum.IN_PROCESSED.getCode().equals(status)){
                updateCollectionRepaymentSignStatus(collectionRepaymentSign.getId(),aipgrsp.getINFO().getREQ_SN(), aipgrsp.getINFO().getRET_CODE(), null, aipgrsp.getINFO().getERR_MSG() == null ? "连接异常，请重试" : aipgrsp.getINFO().getERR_MSG(),responseTime,PayStatusEnum.IN_PROCESSED.getCode(),new Date());
                BatchQueryTradeThread queryTradeThread = new BatchQueryTradeThread(TradeTypeEnum.MULTI_REPAYMENT.getCode(),aipgrsp.getINFO().getREQ_SN(),tongLianInfo);
                payFixedThreadPool.execute(queryTradeThread);
            }else if(PayStatusEnum.ERROR.getCode().equals(status)){
                TransRet ret = null;
                String errorMsg = null;
                String retCode = null;
                if(CollectionUtils.isNotEmpty(aipgrsp.getBODY().getDetails())) {
                    ret = (TransRet) aipgrsp.getBODY().getDetails().get(0);
                    errorMsg = ret.getERR_MSG();
                    retCode = ret.getRET_CODE();
                }else {
                    errorMsg = aipgrsp.getINFO().getERR_MSG();
                    retCode = aipgrsp.getINFO().getRET_CODE();
                }
                updateCollectionRepaymentSignStatus(collectionRepaymentSign.getId(),aipgrsp.getINFO().getREQ_SN(), aipgrsp.getINFO().getRET_CODE(), retCode, errorMsg,responseTime,PayStatusEnum.ERROR.getCode(),new Date());
            }
            return result;
        }catch (Exception e){
            result.put("status",PayStatusEnum.ERROR.getCode());
            result.put("completeTime",new Date());
            e.printStackTrace();
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
            LogUtil.debug("处理通联接口异常:"+sw.toString());
            return result;
        }
   }
    
   
   
   
   
   
   
   public static String sendXml(String xml,String url,boolean isFront,XmlRequestResponse xrr,TongLianInfo tongLianInfo) throws UnsupportedEncodingException, Exception{
   		LogUtil.debug("======================发送报文======================：\n"+xml);
   		String resp=XmlTools.send(url,xml);
   		LogUtil.debug("======================响应内容======================：\n"+resp);
   		boolean flag= verifyMsg(resp, tongLianInfo.getTltcerPath(),isFront);
   		if(flag){
   			LogUtil.debug("响应内容验证通过");
   		}else{
   			LogUtil.debug("响应内容验证不通过");
   		}
   		xrr.setXmlRequest(xml);
   		xrr.setXmlResponse(resp);
   		return resp;
   }
   
   public static String sendToTlt(String xml,boolean flag,String url,XmlRequestResponse xrr,TongLianInfo tongLianInfo)throws Exception {
       if(!flag){
           xml=signMsg(xml,tongLianInfo);
       }else{
           xml=xml.replaceAll("<SIGNED_MSG></SIGNED_MSG>", "");
       }
       return sendXml(xml,url,flag,xrr,tongLianInfo);

   }
   /**
    * 报文签名
    * @param xml
    * @return
    *日期：Sep 9, 2012
    * @throws Exception
    */
   public static String signMsg(String xml,TongLianInfo tongLianInfo) throws Exception{
       xml= XmlTools.signMsg(xml, tongLianInfo.getPfxPath(),tongLianInfo.getPfxPassword(), false);
       return xml;
   }
   
   /**
    * 验证签名
    * @param msg
    * @return
    *日期：Sep 9, 2012
    * @throws Exception
    */
   public static boolean verifyMsg(String msg,String cer,boolean isFront) throws Exception{
       boolean flag=XmlTools.verifySign(msg, cer, false,isFront);
       System.out.println("验签结果["+flag+"]") ;
       return flag;
   }
   
   
   /**
    * 杉徳支付
    * @param model
    * @param userName
    * @param daiZhengInfo
    * @param sysPaySwitch
    * @return
    */
	public Map<String, Object> singlePaymentSand(SingleTranReq model, String userName, SandInfo sandInfo,String sysPaySwitch) {
		Map<String, Object> result = new HashMap<>();
		try {
			String reqAddr = SandInterfaceEnums.AGENT_PAY.getCode();// 接口报文规范中获取

			AgentPayRequest request = new AgentPayRequest();
			request.setTranAmt(BigDecimalUtil.formatTo12Str(new BigDecimal(model.getAMOUNT())));// 金额
			request.setAccNo(model.getACCOUNT_NO());// 收款人账户号
			request.setAccName(model.getACCOUNT_NAME());// 收款人账户名
//      		request.setBankType("123456123456");//收款人账户联行号
			request.setRemark("代付");// 摘要

			JSONObject jsonRequest = (JSONObject) JSONObject.toJSON(request);
			
			String reqData = jsonRequest.toJSONString();
				

			CollectionRepaymentSign collectionRepaymentSign = wrapSand(request, model, userName);
			if (collectionRepaymentSignMapper.insert(collectionRepaymentSign) < 1) {
				throw new Exception("插入支付流水表异常");
			}

			JSONObject resp = new JSONObject();
			if (Const.SYS_PAY_SWITCH_TEST.equalsIgnoreCase(sysPaySwitch)) {
				LogUtil.info("挡板请求数据：\n"+StringUtil.formatJson(reqData));	
				SandPayResultDemo query = new SandPayResultDemo();
    			query.setType("agentPayResult");
    			SandPayResultDemo record = sandPayResultDemoMapper.selectOne(query);
    			String json = record.getJson();
    	        resp = JSONObject.parseObject(json);
    			LogUtil.info("挡板返回数据：\n"+StringUtil.formatJson(resp.toJSONString()));	
			} else if (Const.SYS_PAY_SWITCH_ONLINE.equalsIgnoreCase(sysPaySwitch)) {
				LogUtil.info("杉徳生产请求数据：\n"+StringUtil.formatJson(reqData));	
				resp = SandBase.requestServer(reqData, reqAddr, SandBase.AGENT_PAY, sandInfo);
				LogUtil.info("杉徳生产返回数据：\n"+StringUtil.formatJson(resp.toJSONString()));	
			}

			if (resp != null) {
				String respCode = resp.getString("respCode");
				String respDesc = resp.getString("respDesc");
				String resultFlag = resp.getString("resultFlag");
				LogUtil.info("响应码：[" + respCode + "]");
				LogUtil.info("响应描述：[" + respDesc + "]");
				LogUtil.info("处理状态：[" + resultFlag + "]");

				if (resultFlag.equalsIgnoreCase(SandResultFlagEnums.RESULT_FLAG_0.getCode())) {
					result.put("status", PayStatusEnum.SUCCESS.getCode());
					result.put("msg", respDesc);
				} else if (resultFlag.equalsIgnoreCase(SandResultFlagEnums.RESULT_FLAG_1.getCode())) {
					result.put("status", PayStatusEnum.ERROR.getCode());
					result.put("msg", respDesc);
				} else {
					result.put("status", PayStatusEnum.IN_PROCESSED.getCode());
					result.put("msg", respDesc);
				}

				Date responseTime = new Date();
				result.put("completeTime", responseTime);
				result.put("liquidTime", responseTime);
				String status = result.get("status").toString();
				if (PayStatusEnum.SUCCESS.getCode().equals(status)) {
					updateCollectionRepaymentSignStatus(collectionRepaymentSign.getId(), request.getOrderCode(),
							respCode, respCode, respDesc, responseTime, PayStatusEnum.SUCCESS.getCode(), new Date());
				} else if (PayStatusEnum.IN_PROCESSED.getCode().equals(status)) {
					updateCollectionRepaymentSignStatus(collectionRepaymentSign.getId(), request.getOrderCode(),
							respCode, respCode, respDesc == null ? "连接异常，请重试" : respDesc, responseTime,
							PayStatusEnum.IN_PROCESSED.getCode(), new Date());
				} else if (PayStatusEnum.ERROR.getCode().equals(status)) {
					updateCollectionRepaymentSignStatus(collectionRepaymentSign.getId(), request.getOrderCode(),
							respCode, respCode, respDesc, responseTime, PayStatusEnum.ERROR.getCode(), new Date());
				}
			} else {
				result.put("status", PayStatusEnum.ERROR.getCode());
				result.put("msg", ReturnMsgConst.RETURN_600016.getMsg() + model.getTransactionFlowId());
			}

			return result;
		} catch (Exception e) {
			result.put("status", PayStatusEnum.ERROR.getCode());
			result.put("completeTime", new Date());
			e.printStackTrace();
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw, true));
			LogUtil.debug("处理通联接口异常:" + sw.toString());
			return result;
		}
	}
	
	
	/**
	    * 帐务明细查询
	    * @param singleSignReq
	    * @return
	    * @throws Exception
	    */
	   public Map<String, Object> queryMerchantDetail(TongLianInfo tongLianInfo,String qDate) throws Exception {
	       Map<String,Object> result = new HashMap<>();
	       try {
	           initProvider();
	           String xml = "";
	           AipgReq aipgReq = new AipgReq();
	           InfoReq info = PaymentUtils.makeReq(TradeTypeEnum.QUERY_MERCHANT_RECHARGE_DETAIL.getCode(),tongLianInfo);
	           aipgReq.setINFO(info);
	           EtQueryReq etQueryReq = new EtQueryReq();
	           etQueryReq.setQ_DATE(qDate);//查询日期
	           aipgReq.setETQREQ(etQueryReq);
	           LogUtil.debug("----------去请求帐务明细查询接口----------");
	           LogUtil.debug("service层请求帐务明细查询接口参数：" + aipgReq);
	           xml = XmlTools.buildXml(aipgReq, true);
	           XmlRequestResponse xrr = new XmlRequestResponse();
	           String responseStr = null;
	           try {
	               	responseStr = sendToTlt(xml, false, tongLianInfo.getUrl(),xrr,tongLianInfo);
	           } catch (Exception e) {
		           	e.printStackTrace();
		           	LogUtil.error("-----------请求帐务明细查询接口失败----------");
		           	LogUtil.error(e.getMessage());
		           	result.put("status",PayStatusEnum.ERROR.getCode());
		           	return result    ;
	           }
	           AipgRsp aipgrsp = null;
	           String trxcode = null;
	           //或者交易码
	           if (responseStr.indexOf("<TRX_CODE>") != -1)
	           {
	               int end = responseStr.indexOf("</TRX_CODE>");
	               int begin = end - 6;
	               if (begin >= 0) trxcode = responseStr.substring(begin, end);
	           }
	           aipgrsp=XSUtil.parseRsp(responseStr);
	           result = checkResponse(trxcode,aipgrsp, result);
	           Date responseTime = new Date();
	           result.put("completeTime", responseTime);
	           result.put("liquidTime", responseTime);
	           String status = result.get("status").toString();
	           return result;
	       }catch (Exception e){
	           result.put("status",PayStatusEnum.ERROR.getCode());
	           result.put("completeTime",new Date());
	           e.printStackTrace();
	           StringWriter sw = new StringWriter();
	           e.printStackTrace(new PrintWriter(sw, true));
	           LogUtil.debug("处理通联帐务明细查询接口异常:"+sw.toString());
	           return result;
	       }
	   }
	   
	   
	   /**
	    * 商户余额查询
	    * @param singleSignReq
	    * @return
	    * @throws Exception
	    */
	   public Map<String, Object> queryMerchantLeftAmount(TongLianInfo tongLianInfo) throws Exception {
	       Map<String,Object> result = new HashMap<>();
	       try {
	           initProvider();
	           String xml = "";
	           AipgReq aipgReq = new AipgReq();
	           InfoReq info = PaymentUtils.makeReq(TradeTypeEnum.QUERY_MERCHANT_INFO.getCode(),tongLianInfo);
	           //获取流水号
	       	   String serialNo =  StringUtil.getSerialNo(tongLianInfo.getMerchantId());
	           info.setREQ_SN(serialNo);//传入流水号
	           aipgReq.setINFO(info);
	           AcQueryReq acQueryReq = new AcQueryReq();
	           acQueryReq.setACCTNO(tongLianInfo.getMerchantId()+"000");
	           aipgReq.setACQUERYREQ(acQueryReq);
	           LogUtil.debug("----------去请求商户余额接口----------");
	           LogUtil.debug("service层请求商户余额接口参数：" + aipgReq);
	           xml = XmlTools.buildXml(aipgReq, true);
	           XmlRequestResponse xrr = new XmlRequestResponse();
	           String responseStr = null;
	           try {
	               	responseStr = sendToTlt(xml, false, tongLianInfo.getUrl(),xrr,tongLianInfo);
	           } catch (Exception e) {
		           	e.printStackTrace();
		           	LogUtil.error("-----------请求商户余额接口失败----------");
		           	LogUtil.error(e.getMessage());
		           	result.put("status",PayStatusEnum.ERROR.getCode());
		           	return result    ;
	           }
	           AipgRsp aipgrsp = null;
	           String trxcode = null;
	           //或者交易码
	           if (responseStr.indexOf("<TRX_CODE>") != -1)
	           {
	               int end = responseStr.indexOf("</TRX_CODE>");
	               int begin = end - 6;
	               if (begin >= 0) trxcode = responseStr.substring(begin, end);
	           }
	           aipgrsp=XSUtil.parseRsp(responseStr);
	           result = checkResponse(trxcode,aipgrsp, result);
	           Date responseTime = new Date();
	           result.put("completeTime", responseTime);
	           result.put("liquidTime", responseTime);
	           String status = result.get("status").toString();
	           return result;
	       }catch (Exception e){
	           result.put("status",PayStatusEnum.ERROR.getCode());
	           result.put("completeTime",new Date());
	           e.printStackTrace();
	           StringWriter sw = new StringWriter();
	           e.printStackTrace(new PrintWriter(sw, true));
	           LogUtil.debug("处理通联接口异常:"+sw.toString());
	           return result;
	       }
	   }
   
}
