package cn.rebornauto.platform.business.controller;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.rebornauto.platform.business.entity.CollectionRepaymentSignVO;
import cn.rebornauto.platform.business.entity.Order;
import cn.rebornauto.platform.business.entity.OrderDetailTable;
import cn.rebornauto.platform.business.entity.OrderDetailVO;
import cn.rebornauto.platform.business.entity.OrderVO;
import cn.rebornauto.platform.business.entity.TransactionFlow;
import cn.rebornauto.platform.business.entity.TransactionFlowTable;
import cn.rebornauto.platform.business.entity.TransactionFlowVO;
import cn.rebornauto.platform.business.form.OrderForm;
import cn.rebornauto.platform.business.form.TransactionFlowForm;
import cn.rebornauto.platform.business.query.CollectionRepaymentSignQuery;
import cn.rebornauto.platform.business.query.OrderDetailQuery;
import cn.rebornauto.platform.business.query.OrderQuery;
import cn.rebornauto.platform.business.query.TransactionListQuery;
import cn.rebornauto.platform.business.service.BusiLogService;
import cn.rebornauto.platform.business.service.CollectionRepaymentSignService;
import cn.rebornauto.platform.business.service.OrderDetailService;
import cn.rebornauto.platform.business.service.PaymentChannelConfigService;
import cn.rebornauto.platform.business.service.SysConfigService;
import cn.rebornauto.platform.business.service.SysDicService;
import cn.rebornauto.platform.business.service.TransactionFlowService;
import cn.rebornauto.platform.business.vo.QuotaVO;
import cn.rebornauto.platform.common.Const;
import cn.rebornauto.platform.common.ReturnMsgConst;
import cn.rebornauto.platform.common.controller.BaseController;
import cn.rebornauto.platform.common.controller.ExportExcel;
import cn.rebornauto.platform.common.data.request.Pagination;
import cn.rebornauto.platform.common.data.request.Request;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.common.data.view.ResponseCode;
import cn.rebornauto.platform.common.data.view.TableBody;
import cn.rebornauto.platform.common.enums.CheckStatusEnum;
import cn.rebornauto.platform.common.enums.OrderStatusEnum;
import cn.rebornauto.platform.common.util.DateUtils;
import cn.rebornauto.platform.common.util.LogUtil;
import cn.rebornauto.platform.common.util.StringUtil;
import cn.rebornauto.platform.pay.sandpay.entity.SandInfo;
import cn.rebornauto.platform.pay.tonglian.Enum.PayStatusEnum;
import cn.rebornauto.platform.pay.tonglian.entity.TongLianInfo;
import cn.rebornauto.platform.pay.tonglian.utils.DateUtil;
import cn.rebornauto.platform.payment.service.OrderService;
import cn.rebornauto.platform.payment.service.impl.tonglianpay.QueryResultHandler;
import cn.rebornauto.platform.sys.entity.SysUser;

/** Title:  ????????????
 * Description: 
 * Company: 
 * @author kgc
 * @date Apr 29, 2019 3:20:21 PM
 */
