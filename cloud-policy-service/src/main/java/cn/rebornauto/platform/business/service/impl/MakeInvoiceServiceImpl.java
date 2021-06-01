package cn.rebornauto.platform.business.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rebornauto.platform.business.dao.CustomerInfoMapper;
import cn.rebornauto.platform.business.dao.CustomerQuotaMapper;
import cn.rebornauto.platform.business.dao.MakeInvoiceDetailMapper;
import cn.rebornauto.platform.business.dao.MakeInvoiceMapper;
import cn.rebornauto.platform.business.entity.CustomerInfo;
import cn.rebornauto.platform.business.entity.CustomerQuota;
import cn.rebornauto.platform.business.entity.MakeInvoice;
import cn.rebornauto.platform.business.query.InvoiceQuery;
import cn.rebornauto.platform.business.service.MakeInvoiceService;
import cn.rebornauto.platform.common.Const;
import lombok.extern.slf4j.Slf4j;

/** Title: 
 * Description: 
 * Company: 
 * @author kgc
 * @date Mar 17, 2020 3:07:05 PM
 */
@Service
@Slf4j
public class MakeInvoiceServiceImpl implements MakeInvoiceService {
	
	@Autowired
	MakeInvoiceMapper makeInvoiceMapper;
	
	@Autowired
	MakeInvoiceDetailMapper makeInvoiceDetailMapper;
	
	@Autowired
	CustomerQuotaMapper customerQuotaMapper;
	
	@Autowired
	CustomerInfoMapper customerInfoMapper;

	/**
	 * 检测开票管理表是否有此商户数据，如果没有创建
	 */
	@Override
	public BigDecimal checkDataUnExistToCreate(String customerId,String createoper) {
		//可开票金额
		BigDecimal invoiceAmount = BigDecimal.ZERO;
		
		MakeInvoice makeInvoiceQuery = new MakeInvoice();
		makeInvoiceQuery.setDataStatus(Const.DATA_STATUS_1);
		makeInvoiceQuery.setCustomerId(customerId);
		MakeInvoice bean = makeInvoiceMapper.selectOne(makeInvoiceQuery);
		
		MakeInvoice quota = getInvoiceAmount(customerId);
		if(null==bean) {
			MakeInvoice record = new MakeInvoice();
			record.setApplyAmount(quota.getApplyAmount());
//			record.setInvoiceAmount(invoiceAmount);
			record.setCreateoper(createoper);
			record.setCreatetime(LocalDateTime.now());
			record.setCustomerId(customerId);
			record.setDataStatus(Const.DATA_STATUS_1);
			makeInvoiceMapper.insertSelective(record);
		}else {
			invoiceAmount = quota.getInvoiceAmount();
		}
		return invoiceAmount;
	}
	
	/**
	 * 获取可开票金额及已经开票总金额
	 * @param customerId
	 * @return
	 */
	@Override
	public MakeInvoice getInvoiceAmount(String customerId) {
		CustomerInfo customerInfo = customerInfoMapper.selectByPrimaryKey(customerId);
		
		MakeInvoice makeInvoice = new MakeInvoice();
		CustomerQuota customerQuotaQuery = new CustomerQuota();
		customerQuotaQuery.setCustomerId(customerId);
		customerQuotaQuery.setDataStatus(Const.DATA_STATUS_1);
		CustomerQuota cqData = customerQuotaMapper.selectOne(customerQuotaQuery);
		//累计放款金额   累计放款金额－已开票金额＝可开票金额
		BigDecimal loanAmount = cqData.getLoanAmount();
		
		//已开票金额
		InvoiceQuery invoiceQuery = new InvoiceQuery();
		invoiceQuery.setCustomerId(customerId);
		BigDecimal totalApplyAmount = makeInvoiceDetailMapper.getTotalApplyAmount(invoiceQuery);
		log.info("已申请开票金额:"+totalApplyAmount);
		
		//总开票本金金额
		BigDecimal totalCapitalAmount = makeInvoiceDetailMapper.getTotalCapitalAmount(invoiceQuery);
		log.info("总开票本金金额:"+totalCapitalAmount);
		
		//剩余已放款金额
		BigDecimal leftLoanAmount = loanAmount.subtract(totalCapitalAmount);
		log.info("剩余已放款金额:"+leftLoanAmount);
		
		
		//实际可开票金额 = 付款金额/(1-服务费率)  保留2位小数 四舍五入  比如   10869.57 = 10000/(1-0.08)
        BigDecimal realAmount = leftLoanAmount.divide((new BigDecimal(1).subtract(customerInfo.getServiceRate().multiply(new BigDecimal(0.01)))),2,BigDecimal.ROUND_HALF_UP);
        log.info("实际可开票金额:"+realAmount);
		
		//可开票金额
//		BigDecimal invoiceAmount = realAmount.subtract(totalApplyAmount);
        BigDecimal invoiceAmount = realAmount;
		makeInvoice.setInvoiceAmount(invoiceAmount);
		makeInvoice.setApplyAmount(totalApplyAmount);
		return makeInvoice;
	}

}
