package cn.rebornauto.platform.payment.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.rebornauto.platform.business.dao.AgentBankNoInfoMapper;
import cn.rebornauto.platform.business.dao.AgentQuotaMapper;
import cn.rebornauto.platform.business.dao.CustomerInfoMapper;
import cn.rebornauto.platform.business.dao.CustomerQuotaMapper;
import cn.rebornauto.platform.business.dao.CustomerTotalQuotaMapper;
import cn.rebornauto.platform.business.dao.DaiZhengInfoMapper;
import cn.rebornauto.platform.business.dao.OrderDetailMapper;
import cn.rebornauto.platform.business.dao.OrderDetailMergeMapper;
import cn.rebornauto.platform.business.dao.OrderMapper;
import cn.rebornauto.platform.business.dao.TransactionFlowMapper;
import cn.rebornauto.platform.business.entity.AgentQuota;
import cn.rebornauto.platform.business.entity.CustomerInfo;
import cn.rebornauto.platform.business.entity.CustomerQuota;
import cn.rebornauto.platform.business.entity.DaiZhengInfo;
import cn.rebornauto.platform.business.entity.Order;
import cn.rebornauto.platform.business.entity.OrderDetail;
import cn.rebornauto.platform.business.entity.OrderDetailMerge;
import cn.rebornauto.platform.business.entity.OrderDetailVO;
import cn.rebornauto.platform.business.entity.OrderVO;
import cn.rebornauto.platform.business.entity.TransactionFlow;
import cn.rebornauto.platform.business.form.OrderForm;
import cn.rebornauto.platform.business.query.OrderQuery;
import cn.rebornauto.platform.business.service.AgentQuotaService;
import cn.rebornauto.platform.business.service.BusiLogService;
import cn.rebornauto.platform.business.service.CustomerInfoService;
import cn.rebornauto.platform.business.service.OrderDetailMergeService;
import cn.rebornauto.platform.business.service.SysConfigService;
import cn.rebornauto.platform.business.service.SysDicService;
import cn.rebornauto.platform.business.service.TransactionFlowService;
import cn.rebornauto.platform.business.vo.AgentInfoVO;
import cn.rebornauto.platform.business.vo.ExcelTemplateVO;
import cn.rebornauto.platform.business.vo.QuotaVO;
import cn.rebornauto.platform.common.Const;
import cn.rebornauto.platform.common.ReturnMsgConst;
import cn.rebornauto.platform.common.SysConfigKey;
import cn.rebornauto.platform.common.data.request.Pagination;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.common.data.view.ResponseCode;
import cn.rebornauto.platform.common.util.DateUtils;
import cn.rebornauto.platform.common.util.LogUtil;
import cn.rebornauto.platform.pay.tonglian.Enum.FtTypeEnum;
import cn.rebornauto.platform.pay.tonglian.Enum.PayStatusEnum;
import cn.rebornauto.platform.pay.tonglian.utils.DateUtil;
import cn.rebornauto.platform.payment.service.OrderService;
import cn.rebornauto.platform.payment.service.impl.tonglianpay.PaymentHandler;
import cn.rebornauto.platform.sys.entity.SysUser;
import cn.rebornauto.platform.utils.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/** Title: 订单支付类
 * Description: 
 * Company: 
 * @author kgc
 * @date Apr 30, 2019 1:47:05 PM
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderMapper orderMapper;
	
	@Autowired
	OrderDetailMapper orderDetailMapper;
	
	@Autowired
	CustomerInfoMapper customerInfoMapper;
	
	@Autowired
	CustomerQuotaMapper customerQuotaMapper;
	
	@Autowired
	DaiZhengInfoMapper daiZhengInfoMapper;
	
	@Autowired
	CustomerInfoService customerInfoService;
	
	@Autowired
	TransactionFlowMapper transactionFlowMapper;
	
	@Autowired
	PaymentHandler paymentHandler;
	
	@Autowired
	TransactionFlowService transactionFlowService;
	
	@Autowired
	AgentBankNoInfoMapper agentBankNoInfoMapper;
	
	@Autowired
	SysConfigService sysConfigService;
	
	@Autowired
	BusiLogService busiLogService;
	
	@Autowired
	SysDicService sysDicService;
	
	@Autowired
	OrderDetailMergeMapper orderDetailMergeMapper;
	
	@Autowired
	AgentQuotaMapper agentQuotaMapper;
	
	@Autowired
	OrderDetailMergeService orderDetailMergeService;
	
	@Autowired
	CustomerTotalQuotaMapper customerTotalQuotaMapper;
	
	@Autowired
	AgentQuotaService agentQuotaService;
	
	@Override
	public Order selectOne(Order param) {
        return orderMapper.selectOne(param);
    }
	
	@Override
    public List<OrderVO> pageQuery(Pagination pagination, OrderQuery query) {
        return orderMapper.selectByQuery(pagination, query);
    }
	
	@Override
    public long countQuery(OrderQuery query) {
        return orderMapper.countByQuery(query);
    }
	
	@Override
    public Order selectByPrimaryKey(Integer id) {
        return orderMapper.selectByPrimaryKey(id);
    }
	
	@Override
	public int updateCheckinfoByOrderId(Integer orderId,String checkoper,Integer checkStatus,String remark) {
		return orderMapper.updateCheckinfoByOrderId(orderId,checkoper,checkStatus,remark);
	}
	
	@Override
	public int updateMergeCountByOrderId(Integer orderId,int mergeCount) {
		Order record = new Order();
		record.setId(orderId);
		record.setMergeCount(mergeCount);
		Example example = new Example(Order.class);
		example.createCriteria().andEqualTo("id", orderId);
		return orderMapper.updateByExampleSelective(record, example);
	}
	
	@Override
    public Response save(OrderForm form,boolean isAdmin) {
		String customerId = form.getUserCustomerId();
		if(isAdmin) {
			customerId = form.getCustomerId();
		}
		CustomerInfo customerInfo = customerInfoMapper.selectByPrimaryKey(customerId);
		if(null!=customerInfo) {
			Order record = new Order();
			record.setCustomerId(customerId);
	        record.setAgentCommission(BigDecimal.ZERO);
	        record.setDataStatus(Const.DATA_STATUS_1);
	        orderMapper.insertSelective(record);
	        return Response.ok();
		}else {
			return Response.error().message(ReturnMsgConst.RETURN_600010.getMsg());
		}
    }
	
	
	/**
	 * 单次上传
	 */
	@Override
	@Transactional
    public Response singleUpload(OrderForm form,boolean isAdmin) {
		try {
			String customerId = form.getUserCustomerId();
			//管理员取传入的客户编号
			if(isAdmin) {
				customerId = form.getCustomerId();
			}
			//1.查找此客户下，代理人卡号、姓名、身份证的数据是否存在
			AgentInfoVO agentInfoVO = agentBankNoInfoMapper.selectAgentInfoByAgentParam(form.getIdcardno(), form.getAgentName(), form.getAgentOpenBankNo(),customerId);
			if(null!=agentInfoVO) {
				if(form.getAmount().compareTo(form.getPolicyAmount())>=0) {
					return Response.error().message(form.getAgentOpenBankNo()+form.getAgentName()+" 佣金必须小于保费");
				}
				//2.入库订单明细表和更新财务付款表
				CustomerInfo queryCustomerInfo = new CustomerInfo();
				queryCustomerInfo.setId(customerId);
				CustomerInfo customerInfo = customerInfoMapper.selectOne(queryCustomerInfo);
				OrderDetail orderDetail = new OrderDetail();
//				orderDetail.setAgentId(agentInfoVO.getAgentId());
				orderDetail.setAgentName(form.getAgentName());
				orderDetail.setAmount(form.getAmount());
				orderDetail.setCreateoper(form.getCurrUserName());
				orderDetail.setCreatetime(LocalDateTime.now());
				orderDetail.setDaiZhengId(customerInfo.getDaiZhengId());
				orderDetail.setIdcardno(form.getIdcardno());
				orderDetail.setOpenBankNo(form.getAgentOpenBankNo());
				orderDetail.setOrderId(form.getOrderId());
				orderDetail.setOuttime(DateUtils.toDateyyyyMMdd(form.getOuttime(),"yyyy-MM-dd"));
//				orderDetail.setPaymentChannelId(paymentChannelId);
				orderDetail.setPolicyAmount(form.getPolicyAmount());
				orderDetail.setPolicyNo(form.getPolicyNo());
				orderDetail.setSource("单次上传");
				orderDetail.setDataStatus(Const.DATA_STATUS_1);
				orderDetailMapper.insertSelective(orderDetail);
				
				//3.更新订单表
				OrderVO vo = orderMapper.selectStatByOrderId(form.getOrderId());
				Order param = new Order();
				param.setId(form.getOrderId());
				Order record = orderMapper.selectOne(param);
				record.setCheckStatus(Const.CHECK_STATUS_2);
				record.setTotalCount(vo.getTotalCount());
				record.setPayStat(vo.getPayStat());
				record.setAgentCommission(vo.getAgentCommission());
				orderMapper.updateByPrimaryKeySelective(record);
				return Response.ok();
			}else {
				return Response.error().message(ReturnMsgConst.RETURN_600011.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.error().message(e.getMessage()+ReturnMsgConst.RETURN_600001.getMsg());
		}
    }
	
	/**
	 *  批量上传保存
	 */
	@Override
	@Transactional
    public Response batchUpload(OrderForm form,boolean isAdmin) {
		try {
			//excel上传未支付数据量不能大于1000
			int limitExcelCount = Integer.parseInt(sysDicService.selectUploadMaxData());
			LogUtil.debug("上传数量限制:"+limitExcelCount);
			
			String customerId = form.getUserCustomerId();
			//管理员取传入的客户编号
			if(isAdmin) {
				customerId = form.getCustomerId();
			}
			//1.查找此客户下，代理人卡号、姓名、身份证的数据是否存在 数据不作合并
			String fileName = form.getExcefile()[0];
			System.out.println("批量文件:"+fileName);
//			fileName = "https://testfile.yunbao.shiyugroup.com/aliyunNAS/cloud_policy/201905/13/aae65d017e0c4649b43288a36e0357a3.xlsx";
//			fileName = fileName.replaceAll("https://", "").replaceAll("http://", "");
//			String path = fileName.substring(fileName.indexOf("/"), fileName.length());
			String path = fileName;
			LogUtil.debug("文件服务器路径:"+path);
			ArrayList<ExcelTemplateVO> excelList = ExcelUtil.readExcel(new File(path));
			//查看是否旧模版
			if(null!=excelList && excelList.size()>0 && StringUtils.isNotBlank(excelList.get(0).getPolicyPerson()) && excelList.get(0).getPolicyPerson().equalsIgnoreCase(Const.TEMPLATE_ERROR)) {
				LogUtil.info(Const.TEMPLATE_ERROR);
				return Response.error().message(Const.TEMPLATE_ERROR);
			}
			//如果上传数据大于1000，不做处理，要求多批次执行
			if(null!=excelList && excelList.size()>limitExcelCount) {
				String msg = "请将数据量控制在"+limitExcelCount+"笔内!";
				LogUtil.info(msg);
				return Response.error().message(msg);
			}
			
			//需要比较是否存在的导入列表
			ArrayList<ExcelTemplateVO> firstArrayList =  new ArrayList<ExcelTemplateVO>();
			String sysPaySwitch = sysDicService.selectSysPaySwitch();
			//获取代理人最高可支付额度
			String agentQuota = sysConfigService.findValueByKey(SysConfigKey.agentQuota, sysPaySwitch);
			if(null!=excelList && excelList.size()>0) {
				for (int i = 0; i < excelList.size(); i++) {
					if(StringUtils.isBlank(excelList.get(i).getAgentName())) {
						return Response.error().message(excelList.get(i).getOpenBankNo()+" 收款人姓名不能为空!");
					}
					if(StringUtils.isBlank(excelList.get(i).getOpenBankNo())) {
						return Response.error().message(excelList.get(i).getAgentName()+" 收款人银行卡不能为空!");
					}
					if(null==excelList.get(i).getAgentCommission()||excelList.get(i).getAgentCommission().compareTo(BigDecimal.ZERO)<=0) {
						return Response.error().message(excelList.get(i).getOpenBankNo()+excelList.get(i).getAgentName()+" 佣金必须大于0!");
					}
					if(excelList.get(i).getAgentCommission().compareTo(new BigDecimal(agentQuota))>0) {
						return Response.error().message(excelList.get(i).getOpenBankNo()+excelList.get(i).getAgentName()+" 佣金不能超过"+agentQuota);
					}
//					if(StringUtils.isBlank(excelList.get(i).getPolicyNo())) {
//						return Response.error().message(excelList.get(i).getOpenBankNo()+excelList.get(i).getAgentName()+" 保单号不能为空");
//					}
//					if(StringUtils.isBlank(excelList.get(i).getPolicyPerson())) {
//						return Response.error().message(excelList.get(i).getOpenBankNo()+excelList.get(i).getAgentName()+" 被保险人不能为空");
//					}
//					if(StringUtils.isBlank(excelList.get(i).getOuttime())) {
//						return Response.error().message(excelList.get(i).getOpenBankNo()+excelList.get(i).getAgentName()+" 出单日期不能为空");
//					}
//					if(null==excelList.get(i).getPolicyAmount()||excelList.get(i).getPolicyAmount().compareTo(BigDecimal.ZERO)==0) {
//						return Response.error().message(excelList.get(i).getOpenBankNo()+excelList.get(i).getAgentName()+" 保费不能为空");
//					}
					ExcelTemplateVO vo = new ExcelTemplateVO();
					vo.setAgentName(excelList.get(i).getAgentName());
//					vo.setIdcardno(excelList.get(i).getIdcardno());
					vo.setOpenBankNo(excelList.get(i).getOpenBankNo());
					firstArrayList.add(vo);
				}
				
				//已在代理人信息表的数据
				List<ExcelTemplateVO> secondArrayList = agentBankNoInfoMapper.selectAgentListByCustomerId(customerId);
				//未匹配的数据
				List<ExcelTemplateVO> unExistlist = ExcelUtil.receiveDefectList(firstArrayList, secondArrayList);
				//有未匹配数据，需要提示报错
				if(null!=unExistlist&&unExistlist.size()>0) {
					LogUtil.info("excel有未匹配数据存在!");
					//未匹配的记录数
					int size = unExistlist.size();
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < unExistlist.size(); i++) {
						sb.append(unExistlist.get(i).getAgentName()+" "+unExistlist.get(i).getOpenBankNo()+"\r\n");
						if(i==4) {
							sb.append("......");
							break;
						}
					}
					return Response.error().message("有"+size+"条未匹配代理人数据，分别是\r\n"+sb.toString());
				}else {
					LogUtil.info("excel数据正常");
				}
				
				//数据量大于1000判断
				Order orderParam = new Order();
				orderParam.setId(form.getOrderId());
				orderParam.setDataStatus(Const.DATA_STATUS_1);
				Order orderRecord = orderMapper.selectOne(orderParam);
				if(orderRecord.getTotalCount()+excelList.size()>limitExcelCount) {
					String msg = "已存在"+orderRecord.getTotalCount()+"笔未支付的数据累加本次新上传数据量"+excelList.size()+"笔，超出最大限量"+limitExcelCount+"笔!";
					LogUtil.info(msg);
					return Response.error().message(msg);
				}
				
				
				//2.根据银行卡或者身份证号   获取所有此客户下已签约的代理人 身份证、银行卡、姓名信息
				List<AgentInfoVO> agentInfoList = agentBankNoInfoMapper.selectAgentInfoListByCustomerId(customerId);
				
				//3.入库订单明细表和更新财务付款表
				List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
				List<OrderDetailVO> orderDetailVOList = new ArrayList<OrderDetailVO>();
				CustomerInfo queryCustomerInfo = new CustomerInfo();
				queryCustomerInfo.setId(customerId);
				CustomerInfo customerInfo = customerInfoMapper.selectOne(queryCustomerInfo);
				for (int i = 0; i < excelList.size(); i++) {
					OrderDetail orderDetail = new OrderDetail();
//					orderDetail.setAgentId(agentInfoVO.getAgentId());
					orderDetail.setAgentName(excelList.get(i).getAgentName());
					orderDetail.setAmount(excelList.get(i).getAgentCommission());
					orderDetail.setCreateoper(form.getCurrUserName());
					orderDetail.setCreatetime(LocalDateTime.now());
					orderDetail.setDaiZhengId(customerInfo.getDaiZhengId());
					orderDetail.setOpenBankNo(excelList.get(i).getOpenBankNo());
					orderDetail.setOrderId(form.getOrderId());
					orderDetail.setRemark(excelList.get(i).getRemark());
					orderDetail.setRemark2(excelList.get(i).getRemark2());
					orderDetail.setRemark3(excelList.get(i).getRemark3());
//					orderDetail.setOuttime(DateUtils.toDateyyyyMMdd(excelList.get(i).getOuttime(),"yyyy-MM-dd"));
//					orderDetail.setPaymentChannelId(paymentChannelId);
//					orderDetail.setPolicyAmount(excelList.get(i).getPolicyAmount());
//					orderDetail.setPolicyNo(excelList.get(i).getPolicyNo());
					orderDetail.setSource(fileName.substring(fileName.lastIndexOf("/")+1, fileName.length()));
					orderDetail.setDataStatus(Const.DATA_STATUS_1);
					//根据银行卡将 身份证号放入
					for (int j = 0; j < agentInfoList.size(); j++) {
						if(agentInfoList.get(j).getAgentOpenBankNo().equalsIgnoreCase(excelList.get(i).getOpenBankNo())) {
							orderDetail.setIdcardno(agentInfoList.get(j).getAgentIdcardno());
						}
					}
					OrderDetailVO vo = new OrderDetailVO();
					org.springframework.beans.BeanUtils.copyProperties(orderDetail,vo );
					orderDetailList.add(orderDetail);
					orderDetailVOList.add(vo);
				}
				//判断额度是否足够
				Response resp = checkQuotaIsEnough(orderDetailVOList,customerId);
				if(resp.getCode()!=ResponseCode.SUCCESS.value()) {
					return Response.error().message(resp.getMessage());
				}
				//额度足够时入库
				orderDetailMapper.insertBatch(orderDetailList);//批量插入
				
				//4.更新订单表
				OrderVO vo = orderMapper.selectStatByOrderId(form.getOrderId());
				Order param = new Order();
				param.setId(form.getOrderId());
				Order record = orderMapper.selectOne(param);
				record.setCheckStatus(Const.CHECK_STATUS_2);
				record.setTotalCount(vo.getTotalCount());
				record.setPayStat(vo.getPayStat());
				record.setAgentCommission(vo.getAgentCommission());
				record.setUploadRemark(form.getUploadRemark());
				orderMapper.updateByPrimaryKeySelective(record);
				
				//更改订单表统计数
	      		orderMapper.updatePayStatByOrderId(record.getId());
				return Response.ok();
			}else {
				return Response.error().message(ReturnMsgConst.RETURN_600012.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.error().message(e.getMessage()+ReturnMsgConst.RETURN_600008.getMsg());
		}
	}

	/**
	 * 审核放款
	 */
	@Override
	@Transactional
	public Response examinePayment(OrderForm form,String sysPaySwitch,HttpServletRequest req,SysUser currentUser,String transactionFlowId) {
		try {
			paymentHandler.payForSecond(form,transactionFlowId, FtTypeEnum.RIGHT_LOAN.getCode(), form.getCurrUserName(),"","",0,sysPaySwitch,req,currentUser);
		} catch (Exception e) {
			e.printStackTrace();
			transactionFlowService.updatePayStatus(transactionFlowId, Integer.parseInt(PayStatusEnum.ERROR.getCode()), form.getCurrUserName());
			LogUtil.error(ReturnMsgConst.RETURN_600016.getMsg()+e.getMessage());
			//操作日志
	        busiLogService.add(req, Const.busi_log_busi_type_5, Const.busi_log_option_type_fail,ReturnMsgConst.RETURN_600016.getMsg()+e.getMessage(), currentUser);
			return Response.error().message(ReturnMsgConst.RETURN_600016.getMsg()+e.getMessage());
		}
		return Response.ok();
	}
	
	
	/**
	 * 检查额度情况
	 * @param form
	 * @return
	 */
	@Override
	public Response checkQuotaIsEnough(List<OrderDetailVO> orderDetailList, String customerId) {
		/**查询是否此订单的额度足够**/
		//按身份证分组合计 查询出此订单每个身份证需要支付金额
		Map<String, BigDecimal> agentAmountMap = new HashMap<String, BigDecimal>();
		//按身份证分组 key为身份证号，value为姓名
		Map<String, String> agentNameMap = new HashMap<String, String>();
		
		
		//0、查询此订单下，订单审核状态已上传、已驳回的额度，也要累计到新上传数据内
		List<OrderDetail> isExistOrderDetailList = new ArrayList<OrderDetail>();
		Order order = orderMapper.selectByPrimaryKey(orderDetailList.get(0).getOrderId());
		if(order.getCheckStatus().intValue()==Const.CHECK_STATUS_2||order.getCheckStatus().intValue()==Const.CHECK_STATUS_5) {
			Example orderDetailExample = new Example(OrderDetail.class);
			orderDetailExample.createCriteria()
			.andEqualTo("orderId",orderDetailList.get(0).getOrderId())
			.andEqualTo("dataStatus", Const.DATA_STATUS_1);
			isExistOrderDetailList = orderDetailMapper.selectByExample(orderDetailExample);
		}
		
		//1、查询订单支付总金额。
		BigDecimal payAmount = BigDecimal.ZERO;
		//1.1累加已上传入库的明细数据
		if(null!=isExistOrderDetailList&&!isExistOrderDetailList.isEmpty()) {
			for (int i = 0; i < isExistOrderDetailList.size(); i++) {
				payAmount = payAmount.add(isExistOrderDetailList.get(i).getAmount());
				agentNameMap.put(isExistOrderDetailList.get(i).getIdcardno(), isExistOrderDetailList.get(i).getAgentName());
				if(null == agentAmountMap.get(isExistOrderDetailList.get(i).getIdcardno())) {
					agentAmountMap.put(isExistOrderDetailList.get(i).getIdcardno(), isExistOrderDetailList.get(i).getAmount());
				}else {
					//此身份证存在的金额
					BigDecimal existAmount = agentAmountMap.get(isExistOrderDetailList.get(i).getIdcardno());
					agentAmountMap.put(isExistOrderDetailList.get(i).getIdcardno(), existAmount.add(isExistOrderDetailList.get(i).getAmount()));
				}
			}
		}
		//1.2累加当次上传未入库的明细数据
		for (int i = 0; i < orderDetailList.size(); i++) {
			payAmount = payAmount.add(orderDetailList.get(i).getAmount());
			agentNameMap.put(orderDetailList.get(i).getIdcardno(), orderDetailList.get(i).getAgentName());
			if(null == agentAmountMap.get(orderDetailList.get(i).getIdcardno())) {
				agentAmountMap.put(orderDetailList.get(i).getIdcardno(), orderDetailList.get(i).getAmount());
			}else {
				//此身份证存在的金额
				BigDecimal existAmount = agentAmountMap.get(orderDetailList.get(i).getIdcardno());
				agentAmountMap.put(orderDetailList.get(i).getIdcardno(), existAmount.add(orderDetailList.get(i).getAmount()));
			}
		}
		LogUtil.info("支付金额:"+payAmount);
		//2、查询订单放款金额是否小于客户已到账剩余额度，小于则通过，大于则拒绝。账户余额(已到账总额-已放款总额)
		CustomerQuota queryCustomerQuota = new CustomerQuota();
		queryCustomerQuota.setCustomerId(customerId);
		CustomerQuota customerQuota = customerQuotaMapper.selectOne(queryCustomerQuota);
		LogUtil.info("客户ID:"+customerId+",账户余额:"+customerQuota.getCustomerBalance());
		if(payAmount.compareTo(customerQuota.getCustomerBalance())>0) {
			return Response.error().message(ReturnMsgConst.RETURN_600002.getMsg());
		}
		//3、查询此代征主体可开票额度
		int daiZhengId = customerInfoService.selectDaiZhengIdByCustomerId(customerId);
		DaiZhengInfo queryDaiZhengInfo = new DaiZhengInfo();
		queryDaiZhengInfo.setId(daiZhengId);
		DaiZhengInfo daiZhengInfo = daiZhengInfoMapper.selectByPrimaryKey(queryDaiZhengInfo);
		LogUtil.info("客户ID:"+customerId+",代征主体剩余开票额度:"+daiZhengInfo.getLeftAmount());
		if(payAmount.compareTo(daiZhengInfo.getLeftAmount())>0) {
			return Response.error().message(ReturnMsgConst.RETURN_600003.getMsg());
		}
		//4、个人用户额度是否足够支付
		//4.1个人限制额度
        BigDecimal limitLoanAmount = new BigDecimal(sysConfigService.findValueByKey(SysConfigKey.agentQuota));
        
        
        //4.1.1每个身份证对应总金额累加 ＝ 待支付总金额   做个验证
		BigDecimal totalAgentAmount = BigDecimal.ZERO;
        //身份证列表,用于查询代理人已用额度表
        List<String> idcardQueryList = new ArrayList<String>();
		//4.2.1按身份证分组合计 查询出此订单每个身份证需要支付金额
        Iterator<Entry<String, BigDecimal>> entries = agentAmountMap.entrySet().iterator();
        while(entries.hasNext()){
            Entry<String, BigDecimal> entry = entries.next();
            String key = entry.getKey();
            idcardQueryList.add(key);
            totalAgentAmount = totalAgentAmount.add(entry.getValue());
        }
        //判断是否一致
        if(payAmount.compareTo(totalAgentAmount)!=0) {
        	return Response.error().message(ReturnMsgConst.RETURN_600044.getMsg());
		}
		//4.2.2个人已使用额度
		List<AgentQuota> agentQuotaList= agentQuotaMapper.selectAgentQuotaList(idcardQueryList);
		//4.2.3每个身份证当月已使用总金额map，用于比较
		Map<String, BigDecimal> agentQuotaMap = new HashMap<String, BigDecimal>();
		for (int i = 0; i < agentQuotaList.size(); i++) {
			agentQuotaMap.put(agentQuotaList.get(i).getAgentIdcardno(), agentQuotaList.get(i).getLoanAmount());
		}
		
		//4.3计算是否有超额信息
		StringBuffer aboveErrorSb = new StringBuffer();
		//4.3.1组装超额信息   姓名+身份证号+超额 金额 元
		//错误标志计数器
		int errorFlag = 0;
		Iterator<Entry<String, BigDecimal>> agentAmountIterator = agentAmountMap.entrySet().iterator();
        while(agentAmountIterator.hasNext()){
            Entry<String, BigDecimal> entry = agentAmountIterator.next();
            //身份证
            String agentIdcardno = entry.getKey();
            //代理人姓名
			String agentName = agentNameMap.get(agentIdcardno);
			//待支付金额
			BigDecimal agentPayAmount = entry.getValue();
			//已支付金额  
			BigDecimal agentQuotaAmount = (null==agentQuotaMap.get(agentIdcardno)?BigDecimal.ZERO:agentQuotaMap.get(agentIdcardno));
			//判断是否 待支付金额＋已支付金额 - 当月总限额  是否大于 0，大于0提示超额
			BigDecimal aboveAmount = agentPayAmount.add(agentQuotaAmount).subtract(limitLoanAmount);
			if(aboveAmount.compareTo(BigDecimal.ZERO)>0) {
				errorFlag++;//有超额累加
				aboveErrorSb = aboveErrorSb.append(agentName).append(" ").append(agentIdcardno).append(" 超额 ").append(aboveAmount).append("元\r\n");
				if(errorFlag==4) {
					aboveErrorSb.append("......");
					break;
				}
			}
        }
		//4.3.2如有超额信息，提示报错
		if(aboveErrorSb.length()>0) {
			return Response.error().message(ReturnMsgConst.RETURN_600043.getMsg()+"\r\n"+aboveErrorSb.toString());
		}
		
		//组装额度等相关信息，用于支付额度处理
		QuotaVO quotaVO = new QuotaVO();
		quotaVO.setAgentAmountMap(agentAmountMap);
		quotaVO.setPayAmount(payAmount);
		quotaVO.setCustomerId(customerId);
		quotaVO.setDaiZhengId(daiZhengId);
		/**查询是否此订单的额度足够**/
		return Response.ok().body(quotaVO);
	}
	
	
	/**
	 * 支付申请
	 */
	@Override
	public Response payApply(OrderForm form,boolean isAdmin) {
		Order queryOrder = new Order();
		queryOrder.setId(form.getOrderId());
		Order order = orderMapper.selectOne(queryOrder);
		String customerId = form.getUserCustomerId();
		if(isAdmin) {
			customerId = order.getCustomerId();
		}
		//查看订单号下的明细数据是否有数量
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setOrderId(form.getOrderId());
		int orderDetailCount = orderDetailMapper.selectCount(orderDetail);
		if(orderDetailCount>0) {
			CustomerInfo customerInfo = customerInfoMapper.selectByPrimaryKey(customerId);
			if(null!=customerInfo) {
				Order record = new Order();
				record.setId(form.getOrderId());
				record.setCreateoper(form.getCurrUserName());
		        record.setCreatetime(LocalDateTime.now());
				record.setCheckStatus(Const.CHECK_STATUS_3);
		        orderMapper.updateByPrimaryKeySelective(record);
		        return Response.ok();
			}else {
				return Response.error().message(ReturnMsgConst.RETURN_600010.getMsg());
			}
		}else {
			return Response.error().message(ReturnMsgConst.RETURN_600027.getMsg());
		}
	}
	
	
	/**
	 * 查看是否通联支付时间段
	 * @return
	 */
	public boolean isTongLianPayTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		//支付环境开关 测试或者生产
        String sysPaySwitch = sysDicService.selectSysPaySwitch();
		String dateStr = sysConfigService.findValueByKey(SysConfigKey.tonglianPayTime,sysPaySwitch);
		if(StringUtils.isNotBlank(dateStr)) {
			String[] dateArr = dateStr.split("-");
			try {
				Date currDate = sdf.parse(sdf.format(new Date()));// 当前时间
				Date startDate = sdf.parse(dateArr[0]);// 每节开始时间
				Date endDate = sdf.parse(dateArr[1]);// 每节结束时间
				if (currDate.after(startDate) && currDate.before(endDate)
						||currDate.compareTo(startDate)==0
						||currDate.compareTo(endDate)==0) {
					System.out.println("当前时间属于通联支付时间段");
						return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	
	/**
	 * 再次支付
	 */
	@Override
	@Transactional
	public Response paymentAgain(OrderForm form,String sysPaySwitch,HttpServletRequest req,SysUser currentUser) {
		String transactionFlowId = form.getTransactionFlowId();
		try {
			paymentHandler.payForSecond(form,transactionFlowId, FtTypeEnum.RIGHT_LOAN.getCode(), form.getCurrUserName(),"","",0,sysPaySwitch,req,currentUser);
		} catch (Exception e) {
			e.printStackTrace();
			transactionFlowService.updatePayStatus(transactionFlowId, Integer.parseInt(PayStatusEnum.ERROR.getCode()), form.getCurrUserName());
			LogUtil.error(ReturnMsgConst.RETURN_600016.getMsg()+e.getMessage());
			return Response.error().message(ReturnMsgConst.RETURN_600016.getMsg()+e.getMessage());
		}
		return Response.ok();
	}

	
	/**
	 * 作废订单明细
	 */
	@Override
	@Transactional
    public Response cancelOrderDetail(OrderForm form,boolean isAdmin) {
		try {
			//2.入库订单明细表和更新财务付款表
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setId(form.getOrderDetailId());
			orderDetail.setDataStatus(Const.DATA_STATUS__1);
			orderDetailMapper.updateByPrimaryKeySelective(orderDetail);
			
			//3.更新订单表
			OrderVO vo = orderMapper.selectStatByOrderId(form.getOrderId());
			Order param = new Order();
			param.setId(form.getOrderId());
			Order record = orderMapper.selectOne(param);
			if(BigDecimal.ZERO.compareTo(vo.getAgentCommission())==0) {
				record.setCheckStatus(Const.CHECK_STATUS_1);
			}else {
				record.setCheckStatus(Const.CHECK_STATUS_2);
			}
			record.setTotalCount(vo.getTotalCount());
			record.setPayStat(vo.getPayStat());
			record.setAgentCommission(vo.getAgentCommission());
			orderMapper.updateByPrimaryKeySelective(record);
			return Response.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.error().message(e.getMessage()+ReturnMsgConst.RETURN_600026.getMsg());
		}
    }
	
	
	/**
	 * 再次支付
	 */
	@Override
	@Transactional
	public Response batchPaymentAgain(OrderForm form,String sysPaySwitch,HttpServletRequest req,SysUser currentUser,String transactionFlowId) {
//		String transactionFlowId = form.getTransactionFlowId();
		try {
			paymentHandler.payForSecond(form,transactionFlowId, FtTypeEnum.RIGHT_LOAN.getCode(), form.getCurrUserName(),"","",0,sysPaySwitch,req,currentUser);
		} catch (Exception e) {
			e.printStackTrace();
			transactionFlowService.updatePayStatus(transactionFlowId, Integer.parseInt(PayStatusEnum.ERROR.getCode()), form.getCurrUserName());
			LogUtil.error(ReturnMsgConst.RETURN_600016.getMsg()+e.getMessage());
			return Response.error().message(ReturnMsgConst.RETURN_600016.getMsg()+e.getMessage());
		}
		return Response.ok();
	}

	
	
	/**
	 * 创建合并数据和支付表初始化
	 */
	@Override
	@Transactional
	public Response createMergeAndTransflowAddQuota(List<OrderDetailVO> orderDetailVOList,String isMerge,String sysPaySwitch,SysUser currentUser,OrderForm form,QuotaVO quotaVO) {
		Response resp = new Response();
		//增加额度
		addQuota(quotaVO,currentUser);
		//加开关  yes合并明细
    	if("yes".equalsIgnoreCase(isMerge)) {
    		//更新订单明细表，插入汇总表  数据汇总用于支付
        	resp = orderDetailMergeService.saveMergeAndUpdateDetail(orderDetailVOList, sysPaySwitch,currentUser);
    	}else {//no不合并明细
        	//更新订单明细表，插入汇总表  数据不汇总
        	resp = orderDetailMergeService.saveAndUpdateDetail(orderDetailVOList, sysPaySwitch,currentUser);
    	}
    	if(resp.getCode()==ResponseCode.SUCCESS.value()) {
			//初始化支付业务流水
			List<String> transactionFlowIdList = new ArrayList<String>();
			List<OrderDetailMerge> mergeList = (List<OrderDetailMerge>) resp.getBody();
			for (int i = 0; i < mergeList.size(); i++) {
	        	transactionFlowIdList.add(paymentHandler.payForLoan(form.getOrderId(), FtTypeEnum.RIGHT_LOAN.getCode(),null,"",form.getCurrUserName(),form.getPaymentChannelId(),mergeList.get(i),orderDetailVOList.get(0).getCustomerId()));
    		}
			LogUtil.info("===========threeListSize:"+transactionFlowIdList.size()+" "+mergeList.size()+" "+orderDetailVOList.size());
			return Response.ok().body(transactionFlowIdList);
    	}else {
    		return Response.error().message(resp.getMessage());
    	}
	}
	
	/**
	 * 先增加额度  锁住额度  支付失败释放
	 */
	public void addQuota(QuotaVO quotaVO,SysUser currentUser) {
		//待支付总金额
		BigDecimal payAmount = quotaVO.getPayAmount();
		//每个身份证对应的总金额
		Map<String, BigDecimal> agentAmountMap = quotaVO.getAgentAmountMap();

		//1.增加代理人当月额度
        Iterator<Entry<String, BigDecimal>> entries = agentAmountMap.entrySet().iterator();
        while(entries.hasNext()){
            Entry<String, BigDecimal> entry = entries.next();
            AgentQuota record = new AgentQuota();
			record.setAgentIdcardno(entry.getKey());
	    	record.setLoanAmount(entry.getValue());
	    	record.setModifyoper(currentUser.getNickname());
	    	record.setYearmonth(DateUtil.format(new Date(), "yyyyMM"));
	    	
	    	//如果没有数据，初始化客户额度表
	    	agentQuotaService.initAgentQuota(entry.getKey(), currentUser.getNickname());
	    	agentQuotaMapper.addAgentQuotaLoanAmount(record);//暂时先增加到放款额度，如果订单关闭了释放
        }
			
    	//2.客户余额变更
  		customerQuotaMapper.addCustomerQuotaByCustomerId(quotaVO.getCustomerId(), payAmount);
  		
  		//3.客户总余额变更
  		customerTotalQuotaMapper.addCustomerTotalQuotaByCustomerId(payAmount);
  		
  		//4.代征主体额度变更
  		daiZhengInfoMapper.addDaiZhengQuotaById(quotaVO.getDaiZhengId(),payAmount);
	}
	
	/**
	 * 先增加额度  锁住额度  支付失败释放
	 */
	@Override
	@Transactional
	public void addQuotaPayAgain(QuotaVO quotaVO,SysUser currentUser,TransactionFlow transactionFlow,Integer paymentChannelId) {
		TransactionFlow transactionFlowUp = new TransactionFlow();
		transactionFlowUp.setId(transactionFlow.getId());
		transactionFlowUp.setPaymentChannelId(paymentChannelId);
		transactionFlowUp.setPayStatus(Integer.parseInt(PayStatusEnum.IN_PROCESSED.getCode()));
		transactionFlowMapper.updateByPrimaryKeySelective(transactionFlowUp);
		//待支付总金额
		BigDecimal payAmount = quotaVO.getPayAmount();
		//每个身份证对应的总金额
		Map<String, BigDecimal> agentAmountMap = quotaVO.getAgentAmountMap();

		//1.增加代理人当月额度
        Iterator<Entry<String, BigDecimal>> entries = agentAmountMap.entrySet().iterator();
        while(entries.hasNext()){
            Entry<String, BigDecimal> entry = entries.next();
            AgentQuota record = new AgentQuota();
			record.setAgentIdcardno(entry.getKey());
	    	record.setLoanAmount(entry.getValue());
	    	record.setModifyoper(currentUser.getNickname());
	    	record.setYearmonth(DateUtil.format(new Date(), "yyyyMM"));
	    	
	    	agentQuotaMapper.addAgentQuotaLoanAmount(record);//暂时先增加到放款额度，如果订单关闭了释放
        }
			
    	//2.客户余额变更
  		customerQuotaMapper.addCustomerQuotaByCustomerId(quotaVO.getCustomerId(), payAmount);
  		
  		//3.客户总余额变更
  		customerTotalQuotaMapper.addCustomerTotalQuotaByCustomerId(payAmount);
  		
  		//4.代征主体额度变更
  		daiZhengInfoMapper.addDaiZhengQuotaById(quotaVO.getDaiZhengId(),payAmount);
	}
	
	/**
	 * 
	 * @param quotaVO
	 * @param currentUser
	 * @param transactionFlowIdList
	 */
	@Override
	@Transactional
	public void batchPaymentAgainInit(QuotaVO quotaVO,SysUser currentUser,List<String> transactionFlowIdList,Integer paymentChannelId) {
		//增加额度  锁住额度
		addQuota(quotaVO,currentUser);
		//初始化transflow
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", transactionFlowIdList.toArray());
    	params.put("payStatus", Integer.parseInt(PayStatusEnum.IN_PROCESSED.getCode()));
    	params.put("paymentChannelId", paymentChannelId);
		transactionFlowService.updatePayStatusByTransactionFlowIds(params);//初始化支付业务流水统一处理成处理中
	}

	/**
	 * 关闭订单
	 */
	@Override
	public void closeOrder(int id) {
		Order record = new Order();
		record.setId(id);
		record.setDataStatus(Const.DATA_STATUS__1);
		orderMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<OrderVO> selectPaySuccessExcelByQuery(OrderQuery query) {
		return orderMapper.selectPaySuccessExcelByQuery(query);
	}
}
