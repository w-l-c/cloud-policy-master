package cn.rebornauto.platform.payment.service.impl.tonglianpay;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import cn.rebornauto.platform.business.dao.AgentQuotaMapper;
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
import tk.mybatis.mapper.entity.Example;

/**
 * @author ligewei
 * @date 2017年11月15日
 */
@Transactional(rollbackFor = Exception.class)
public class QueryTradeThread implements Runnable {
    private String reqSn;

    private TongLianInfo tongLianInfo;

    private boolean flag = true;

    private String tradeType;

    private TransactionFlowMapper transactionFlowMapper = MyApplicationContextAware.getApplicationContext().getBean(TransactionFlowMapper.class);

    private TransactionFlowService transactionFlowService = MyApplicationContextAware.getApplicationContext().getBean(TransactionFlowService.class);

    private CollectionRepaymentSignMapper collectionRepaymentSignMapper = MyApplicationContextAware.getApplicationContext().getBean(CollectionRepaymentSignMapper.class);

    private CustomerQuotaMapper customerQuotaMapper = MyApplicationContextAware.getApplicationContext().getBean(CustomerQuotaMapper.class);

    private CustomerTotalQuotaMapper customerTotalQuotaMapper = MyApplicationContextAware.getApplicationContext().getBean(CustomerTotalQuotaMapper.class);

    private OrderMapper orderMapper = MyApplicationContextAware.getApplicationContext().getBean(OrderMapper.class);

    private DaiZhengInfoMapper daiZhengInfoMapper = MyApplicationContextAware.getApplicationContext().getBean(DaiZhengInfoMapper.class);

    private AgentQuotaMapper agentQuotaMapper = MyApplicationContextAware.getApplicationContext().getBean(AgentQuotaMapper.class);
    
    public QueryTradeThread(String tradeType,String reqSn,TongLianInfo tongLianInfo) {
        this.tradeType = tradeType;
        this.reqSn = reqSn;
        this.tongLianInfo = tongLianInfo;
    }
    

