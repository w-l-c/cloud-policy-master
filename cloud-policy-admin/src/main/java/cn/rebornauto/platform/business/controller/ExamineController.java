package cn.rebornauto.platform.business.controller;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.rebornauto.platform.business.entity.Order;
import cn.rebornauto.platform.business.entity.OrderDetailVO;
import cn.rebornauto.platform.business.entity.OrderVO;
import cn.rebornauto.platform.business.form.OrderForm;
import cn.rebornauto.platform.business.query.OrderQuery;
import cn.rebornauto.platform.business.service.BusiLogService;
import cn.rebornauto.platform.business.service.OrderDetailMergeService;
import cn.rebornauto.platform.business.service.OrderDetailService;
import cn.rebornauto.platform.business.service.SysConfigService;
import cn.rebornauto.platform.business.service.SysDicService;
import cn.rebornauto.platform.business.service.TransactionFlowService;
import cn.rebornauto.platform.business.vo.QuotaVO;
import cn.rebornauto.platform.common.Const;
import cn.rebornauto.platform.common.ReturnMsgConst;
import cn.rebornauto.platform.common.controller.BaseController;
import cn.rebornauto.platform.common.data.request.Pagination;
import cn.rebornauto.platform.common.data.request.Request;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.common.data.view.ResponseCode;
import cn.rebornauto.platform.common.data.view.TableBody;
import cn.rebornauto.platform.common.util.LogUtil;
import cn.rebornauto.platform.payment.service.OrderService;
import cn.rebornauto.platform.payment.service.impl.tonglianpay.PaymentHandler;
import cn.rebornauto.platform.sys.entity.SysUser;

/** Title:  财务审核付款
 * Description: 
 * Company: 
 * @author kgc
 * @date Apr 29, 2019 3:20:21 PM
 */
@RestController
@RequestMapping("examine")
public class ExamineController  extends BaseController {
	
