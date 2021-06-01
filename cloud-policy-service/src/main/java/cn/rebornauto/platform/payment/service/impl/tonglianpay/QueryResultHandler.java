package cn.rebornauto.platform.payment.service.impl.tonglianpay;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

import cn.rebornauto.platform.business.dao.CollectionRepaymentSignMapper;
import cn.rebornauto.platform.business.dao.CustomerQuotaMapper;
import cn.rebornauto.platform.business.dao.CustomerTotalQuotaMapper;
import cn.rebornauto.platform.business.dao.DaiZhengInfoMapper;
import cn.rebornauto.platform.business.dao.OrderMapper;
import cn.rebornauto.platform.business.dao.TransactionFlowMapper;
import cn.rebornauto.platform.business.entity.AgentQuota;
import cn.rebornauto.platform.business.entity.CollectionRepaymentSign;
import cn.rebornauto.platform.business.entity.CollectionRepaymentSignVO;
import cn.rebornauto.platform.business.entity.SandPayResultDemo;
import cn.rebornauto.platform.business.entity.TransactionFlow;
import cn.rebornauto.platform.business.entity.TransactionFlowVO;
import cn.rebornauto.platform.business.form.OrderForm;
import cn.rebornauto.platform.business.service.AgentQuotaService;
import cn.rebornauto.platform.business.service.BusiLogService;
import cn.rebornauto.platform.business.service.TransactionFlowService;
import cn.rebornauto.platform.common.Const;
import cn.rebornauto.platform.common.ReturnMsgConst;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.common.util.DateUtils;
import cn.rebornauto.platform.common.util.LogUtil;
import cn.rebornauto.platform.pay.sandpay.dsf.demo.SandBase;
import cn.rebornauto.platform.pay.sandpay.entity.SandInfo;
import cn.rebornauto.platform.pay.sandpay.enums.SandInterfaceEnums;
import cn.rebornauto.platform.pay.sandpay.enums.SandResultFlagEnums;
import cn.rebornauto.platform.pay.sandpay.request.QueryOrderRequest;
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
import cn.rebornauto.platform.sys.entity.SysUser;
import tk.mybatis.mapper.entity.Example;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Company: </p>
 * @author kgc
 * @date May 12, 2019 5:33:52 PM
 */
@Service
public class QueryResultHandler extends BaseHandler {

    
    @Autowired
    PaymentHandler paymentHandler;
    @Autowired
    TransactionFlowMapper transactionFlowMapper;
    @Autowired
    CollectionRepaymentSignMapper collectionRepaymentSignMapper;
    @Autowired
    CustomerQuotaMapper customerQuotaMapper;
    @Autowired
    CustomerTotalQuotaMapper customerTotalQuotaMapper ;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    DaiZhengInfoMapper daiZhengInfoMapper;
    @Autowired
    TransactionFlowService transactionFlowService;
    
    @Autowired
    BusiLogService busiLogService;
    
    @Autowired
    AgentQuotaService agentQuotaService;



    public Response queryResultTongLian(String transactionFlowId,TongLianInfo tongLianInfo,HttpServletRequest req,SysUser currentUser) throws Exception{
    	Response response = new Response();
        List<TransactionFlowVO> mapList = transactionFlowMapper.selectRequestSnById(transactionFlowId);
        if(null==mapList || mapList.size()==0){
            return response.error().message(ReturnMsgConst.RETURN_600019.getMsg());
        }
        String reqSn = mapList.get(0).getRequestSn();
        LogUtil.debug("开始查询交易结果,requestSn:" + reqSn);
        String xml = "";
        AipgReq aipgReq = new AipgReq();
        InfoReq info = PaymentUtils.makeReq("200004",tongLianInfo);
        aipgReq.setINFO(info);
        TransQueryReq dr = new TransQueryReq();
        dr.setMERCHANT_ID(tongLianInfo.getMerchantId());
        dr.setQUERY_SN(reqSn);
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
            //操作日志
            busiLogService.add(req, Const.busi_log_busi_type_9, Const.busi_log_option_type_fail,"查询交易结果失败", currentUser);
            Map<String, Object> result = new HashMap<>();

            result.put("status",PayStatusEnum.IN_PROCESSED.getMsg());
            result.put("code","9999");
            result.put("msg","请求交易结果查询接口失败");
            result.put("completeTime",DateUtil.getTime());
            result.put("responseTime",DateUtil.getTime());
            result.put("createTime",mapList.get(0).getCreatetime());
            result.put("transactionFlowId", transactionFlowId);
            result.put("orderId", mapList.get(0).getOrderId());
            return response.error().body(result);
        }
        LogUtil.debug("----------请求交易结果查询接口成功----------");
        AipgRsp aipgrsp = XSUtil.parseRsp(responseStr);
        LogUtil.debug("请求结果：" + aipgrsp);
        Map<String, Object> result = checkResult(aipgrsp);
        String status = result.get("status").toString();
        result.put("status",Integer.parseInt(status));
//        Date liquidTime = (Date) result.get("completeTime");
        result.put("completeTime",DateUtil.getTime());
        result.put("responseTime",DateUtil.getTime());
        result.put("createTime",mapList.get(0).getCreatetime());
        result.put("transactionFlowId", transactionFlowId);
        result.put("orderId", mapList.get(0).getOrderId());
        //操作日志
        busiLogService.add(req, Const.busi_log_busi_type_9, Const.busi_log_option_type_success,"查询交易结果成功", currentUser);
		
