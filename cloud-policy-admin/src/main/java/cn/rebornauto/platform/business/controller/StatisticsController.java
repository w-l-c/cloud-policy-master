package cn.rebornauto.platform.business.controller;

import cn.rebornauto.platform.business.entity.TransactionFlowTable;
import cn.rebornauto.platform.business.entity.TransactionFlowVO;
import cn.rebornauto.platform.business.form.OrderForm;
import cn.rebornauto.platform.business.form.RechargeForm;
import cn.rebornauto.platform.business.form.StatForm;
import cn.rebornauto.platform.business.query.RechargeQuery;
import cn.rebornauto.platform.business.query.StatQuery;
import cn.rebornauto.platform.business.query.TransactionListQuery;
import cn.rebornauto.platform.business.service.RechargeService;
import cn.rebornauto.platform.business.service.StatService;
import cn.rebornauto.platform.business.service.SysConfigService;
import cn.rebornauto.platform.business.service.TransactionFlowService;
import cn.rebornauto.platform.business.vo.*;
import cn.rebornauto.platform.common.Const;
import cn.rebornauto.platform.common.ReturnMsgConst;
import cn.rebornauto.platform.common.controller.BaseController;
import cn.rebornauto.platform.common.controller.ExportExcel;
import cn.rebornauto.platform.common.data.request.Form;
import cn.rebornauto.platform.common.data.request.Pagination;
import cn.rebornauto.platform.common.data.request.Request;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.common.data.view.TableBody;
import cn.rebornauto.platform.common.util.DateUtils;
import cn.rebornauto.platform.common.util.LogUtil;
import cn.rebornauto.platform.common.util.TuoMinUtil;
import cn.rebornauto.platform.pay.tonglian.Enum.PayStatusEnum;
import cn.rebornauto.platform.pay.tonglian.utils.DateUtil;
import cn.rebornauto.platform.sys.entity.SysUser;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/** Title: ????????????
 * Description: 
 * Company: 
 * @author kgc
 * @date Apr 2, 2020 10:46:40 AM
 */
@RestController
@RequestMapping("/stat")
public class StatisticsController extends BaseController{

	protected static Logger logger = LoggerFactory.getLogger(StatisticsController.class);
	
	@Autowired
	StatService statService;
	
	@Autowired
	TransactionFlowService transactionFlowService;
	
	@Autowired
	RechargeService rechargeService;
	
