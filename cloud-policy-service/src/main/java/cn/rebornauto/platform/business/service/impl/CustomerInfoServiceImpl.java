package cn.rebornauto.platform.business.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.rebornauto.platform.business.dao.AutoCustomerNoMapper;
import cn.rebornauto.platform.business.dao.BankCodeInfoMapper;
import cn.rebornauto.platform.business.dao.CustomerInfoMapper;
import cn.rebornauto.platform.business.dao.CustomerQuotaMapper;
import cn.rebornauto.platform.business.dao.DaiZhengRechargeMapper;
import cn.rebornauto.platform.business.dao.MakeInvoiceDetailMapper;
import cn.rebornauto.platform.business.dao.PostAddressMapper;
import cn.rebornauto.platform.business.entity.BankCodeInfo;
import cn.rebornauto.platform.business.entity.CustomerInfo;
import cn.rebornauto.platform.business.entity.CustomerQuota;
import cn.rebornauto.platform.business.entity.PostAddress;
import cn.rebornauto.platform.business.form.CustomerInfoForm;
import cn.rebornauto.platform.business.query.CustomerInfoQuery;
import cn.rebornauto.platform.business.query.InvoiceQuery;
import cn.rebornauto.platform.business.service.CustomerInfoService;
import cn.rebornauto.platform.business.service.SysConfigService;
import cn.rebornauto.platform.business.service.SysDicService;
import cn.rebornauto.platform.business.vo.CustomerVo;
import cn.rebornauto.platform.common.data.request.Pagination;
import cn.rebornauto.platform.common.util.LogUtil;
import cn.rebornauto.platform.qrcode.utils.QRCodeTool;
import cn.rebornauto.platform.upload.entity.UploadInfo;
import cn.rebornauto.platform.wx.entity.AuthorizeInfo;
import tk.mybatis.mapper.entity.Example;
/**
 * 
 * <p>Title: CustomerInfoServiceImpl</p>  
 * <p>Description:</p>  
 * @author zjl  
 * @date 2019年4月30日
 */
@Service
public class CustomerInfoServiceImpl implements CustomerInfoService{
	
	@Autowired
	private CustomerInfoMapper  customerInfoMapper;
	
	@Autowired
	private CustomerQuotaMapper   customerQuotaMapper;

	@Autowired
	private BankCodeInfoMapper   bankCodeInfoMapper;
	
	@Autowired
	private AutoCustomerNoMapper   autoCustomerNoMapper;
	
	@Autowired
	private SysDicService sysDicService;

	@Autowired
	private SysConfigService sysConfigService;
	
	@Autowired
	PostAddressMapper postAddressMapper;
	
	@Autowired
	MakeInvoiceDetailMapper makeInvoiceDetailMapper;
	
	@Autowired
	DaiZhengRechargeMapper daiZhengRechargeMapper;

	@Override
	public Integer selectDaiZhengIdByCustomerId(String customerId) {
		CustomerInfo customerInfo = new CustomerInfo();
		customerInfo.setId(customerId);
		CustomerInfo info = customerInfoMapper.selectOne(customerInfo);
	    return info.getDaiZhengId();
	}

	@Override
	public int countByQuery(CustomerInfoQuery query) {
		// TODO Auto-generated method stub
		return customerInfoMapper.countByQuery(query);
	}

	@Override
	public List<CustomerVo> pageQuery(Pagination pagination,
			CustomerInfoQuery query) {
		// TODO Auto-generated method stub
		 List<CustomerVo> customerVos = customerInfoMapper.pageQuery(pagination,query);
		 UploadInfo  uploadInfo=sysConfigService.getUploadInfo(sysDicService.selectSysPaySwitch());
		 customerVos.forEach(customerVo -> {
			 if (StringUtils.hasText(customerVo.getQrCodeLogoImgPicUrl())) {
				 customerVo.setQrCodeLogoImgPicUrl(uploadInfo.getDomain()+customerVo.getQrCodeLogoImgPicUrl());
			 }
			 if (StringUtils.hasText(customerVo.getQrCodeImgPicUrl())) {
				 customerVo.setQrCodeImgPicUrl(uploadInfo.getDomain()+customerVo.getQrCodeImgPicUrl());
			 }
			 if (StringUtils.hasText(customerVo.getInvoiceDemoUrl())) {
				 customerVo.setInvoiceDemoUrl(uploadInfo.getDomain()+customerVo.getInvoiceDemoUrl());
			 }
		 });
		 return customerVos;
	}