    @Override
    public void run() {
        try {
            Long sleepTime = 20 * 1000L;
            try {
                Thread.sleep(sleepTime);
            } catch (Exception e) {
                LogUtil.error(e.getMessage());

            }
            
            long requestTime = System.currentTimeMillis();

            while (flag) {
                long excuteTime = System.currentTimeMillis() - requestTime;
                if (excuteTime <= 1000 * 60 * 3L) {
                    sleepTime = 30 * 1000L;
                } else if (excuteTime > 1000 * 60 * 3L && excuteTime <= 1000 * 60 * 10L) {
                    sleepTime = 2 * 60 * 1000L;
                } else if (excuteTime > 1000 * 60 * 10L && excuteTime <= 1000 * 60 * 30L) {
                    sleepTime = 10 * 60 * 1000L;
                } else if (excuteTime > 1000 * 60 * 30L) {
                    this.flag = false;
                    continue;
                }
                try {
                    Thread.sleep(sleepTime);
                    LogUtil.debug("启动查询交易结果线程,requestSn:" + this.reqSn);
                } catch (Exception e) {
                	LogUtil.error(e.getMessage());
                }
                String xml = "";
                AipgReq aipgReq = new AipgReq();
                InfoReq info = PaymentUtils.makeReq("200004",tongLianInfo);
                aipgReq.setINFO(info);
                TransQueryReq dr = new TransQueryReq();
                dr.setMERCHANT_ID(tongLianInfo.getMerchantId());
                dr.setQUERY_SN(this.reqSn);
                dr.setSTATUS(2);
                dr.setTYPE(1);
                aipgReq.addTrx(dr);
                xml = XmlTools.buildXml(aipgReq, true);
                LogUtil.debug("----------去请求交易结果查询接口----------");
                String responseStr = null;
                try {
                    responseStr = PaymentUtils.sendToTlt(xml, false, tongLianInfo.getUrl(),tongLianInfo);
                } catch (Exception e) {
                	LogUtil.error("----------请求交易结果查询接口失败----------"+e.getMessage());
                }
                LogUtil.debug("----------请求交易结果查询接口成功----------");
                String status = null;
                String code = null;
                String msg = null;
                LocalDateTime liquidTime =null;
                LocalDateTime responseTime = LocalDateTime.now();
                if(StringUtils.isNotEmpty(responseStr)) {
                    AipgRsp aipgrsp = XSUtil.parseRsp(responseStr);
                    LogUtil.debug("请求结果：" + aipgrsp);
                    //检查返回结果
                    Map<String, Object> result = checkResult(aipgrsp);
                    status = result.get("status").toString();
                    code = result.get("code").toString();
                    msg = result.get("msg").toString();
                    liquidTime = DateUtils.dateToLocalDateTime((Date)result.get("completeTime"));
                }else{
                    status = PayStatusEnum.IN_PROCESSED.getCode();
                    code = "9999";
                    msg = "请求交易结果查询接口失败";
                    liquidTime = responseTime;
                }
                if (excuteTime > 20 * 60 * 1000L) {
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
                if(TradeTypeEnum.SINGLE_REPAYMENT.getCode().equals(tradeType)) {
                    synchronized (this.reqSn) {
                    	CollectionRepaymentSignVO vo = collectionRepaymentSignMapper.selectListByRequestSn(this.reqSn);
                        Integer ftTypeId = vo.getFtTypeId();
                        String transactionFlowId = vo.getTransactionFlowId();
                        LogUtil.debug("交易类型:" + FtTypeEnum.getMsg(ftTypeId));
                        LogUtil.debug("交易结果:" + PayStatusEnum.getMsg(status));
                        TransactionFlow resultTransactionFlow = transactionFlowService.selectOne(transactionFlowId);
                        if (FtTypeEnum.RIGHT_LOAN.getCode().equals(ftTypeId) ) {
                        	if(PayStatusEnum.SUCCESS.getCode().equals(status)) {
                	      		//更改订单表成功数
                	      		orderMapper.updateSuccCountByOrderId(vo.getOrderId());
                	      		
                	      		//更改订单表统计数
                	      		orderMapper.updatePayStatByOrderId(vo.getOrderId());
                	      		
                	      		//更新订单状态
                	      		orderMapper.updateOrderStatusByOrderId(vo.getOrderId());
                        	}else if(PayStatusEnum.ERROR.getCode().equals(status)) {
                        		//1.代理人月额度释放
                        		AgentQuota record = new AgentQuota();
                        		record.setAgentIdcardno(resultTransactionFlow.getIdcardno());
            		        	record.setLoanAmount(vo.getAmount());
            		        	record.setYearmonth(DateUtil.format(new Date(), "yyyyMM"));
            		        	agentQuotaMapper.cancelAgentQuotaLoanAmount(record);//放款失败释放额度
            		        	
            		        	//2.客户余额释放
            		      		customerQuotaMapper.cancelCustomerQuotaByCustomerId(vo.getCustomerId(), vo.getAmount());
            		      		
            		      		//3.客户总余额释放
            		      		customerTotalQuotaMapper.cancelCustomerTotalQuotaByCustomerId(vo.getAmount());
            		      		
            		      		//4.代征主体额度释放
            		      		daiZhengInfoMapper.cancelDaiZhengQuotaById(resultTransactionFlow.getDaiZhengId(),vo.getAmount());
                        		

                        		//更改订单表失败数
                	      		orderMapper.updateFailCountByOrderId(vo.getOrderId());
                        	}
                        }
                        LogUtil.debug("修改支付流水记录");
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
                        LogUtil.debug("修改收付款记录");
                        
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
            LogUtil.error("线程执行异常:"+e.getMessage());
        }
    }
    private Map<String,Object >  checkResult(AipgRsp aipgrsp){
        Map<String ,Object > result = new HashMap<>();
        result.put("code",aipgrsp.getINFO().getRET_CODE());
        result.put("msg",aipgrsp.getINFO().getERR_MSG());
        result.put("completeTime",new Date());
        if ("0000".equals(aipgrsp.getINFO().getRET_CODE())) {
            QTransRsp qrsq = (QTransRsp) aipgrsp.getTrxData().get(0);
            LogUtil.debug("查询成功，具体结果明细如下:");
            List<QTDetail> details = qrsq.getDetails();
            for (QTDetail lobj : details) {
                LogUtil.debug("原支付交易批次号:" + lobj.getBATCHID() + "  ");
                LogUtil.debug("记录序号:" + lobj.getSN() + "  ");
                LogUtil.debug("账号:" + lobj.getACCOUNT_NO() + "  ");
                LogUtil.debug("户名:" + lobj.getACCOUNT_NAME() + "  ");
                LogUtil.debug("金额:" + lobj.getAMOUNT() + "  ");
                LogUtil.debug("返回结果:" + lobj.getRET_CODE() + "  ");
                result.put("msg",lobj.getERR_MSG());
                if ("0000".equals(lobj.getRET_CODE())) {
                    LogUtil.debug("返回说明:交易成功  ");
                    LogUtil.debug("更新交易库状态（原交易的状态）");
                    result.put("completeTime", DateUtils.dateToLocalDateTime(DateUtil.parseSimpleDate(lobj.getSETTDAY())));
                    result.put("status", PayStatusEnum.SUCCESS.getCode());
                    result.put("code", lobj.getRET_CODE());
                    result.put("msg", lobj.getERR_MSG());
                } else {
                    LogUtil.debug("返回说明:" + lobj.getERR_MSG() + "  ");
                    LogUtil.debug("更新交易库状态（原交易的状态）");
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
            LogUtil.debug("返回说明:" + aipgrsp.getINFO().getRET_CODE() + "  ");
            LogUtil.debug("返回说明：" + aipgrsp.getINFO().getERR_MSG());
            LogUtil.debug("该状态时，说明整个批次的交易都在处理中");
            result.put("status", PayStatusEnum.IN_PROCESSED.getCode());
        } else if ("2004".equals(aipgrsp.getINFO().getRET_CODE())) {
            flag = false;
            LogUtil.debug("整批交易未受理通过（最终失败）");
            result.put("status", PayStatusEnum.ERROR.getCode());
        } else if ("1002".equals(aipgrsp.getINFO().getRET_CODE())) {
            LogUtil.debug("查询无结果集（表示通联端根据商户请求上送的条件查不到对应的结果集）");
            result.put("status", PayStatusEnum.WAIT_HUMAN_DEAL.getCode());
        } else {
            LogUtil.debug("查询请求失败，请重新发起查询");
            result.put("status", PayStatusEnum.IN_PROCESSED.getCode());
        }
        return result;
    }
    
    
}
