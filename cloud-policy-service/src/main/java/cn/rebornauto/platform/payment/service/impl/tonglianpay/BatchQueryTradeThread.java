package cn.rebornauto.platform.payment.service.impl.tonglianpay;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import cn.rebornauto.platform.business.dao.CollectionRepaymentSignMapper;
import cn.rebornauto.platform.business.dao.CustomerQuotaMapper;
import cn.rebornauto.platform.business.dao.CustomerTotalQuotaMapper;
import cn.rebornauto.platform.business.dao.DaiZhengInfoMapper;
import cn.rebornauto.platform.business.dao.OrderMapper;
import cn.rebornauto.platform.business.dao.TransactionFlowMapper;
import cn.rebornauto.platform.business.entity.AgentQuota;
import cn.rebornauto.platform.business.entity.CollectionRepaymentSign;
import cn.rebornauto.platform.business.entity.CollectionRepaymentSignVO;
import cn.rebornauto.platform.business.entity.TransactionFlow;
import cn.rebornauto.platform.business.service.AgentQuotaService;
import cn.rebornauto.platform.business.service.TransactionFlowService;
import cn.rebornauto.platform.common.util.DateUtils;
import cn.rebornauto.platform.common.util.LogUtil;
import cn.rebornauto.platform.pay.tonglian.Enum.FtTypeEnum;
import cn.rebornauto.platform.pay.tonglian.Enum.PayStatusEnum;
import cn.rebornauto.platform.pay.tonglian.Enum.TradeTypeEnum;
import cn.rebornauto.platform.pay.tonglian.aipg.common.AipgReq;
import cn.rebornauto.platform.pay.tonglian.aipg.common.AipgRsp;
import cn.rebornauto.platform.pay.tonglian.aipg.common.InfoReq;
import cn.rebornauto.platform.pay.tonglian.aipg.common.XSUtil;
import cn.rebornauto.platform.pay.tonglian.aipg.transquery.QTDetail;
import cn.rebornauto.platform.pay.tonglian.aipg.transquery.QTransRsp;
import cn.rebornauto.platform.pay.tonglian.aipg.transquery.TransQueryReq;
import cn.rebornauto.platform.pay.tonglian.allinpay.XmlTools;
import cn.rebornauto.platform.pay.tonglian.entity.TongLianInfo;
import cn.rebornauto.platform.pay.tonglian.utils.DateUtil;
import cn.rebornauto.platform.pay.tonglian.utils.PaymentUtils;

/**
 * @author ligewei
 * @date 2017???11???15???
 */
@Transactional(rollbackFor = Exception.class)
public class BatchQueryTradeThread implements Runnable {
	private String reqSn;


    private boolean flag = true;

    private String tradeType;
    
    private TongLianInfo tongLianInfo;

    private TransactionFlowMapper transactionFlowMapper = MyApplicationContextAware.getApplicationContext().getBean(TransactionFlowMapper.class);

    private CollectionRepaymentSignMapper collectionRepaymentSignMapper = MyApplicationContextAware.getApplicationContext().getBean(CollectionRepaymentSignMapper.class);

    private CustomerQuotaMapper customerQuotaMapper = MyApplicationContextAware.getApplicationContext().getBean(CustomerQuotaMapper.class);

    private CustomerTotalQuotaMapper customerTotalQuotaMapper = MyApplicationContextAware.getApplicationContext().getBean(CustomerTotalQuotaMapper.class);

    private OrderMapper orderMapper = MyApplicationContextAware.getApplicationContext().getBean(OrderMapper.class);

    private DaiZhengInfoMapper daiZhengInfoMapper = MyApplicationContextAware.getApplicationContext().getBean(DaiZhengInfoMapper.class);

    private TransactionFlowService transactionFlowService = MyApplicationContextAware.getApplicationContext().getBean(TransactionFlowService.class);

    AgentQuotaService agentQuotaService = MyApplicationContextAware.getApplicationContext().getBean(AgentQuotaService.class);

    public BatchQueryTradeThread(String tradeType, String reqSn,TongLianInfo tongLianInfo) {
        this.tradeType = tradeType;
        this.reqSn = reqSn;
        this.tongLianInfo = tongLianInfo;
    }