	@Override
	@Transactional
	public void  add(CustomerInfoForm form) throws Exception {
	
		// 根据银行code查询银行名称
		Example bankCodeInfoExample = new Example(BankCodeInfo.class);
		bankCodeInfoExample.createCriteria().andEqualTo("bankCode",form.getCustomerOpenBankCode());			
		BankCodeInfo selectBankCodeInfo = bankCodeInfoMapper.selectOneByExample(bankCodeInfoExample);
		//set客户编号		
		autoCustomerNoMapper.setCustomerId();
		String customerId = autoCustomerNoMapper.getAutoCustomerNo().getCustomerId();
		// TODO Auto-generated method stub
		CustomerInfo customerInfo = new CustomerInfo();
		customerInfo.setCustomerName(form.getCustomerName());
		customerInfo.setServiceRate(form.getServiceRate());
		customerInfo.setId(customerId);
		customerInfo.setInvoiceTitle(form.getInvoiceTitle());
		customerInfo.setTaxpayerType(form.getTaxpayerType());
		customerInfo.setTaxpayerNumber(form.getTaxpayerNumber());
		customerInfo.setCustomerRegAddress(form.getCustomerRegAddress());
		customerInfo.setCustomerOpenName(form.getCustomerOpenName());
		customerInfo.setCustomerOpenBankName(selectBankCodeInfo.getBankName());
		customerInfo.setCustomerOpenBankCode(form.getCustomerOpenBankCode());
		customerInfo.setCustomerOpenBankNo(form.getCustomerOpenBankNo());
		customerInfo.setInvoiceContent(form.getInvoiceContent());
		customerInfo.setReceiver(form.getReceiver());
		customerInfo.setCustomerMobile(form.getCustomerMobile());
		customerInfo.setCustomerAddress(form.getCustomerAddress());
		customerInfo.setDaiZhengId(form.getDaiZhengId());
		customerInfo.setRemark(form.getRemark());	
		customerInfo.setCustomerNickname(form.getCustomerNickname());//客户简称
		customerInfo.setCustomerSubOpenBankName(form.getCustomerSubOpenBankName());//支行名称
		//获取客户log
		String qrCodeLogoImgPicUrl = form.getQrCodeLogoImgPicUrl();
		customerInfo.setQrCodeLogoImgPicUrl(qrCodeLogoImgPicUrl);
		
		
		customerInfo.setInvoiceDemoUrl(form.getInvoiceDemoUrl());//发票样张
		
		//获取upload配置信息
	    UploadInfo uploadInfo = sysConfigService.getUploadInfo(sysDicService.selectSysPaySwitch());
	    //获取微信授权链接
	    AuthorizeInfo authorize=sysConfigService.getAuthorizeInfo(sysDicService.selectSysPaySwitch());
		//生成客户二维码
		String qrCodeImgPicUrl = QRCodeTool.toGenerateQRCode(authorize.getAuthorize()+customerId,uploadInfo,qrCodeLogoImgPicUrl);
		customerInfo.setQrCodeImgPicUrl(qrCodeImgPicUrl);
		customerInfo.setQrCodeImgPostUrl(authorize.getAuthorize()+customerId);	
		customerInfo.setCreateoper(form.getCurrUserName());
		LocalDateTime now = LocalDateTime.now();
		customerInfo.setEntertime(now);
		customerInfo.setCreatetime(now);
		customerInfo.setTel(form.getTel());//增加电话号码
		customerInfoMapper.insertSelective(customerInfo);
		//客户额度管理表insert
		CustomerQuota customerQuota = new CustomerQuota();
		customerQuota.setCustomerId(customerId);
		customerQuota.setCreatetime(now);
		customerQuota.setCreateoper(form.getCurrUserName());
		customerQuotaMapper.insertSelective(customerQuota);
		
		
		//地址入库
		Example postAddressExample = new Example(PostAddress.class);
		postAddressExample.createCriteria().andEqualTo("customerId",customerId);
		List<PostAddress> list = postAddressMapper.selectByExample(postAddressExample);
		if(null != list && list.size()>0) {
			PostAddress postAddress = list.get(0);
			postAddress.setCustomerId(customerId);
			postAddress.setAddress(form.getCustomerAddress());
			postAddress.setMobile(form.getCustomerMobile());
			postAddress.setReceiver(form.getReceiver());
			postAddress.setModifyoper(form.getCurrUserName());
			postAddress.setModifytime(LocalDateTime.now());
			postAddressMapper.updateByPrimaryKeySelective(postAddress);
		}else {
			PostAddress postAddress = new PostAddress();
			postAddress.setCustomerId(customerId);
			postAddress.setAddress(form.getCustomerAddress());
			postAddress.setMobile(form.getCustomerMobile());
			postAddress.setReceiver(form.getReceiver());
			postAddress.setCreateoper(form.getCurrUserName());
			postAddress.setCreatetime(LocalDateTime.now());
			postAddressMapper.insertSelective(postAddress);
		}
	}