	@Autowired
	SysConfigService sysConfigService;
	/**
	 * ??????????????????   ?????????
	 * @param request
	 * @return
	 */
	@RequestMapping("/rechargeStat")
	@RequiresPermissions("stat:rechargeStat")
    public Response rechargeStat(@RequestBody @Valid Request<StatForm, StatQuery> request) {
        try {
        	StatQuery query = request.getQuery();
            List<Stat> rechargeStatList = statService.rechargeStat(query);
            return Response.ok().body(rechargeStatList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
            return Response.error().message(e.getMessage());
        }
    }
	
	
	/**
	 * ??????????????????   ?????????
	 * @param request
	 * @return
	 */
	@RequestMapping("/paymentStat")
	@RequiresPermissions("stat:paymentStat")
    public Response paymentStat(@RequestBody @Valid Request<StatForm, StatQuery> request) {
        try {
        	StatQuery query = request.getQuery();
            List<Stat> rechargeStatList = statService.paymentStat(query);
            return Response.ok().body(rechargeStatList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
            return Response.error().message(e.getMessage());
        }
    }
	
	/**
	 * ??????????????????     ????????????
	 * @param request
	 * @return
	 */
	@RequestMapping("/rechargeMoneyOrder")
	@RequiresPermissions("stat:rechargeMoneyOrder")
    public Response rechargeMoneyOrder(@RequestBody @Valid Request<StatForm, StatQuery> request) {
        try {
        	StatQuery query = request.getQuery();
            List<StatMoneyOrder> rechargeStatList = statService.rechargeMoneyOrder(query);
            return Response.ok().body(rechargeStatList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
            return Response.error().message(e.getMessage());
        }
    }
	
	
	/**
	 * ??????????????????    ????????????
	 * @param request
	 * @return
	 */
	@RequestMapping("/paymentMoneyOrder")
	@RequiresPermissions("stat:paymentMoneyOrder")
    public Response paymentMoneyOrder(@RequestBody @Valid Request<StatForm, StatQuery> request) {
        try {
        	StatQuery query = request.getQuery();
            List<StatMoneyOrder> rechargeStatList = statService.paymentMoneyOrder(query);
            return Response.ok().body(rechargeStatList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
            return Response.error().message(e.getMessage());
        }
    }
	
	/**
	 * ???????????????????????????
	 * @param request
	 * @return
	 */
	@RequestMapping("/customerStat")
	@RequiresPermissions("stat:customerStat")
    public Response customerStat(@RequestBody @Valid Request<StatForm, StatQuery> request) {
        try {
        	StatQuery query = request.getQuery();
        	List<StatCustomerVO> statList = statService.selectStatCustomerByQuery(query);
            return Response.ok().body(statList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
            return Response.error().message(e.getMessage());
        }
    }
	
	/**
     * ???????????????????????????  ?????????excel
     * @param response
     * @param page
     * @return
     */
    @SuppressWarnings("all")
    @GetMapping("/exportCustomerStatData")
    public String exportCustomerStatData(HttpServletResponse response, HttpServletRequest request){
    	String queryTime = request.getParameter("queryTime");
    	
    	StatQuery query = new StatQuery();
    	query.setQueryTime(queryTime);
    	
        LogUtil.info("?????? ??????????????????????????? ??????");
        try{
            response.setCharacterEncoding("UTF-8");
            String filedisplay = "?????????????????????????????????.xls";
            filedisplay = URLEncoder.encode(filedisplay, "UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename="+ filedisplay);
            response.setContentType("application/x-download");

            List<StatCustomerVO> statList = statService.selectStatCustomerByQuery(query);
            
            List<Object[]> dataList=new ArrayList<Object[]>();
            for(int i=0;i<statList.size();i++){
            	String s1 = statList.get(i).getCustomerId();
            	String s2= statList.get(i).getCustomerName();
            	BigDecimal s3 = statList.get(i).getRealPayment();
            	BigDecimal s4 = statList.get(i).getAgentCommission();
            	BigDecimal s5 = statList.get(i).getPayAmount();
            	int s6 = statList.get(i).getPayCount();
            	BigDecimal s7 = statList.get(i).getAvgAmount();
            	BigDecimal s8 = statList.get(i).getLeftAmount();
            	BigDecimal s9 = statList.get(i).getApplyAmount();
            	
                
                Object[] datas=new Object[]{s1,s2,s3,s4,s5,s6,s7,s8,s9};
                dataList.add(datas);
            }
            String [] rowName = new String[] {
            		"?????????",
            		"????????????",
                    "??????????????????",
                    "??????????????????",
                    "????????????",
                    "????????????",
                    "???????????????",
                    "??????",
                    "????????????"
            };
            ExportExcel exportExcel = new ExportExcel(response,"?????????????????????????????????",rowName,dataList);
            exportExcel.export();
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
        return null;
    }
    
    
    /**
	 * ??????????????????
	 * @param request
	 * @return
	 */
	@RequestMapping("/monthStat")
	@RequiresPermissions("stat:monthStat")
    public Response monthStat(@RequestBody @Valid Request<Form, StatQuery> request) {
		Pagination pagination = request.getPagination();
		StatQuery query = request.getQuery();
		
		//????????????
		String daiZhengArea = sysConfigService.findValueByKey("daiZhengArea");
		query.setDaiZhengArea(daiZhengArea);
		if(StringUtils.isBlank(query.getCompleteTime())) {
			return Response.error().message("????????? ????????????!");
		}
        
        TableBody body = TableBody.factory();
        
        int rowcount = statService.monthStatCount(query);
        pagination.setTotal(rowcount);
        body.setPagination(pagination);
        List<MonthStatVO> list = statService.selectMonthStatList(query,pagination);
        body.setList(list);
        return Response.ok().body(body);
    }
	
	
	/**
     * ??????????????????  ?????????excel
     * @param response
     * @param page
     * @return
     */
    @SuppressWarnings("all")
    @GetMapping("/exportMonthStat")
    public String exportMonthStat(HttpServletResponse response, HttpServletRequest request){
    	String completeTime = request.getParameter("completeTime");
    	if(StringUtils.isBlank(completeTime)) {
    		logger.info("????????? ????????????!");
		}else {
			StatQuery query = new StatQuery();
	    	query.setCompleteTime(completeTime);
	    	
	    	//????????????
			String daiZhengArea = sysConfigService.findValueByKey("daiZhengArea");
			query.setDaiZhengArea(daiZhengArea);
	    	
	        LogUtil.info("?????? ?????????????????? ??????");
	        try{
	            response.setCharacterEncoding("UTF-8");
	            String filedisplay = "??????????????????.xls";
	            filedisplay = URLEncoder.encode(filedisplay, "UTF-8");
	            response.addHeader("Content-Disposition", "attachment;filename="+ filedisplay);
	            response.setContentType("application/x-download");

	            List<MonthStatVO> statList = statService.excelMonthStatList(query);
	            
	            List<Object[]> dataList=new ArrayList<Object[]>();
	            for(int i=0;i<statList.size();i++){
	            	int s0 = i+1;
	            	String s1 = statList.get(i).getCustomerName();
	            	String s2= DateUtils.LocalDateTimeToString(statList.get(i).getCompleteTime());
	            	String s3 = statList.get(i).getAgentName();
	            	String s4 = statList.get(i).getIdType();
	            	String s5 = statList.get(i).getAgentIdcardno();
	            	String s6 = statList.get(i).getCardType();
	            	String s7 = statList.get(i).getAgentMobile();
	            	String s8 = statList.get(i).getDaiZheng();
	            	String s9 = statList.get(i).getAmount();
	            	String s10 = statList.get(i).getTaxableIncomeRate();
	            	String s11 = statList.get(i).getTaxYiju();
	            	String s12 = statList.get(i).getTaxRate();
	            	String s13 = statList.get(i).getQcNumber();
	            	String s14 = statList.get(i).getTaxPayable();
	            	String s15 = statList.get(i).getAccumulatedTaxPaid();
	            	String s16 = statList.get(i).getTaxRefundable();
	            	
	                
	                Object[] datas=new Object[]{s0,s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12,s13,s14,s15,s16};
	                dataList.add(datas);
	            }
	            String [] rowName = new String[] {
	            		"??????",
	            		"????????????",
	            		"????????????",
	                    "??????",
	                    "??????????????????",
	                    "??????????????????",
	                    "??????????????????",
	                    "????????????",
	                    "???????????????????????????",
	                    "????????????",
	                    "???????????????",
	                    "????????????",
	                    "??????",
	                    "???????????????",
	                    "????????????",
	                    "?????????????????????",
	                    "?????????????????????"
	            };
	            ExportExcel exportExcel = new ExportExcel(response,"??????????????????",rowName,dataList);
	            exportExcel.export();
	        } catch(Exception e){
	            logger.error(e.toString(), e);
	        }
		}
        return null;
    }
    
    
    /**
	 * ??????????????????
	 * @param request
	 * @return
	 */
	@RequestMapping("/transflowStat")
	@RequiresPermissions("stat:transflowStat")
	public Response transflowStat(@RequestBody Request<OrderForm, TransactionListQuery> request, HttpServletRequest req) {
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
  			if (StringUtils.isNotBlank(query.getCustomerId())) {
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
  		TransactionFlowVO countVO = transactionFlowService.selectTransactionFlowCountByQuery(query);
        pagination.setTotal(countVO.getCount());
        //??????????????????
        List<TransactionFlowVO> list = transactionFlowService.selectTransactionFlowListByQuery(pagination, query);
        if(null!=list && list.size()>0) {
        	//??????????????????????????????true:????????? false:??????????????????
      		if(!isAdministrator(form.getUserCustomerId())){		
      			for (int i = 0; i < list.size(); i++) {
    				list.get(i).setOpenBankNo(TuoMinUtil.bankNoEncrypt(list.get(i).getOpenBankNo()));
    				list.get(i).setIdcardno(TuoMinUtil.idcardEncrypt(list.get(i).getIdcardno()));
    			}
      		}
        }
        body.setList(list);
        if(null!=list && list.size()>0) {
        	body.setTotalAmountQueryFin(countVO.getAmountFin());
        	body.setTotalCountQuery(countVO.getCount());
        }else {
        	body.setTotalAmountQueryFin("0");
        	body.setTotalCountQuery(0);
        }

        return Response.ok().body(body).message(ReturnMsgConst.RETURN_000000.getMsg());
    }
	
	/**
	 * ??????????????????
	 * @param response
	 * @param request
	 * @return
	 */
	@SuppressWarnings("all")
    @GetMapping("/exportTransflowStat")
    public String exportTransflowStat(HttpServletResponse response, HttpServletRequest request){
    	String completeTime = request.getParameter("completeTime");
    	TransactionListQuery query = new TransactionListQuery();
    	query.setCompleteTime(completeTime);

		OrderForm form = new OrderForm();
		setCurrUser(form);
		if(!isAdministrator(form.getUserCustomerId())){
			if (StringUtils.isNotBlank(query.getCustomerId())) {
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
        LogUtil.info("?????? ???????????? ??????");
        try{
            response.setCharacterEncoding("UTF-8");
            String filedisplay = "??????????????????.xls";
            filedisplay = URLEncoder.encode(filedisplay, "UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename="+ filedisplay);
            response.setContentType("application/x-download");

            List<TransactionFlowVO> statList = transactionFlowService.selectTransactionFlowExcelByQuery(query);
            
            List<Object[]> dataList=new ArrayList<Object[]>();
            for(int i=0;i<statList.size();i++){
            	int s0 = i+1;
            	String s1 = statList.get(i).getCustomerName();
            	String s2= 	statList.get(i).getBatchNo();
            	String s3 = statList.get(i).getAgentName();
            	String s4 = statList.get(i).getOpenBankNo();
            	String s5 = statList.get(i).getAmountFin();
            	String s6 = DateUtils.LocalDateTimeToString(statList.get(i).getCompleteTime());
            	String s7 = PayStatusEnum.getMsg(String.valueOf(statList.get(i).getPayStatus()));
            	String s8 = statList.get(i).getCreateoper();
            	
                
                Object[] datas=new Object[]{s0,s1,s2,s3,s4,s5,s6,s7,s8};
                dataList.add(datas);
            }
            String [] rowName = new String[] {
            		"??????",
            		"????????????",
            		"?????????",
                    "???????????????",
                    "?????????????????????",
                    "????????????",
                    "????????????",
                    "????????????",
                    "?????????"
            };
            ExportExcel exportExcel = new ExportExcel(response,"??????????????????",rowName,dataList);
            exportExcel.export();
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
        return null;
    }
	
	
	/**
	 * ??????????????????
	 * @param request
	 * @return
	 */
	@RequestMapping("/rechargeTimeStat")
	@RequiresPermissions("stat:rechargeTimeStat")
	public Response rechargeTimeStat(@RequestBody Request<RechargeForm, RechargeQuery> request, HttpServletRequest req) {
		Pagination pagination = request.getPagination();
        RechargeQuery query = request.getQuery();
        query.setRecharge(Const.recharge_3);
        //?????????????????????
        SysUser sysUser = currentUser();
        TableBody body = TableBody.factory();
        body.setPagination(pagination);
        long rowcount = rechargeService.count(query,sysUser);
        pagination.setTotal(rowcount);
        List<RechargeVo> list = rechargeService.page(query, sysUser, pagination);
        body.setList(list);
        if(null!=list && list.size()>0) {
        	for (int i = 0; i < list.size(); i++) {
        		if(null!=list.get(i).getApplytime() && null!=list.get(i).getChecktime()) {
            		list.get(i).setWorktime(DateUtil.getTime(DateUtils.LocalDateTimeToString(list.get(i).getApplytime()),DateUtils.LocalDateTimeToString(list.get(i).getChecktime())));
            	}
			}
        }
        return Response.ok().body(body);
    }
	
	/**
	 * ??????????????????
	 * @param response
	 * @param request
	 * @return
	 */
	@SuppressWarnings("all")
    @GetMapping("/exportRechargeTimeStat")
    public String exportRechargeTimeStat(HttpServletResponse response, HttpServletRequest request){
		String customerName = request.getParameter("customerName");
		
    	String applytime = request.getParameter("applytime");
    	
    	String checktime = request.getParameter("checktime");
    	
    	RechargeQuery query = new RechargeQuery();
    	query.setApplytime(applytime);
    	query.setChecktime(checktime);
    	query.setCustomerName(customerName);
    	query.setRecharge(Const.recharge_3);
    	
        LogUtil.info("?????? ?????????????????? ??????");
        try{
            response.setCharacterEncoding("UTF-8");
            String filedisplay = "??????????????????.xls";
            filedisplay = URLEncoder.encode(filedisplay, "UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename="+ filedisplay);
            response.setContentType("application/x-download");

            List<RechargeVo> statList = rechargeService.selectRechargeExcelByQuery(query);
            
            List<Object[]> dataList=new ArrayList<Object[]>();
            for(int i=0;i<statList.size();i++){
            	int s0 = i+1;
            	String s1 = statList.get(i).getCustomerName();
            	String s2= 	statList.get(i).getAgentCommissionFin();
            	String s3 = statList.get(i).getRealPaymentFin();
            	String s4 = DateUtils.LocalDateTimeToString(statList.get(i).getApplytime());
            	String s5 = DateUtils.LocalDateTimeToString(statList.get(i).getChecktime());
            	String s6 = "";
            	if(null!=statList.get(i).getApplytime() && null!=statList.get(i).getChecktime()) {
            		s6 = String.valueOf(DateUtil.getTime(DateUtils.LocalDateTimeToString(statList.get(i).getApplytime()),DateUtils.LocalDateTimeToString(statList.get(i).getChecktime())));
            	}
            	String s7 = statList.get(i).getCheckoper();
            	
                
                Object[] datas=new Object[]{s0,s1,s2,s3,s4,s5,s6,s7};
                dataList.add(datas);
            }
            String [] rowName = new String[] {
            		"??????",
            		"????????????",
            		"????????????",
                    "??????????????????",
                    "????????????",
                    "????????????",
                    "????????????????????????",
                    "????????????"
            };
            ExportExcel exportExcel = new ExportExcel(response,"??????????????????",rowName,dataList);
            exportExcel.export();
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
        return null;
    }
}