	/**
	 * 核心线程数，指保留的线程池大小（不超过maximumPoolSize值时，线程池中最多有corePoolSize 个线程工作）。
	 */
	private static int CORE_POOL_SIZE = 20;//cpu几核就几个一起处理，20相当于20核，很牛逼了
	/**
	 * 指的是线程池的最大大小（线程池中最大有corePoolSize 个线程可运行）。
	 */
	private static int MAX_POOL_SIZE = 300;//默认
	/**
	 * 指的是空闲线程结束的超时时间（当一个线程不工作时，过keepAliveTime 长时间将停止该线程）。
	 */
	private static int KEEPALIVE_TIME = 2;//默认
//	private final int QUEUE_CAPACITY = (CORE_POOL_SIZE + MAX_POOL_SIZE) / 2;
	/**
	 * 是一个枚举，表示 keepAliveTime 的单位（有NANOSECONDS, MICROSECONDS, MILLISECONDS, SECONDS, MINUTES, HOURS, DAYS，7个可选值）。
	 */
	private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
	/**
	 * 表示存放任务的队列（存放需要被线程池执行的线程队列）。
	 */
	private BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();
	/**
	 * handler 拒绝策略（添加任务失败后如何处理该任务）.
	 */
	private RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy();
	
	
	private ThreadPoolExecutor executorServicePool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEPALIVE_TIME, TIME_UNIT, workQueue, rejectedExecutionHandler);;
	

	@Autowired
	OrderService orderService;
	
	@Autowired
	OrderDetailService orderDetailService;
	
	@Autowired
	SysDicService sysDicService;
	
	@Autowired
	BusiLogService busiLogService;
	
	@Autowired
	TransactionFlowService transactionFlowService;
	
	@Autowired
	PaymentHandler paymentHandler;
	
	@Autowired
	OrderDetailMergeService orderDetailMergeService;
	
	@Autowired
	SysConfigService sysConfigService;
	/**
     * 分页查询
     *
     * @return
     */
    @PostMapping("/list")
    @RequiresPermissions("examine:list")
    public Response list(@RequestBody Request<OrderForm, OrderQuery> request, HttpServletRequest req) {
        Pagination pagination = request.getPagination();
        OrderQuery query = request.getQuery();
        
        query.setCheckStatus(String.valueOf(Const.CHECK_STATUS_3));

        TableBody body = TableBody.factory();
        body.setPagination(pagination);
        
        OrderForm form = request.getForm();
        setCurrUser(form);
        //判断是否是管理员账号true:管理员 false:其他客户账号
  		if(!isAdministrator(form.getUserCustomerId())){			
  			if (StringUtils.hasText(query.getCustomerId())) {
  				if (form.getUserCustomerId().equals(query.getCustomerId())) {
  					query.setCustomerId(form.getUserCustomerId());
  				} else {
  					query.setCustomerId(Const.NOTFOUND);
  				}
  			} else {
  				// 获取customerId 数据隔离
  				query.setCustomerId(form.getUserCustomerId());
  			}		
  		}
        //获取总条数
        long rowcount = orderService.countQuery(query);
        pagination.setTotal(rowcount);
        //获取分页数据
        List<OrderVO> list = orderService.pageQuery(pagination, query);
        body.setList(list);

//        body.putDictionary("dataStatus", dataStatusDic());
        return Response.ok().body(body).message(ReturnMsgConst.RETURN_000000.getMsg());
    }
    
    
    /**
     * 审核付款
     *
     * @param request
     * @return
     */
    @PostMapping("/payment")
    @RequiresPermissions("examine:payment")
    public Response examinePayment(@RequestBody Request<OrderForm, OrderQuery> request, HttpServletRequest req) {
    	long startTime = System.currentTimeMillis();
    	OrderForm form = request.getForm();
        setCurrUser(form);
        Order param = new Order();
		param.setId(form.getOrderId());
		param.setDataStatus(Const.DATA_STATUS_1);
		param.setCustomerId(form.getUserCustomerId());
		Order record = orderService.selectOne(param);
		//防止postman直接调用，增加customer_id和orderId的关系，必须是客户下的代理人才能打款
		if(null==record) {
			return Response.error().message(ReturnMsgConst.RETURN_600030.getMsg());
		}
		
		//判断是否管理员
        boolean isAdmin = isAdministrator(form.getUserCustomerId());
        
        SysUser currentUser = currentUser();
		//如果不是待审核状态，不可提交审核放款
		if(record.getCheckStatus()!=Const.CHECK_STATUS_3) {
			return Response.error().message(ReturnMsgConst.RETURN_600015.getMsg());
		}else {
			//检查是否通联支付时间段内
			if(!orderService.isTongLianPayTime()) {
				return Response.error().message(ReturnMsgConst.RETURN_600017.getMsg());
			}else {
				//支付环境开关 测试或者生产
		        String sysPaySwitch = sysDicService.selectSysPaySwitch();
		        
		        //是否需要合并   yes需要合并，no不需要合并   
		        String isMerge = sysDicService.selectIsMerge();
		        
		        //支付渠道
		        int paymentChannelId = Const.SYS_PAYMENT_CHANNEL_TONGLIAN;//默认通联
				String paymentChannel = sysConfigService.findValueByKey("paymentChannel");
				//获取支付通道配置
				if(org.apache.commons.lang.StringUtils.isNotBlank(paymentChannel)) {
					paymentChannelId = Integer.parseInt(paymentChannel);
				}
		        //设置支付渠道
		        form.setPaymentChannelId(paymentChannelId);
		        
				//操作日志
		        busiLogService.add(req, Const.busi_log_busi_type_8, Const.busi_log_option_type_tongguo,"审核通过", currentUser);
		        
		        //查找需要支付并且未支付的订单明细数据
		    	List<OrderDetailVO> orderDetailVOList =  orderDetailService.selectUnpayOrderInfoByOrderId(form.getOrderId());
		    	if(null!=orderDetailVOList && orderDetailVOList.size()>0) {
		    		//检查额度是否足够
		    		String customerId = form.getUserCustomerId();
		    		if(isAdmin) {
		    			customerId = form.getCustomerId();
		    		}
		    		Response resp= orderService.checkQuotaIsEnough(orderDetailVOList,customerId);
		            if(resp.getCode()==ResponseCode.SUCCESS.value()) {
		            	LogUtil.info("修改审核信息");
						orderService.updateCheckinfoByOrderId(form.getOrderId(),form.getCurrUserName(),Const.CHECK_STATUS_4,"审核通过");
						
						//创建合并数据和支付表初始化
						QuotaVO quotaVO = (QuotaVO) resp.getBody();
	            		resp = orderService.createMergeAndTransflowAddQuota(orderDetailVOList, isMerge, sysPaySwitch, currentUser, form,quotaVO);
	            		long execTime = System.currentTimeMillis();
	            		LogUtil.info("===========初始化数据耗时:"+(execTime-startTime)/1000+"秒");
	            		if(resp.getCode()==ResponseCode.SUCCESS.value()) {
	            			List<String> transactionFlowIdList = (List<String>) resp.getBody();
	            			//异步支付处理
							executorServicePool.allowCoreThreadTimeOut(true);
							executorServicePool.execute(new Thread(new Runnable() {
								@Override
								public void run() {
									for (int i = 0; i < transactionFlowIdList.size(); i++) {
										System.out.println("线程名:"+Thread.currentThread().getName());
					        			//额度足够时发起支付
										orderService.examinePayment(form,sysPaySwitch,req,currentUser,transactionFlowIdList.get(i));
									}
								}
							}));
	            		}else {
	            			return Response.error().message(resp.getMessage());
	            		}
		            }else {
		            	 return Response.error().message(resp.getMessage());
		            }
		    	}else {
		    		return Response.error().message(ReturnMsgConst.RETURN_600007.getMsg());
		    	}
			}
		}
        return list(request,req);
    }
    
    
    /**
     * 审核付款
     *
     * @param request
     * @return
     */
    @PostMapping("/refuse")
    @RequiresPermissions("examine:refuse")
    public Response refuse(@RequestBody Request<OrderForm, OrderQuery> request, HttpServletRequest req) {
    	Response response= new Response();
    	OrderForm form = request.getForm();
        setCurrUser(form);
        Order param = new Order();
		param.setId(form.getOrderId());
		param.setDataStatus(Const.DATA_STATUS_1);
		Order record = orderService.selectOne(param);
		//如果不是待审核状态，不可提交审核放款
		if(record.getCheckStatus()!=Const.CHECK_STATUS_3) {
			return response.error().message(ReturnMsgConst.RETURN_600015.getMsg());
		}else {
			//检查是否通联支付时间段内
			if(!orderService.isTongLianPayTime()) {
				return response.error().message(ReturnMsgConst.RETURN_600017.getMsg());
			}else {
				LogUtil.debug("修改审核信息");
				orderService.updateCheckinfoByOrderId(form.getOrderId(),form.getCurrUserName(),Const.CHECK_STATUS_5,form.getRemark());
				response.ok();
			}
		}
        return list(request,req);
    }
}