	@Override
	@Transactional
	public int edit(CustomerInfoForm form) throws Exception {
		// TODO Auto-generated method stub
		CustomerInfo selectCustomerInfo = customerInfoMapper.selectByPrimaryKey(form.getId());
		if(selectCustomerInfo==null){
			return 0;
		}
		LogUtil.info("原费率:"+selectCustomerInfo.getServiceRate());
		LogUtil.info("现费率:"+form.getServiceRate());
		//当费率变化时
		if(selectCustomerInfo.getServiceRate().compareTo(form.getServiceRate())!=0) {
			//总开票本金金额
			InvoiceQuery invoiceQuery = new InvoiceQuery();
			invoiceQuery.setCustomerId(selectCustomerInfo.getId());
			//累计充值金额   累计充值金额－已开票金额＝可开票金额
			BigDecimal rechargeAmount = daiZhengRechargeMapper.selectTotalRechargeAmount(invoiceQuery);
			LogUtil.info("累计充值金额:"+rechargeAmount);
			
			BigDecimal totalApplyAmount = makeInvoiceDetailMapper.getTotalApplyAmount(invoiceQuery);
			LogUtil.info("总申请金额:"+totalApplyAmount);
			
			//剩余已放款金额
			BigDecimal leftLoanAmount = rechargeAmount.subtract(totalApplyAmount);
			LogUtil.info("剩余已放款金额:"+leftLoanAmount);
			if(leftLoanAmount.compareTo(BigDecimal.ZERO)>0) {
				return 500;
			}
		}
		
		// 根据银行code查询银行名称
		Example bankCodeInfoExample = new Example(BankCodeInfo.class);
		bankCodeInfoExample.createCriteria().andEqualTo("bankCode",form.getCustomerOpenBankCode());			
		BankCodeInfo selectBankCodeInfo = bankCodeInfoMapper.selectOneByExample(bankCodeInfoExample);
		CustomerInfo customerInfo = new CustomerInfo();
		customerInfo.setId(String.valueOf(form.getId()));
		customerInfo.setCustomerName(form.getCustomerName());
		customerInfo.setServiceRate(form.getServiceRate());
		customerInfo.setInvoiceTitle(form.getInvoiceTitle());
		customerInfo.setTaxpayerType(form.getTaxpayerType());
		customerInfo.setTaxpayerNumber(form.getTaxpayerNumber());
		customerInfo.setCustomerRegAddress(form.getCustomerRegAddress());
		customerInfo.setCustomerOpenName(form.getCustomerOpenName());
		customerInfo.setCustomerOpenBankName(selectBankCodeInfo.getBankName());
		customerInfo.setCustomerOpenBankCode(form.getCustomerOpenBankCode());
		customerInfo.setCustomerOpenBankNo(form.getCustomerOpenBankNo());
		customerInfo.setInvoiceContent(form.getInvoiceContent());
		customerInfo.setReceiver(form.getReceiver());
		customerInfo.setCustomerMobile(form.getCustomerMobile());
		customerInfo.setCustomerAddress(form.getCustomerAddress());
		customerInfo.setDaiZhengId(form.getDaiZhengId());
		customerInfo.setRemark(form.getRemark());	
		customerInfo.setCustomerNickname(form.getCustomerNickname());//客户简称
		customerInfo.setCustomerSubOpenBankName(form.getCustomerSubOpenBankName());//支行名称
		
		customerInfo.setInvoiceDemoUrl(form.getInvoiceDemoUrl());//发票样张
		String qrCodeLogoImgPicUrl = form.getQrCodeLogoImgPicUrl();
		if(StringUtils.hasText(qrCodeLogoImgPicUrl)){
			//获取upload配置信息
		    UploadInfo uploadInfo = sysConfigService.getUploadInfo(sysDicService.selectSysPaySwitch());
		    String absoluteQrCodeLogoImgPicUrl=uploadInfo.getDomain()+selectCustomerInfo.getQrCodeLogoImgPicUrl();
		    if(!absoluteQrCodeLogoImgPicUrl.equals(form.getQrCodeLogoImgPicUrl())){
				customerInfo.setQrCodeLogoImgPicUrl(qrCodeLogoImgPicUrl);
				 //获取微信授权链接
			    AuthorizeInfo authorize=sysConfigService.getAuthorizeInfo(sysDicService.selectSysPaySwitch());
				//生成客户二维码
				String qrCodeImgPicUrl = QRCodeTool.toGenerateQRCode(authorize.getAuthorize()+form.getId(),uploadInfo,qrCodeLogoImgPicUrl);

				customerInfo.setQrCodeImgPicUrl(qrCodeImgPicUrl);
				customerInfo.setQrCodeImgPostUrl(authorize.getAuthorize()+form.getId());	
		    }	    
		};	
		customerInfo.setModifyoper(form.getCurrUserName());
		LocalDateTime now = LocalDateTime.now();
		customerInfo.setEntertime(now);
		customerInfo.setModifytime(now);
		customerInfo.setTel(form.getTel());
		customerInfoMapper.updateByPrimaryKeySelective(customerInfo);
		
		//地址入库
//		String customerId = autoCustomerNoMapper.getAutoCustomerNo().getCustomerId();
		String customerId = customerInfo.getId();
		Example postAddressExample = new Example(PostAddress.class);
		postAddressExample.createCriteria().andEqualTo("customerId",customerId);
		List<PostAddress> list = postAddressMapper.selectByExample(postAddressExample);
		if(null != list && list.size()>0) {
			PostAddress postAddress = list.get(0);
			postAddress.setCustomerId(customerId);
			postAddress.setAddress(form.getCustomerAddress());
			postAddress.setMobile(form.getCustomerMobile());
			postAddress.setReceiver(form.getReceiver());
			postAddress.setModifyoper(form.getCurrUserName());
			postAddress.setModifytime(LocalDateTime.now());
			postAddressMapper.updateByPrimaryKeySelective(postAddress);
		}else {
			PostAddress postAddress = new PostAddress();
			postAddress.setCustomerId(customerId);
			postAddress.setAddress(form.getCustomerAddress());
			postAddress.setMobile(form.getCustomerMobile());
			postAddress.setReceiver(form.getReceiver());
			postAddress.setCreateoper(form.getCurrUserName());
			postAddress.setCreatetime(LocalDateTime.now());
			postAddressMapper.insertSelective(postAddress);
		}
		return 1;
	}

}