@RestController
@RequestMapping("financialPayment")
public class FinancialPaymentController  extends BaseController {
	/**
	 * ?????????????????????????????????????????????????????????maximumPoolSize??????????????????????????????corePoolSize ?????????????????????
	 */
	private static int CORE_POOL_SIZE = 20;//cpu??????????????????????????????20?????????20??????????????????
	/**
	 * ?????????????????????????????????????????????????????????corePoolSize ????????????????????????
	 */
	private static int MAX_POOL_SIZE = 300;//??????
	/**
	 * ??????????????????????????????????????????????????????????????????????????????keepAliveTime ?????????????????????????????????
	 */
	private static int KEEPALIVE_TIME = 2;//??????
//	private final int QUEUE_CAPACITY = (CORE_POOL_SIZE + MAX_POOL_SIZE) / 2;
	/**
	 * ???????????????????????? keepAliveTime ???????????????NANOSECONDS, MICROSECONDS, MILLISECONDS, SECONDS, MINUTES, HOURS, DAYS???7??????????????????
	 */
	private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
	/**
	 * ?????????????????????????????????????????????????????????????????????????????????
	 */
	private BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();
	/**
	 * handler ????????????????????????????????????????????????????????????.
	 */
	private RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy();
	
	
	private ThreadPoolExecutor executorServicePool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEPALIVE_TIME, TIME_UNIT, workQueue, rejectedExecutionHandler);;
	

	@Autowired
	OrderService orderService;
	
	@Autowired
	OrderDetailService orderDetailService;
	
	@Autowired
	TransactionFlowService transactionFlowService;
	
	@Autowired
	CollectionRepaymentSignService collectionRepaymentSignService;
	
	@Autowired
	QueryResultHandler queryResultHandler;
	
	@Autowired
	PaymentChannelConfigService paymentChannelConfigService;
	
	@Autowired
	SysDicService sysDicService;
	
	@Autowired
	BusiLogService busiLogService;
	
	@Autowired
	SysConfigService sysConfigService;
	
	/**
     * ????????????-????????????
     *
     * @return
     */
    @PostMapping("/list")
    @RequiresPermissions("financialPayment:list")
    public Response list(@RequestBody Request<OrderForm, OrderQuery> request, HttpServletRequest req) {
        LogUtil.info("financialPaymentList");
    	Pagination pagination = request.getPagination();
        OrderQuery query = request.getQuery();

        TableBody body = TableBody.factory();
        body.setPagination(pagination);
        
        OrderForm form = request.getForm();
        setCurrUser(form);
        //??????????????????????????????true:????????? false:??????????????????
  		if(!isAdministrator(form.getUserCustomerId())){			
  			if (StringUtils.hasText(query.getCustomerId())) {
  				if (form.getUserCustomerId().equals(query.getCustomerId())) {
  					query.setCustomerId(form.getUserCustomerId());
  				} else {
  					query.setCustomerId(Const.NOTFOUND);
  				}
  			} else {
  				// ??????customerId ????????????
  				query.setCustomerId(form.getUserCustomerId());
  			}		
  		}
        //???????????????
        long rowcount = orderService.countQuery(query);
        pagination.setTotal(rowcount);
        //??????????????????
        List<OrderVO> list = orderService.pageQuery(pagination, query);
        body.setList(list);

//        body.putDictionary("dataStatus", dataStatusDic());
        return Response.ok().body(body).message(ReturnMsgConst.RETURN_000000.getMsg());
    }
    
    /**
     * ????????????-????????????
     *
     * @return
     */
    @PostMapping("/queryOrderDetailList")
    @RequiresPermissions("financialPayment:queryOrderDetailList")
    public Response queryOrderDetailList(@RequestBody Request<OrderForm, OrderDetailQuery> request, HttpServletRequest req) {
        Pagination pagination = request.getPagination();
        OrderDetailQuery query = request.getQuery();

        OrderDetailTable body = OrderDetailTable.factory();
        body.setPagination(pagination);
        
        OrderForm form = request.getForm();
        setCurrUser(form);
        if(query.getOrderId()==null) {
        	query.setOrderId(form.getOrderId());
        }
        //??????????????????????????????true:????????? false:??????????????????
  		if(!isAdministrator(form.getUserCustomerId())){			
  			if (StringUtils.hasText(query.getCustomerId())) {
  				if (form.getUserCustomerId().equals(query.getCustomerId())) {
  					query.setCustomerId(form.getUserCustomerId());
  				} else {
  					query.setCustomerId(Const.NOTFOUND);
  				}
  			} else {
  				// ??????customerId ????????????
  				query.setCustomerId(form.getUserCustomerId());
  			}		
  		}
        //???????????????
        long rowcount = orderDetailService.countDetailQuery(query);
        pagination.setTotal(rowcount);
        //??????????????????
        List<OrderDetailVO> list = orderDetailService.pageDetailQuery(pagination, query);
        body.setList(list);
        if(null!=list && list.size()>0) {
        	body.setTotalAmountQuery(list.get(0).getTotalAmountQuery());
        	body.setTotalAmountQueryFin(list.get(0).getTotalAmountQueryFin());
        	body.setTotalCountQuery(list.get(0).getTotalCountQuery());
        }else {
        	body.setTotalAmountQuery(BigDecimal.ZERO);
        	body.setTotalAmountQueryFin("0");
        	body.setTotalCountQuery(0);
        }
        

//        body.putDictionary("dataStatus", dataStatusDic());
        return Response.ok().body(body).message(ReturnMsgConst.RETURN_000000.getMsg());
    }
    
    /**
     * ????????????-????????????
     *
     * @return
     */
    @PostMapping("/queryTransflowList")
    @RequiresPermissions("financialPayment:queryTransflowList")
    public Response queryTransflowList(@RequestBody Request<OrderForm, TransactionListQuery> request, HttpServletRequest req) {
        Pagination pagination = request.getPagination();
        TransactionListQuery query = request.getQuery();

        TransactionFlowTable body = TransactionFlowTable.factory();
        body.setPagination(pagination);
        
        OrderForm form = request.getForm();
        setCurrUser(form);
        if(query.getOrderId()==null) {
        	query.setOrderId(form.getOrderId());
        }
        if(query.getPayStatus()==null) {
        	query.setPayStatus(form.getPayStatus());
        }
        //??????????????????????????????true:????????? false:??????????????????
  		if(!isAdministrator(form.getUserCustomerId())){			
  			if (StringUtils.hasText(query.getCustomerId())) {
  				if (form.getUserCustomerId().equals(query.getCustomerId())) {
  					query.setCustomerId(form.getUserCustomerId());
  				} else {
  					query.setCustomerId(Const.NOTFOUND);
  				}
  			} else {
  				// ??????customerId ????????????
  				query.setCustomerId(form.getUserCustomerId());
  			}		
  		}
  		
        //???????????????
        long rowcount = transactionFlowService.countQuery(query);
        pagination.setTotal(rowcount);
        //??????????????????
        List<TransactionFlowVO> list = transactionFlowService.pageQuery(pagination, query);
        body.setList(list);
        if(null!=list && list.size()>0) {
        	body.setTotalAmountQuery(list.get(0).getTotalAmountQuery());
        	body.setTotalAmountQueryFin(list.get(0).getTotalAmountQueryFin());
        	body.setTotalCountQuery(list.get(0).getTotalCountQuery());
        }else {
        	body.setTotalAmountQuery(BigDecimal.ZERO);
        	body.setTotalAmountQueryFin("0");
        	body.setTotalCountQuery(0);
        }

        return Response.ok().body(body).message(ReturnMsgConst.RETURN_000000.getMsg());
    }
    
    /**
     * ????????????-??????????????????
     *
     * @return
     */
    @PostMapping("/queryCollectionList")
    @RequiresPermissions("financialPayment:queryCollectionList")
    public Response queryCollectionList(@RequestBody Request<OrderForm, CollectionRepaymentSignQuery> request, HttpServletRequest req) {
        Pagination pagination = request.getPagination();
        CollectionRepaymentSignQuery query = request.getQuery();

        TableBody body = TableBody.factory();
        body.setPagination(pagination);
        
        OrderForm form = request.getForm();
        setCurrUser(form);
        //??????????????????????????????true:????????? false:??????????????????
  		if(!isAdministrator(form.getUserCustomerId())){			
  			if (StringUtils.hasText(query.getCustomerId())) {
  				if (form.getUserCustomerId().equals(query.getCustomerId())) {
  					query.setCustomerId(form.getUserCustomerId());
  				} else {
  					query.setCustomerId(Const.NOTFOUND);
  				}
  			} else {
  				// ??????customerId ????????????
  				query.setCustomerId(form.getUserCustomerId());
  			}		
  		}
        
        //???????????????
        long rowcount = collectionRepaymentSignService.countQuery(query);
        pagination.setTotal(rowcount);
        //??????????????????
        List<CollectionRepaymentSignVO> list = collectionRepaymentSignService.pageQuery(pagination, query);
        body.setList(list);

        return Response.ok().body(body).message(ReturnMsgConst.RETURN_000000.getMsg());
    }
    
    /**
     * ????????????
     * @param request
     * @return
     */
    @PostMapping("/order/add")
    @RequiresPermissions("financialPayment:order:add")
    public Response add(@RequestBody Request<OrderForm, OrderQuery> request, HttpServletRequest req) {
    	OrderForm form = request.getForm();
        setCurrUser(form);
        //?????????????????????
        boolean isAdmin = isAdministrator(form.getUserCustomerId());
        
        SysUser currentUser = currentUser();
        Response response = orderService.save(form,isAdmin);
        if (response.getCode()!=ResponseCode.SUCCESS.value()) {
        	//????????????
	        busiLogService.add(req, Const.busi_log_busi_type_7, Const.busi_log_option_type_fail,"??????????????????", currentUser);
            return response;
        }
        //????????????
        busiLogService.add(req, Const.busi_log_busi_type_7, Const.busi_log_option_type_success,"??????????????????", currentUser);
        return list(request,req);

    }
    
    
    /**
     * ????????????
     *
     * @param request
     * @return
     */
    @PostMapping("/singleUpload")
    @RequiresPermissions("financialPayment:singleUpload")
    public Response singleUpload(@RequestBody Request<OrderForm, OrderQuery> request, HttpServletRequest req) {
    	OrderForm form = request.getForm();
        setCurrUser(form);
        Order param = new Order();
		param.setId(form.getOrderId());
		param.setDataStatus(Const.DATA_STATUS_1);
		Order record = orderService.selectOne(param);
		
		SysUser currentUser = currentUser();
		//?????????????????????????????????????????????
		if(record.getCheckStatus()==Const.CHECK_STATUS_3||record.getCheckStatus()==Const.CHECK_STATUS_4) {
			return Response.error().message(ReturnMsgConst.RETURN_600013.getMsg());
		}else {
			//?????????????????????
	        boolean isAdmin = isAdministrator(form.getUserCustomerId());
			Response response= orderService.singleUpload(form,isAdmin);
	        if (response.getCode()!=ResponseCode.SUCCESS.value()) {
	        	//????????????
		        busiLogService.add(req, Const.busi_log_busi_type_7, Const.busi_log_option_type_fail,"????????????", currentUser);
	            return response;
	        }
	        //????????????
	        busiLogService.add(req, Const.busi_log_busi_type_7, Const.busi_log_option_type_success,"????????????", currentUser);
		}
        return list(request,req);
    }
    
    
    /**
     * ????????????
     *
     * @param request
     * @return
     */
    @PostMapping("/batchUpload")
    @RequiresPermissions("financialPayment:batchUpload")
    public Response batchUpload(@RequestBody Request<OrderForm, OrderQuery> request, HttpServletRequest req) {
    	OrderForm form = request.getForm();
        setCurrUser(form);
        System.out.println("??????excel>>>>>>>>>>>>>>>>>");
        Order param = new Order();
		param.setId(form.getOrderId());
		param.setDataStatus(Const.DATA_STATUS_1);
		Order record = orderService.selectOne(param);
		
		SysUser currentUser = currentUser();
		//?????????????????????????????????????????????
		if(record.getCheckStatus()==Const.CHECK_STATUS_3||record.getCheckStatus()==Const.CHECK_STATUS_4) {
			return Response.error().message(ReturnMsgConst.RETURN_600013.getMsg());
		}else {
			//?????????????????????
	        boolean isAdmin = isAdministrator(form.getUserCustomerId());
			Response response= orderService.batchUpload(form,isAdmin);
	        if (response.getCode()!=ResponseCode.SUCCESS.value()) {
	        	//????????????
		        busiLogService.add(req, Const.busi_log_busi_type_7, Const.busi_log_option_type_fail,"????????????", currentUser);
	            return response;
	        }
	        //????????????
	        busiLogService.add(req, Const.busi_log_busi_type_7, Const.busi_log_option_type_success,"????????????", currentUser);
		}
        return list(request,req);
    }
    
    /**
     * ????????????
     * @param request
     * @return
     */
    @PostMapping("payApply")
    @RequiresPermissions("financialPayment:payApply")
    public Response payApply(@RequestBody Request<OrderForm, OrderQuery> request, HttpServletRequest req) {
    	OrderForm form = request.getForm();
        setCurrUser(form);
        Order param = new Order();
		param.setId(form.getOrderId());
		param.setDataStatus(Const.DATA_STATUS_1);
		Order record = orderService.selectOne(param);
		
		SysUser currentUser = currentUser();
		//???????????????????????????????????????
		if(record.getCheckStatus()!=Const.CHECK_STATUS_2) {
			return Response.error().message(ReturnMsgConst.RETURN_600014.getMsg());
		}else {
			//?????????????????????
	        boolean isAdmin = isAdministrator(form.getUserCustomerId());
			Response response = orderService.payApply(form,isAdmin);
	        if (response.getCode()!=ResponseCode.SUCCESS.value()) {
	        	//????????????
		        busiLogService.add(req, Const.busi_log_busi_type_7, Const.busi_log_option_type_fail,"????????????", currentUser);
	            return response;
	        }
	        //????????????
	        busiLogService.add(req, Const.busi_log_busi_type_7, Const.busi_log_option_type_success,"????????????", currentUser);
		}
        return list(request,req);
    }
    
    
    
    /**
     * ????????????
     * @param request
     * @return
     */
    @PostMapping("payAgain")
    @RequiresPermissions("financialPayment:payAgain")
    public Response payAgain(@RequestBody Request<OrderForm, TransactionListQuery> request, HttpServletRequest req) {
    	OrderForm form = request.getForm();
        setCurrUser(form);
        
        //??????????????????????????????
        TransactionFlow transactionFlow = transactionFlowService.selectOne(form.getTransactionFlowId());
        if(null==transactionFlow || transactionFlow.getPayStatus()!=Integer.parseInt(PayStatusEnum.ERROR.getCode())) {
        	return Response.error().message(ReturnMsgConst.RETURN_600024.getMsg());
        }
        
        Order param = new Order();
		param.setId(form.getOrderId());
		param.setDataStatus(Const.DATA_STATUS_1);
		param.setCustomerId(form.getCustomerId());
		Order record = orderService.selectOne(param);
		//??????postman?????????????????????customer_id???orderId??????????????????????????????????????????????????????
		if(null==record) {
			return Response.error().message(ReturnMsgConst.RETURN_600030.getMsg());
		}
        //?????????????????????????????????????????????????????????
  		if(record.getCheckStatus()!=Const.CHECK_STATUS_4) {
  			return Response.error().message(ReturnMsgConst.RETURN_600018.getMsg());
  		}
        //????????????????????????????????????
		if(!orderService.isTongLianPayTime()) {
			return Response.error().message(ReturnMsgConst.RETURN_600017.getMsg());
		}
		SysUser currentUser = currentUser();
		//?????????????????? ??????????????????
        String sysPaySwitch = sysDicService.selectSysPaySwitch();
        //????????????????????????
        List<OrderDetailVO> orderDetailList = new ArrayList<OrderDetailVO>();
        OrderDetailVO vo = new OrderDetailVO();
        vo.setIdcardno(transactionFlow.getIdcardno());
        vo.setAmount(transactionFlow.getAmount());
        vo.setOrderId(transactionFlow.getOrderId());
        vo.setCustomerId(transactionFlow.getCustomerId());
        vo.setDaiZhengId(transactionFlow.getDaiZhengId());
        orderDetailList.add(vo);
        Response response= orderService.checkQuotaIsEnough(orderDetailList,transactionFlow.getCustomerId());
        if(response.getCode()==ResponseCode.SUCCESS.value()) {
        	QuotaVO quotaVO = (QuotaVO) response.getBody();
        	//????????????
	        int paymentChannelId = Const.SYS_PAYMENT_CHANNEL_TONGLIAN;//????????????
			String paymentChannel = sysConfigService.findValueByKey("paymentChannel");
			//????????????????????????
			if(org.apache.commons.lang.StringUtils.isNotBlank(paymentChannel)) {
				paymentChannelId = Integer.parseInt(paymentChannel);
			}
        	//????????????  ????????????
        	orderService.addQuotaPayAgain(quotaVO,currentUser,transactionFlow,paymentChannelId);
        	//????????????
            orderService.paymentAgain(form,sysPaySwitch,req,currentUser);
        }else {
        	return response;
        }
        return queryTransflowList(request,req);
    }
    
    
    
    
    
    /**
     * ??????????????????
     * @param request
     * @return
     */
    @PostMapping("queryTradeResult")
    @RequiresPermissions("financialPayment:queryTradeResult")
    public Response queryTradeResult(@RequestBody Request<TransactionFlowForm, TransactionListQuery> request, HttpServletRequest req) {
    	Response response= new Response();
    	TransactionFlowForm form = request.getForm();
        setCurrUser(form);
        List<TransactionFlowVO> mapList = transactionFlowService.selectRequestSnById(form.getTransactionFlowId());
        if(null==mapList || mapList.size()==0) {
        	return response.error().message(ReturnMsgConst.RETURN_600021.getMsg());
        }
        //????????????????????????????????????????????????
    	Date postTime = DateUtils.LocalDateTimeToUdate(mapList.get(0).getPostTime());
    	int orderId = mapList.get(0).getOrderId();
    	LogUtil.debug(orderId+" [queryStatus]postTime="+postTime);
    	if(null!=postTime) {//?????????
    		//??????????????????
    		long tonglianTime = postTime.getTime() + 60 * 60 * 1000;//???????????????
        	Date tlDate = new Date(tonglianTime);
        	
        	//?????????????????????1????????????????????????
        	if(DateUtil.compareDateTime(DateUtil.getTime(tlDate),DateUtil.getTime(new Date()))) {
        		return response.error().message(ReturnMsgConst.RETURN_600020.getMsg());
        	}
        	SysUser currentUser = currentUser();
        	//?????????????????? ??????????????????
	        String sysPaySwitch = sysDicService.selectSysPaySwitch();
        	TransactionFlow transactionFlow = transactionFlowService.selectOne(form.getTransactionFlowId());
        	if(transactionFlow.getPaymentChannelId()==Const.SYS_PAYMENT_CHANNEL_SAND) {
        		SandInfo sandInfo = paymentChannelConfigService.getSandInfo(transactionFlow.getPaymentChannelId(), sysPaySwitch);
            	//??????30?????????????????????
            	try {
            		response = queryResultHandler.queryResultSand(form.getTransactionFlowId(),sandInfo,req,currentUser,sysPaySwitch);
    			} catch (Exception e) {
    				e.printStackTrace();
    				//????????????
    		        busiLogService.add(req, Const.busi_log_busi_type_9, Const.busi_log_option_type_fail,"????????????????????????"+e.getMessage(), currentUser);
    		        return response.error().message(ReturnMsgConst.RETURN_600021.getMsg()+e.getMessage());
    			}
            }else if(transactionFlow.getPaymentChannelId()==Const.SYS_PAYMENT_CHANNEL_TONGLIAN){
            	TongLianInfo tongLianInfo = paymentChannelConfigService.getTongLianInfo(transactionFlow.getPaymentChannelId(), sysPaySwitch);
            	//??????30?????????????????????
            	try {
            		response = queryResultHandler.queryResultTongLian(form.getTransactionFlowId(),tongLianInfo,req,currentUser);
    			} catch (Exception e) {
    				e.printStackTrace();
    				//????????????
    		        busiLogService.add(req, Const.busi_log_busi_type_9, Const.busi_log_option_type_fail,"????????????????????????"+e.getMessage(), currentUser);
    		        return response.error().message(ReturnMsgConst.RETURN_600021.getMsg()+e.getMessage());
    			}
            }
    	}
    	return response;
    }
    
    /**
     * ??????????????????
     * @param request
     * @return
     */
    @PostMapping("updateTradeResult")
    @RequiresPermissions("financialPayment:updateTradeResult")
    public Response updateTradeResult(@RequestBody Request<OrderForm, TransactionListQuery> request, HttpServletRequest req) {
    	Response response= new Response();
    	OrderForm form = request.getForm();
        setCurrUser(form);
        List<TransactionFlowVO> mapList = transactionFlowService.selectRequestSnById(form.getTransactionFlowId());
        if(null==mapList || mapList.size()==0) {
        	return response.error().message(ReturnMsgConst.RETURN_600023.getMsg());
        }
        Date postTime = new Date();
        if(null!=mapList && mapList.size()>0) {
        	//????????????????????????????????????????????????
        	postTime = DateUtils.LocalDateTimeToUdate(mapList.get(0).getPostTime());
        }
        SysUser currentUser = currentUser();
    	int orderId = mapList.get(0).getOrderId();
    	LogUtil.debug(orderId+" [queryStatus]postTime="+postTime);
    	if(null!=postTime) {//?????????
    		//??????????????????
    		long tonglianTime = postTime.getTime() + 60 * 60 * 1000;//???????????????
        	Date tlDate = new Date(tonglianTime);
        	
        	//?????????????????????1????????????????????????
        	if(DateUtil.compareDateTime(DateUtil.getTime(tlDate),DateUtil.getTime(new Date()))) {
        		return response.error().message(ReturnMsgConst.RETURN_600020.getMsg());
        	}
        	
//            if(!PayStatusEnum.SUCCESS.getCode().equals(form.getStatus())&&!PayStatusEnum.ERROR.getCode().equals(form.getStatus()))
//            {
//            	response.error().message(ReturnMsgConst.RETURN_600022.getMsg());
//            }
            try {
            	response = queryResultHandler.updateResult(form,req,currentUser);
			} catch (Exception e) {
				e.printStackTrace();
				//????????????
		        busiLogService.add(req, Const.busi_log_busi_type_9, Const.busi_log_option_type_fail,"????????????????????????"+e.getMessage(), currentUser);
				
		        return response.error().message(ReturnMsgConst.RETURN_600023.getMsg()+e.getMessage());
			}
    	}
        return queryTransflowList(request,req);
    }
    
    
    /**
     * ??????????????????
     *
     * @param request
     * @return
     */
    @PostMapping("/cancelOrderDetail")
    @RequiresPermissions("financialPayment:cancelOrderDetail")
    public Response cancelOrderDetail(@RequestBody Request<OrderForm, OrderDetailQuery> request, HttpServletRequest req) {
    	OrderForm form = request.getForm();
        setCurrUser(form);
        Order param = new Order();
		param.setId(form.getOrderId());
		param.setDataStatus(Const.DATA_STATUS_1);
		
		SysUser currentUser = currentUser();
		Order record = orderService.selectOne(param);
		//??????????????????????????????????????????????????????????????????
		if(record.getCheckStatus()==Const.CHECK_STATUS_3 || record.getCheckStatus()==Const.CHECK_STATUS_4) {
			Response response = new Response();
			return response.error().message(ReturnMsgConst.RETURN_600025.getMsg());
		}else {
			//?????????????????????
	        boolean isAdmin = isAdministrator(form.getUserCustomerId());
			Response response= orderService.cancelOrderDetail(form,isAdmin);
	        if (response.getCode()!=ResponseCode.SUCCESS.value()) {
	        	//????????????
		        busiLogService.add(req, Const.busi_log_busi_type_7, Const.busi_log_option_type_fail,"??????????????????", currentUser);
	            return response;
	        }
	        //????????????
	        busiLogService.add(req, Const.busi_log_busi_type_7, Const.busi_log_option_type_success,"??????????????????", currentUser);
		}
        return queryOrderDetailList(request,req);
    }
    
    
    /**
     * ??????????????????
     * @param request
     * @return
     */
    @PostMapping("batchPayAgain")
    @RequiresPermissions("financialPayment:batchPayAgain")
    public Response batchPayAgain(@RequestBody Request<OrderForm, TransactionListQuery> request, HttpServletRequest req) {
    	OrderForm form = request.getForm();
        setCurrUser(form);
        
        Order param = new Order();
		param.setId(form.getOrderId());
		param.setDataStatus(Const.DATA_STATUS_1);
		Order record = orderService.selectOne(param);
        //?????????????????????????????????????????????????????????
  		if(record.getCheckStatus()!=Const.CHECK_STATUS_4) {
  			return Response.error().message(ReturnMsgConst.RETURN_600018.getMsg());
  		}
        //????????????????????????????????????
		if(!orderService.isTongLianPayTime()) {
			return Response.error().message(ReturnMsgConst.RETURN_600017.getMsg());
		}
		//?????????????????? ??????????????????
        String sysPaySwitch = sysDicService.selectSysPaySwitch();
        
    	//??????????????????????????????????????????
		List<String> transactionFlowIdList = transactionFlowService.selectPayErrorList(form.getOrderId());
//		//?????????????????????
//        boolean isAdmin = isAdministrator(form.getUserCustomerId());
        
        SysUser currentUser = currentUser();
		if(null!=transactionFlowIdList && transactionFlowIdList.size()>0) {
			List<OrderDetailVO> orderDetailList = new ArrayList<OrderDetailVO>();
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("ids", transactionFlowIdList.toArray());
			List<TransactionFlow> tFList = transactionFlowService.selectByTransactionFlowIds(params);
			for (int i = 0; i < tFList.size(); i++) {
				OrderDetailVO vo = new OrderDetailVO();
				vo.setIdcardno(tFList.get(i).getIdcardno());
		        vo.setAmount(tFList.get(i).getAmount());
		        vo.setOrderId(tFList.get(i).getOrderId());
		        vo.setCustomerId(tFList.get(i).getCustomerId());
		        vo.setDaiZhengId(tFList.get(i).getDaiZhengId());
		        orderDetailList.add(vo);
			}
			
			//????????????????????????
			Response response= orderService.checkQuotaIsEnough(orderDetailList,currentUser.getCustomerId());
	        if(response.getCode()==ResponseCode.SUCCESS.value()) {
	        	QuotaVO quotaVO = (QuotaVO) response.getBody();
	        	//????????????
		        int paymentChannelId = Const.SYS_PAYMENT_CHANNEL_TONGLIAN;//????????????
				String paymentChannel = sysConfigService.findValueByKey("paymentChannel");
				//????????????????????????
				if(org.apache.commons.lang.StringUtils.isNotBlank(paymentChannel)) {
					paymentChannelId = Integer.parseInt(paymentChannel);
				}
	        	//????????????  ????????????  ????????????????????????????????????
	        	orderService.batchPaymentAgainInit(quotaVO,currentUser,transactionFlowIdList,paymentChannelId);//???????????????????????????????????????????????????
				//??????????????????
				executorServicePool.allowCoreThreadTimeOut(true);
				executorServicePool.execute(new Thread(new Runnable() {
					@Override
					public void run() {
						for (int i = 0; i < transactionFlowIdList.size(); i++) {
							System.out.println("?????????????????????:"+Thread.currentThread().getName());
							orderService.batchPaymentAgain(form, sysPaySwitch,req,currentUser,transactionFlowIdList.get(i));
						}
					}
				}));
				Response.ok().message(ReturnMsgConst.RETURN_600006.getMsg());
	        }else {
	        	return response;
	        }
		}else {
			return Response.error().message(ReturnMsgConst.RETURN_600007.getMsg());
		}
        return queryTransflowList(request,req);
    }
    
    
    /**
     * ?????????excel
     * @param response
     * @param page
     * @return
     */
    @SuppressWarnings("all")
    @GetMapping("/exportPayErrorData")
    public String exportPayErrorData(HttpServletResponse response, HttpServletRequest request){
    	String orderIdStr = request.getParameter("orderId");
    	Integer orderId = Integer.parseInt(orderIdStr);
        LogUtil.info("??????????????????");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
        try{
            response.setCharacterEncoding("UTF-8");
            String filedisplay = "??????????????????.xls";
            filedisplay = URLEncoder.encode(filedisplay, "UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename="+ filedisplay);
            response.setContentType("application/x-download");


            //??????????????????????????????
            List<OrderDetailVO> list = orderDetailService.exportPayErrorDataExcel(orderId);
            

            List<Object[]> dataList=new ArrayList<Object[]>();
            for(int i=0;i<list.size();i++){
//            	int s10 = i+1;
            	String s0 = list.get(i).getPolicyNo();
            	String s1= StringUtil.nullToString(DateUtils.localDateTimeToYYYYMMDD(list.get(i).getOuttime()));
            	String s2= list.get(i).getPolicyPerson();
                String s3= StringUtil.nullToString(list.get(i).getPolicyAmount());
                String s4= list.get(i).getAgentName();
                String s5= list.get(i).getOpenBankNo();
//                String s6= list.get(i).getIdcardno();
                String s7= StringUtil.nullToString(list.get(i).getAmount());
                String s8= list.get(i).getPayStatusResult();
                
                Object[] datas=new Object[]{s0,s1,s2,s3,s4,s5,s7,s8};
                dataList.add(datas);
            }
            String [] rowName = new String[] {
//            		"??????",
            		"?????????",
                    "????????????",
                    "??????",
                    "????????????",
                    "???????????????",
                    "??????????????????",
//                    "??????????????????",
                    "????????????",
                    "????????????"
                    
            };
            ExportExcel exportExcel = new ExportExcel(response,"??????????????????",rowName,dataList);
            exportExcel.exportWithoutNo();
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
        return null;
    }
    
    
    /**
     * ????????????
     * @param request
     * @return
     */
    @PostMapping("closeTransactionFlow")
    @RequiresPermissions("financialPayment:closeTransactionFlow")
    public Response closeTransactionFlow(@RequestBody Request<OrderForm, TransactionListQuery> request, HttpServletRequest req) {
    	Response response= new Response();
    	OrderForm form = request.getForm();
        setCurrUser(form);
        
        //??????????????????????????????
        TransactionFlow transactionFlow = transactionFlowService.selectOne(form.getTransactionFlowId());
        if(null==transactionFlow || transactionFlow.getPayStatus()!=Integer.parseInt(PayStatusEnum.ERROR.getCode())) {
        	return response.error().message(ReturnMsgConst.RETURN_600024.getMsg());
        }
        
        transactionFlowService.closeTransactionFlow(transactionFlow);
        return queryTransflowList(request,req);
    }
    
    
    /**
     * ????????????
     * @param request
     * @return
     */
    @PostMapping("closeOrder")
    @RequiresPermissions("financialPayment:closeOrder")
    public Response closeOrder(@RequestBody Request<OrderForm, OrderQuery> request, HttpServletRequest req) {
    	OrderForm form = request.getForm();
        setCurrUser(form);
        
        //??????????????????????????????
        Order order = orderService.selectByPrimaryKey(form.getId());
        if(order.getCheckStatus()==Const.CHECK_STATUS_4) {
        	return Response.error().message(ReturnMsgConst.RETURN_600048.getMsg());
        }
        
        orderService.closeOrder(form.getId());
        return list(request,req);
    }
    
    /**
     * ????????????????????????
     * @param request
     * @param req
     * @return
     */
    @GetMapping("/getNoticeMsg")
    @ResponseBody
    public Response getNoticeMsg() {
    	//?????????????????? ??????????????????
        String sysPaySwitch = sysDicService.selectSysPaySwitch();
    	//????????????
        String key = "noticeMsg";
    	String value = sysConfigService.findValueByKey(key,sysPaySwitch);
        return Response.ok().body(value);
    }
    
    
    /**
	 * ????????????????????????
	 * @param response
	 * @param request
	 * @return
	 */
	@SuppressWarnings("all")
    @GetMapping("/exportPaySuccessData")
    public String exportPaySuccessData(HttpServletResponse response, HttpServletRequest request){
		String orderId = request.getParameter("orderId");
		String customerId = request.getParameter("customerId");
    	String createtime = request.getParameter("createtime");
    	OrderQuery query = new OrderQuery();
    	query.setCreatetime(createtime);
    	if(org.apache.commons.lang.StringUtils.isBlank(createtime)) {
    		LogUtil.info("??????????????????!");
    		return "??????????????????!";
    	}else{
    		if(DateUtil.getDaySub(query.getStartTime(), query.getEndTime())>31) {
    			LogUtil.info("?????????????????????????????????!");
        		return "?????????????????????????????????!";
    		}
    	}
    	query.setCheckStatus(String.valueOf(Const.CHECK_STATUS_4));
    	
    	String title = "??????????????????";
    	
        LogUtil.info("?????? "+title);
        try{
            response.setCharacterEncoding("UTF-8");
            String filedisplay = title+".xls";
            filedisplay = URLEncoder.encode(filedisplay, "UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename="+ filedisplay);
            response.setContentType("application/x-download");
            
            
            //?????????????????????
            SysUser sysUser = currentUser();
            //???????????????????????????
            boolean administrator = isAdministrator(sysUser.getCustomerId());
            if (!administrator) {
                //???????????????????????????????????????????????????
                query.setCustomerId(sysUser.getCustomerId());
            }

            List<OrderVO> statList = orderService.selectPaySuccessExcelByQuery(query);
            
            List<Object[]> dataList=new ArrayList<Object[]>();
            for(int i=0;i<statList.size();i++){
            	String s1 = String.valueOf(statList.get(i).getOrderId());
            	String s2= 	DateUtils.LocalDateTimeToString(statList.get(i).getCreatetime());
            	String s3 = String.valueOf(statList.get(i).getAgentCommissionFin());
            	String s4 = OrderStatusEnum.getName(statList.get(i).getOrderStatus());
            	String s5 = String.valueOf(statList.get(i).getTotalCount());
            	String s6 = CheckStatusEnum.getName(statList.get(i).getCheckStatus());
            	String s7 = DateUtils.LocalDateTimeToString(statList.get(i).getChecktime());
            	String s8 = String.valueOf(statList.get(i).getCheckoper());
            	String s9 = String.valueOf(statList.get(i).getRemark());
            	String s10 = String.valueOf(statList.get(i).getCreateoper());
            	String s11 = statList.get(i).getCustomerId();
            	String s12 = statList.get(i).getCustomerName();
            	
                
                Object[] datas=new Object[]{s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12};
                dataList.add(datas);
            }
            String [] rowName = new String[] {
            		"?????????",
            		"????????????",
            		"???????????????",
                    "????????????",
                    "???????????????",
                    "????????????",
                    "????????????",
                    "?????????",
                    "????????????",
                    "?????????",
                    "????????????",
                    "????????????"
            };
            ExportExcel exportExcel = new ExportExcel(response,title,rowName,dataList);
            exportExcel.export();
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
        return null;
    }
  
    
}
