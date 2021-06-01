package cn.rebornauto.platform.business.service.impl;

import cn.rebornauto.platform.business.dao.*;
import cn.rebornauto.platform.business.entity.*;
import cn.rebornauto.platform.business.form.RechargeForm;
import cn.rebornauto.platform.business.query.RechargeQuery;
import cn.rebornauto.platform.business.service.DaiZhengInfoService;
import cn.rebornauto.platform.business.service.RechargeService;
import cn.rebornauto.platform.business.service.SysConfigService;
import cn.rebornauto.platform.business.service.SysDicService;
import cn.rebornauto.platform.business.vo.RechargeVo;
import cn.rebornauto.platform.common.Const;
import cn.rebornauto.platform.common.ReturnMsgConst;
import cn.rebornauto.platform.common.data.request.Pagination;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.common.util.BusiLogUtil;
import cn.rebornauto.platform.common.util.CheckUtil;
import cn.rebornauto.platform.common.util.LogUtil;
import cn.rebornauto.platform.sys.entity.SysUser;
import cn.rebornauto.platform.upload.entity.UploadInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RechargeServiceImpl implements RechargeService {
    @Autowired
    private DaiZhengRechargeMapper daiZhengRechargeMapper;
    @Autowired
    private DaiZhengInfoMapper daiZhengInfoMapper;
    @Autowired
    private CustomerInfoMapper customerInfoMapper;
    @Autowired
    private InvoiceMapper invoiceMapper;
    @Autowired
    private BusiLogMapper busiLogMapper;
    @Autowired
    private CustomerQuotaMapper customerQuotaMapper;
    @Autowired
    private CustomerTotalQuotaMapper customerTotalQuotaMapper;
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private SysDicService sysDicService;
    
    @Autowired
    DaiZhengInfoService daiZhengInfoService;
    @Override
    public long count(RechargeQuery query, SysUser sysUser) {

        return daiZhengRechargeMapper.count(query);
    }

    @Override
    @Transactional
    public Response save(RechargeForm form) {
        DaiZhengRecharge daiZhengRecharge = getParam(form);
        daiZhengRecharge.setDaiZhengId(daiZhengInfoService.getDaiZhengId());
        
        CustomerInfo customerInfo = customerInfoMapper.selectByPrimaryKey(daiZhengRecharge.getCustomerId());
        //服务费率
        daiZhengRecharge.setCustomerServiceRate(customerInfo.getServiceRate());
        //付款金额
        BigDecimal agentCommission = daiZhengRecharge.getAgentCommission();
        //实际充值金额 = 付款金额/(1-服务费率)  保留2位小数 四舍五入  比如   10869.57 = 10000/(1-0.08)
        BigDecimal realPayment = agentCommission.divide((new BigDecimal(1).subtract(customerInfo.getServiceRate().multiply(new BigDecimal(0.01)))),2,BigDecimal.ROUND_HALF_UP);
        //服务费
        BigDecimal subtract = realPayment.subtract(agentCommission);
        LogUtil.info("========前端计算实际充值金额:"+form.getRealPayment() +" 后端计算实际充值金额:"+realPayment);
        //判断前端计算的金额是否和后端计算的一致，不一致报错
        if(form.getRealPayment().compareTo(realPayment)!=0) {
        	LogUtil.info("=====校验失败=====");
        	return Response.error().message(ReturnMsgConst.RETURN_600045.getMsg());
        }
        LogUtil.info("=====校验通过=====");
        
        try {
        	daiZhengRecharge.setRealPayment(realPayment);
            daiZhengRecharge.setServiceAmount(subtract);
            daiZhengRecharge.setApplytime(LocalDateTime.now());
            daiZhengRecharge.setCreatetime(LocalDateTime.now());
            daiZhengRecharge.setCreateoper(form.getCurrUserName());
            daiZhengRecharge.setRecharge(Const.recharge_1);
            daiZhengRechargeMapper.insertSelective(daiZhengRecharge);

            BusiLog busiLog = BusiLogUtil.rechargeAdd(form, form.getIp());
            busiLog.setBusiType(Const.busi_log_busi_type_4);
            busiLog.setOptionType(Const.busi_log_option_type_shenqing);
            busiLogMapper.insertSelective(busiLog);
            
            return Response.ok();
		} catch (Exception e) {
			return Response.error().message(ReturnMsgConst.RETURN_600046.getMsg()+e.getMessage());
		}
    }

    @Override
    @Transactional
    public Response edit(RechargeForm form) {
        DaiZhengRecharge daiZhengRecharge = getParam(form);
        daiZhengRecharge.setDaiZhengId(daiZhengInfoService.getDaiZhengId());
        
        CustomerInfo customerInfo = customerInfoMapper.selectByPrimaryKey(daiZhengRecharge.getCustomerId());
        //服务费率
        daiZhengRecharge.setCustomerServiceRate(customerInfo.getServiceRate());
        //付款金额
        BigDecimal agentCommission = daiZhengRecharge.getAgentCommission();
        //实际充值金额 = 付款金额/(1-服务费率)  保留2位小数 四舍五入  比如   10869.57 = 10000/(1-0.08)
        BigDecimal realPayment = agentCommission.divide((new BigDecimal(1).subtract(customerInfo.getServiceRate().multiply(new BigDecimal(0.01)))),2,BigDecimal.ROUND_HALF_UP);
        LogUtil.info("========前端计算实际充值金额:"+form.getRealPayment() +" 后端计算实际充值金额:"+realPayment);
        //判断前端计算的金额是否和后端计算的一致，不一致报错
        if(form.getRealPayment().compareTo(realPayment)!=0) {
        	LogUtil.info("=====校验失败=====");
        	return Response.error().message(ReturnMsgConst.RETURN_600045.getMsg());
        }
        LogUtil.info("=====校验通过=====");
        daiZhengRecharge.setServiceAmount(realPayment.subtract(agentCommission));
        DaiZhengRecharge daiZhengRecharge1 = daiZhengRechargeMapper.selectByPrimaryKey(form.getId());
    	UploadInfo uploadInfo=sysConfigService.getUploadInfo(sysDicService.selectSysPaySwitch());
        String s = CheckUtil.checkUrl(daiZhengRecharge1.getRechargeVoucherPicUrl(), daiZhengRecharge.getRechargeVoucherPicUrl(), uploadInfo.getDomain());
        daiZhengRecharge.setRechargeVoucherPicUrl(s);
        //判断状态
        if (daiZhengRecharge1.getRecharge() == Const.recharge_2 || daiZhengRecharge1.getRecharge() == Const.recharge_3) {
        	return Response.error().message(ReturnMsgConst.RETURN_600047.getMsg());
        }
        daiZhengRechargeMapper.updateByPrimaryKeySelective(daiZhengRecharge);
        return Response.ok();
        
    }

    @Override
    public List<RechargeVo> page(RechargeQuery query, SysUser sysUser, Pagination pagination) {
        List<RechargeVo> list = daiZhengRechargeMapper.page(query,pagination);
        List list1 = getList(list);
        return list1;
    }

    @Override
    public CustomerInfo getCustomerInfo(String customerId) {
        return customerInfoMapper.selectByPrimaryKey(customerId);
    }

    @Override
    @Transactional
    public synchronized Response submitCheck(RechargeForm form) {
        int id = form.getId();
        DaiZhengRecharge daiZhengRecharge = daiZhengRechargeMapper.selectByPrimaryKey(id);
        if (org.springframework.util.StringUtils.isEmpty(daiZhengRecharge.getDaiZhengId())) {
        	LogUtil.info("DaiZhengId为空:"+ReturnMsgConst.RETURN_600051.getMsg());
            return Response.error().message(ReturnMsgConst.RETURN_600051.getMsg());
        }
        if (org.springframework.util.StringUtils.isEmpty(daiZhengRecharge.getRechargeVoucherPicUrl())) {
        	LogUtil.info("rechargeVoucherPicUrl为空:"+ReturnMsgConst.RETURN_600052.getMsg());
            return Response.error().message(ReturnMsgConst.RETURN_600052.getMsg());
        }
        if (org.springframework.util.StringUtils.isEmpty(daiZhengRecharge.getArrivetime())) {
        	LogUtil.info("arrivetime为空:"+ReturnMsgConst.RETURN_600053.getMsg());
            return Response.error().message(ReturnMsgConst.RETURN_600053.getMsg());
        }
        //已申请状态和已驳回状态才能提交审核
        if (daiZhengRecharge.getRecharge() == Const.recharge_1||daiZhengRecharge.getRecharge() == Const.recharge_4) {
        	daiZhengRecharge.setRecharge(Const.recharge_2);
            CustomerQuota customerQuota = customerQuotaMapper.selectByCustomerId(daiZhengRecharge.getCustomerId());
            BigDecimal pendingApprAmount = customerQuota.getPendingApprAmount();
            BigDecimal add2 = pendingApprAmount.add(daiZhengRecharge.getAgentCommission());
            customerQuota.setPendingApprAmount(add2);
            customerQuotaMapper.updateByPrimaryKeySelective(customerQuota);
            CustomerTotalQuota customerTotalQuota = customerTotalQuotaMapper.selectByCustomerId();
            BigDecimal pendingApprAmount1 = customerTotalQuota.getPendingApprAmount();
            BigDecimal add1 = pendingApprAmount1.add(daiZhengRecharge.getAgentCommission());
            customerTotalQuota.setPendingApprAmount(add1);
            customerTotalQuotaMapper.updateByPrimaryKeySelective(customerTotalQuota);
            daiZhengRechargeMapper.updateByPrimaryKeySelective(daiZhengRecharge);
    		return Response.ok();
        }else {
        	return Response.error().message(ReturnMsgConst.RETURN_600054.getMsg());
        }
        
    }

    @Override
    @Transactional
    public synchronized Response checkSuccess(RechargeForm form,SysUser user) {
        int id = form.getId();
        DaiZhengRecharge daiZhengRecharge = daiZhengRechargeMapper.selectByPrimaryKey(id);
        //待审核状态才能审核
        if(daiZhengRecharge.getRecharge() == Const.recharge_2) {
        	daiZhengRecharge.setRecharge(Const.recharge_3);
            //更新代征主体累计充值金额（添加实际到账金额）和账户余额（向代理人付款金额）
            DaiZhengInfo daiZhengInfo = daiZhengInfoMapper.selectByPrimaryKey(daiZhengRecharge.getDaiZhengId());
            BigDecimal add = daiZhengInfo.getLeftAmount().add(daiZhengRecharge.getAgentCommission());
            daiZhengInfo.setLeftAmount(add);
            daiZhengInfo.setRechargeAmount(daiZhengInfo.getRechargeAmount().add(daiZhengRecharge.getAgentCommission()));
            int i = daiZhengInfoMapper.updateByPrimaryKeySelective(daiZhengInfo);
            Invoice invoiceParam = getInvoiceParam(daiZhengRecharge);
            invoiceParam.setRecharge(Const.recharge_3);
            invoiceMapper.insertSelective(invoiceParam);
            //添加到账日志
            BusiLog busiLog = BusiLogUtil.rechargeUploadRechargeVoucher(form, form.getIp());
            busiLog.setOptionType(Const.busi_log_option_type_success);
            busiLog.setBusiType(Const.busi_log_busi_type_4);
            busiLogMapper.insertSelective(busiLog);
            //修改客户余额信息
            CustomerQuota customerQuota = customerQuotaMapper.selectByCustomerId(daiZhengRecharge.getCustomerId());
            //减去待充值确认金额
            BigDecimal pendingApprAmount = customerQuota.getPendingApprAmount();
            BigDecimal subtract = pendingApprAmount.subtract(daiZhengRecharge.getAgentCommission());
            customerQuota.setPendingApprAmount(subtract);
            //添加可用余额
            BigDecimal availableBalance = customerQuota.getAvailableBalance();
            BigDecimal availableBalanceAdd = availableBalance.add(daiZhengRecharge.getAgentCommission());
            customerQuota.setAvailableBalance(availableBalanceAdd);
            //添加账户余额
            BigDecimal customerBalance = customerQuota.getCustomerBalance();
            BigDecimal customerBalanceAdd = customerBalance.add(daiZhengRecharge.getAgentCommission());
            customerQuota.setCustomerBalance(customerBalanceAdd);
            //添加累计充值金额
            BigDecimal rechargeAmount = customerQuota.getRechargeAmount();
            BigDecimal add1 = rechargeAmount.add(daiZhengRecharge.getAgentCommission());
            customerQuota.setRechargeAmount(add1);
            customerQuotaMapper.updateByPrimaryKeySelective(customerQuota);
            //修改总额
            CustomerTotalQuota customerTotalQuota = customerTotalQuotaMapper.selectByCustomerId();
            BigDecimal availableBalance1 = customerTotalQuota.getAvailableBalance();
            BigDecimal availableBalanceAdd1 = availableBalance1.add(daiZhengRecharge.getAgentCommission());
            customerTotalQuota.setAvailableBalance(availableBalanceAdd1);
            BigDecimal pendingApprAmount1 = customerTotalQuota.getPendingApprAmount();
            BigDecimal subtract1 = pendingApprAmount1.subtract(daiZhengRecharge.getAgentCommission());
            customerTotalQuota.setPendingApprAmount(subtract1);
            BigDecimal rechargeAmount1 = customerTotalQuota.getRechargeAmount();
            BigDecimal rechargeAmountAdd1 = rechargeAmount1.add(daiZhengRecharge.getAgentCommission());
            customerTotalQuota.setRechargeAmount(rechargeAmountAdd1);
            BigDecimal customerBalance1 = customerTotalQuota.getCustomerBalance();
            BigDecimal customerBalanceAdd1 = customerBalance1.add(daiZhengRecharge.getAgentCommission());
            customerTotalQuota.setCustomerBalance(customerBalanceAdd1);
            customerTotalQuotaMapper.updateByPrimaryKeySelective(customerTotalQuota);
            
            daiZhengRecharge.setCheckoper(user.getNickname());
            daiZhengRecharge.setChecktime(LocalDateTime.now());
            daiZhengRechargeMapper.updateByPrimaryKeySelective(daiZhengRecharge);
            return Response.ok();
        }else {
        	return Response.error().message(ReturnMsgConst.RETURN_600055.getMsg());
        }
        
    }

    @Override
    @Transactional
    public synchronized Response checkFail(RechargeForm form) {
    	int id = form.getId();
        DaiZhengRecharge daiZhengRecharge = daiZhengRechargeMapper.selectByPrimaryKey(id);
        //待审核状态才能审核
        if(daiZhengRecharge.getRecharge() == Const.recharge_2) {
            daiZhengRecharge.setRecharge(Const.recharge_4);
            //修改客户余额
            CustomerQuota customerQuota = customerQuotaMapper.selectByCustomerId(daiZhengRecharge.getCustomerId());
            //减去待确认金额
            BigDecimal subtract = customerQuota.getPendingApprAmount().subtract(daiZhengRecharge.getAgentCommission());
            customerQuota.setPendingApprAmount(subtract);
            customerQuotaMapper.updateByPrimaryKeySelective(customerQuota);
            //修改总额
            CustomerTotalQuota customerTotalQuota = customerTotalQuotaMapper.selectByCustomerId();
            BigDecimal subtract1 = customerTotalQuota.getPendingApprAmount().subtract(daiZhengRecharge.getAgentCommission());
            customerTotalQuota.setPendingApprAmount(subtract1);
            customerTotalQuotaMapper.updateByPrimaryKeySelective(customerTotalQuota);
            daiZhengRechargeMapper.updateByPrimaryKeySelective(daiZhengRecharge);
            return Response.ok();
        }else {
        	return Response.error().message(ReturnMsgConst.RETURN_600055.getMsg());
        }
    }

    @Override
    public int count4CheckList(RechargeQuery query) {
        return daiZhengRechargeMapper.count4CheckList(query);
    }

    @Override
    public List<RechargeVo> page4CheckList(RechargeQuery query, Pagination pagination) {
        List<RechargeVo> list = daiZhengRechargeMapper.page4CheckList(query, pagination);
        List list1 = getList(list);
        return list1;
    }

    public DaiZhengRecharge getParam(RechargeForm form) {
        DaiZhengRecharge daiZhengRecharge = new DaiZhengRecharge();
        daiZhengRecharge.setId(form.getId());
        daiZhengRecharge.setAgentCommission(form.getAgentCommission());
        daiZhengRecharge.setArrivetime(form.getArrivetime());
        daiZhengRecharge.setCustomerId(form.getUserCustomerId());
        daiZhengRecharge.setDaiZhengId(form.getDaiZhengId());
        daiZhengRecharge.setRealPayment(form.getRealPayment());
        daiZhengRecharge.setRealPayment(form.getRealPayment());
        daiZhengRecharge.setModifyoper(form.getCurrUserName());
        daiZhengRecharge.setModifytime(LocalDateTime.now());
        daiZhengRecharge.setRechargeVoucherPicUrl(form.getRechargeVoucherPicUrl());
        return daiZhengRecharge;
    }

    public Invoice getInvoiceParam(DaiZhengRecharge daiZhengRecharge) {
        Invoice invoice = new Invoice();
        CustomerInfo customerInfo = customerInfoMapper.selectByPrimaryKey(daiZhengRecharge.getCustomerId());
        invoice.setAddress(customerInfo.getCustomerAddress());
        invoice.setAgentCommission(daiZhengRecharge.getAgentCommission());
        invoice.setCreateoper(daiZhengRecharge.getModifyoper());
        invoice.setCreatetime(LocalDateTime.now());
        invoice.setCustomerId(daiZhengRecharge.getCustomerId());
        invoice.setInvoiceAmount(daiZhengRecharge.getRealPayment());
        invoice.setInvoiceContent(customerInfo.getInvoiceContent());
        invoice.setInvoiceTitle(customerInfo.getInvoiceTitle());
        invoice.setMobile(customerInfo.getCustomerMobile());
        invoice.setOpenBank(customerInfo.getCustomerOpenBankName());
        invoice.setOpenBankNo(customerInfo.getCustomerOpenBankNo());
        invoice.setOrderId(daiZhengRecharge.getId());
        invoice.setRealPayment(daiZhengRecharge.getRealPayment());
        invoice.setRegAddress(customerInfo.getCustomerRegAddress());
        invoice.setTaxpayerNumber(customerInfo.getTaxpayerNumber());
        invoice.setTaxpayerType(customerInfo.getTaxpayerType());
        invoice.setInvoiceContent(customerInfo.getInvoiceContent());
        invoice.setOutInvoiceStatus(Const.out_invoice_status_1);
        invoice.setInvoiceStatus(Const.invoice_status_1);
        invoice.setReceiver(customerInfo.getReceiver());
        return invoice;
    }
    public List getList(List<RechargeVo> list) {
        UploadInfo uploadInfo=sysConfigService.getUploadInfo(sysDicService.selectSysPaySwitch());
        list.forEach(rechargeVo -> {
            if (StringUtils.isNotEmpty(rechargeVo.getRechargeVoucherPicUrl())) {
                rechargeVo.setRechargeVoucherPicUrl(uploadInfo.getDomain()+rechargeVo.getRechargeVoucherPicUrl());
            } else {
                rechargeVo.setRechargeVoucherPicUrl("");
            }
        });
        return list;
    }

	@Override
	public List<RechargeVo> selectRechargeExcelByQuery(RechargeQuery query) {
		return daiZhengRechargeMapper.selectRechargeExcelByQuery(query);
	}

}