    @Override
    public void run() {
        try {
            Long sleepTime = 5*60 * 1000L;
            
            
            long requestTime = System.currentTimeMillis();

            while (flag) {
                long excuteTime = System.currentTimeMillis() - requestTime;
                if (excuteTime > 1000 * 60 * 50L) {
                    this.flag = false;
                    continue;
                }
                try {
                    Thread.sleep(sleepTime);
                    LogUtil.debug("??????????????????????????????,requestSn:" + this.reqSn);
                } catch (Exception e) {
                	LogUtil.error(e.getMessage());
                }
                String xml = "";
                AipgReq aipgReq = new AipgReq();
                InfoReq info = PaymentUtils.makeReq("200004", tongLianInfo);
                aipgReq.setINFO(info);
                TransQueryReq dr = new TransQueryReq();
                dr.setMERCHANT_ID(tongLianInfo.getMerchantId());
                dr.setQUERY_SN(this.reqSn);
                dr.setSTATUS(2);
                dr.setTYPE(1);
                aipgReq.addTrx(dr);
                xml = XmlTools.buildXml(aipgReq, true);
                LogUtil.debug("----------?????????????????????????????????----------");
                String responseStr = null;
                try {
                    responseStr = PaymentUtils.sendToTlt(xml, false, tongLianInfo.getUrl(),tongLianInfo);
                } catch (Exception e) {
                	LogUtil.error("----------????????????????????????????????????----------"+e.getMessage());
                }
                LogUtil.debug("----------????????????????????????????????????----------");
                String status = null;
                String code = null;
                String msg = null;
                LocalDateTime liquidTime =null;
                LocalDateTime responseTime = LocalDateTime.now();
                if(StringUtils.isNotEmpty(responseStr)) {
                    AipgRsp aipgrsp = XSUtil.parseRsp(responseStr);
                    LogUtil.debug("???????????????" + aipgrsp);
                    //??????????????????
                    Map<String, Object> result = checkResult(aipgrsp);
                    status = result.get("status").toString();
                    code = result.get("code").toString();
                    msg = result.get("msg").toString();
                    liquidTime = DateUtils.dateToLocalDateTime((Date)result.get("completeTime"));
                }else{
                    status = PayStatusEnum.IN_PROCESSED.getCode();
                    code = "9999";
                    msg = "????????????????????????????????????";
                    liquidTime = responseTime;
                }
                if (excuteTime > 50 * 60 * 1000L) {
                    if (PayStatusEnum.WAIT_HUMAN_DEAL.getCode().equals(status)) {
                        status = PayStatusEnum.ERROR.getCode();
                    } else if (PayStatusEnum.IN_PROCESSED.getCode().equals(status)) {
                        status = PayStatusEnum.WAIT_HUMAN_DEAL.getCode();
                    }
                    this.flag = false;
                } else {
                    if (PayStatusEnum.WAIT_HUMAN_DEAL.getCode().equals(status)) {
                        status = PayStatusEnum.IN_PROCESSED.getCode();
                    }
                }
                if(TradeTypeEnum.MULTI_REPAYMENT.getCode().equals(tradeType)) {
                    synchronized (this.reqSn) {
                    	CollectionRepaymentSignVO vo = collectionRepaymentSignMapper.selectListByRequestSn(this.reqSn);
                        Integer ftTypeId = vo.getFtTypeId();
                        String transactionFlowId = vo.getTransactionFlowId();
                        String customerId = vo.getCustomerId();
                        LogUtil.debug("????????????:" + FtTypeEnum.getMsg(ftTypeId));
                        LogUtil.debug("????????????:" + PayStatusEnum.getMsg(status));
                        TransactionFlow resultTransactionFlow = transactionFlowService.selectOne(transactionFlowId);
                        if (FtTypeEnum.RIGHT_LOAN.getCode().equals(ftTypeId) ) {
                        	if(PayStatusEnum.SUCCESS.getCode().equals(status)) {
                	      		//????????????????????????
                	      		orderMapper.updateSuccCountByOrderId(vo.getOrderId());
                	      		
                	      		//????????????????????????
                	      		orderMapper.updatePayStatByOrderId(vo.getOrderId());
                	      		
                	      		//??????????????????
                	      		orderMapper.updateOrderStatusByOrderId(vo.getOrderId());
                        	}else if(PayStatusEnum.ERROR.getCode().equals(status)) {
                        		//1.????????????????????????
                        		AgentQuota record = new AgentQuota();
            		        	record.setAgentOpenBankNo(vo.getOpenBankNo());
            		        	record.setLoanAmount(vo.getAmount());
            		        	record.setYearmonth(DateUtil.format(new Date(), "yyyyMM"));
            		        	agentQuotaService.cancelAgentQuotaLoanAmount(record);//????????????????????????
            		        	
            		        	//2.??????????????????
            		      		customerQuotaMapper.cancelCustomerQuotaByCustomerId(vo.getCustomerId(), vo.getAmount());
            		      		
            		      		//3.?????????????????????
            		      		customerTotalQuotaMapper.cancelCustomerTotalQuotaByCustomerId(vo.getAmount());
            		      		
            		      		//4.????????????????????????
            		      		daiZhengInfoMapper.cancelDaiZhengQuotaById(resultTransactionFlow.getDaiZhengId(),vo.getAmount());
                        		//????????????????????????
                	      		orderMapper.updateFailCountByOrderId(vo.getOrderId());
                        	}
                        }
                        LogUtil.debug("????????????????????????");
                        CollectionRepaymentSign collectionRepaymentSign = new CollectionRepaymentSign();
                        collectionRepaymentSign.setId(vo.getId());
                        collectionRepaymentSign.setRequestSn(this.reqSn);
                        collectionRepaymentSign.setStatus1(code);
                        collectionRepaymentSign.setStatus2(code);
                        collectionRepaymentSign.setCompleteTime(responseTime);
                        collectionRepaymentSign.setPostTime(liquidTime);
                        collectionRepaymentSign.setRemark(msg);
                        collectionRepaymentSign.setStatus(Integer.parseInt(status));
                        collectionRepaymentSignMapper.updateByPrimaryKeySelective(collectionRepaymentSign);
                        LogUtil.debug("?????????????????????");
                        TransactionFlow transactionFlow = new TransactionFlow();
                        transactionFlow.setPayStatus(Integer.parseInt(status));
                        transactionFlow.setResult(msg);
                        transactionFlow.setCompleteTime(responseTime);
                        transactionFlow.setCreatetime(liquidTime);
                        Example transactionFlowExample = new Example(TransactionFlow.class);
                        Example.Criteria criteria = transactionFlowExample.createCriteria();
                        criteria.andEqualTo("transactionFlowId", transactionFlowId);
                        transactionFlowMapper.updateByExampleSelective(transactionFlow, transactionFlowExample);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.error("??????????????????:"+e.getMessage());
        }
        //throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
    }
    private Map<String,Object >  checkResult(AipgRsp aipgrsp){
        Map<String ,Object > result = new HashMap<>();
        result.put("code",aipgrsp.getINFO().getRET_CODE());
        result.put("msg",aipgrsp.getINFO().getERR_MSG());
        result.put("completeTime",new Date());
        if ("0000".equals(aipgrsp.getINFO().getRET_CODE())||"4000".equals(aipgrsp.getINFO().getRET_CODE())) {
            QTransRsp qrsq = (QTransRsp) aipgrsp.getTrxData().get(0);
            LogUtil.debug("???????????????????????????????????????:");
            List<QTDetail> details = qrsq.getDetails();
            for (QTDetail lobj : details) {
                LogUtil.debug("????????????????????????:" + lobj.getBATCHID() + "  ");
                LogUtil.debug("????????????:" + lobj.getSN() + "  ");
                LogUtil.debug("??????:" + lobj.getACCOUNT_NO() + "  ");
                LogUtil.debug("??????:" + lobj.getACCOUNT_NAME() + "  ");
                LogUtil.debug("??????:" + lobj.getAMOUNT() + "  ");
                LogUtil.debug("????????????:" + lobj.getRET_CODE() + "  ");
                result.put("msg",lobj.getERR_MSG());
                if ("0000".equals(lobj.getRET_CODE())||"4000".equals(lobj.getRET_CODE())) {
                    LogUtil.debug("????????????:????????????  ");
                    LogUtil.debug("?????????????????????????????????????????????");
                    result.put("completeTime", DateUtil.parseSimpleDate(lobj.getSETTDAY()));
                    result.put("status", PayStatusEnum.SUCCESS.getCode());
                    result.put("code", lobj.getRET_CODE());
                    result.put("msg", lobj.getERR_MSG());
                } else {
                    LogUtil.debug("????????????:" + lobj.getERR_MSG() + "  ");
                    LogUtil.debug("?????????????????????????????????????????????");
                    result.put("status", PayStatusEnum.ERROR.getCode());
                    result.put("code", lobj.getRET_CODE());
                    result.put("msg", lobj.getERR_MSG());
                }
                this.flag = false;
            }
        } else if ("2000".equals(aipgrsp.getINFO().getRET_CODE())
                || "2001".equals(aipgrsp.getINFO().getRET_CODE())
                || "2003".equals(aipgrsp.getINFO().getRET_CODE())
                || "2005".equals(aipgrsp.getINFO().getRET_CODE())
                || "2007".equals(aipgrsp.getINFO().getRET_CODE())
                || "2008".equals(aipgrsp.getINFO().getRET_CODE())) {
            LogUtil.debug("????????????:" + aipgrsp.getINFO().getRET_CODE() + "  ");
            LogUtil.debug("???????????????" + aipgrsp.getINFO().getERR_MSG());
            LogUtil.debug("?????????????????????????????????????????????????????????");
            result.put("status", PayStatusEnum.IN_PROCESSED.getCode());
        } else if ("2004".equals(aipgrsp.getINFO().getRET_CODE())||"2002".equals(aipgrsp.getINFO().getRET_CODE())||"2006".equals(aipgrsp.getINFO().getRET_CODE())) {
            flag = false;
            LogUtil.debug("?????????????????????????????????????????????");
            result.put("status", PayStatusEnum.ERROR.getCode());
        } else if ("1002".equals(aipgrsp.getINFO().getRET_CODE())) {
            LogUtil.debug("???????????????????????????????????????????????????????????????????????????????????????????????????");
            result.put("status", PayStatusEnum.WAIT_HUMAN_DEAL.getCode());
        } else {
            LogUtil.debug("??????????????????????????????????????????");
            result.put("status", PayStatusEnum.IN_PROCESSED.getCode());
        }
        return result;
    }
    
    
}