        return response.ok().body(result);
    }

    private Map<String,Object >  checkResult(AipgRsp aipgrsp) throws Exception{
        Map<String ,Object > result = new HashMap<>();
        result.put("code",aipgrsp.getINFO().getRET_CODE());
        result.put("msg",aipgrsp.getINFO().getERR_MSG());
        result.put("completeTime",new Date());
        if ("0000".equals(aipgrsp.getINFO().getRET_CODE())
                ||"4000".equals(aipgrsp.getINFO().getRET_CODE())) {
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
                if ("0000".equals(lobj.getRET_CODE())
                        ||"4000".equals(lobj.getRET_CODE())) {
                    LogUtil.debug("返回说明:交易成功  ");
                    LogUtil.debug("更新交易库状态（原交易的状态）");
                    result.put("completeTime", DateUtil.parseSimpleDate(lobj.getSETTDAY()));
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
            LogUtil.debug("整批交易未受理通过（最终失败）");
            result.put("status", PayStatusEnum.ERROR.getCode());
        } else if ("1002".equals(aipgrsp.getINFO().getRET_CODE())) {
            LogUtil.debug("查询无结果集（表示通联端根据商户请求上送的条件查不到对应的结果集）");
            LogUtil.debug("查询无结果集（表示通联端根据商户请求上送的条件查不到对应的结果集）");
            result.put("status", PayStatusEnum.ERROR.getCode());
        } else {
            LogUtil.debug("查询请求失败，请重新发起查询");
        }
        return result;
    }
    
    @Transactional
    public Response updateResult(OrderForm updateResultForm,HttpServletRequest req,SysUser currentUser) throws Exception{
    	Response response = new Response();
        List<TransactionFlowVO> mapList = transactionFlowMapper.selectRequestSnById(updateResultForm.getTransactionFlowId());
        String tradeType = mapList.get(0).getTradeCode();
        String reqSn = mapList.get(0).getRequestSn();
        int status = updateResultForm.getStatus();
        String responseTime = updateResultForm.getResponseTime();
        String code = updateResultForm.getCode();
        String liquidTime = updateResultForm.getCompleteTime();
        String msg = updateResultForm.getMsg();
        if(TradeTypeEnum.SINGLE_REPAYMENT.getCode().equals(tradeType)||SandBase.PRODUCTID_AGENTPAY_TOC.equals(tradeType)) {
            synchronized (reqSn) {
            	CollectionRepaymentSignVO vo = collectionRepaymentSignMapper.selectListByRequestSn(reqSn);
                Integer ftTypeId = vo.getFtTypeId();
                String transactionFlowId = vo.getTransactionFlowId();
                LogUtil.debug("交易类型:" + FtTypeEnum.getMsg(ftTypeId));
                LogUtil.debug("交易结果:" + PayStatusEnum.getMsg(String.valueOf(status)));
                TransactionFlow resultTransactionFlow = transactionFlowService.selectOne(transactionFlowId);
                if (FtTypeEnum.RIGHT_LOAN.getCode().equals(ftTypeId) ) {
                	if(PayStatusEnum.SUCCESS.getCode().equals(String.valueOf(status))) {
        	      		//更改订单表成功数
        	      		orderMapper.updateSuccCountByOrderId(vo.getOrderId());
        	      		
        	      		//更改订单表统计数
        	      		orderMapper.updatePayStatByOrderId(vo.getOrderId());
        	      		
        	      		//更新订单状态
        	      		orderMapper.updateOrderStatusByOrderId(vo.getOrderId());
        	      		
        	      		//操作日志
        	            busiLogService.add(req, Const.busi_log_busi_type_9, Const.busi_log_option_type_success,"更新交易结果成功[orderId:"+vo.getOrderId()+",代理人姓名:"+resultTransactionFlow.getAgentName()+",卡号:"+resultTransactionFlow.getOpenBankNo()+",amount:"+vo.getAmount()+"]", currentUser);
        	    		
                	}else if(PayStatusEnum.ERROR.getCode().equals(String.valueOf(status))) {
                		//1.代理人月额度释放
                		AgentQuota record = new AgentQuota();
    		        	record.setAgentOpenBankNo(vo.getOpenBankNo());
    		        	record.setLoanAmount(vo.getAmount());
    		        	record.setYearmonth(DateUtil.format(new Date(), "yyyyMM"));
    		        	agentQuotaService.cancelAgentQuotaLoanAmount(record);//放款失败释放额度
    		        	
    		        	//2.客户余额释放
    		      		customerQuotaMapper.cancelCustomerQuotaByCustomerId(vo.getCustomerId(), vo.getAmount());
    		      		
    		      		//3.客户总余额释放
    		      		customerTotalQuotaMapper.cancelCustomerTotalQuotaByCustomerId(vo.getAmount());
    		      		
    		      		//4.代征主体额度释放
    		      		daiZhengInfoMapper.cancelDaiZhengQuotaById(resultTransactionFlow.getDaiZhengId(),vo.getAmount());
                		//更改订单表失败数
        	      		orderMapper.updateFailCountByOrderId(vo.getOrderId());
                		//更改订单表失败数
        	      		orderMapper.updateFailCountByOrderId(vo.getOrderId());
        	      		//操作日志
        	            busiLogService.add(req, Const.busi_log_busi_type_9, Const.busi_log_option_type_fail," 更新交易结果失败", currentUser);
        	    		
                	}
                }
                LogUtil.debug("修改支付流水记录");
                CollectionRepaymentSign collectionRepaymentSign = new CollectionRepaymentSign();
                collectionRepaymentSign.setId(vo.getId());
                collectionRepaymentSign.setRequestSn(reqSn);
                collectionRepaymentSign.setStatus1(code);
                collectionRepaymentSign.setStatus2(code);
                collectionRepaymentSign.setCompleteTime(DateUtils.dateToLocalDateTime(DateUtil.fomatDateTime(responseTime)));
                collectionRepaymentSign.setPostTime(DateUtils.dateToLocalDateTime(DateUtil.fomatDateTime(liquidTime)));
                collectionRepaymentSign.setRemark(msg);
                collectionRepaymentSign.setStatus(status);
                collectionRepaymentSignMapper.updateByPrimaryKeySelective(collectionRepaymentSign);
                LogUtil.debug("修改收付款记录");
                
                TransactionFlow transactionFlow = new TransactionFlow();
                transactionFlow.setPayStatus(status);
                transactionFlow.setResult(msg);
                transactionFlow.setCompleteTime(DateUtils.dateToLocalDateTime(DateUtil.fomatDateTime(responseTime)));
                transactionFlow.setModifytime(DateUtils.dateToLocalDateTime(DateUtil.fomatDateTime(liquidTime)));
                Example transactionFlowExample = new Example(TransactionFlow.class);
                Example.Criteria criteria = transactionFlowExample.createCriteria();
                criteria.andEqualTo("transactionFlowId", transactionFlowId);
                transactionFlowMapper.updateByExampleSelective(transactionFlow, transactionFlowExample);
                response.ok();
            }
        }
		return response;
    }

    
    public Response queryResultSand(String transactionFlowId,SandInfo sandInfo,HttpServletRequest req,SysUser currentUser,String sysPaySwitch) throws Exception{
    	Map<String, Object> result = new HashMap<>();
    	Response response = new Response();
        List<TransactionFlowVO> mapList = transactionFlowMapper.selectRequestSnById(transactionFlowId);
        if(null==mapList || mapList.size()==0){
            return response.error().message(ReturnMsgConst.RETURN_600019.getMsg());
        }
        TransactionFlowVO vo = mapList.get(0); 
        try {
        	// 要查询的订单号 
            String orderCode = vo.getRequestSn();
            LogUtil.debug("开始查询交易结果,requestSn:" + orderCode);
            // 查询订单的交易时间
            String tranTime = DateUtils.localDateTimeToStringYYMMDDHHMMSS(vo.getPostTime());
            
            QueryOrderRequest request = new QueryOrderRequest(tranTime, orderCode);
            
            JSONObject jsonRequest = (JSONObject) JSONObject.toJSON(request);
    		
    		String reqAddr = SandInterfaceEnums.QUERY_ORDER.getCode();//接口报文规范中获取
    		
    		
    		JSONObject resp = new JSONObject();
    		if (Const.SYS_PAY_SWITCH_TEST.equalsIgnoreCase(sysPaySwitch)) {
    			SandPayResultDemo query = new SandPayResultDemo();
    			query.setType("queryOrderResult");
    			SandPayResultDemo record = sandPayResultDemoMapper.selectOne(query);
    			String json = record.getJson();
    	        resp = JSONObject.parseObject(json);
    		} else if (Const.SYS_PAY_SWITCH_ONLINE.equalsIgnoreCase(sysPaySwitch)) {
    			resp = SandBase.requestServer(jsonRequest.toJSONString(), reqAddr, SandBase.ORDER_QUERY, sandInfo);
    		}
    		
    		System.out.println("resp:"+resp);	
    		if(resp!=null) {
    			String respCode = resp.getString("respCode");
				String respDesc = resp.getString("respDesc");
				String resultFlag = resp.getString("resultFlag");
				LogUtil.info("响应码：[" + respCode + "]");
				LogUtil.info("响应描述：[" + respDesc + "]");
				LogUtil.info("处理状态：[" + resultFlag + "]");
    			
    			LogUtil.debug("----------请求交易结果查询接口成功----------");
    			if(respCode.equalsIgnoreCase(Const.SAND_PAY_RESULT_CODE_0000)) {
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
    			}
    	        String status = result.get("status").toString();
    	        result.put("status",Integer.parseInt(status));
    	        result.put("completeTime",DateUtil.getTime());
    	        result.put("responseTime",DateUtil.getTime());
    	        result.put("createTime",mapList.get(0).getCreatetime());
    	        result.put("transactionFlowId", transactionFlowId);
    	        result.put("orderId", mapList.get(0).getOrderId());
    	        //操作日志
    	        busiLogService.add(req, Const.busi_log_busi_type_9, Const.busi_log_option_type_success,"查询交易结果成功", currentUser);
    			
    	        return response.ok().body(result);
    		}else {
    			System.out.println("服务器请求异常！！！");
    			LogUtil.error("服务器请求异常！！！");	
    			result.put("status",PayStatusEnum.ERROR.getMsg());
                result.put("code","9999");
                result.put("msg","服务器请求异常");
                result.put("completeTime",DateUtil.getTime());
                result.put("responseTime",DateUtil.getTime());
                result.put("createTime",mapList.get(0).getCreatetime());
                result.put("transactionFlowId", transactionFlowId);
                result.put("orderId", mapList.get(0).getOrderId());
    			return response.error().body(result);
    		}
        } catch (Exception e) {
            LogUtil.error("----------请求交易结果查询接口失败----------"+e.getMessage());
            //操作日志
            busiLogService.add(req, Const.busi_log_busi_type_9, Const.busi_log_option_type_fail,"查询交易结果失败", currentUser);
            

            result.put("status",PayStatusEnum.IN_PROCESSED.getMsg());
            result.put("code","9999");
            result.put("msg","请求交易结果查询接口失败");
            result.put("completeTime",DateUtil.getTime());
            result.put("responseTime",DateUtil.getTime());
            result.put("createTime",mapList.get(0).getCreatetime());
            result.put("transactionFlowId", transactionFlowId);
            result.put("orderId", mapList.get(0).getOrderId());
            return response.error().body(result);
        }
    }

}
