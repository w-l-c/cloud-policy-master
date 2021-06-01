package cn.rebornauto.platform.business.service.impl;

import cn.rebornauto.platform.bestSign.BestsignClient;
import cn.rebornauto.platform.bestSign.BestsignOpenApiClient;
import cn.rebornauto.platform.bestSign.entity.BestSignInfo;
import cn.rebornauto.platform.bestSign.entity.GxjjhzhbxyInfo;
import cn.rebornauto.platform.bestSign.entity.XmsmqswjwtsInfo;
import cn.rebornauto.platform.business.dao.*;
import cn.rebornauto.platform.business.entity.*;
import cn.rebornauto.platform.business.form.AgentBankNoInfoForm;
import cn.rebornauto.platform.business.form.AgentCustomerForm;
import cn.rebornauto.platform.business.form.AgentInfoForm;
import cn.rebornauto.platform.business.query.AgentInfoQuery;
import cn.rebornauto.platform.business.query.PaymentStatisticsQuery;
import cn.rebornauto.platform.business.service.*;
import cn.rebornauto.platform.business.vo.*;
import cn.rebornauto.platform.common.Const;
import cn.rebornauto.platform.common.data.request.Pagination;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.common.data.view.ResponseCode;
import cn.rebornauto.platform.common.data.view.UserDicOptionVO;
import cn.rebornauto.platform.common.enums.ContractTypeEnum;
import cn.rebornauto.platform.common.exception.BizExceptionEnum;
import cn.rebornauto.platform.common.exception.BussinessException;
import cn.rebornauto.platform.common.util.StringUtil;
import cn.rebornauto.platform.pay.tonglian.entity.TongLianInfo;
import cn.rebornauto.platform.pay.tonglian.utils.Bankcardverify3Util;
import cn.rebornauto.platform.sys.entity.SysUser;
import cn.rebornauto.platform.upload.entity.UploadInfo;
import cn.rebornauto.platform.utils.PdfUtil;
import cn.rebornauto.platform.utils.ZipUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
/**
 * <p>Title: OauthController</p>  
 * <p>Description:</p>  
 * @author zjl  
 * @date 2019年4月28日
 */
@Service
@Transactional
public class AgentInfoServiceImpl implements AgentInfoService{
	
    private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private	AgentInfoMapper  agentInfoMapper;
	
	@Autowired
	private	CustomerInfoMapper  customerInfoMapper;
	
	@Autowired
	private	AgentCustomerMapper  agentCustomerMapper;
	
	@Autowired
	private	AgentBankNoInfoMapper   agentBankNoInfoMapper;
	
	@Autowired
	private	BankCodeInfoMapper     bankCodeInfoMapper;
	
   @Autowired
   private   BankcardVerify4AllinpayMapper  bankcardVerify4AllinpayMapper;
   
   @Autowired
   private   BankcardVerify3AllinpayMapper  bankcardVerify3AllinpayMapper;
    
   @Autowired
   private   SysDicService  sysDicService;
   
   @Autowired
   private   SysConfigService  sysConfigService;
   
   @Autowired
   private SignatureChannelMapper  signatureChannelMapper;
   
   @Autowired
   private  SignatureChannelConfigService  signatureChannelConfigService;
   
   @Autowired
   private  ContractPdfMapper  contractPdfMapper;
   
   @Autowired
   private  ContractPdfHisMapper  contractPdfHisMapper;

   @Autowired
   private ContractPdfExtraMapper contractPdfExtraMapper;
   
   @Autowired
   private  PersonalCertificateStatusMapper    personalCertificateStatusMapper;
   
   @Autowired
   DaiZhengInfoService daiZhengInfoService;
   
   @Autowired
   AgentBankNoInfoService agentBankNoInfoService;
   
   @Autowired
   PaymentChannelConfigService paymentChannelConfigService;
   
   @Autowired
   CustomerInfoService customerInfoService;
   
   @Override
	public Byte getTypeByQuery(AgentInfoQuery query) {
		// TODO Auto-generated method stub
		Example agentExample = new Example(AgentInfo.class);
		agentExample.createCriteria().andEqualTo("openid", query.getOpenid());
		int agenttCount = agentInfoMapper.selectCountByExample(agentExample);
		if (StringUtils.hasText(query.getCustomerId())) {
		 if (agenttCount > 0) {
			 Example agentCustomerExample = new Example(AgentCustomer.class);
				agentCustomerExample.createCriteria()
				.andEqualTo("agentId",agentInfoMapper.selectOneByExample(agentExample).getId())				
				.andEqualTo("customerId", query.getCustomerId());
				//.andEqualTo("authStatus",Const.AUTH_STATUS_3);
				int agentCustomerCount = agentCustomerMapper.selectCountByExample(agentCustomerExample);	
				if(agentCustomerCount>0){
					return Const.TYPE_USER;
				}
				return Const.TYPE_LOGON;
			}
			return Const.TYPE_LOGON;
		}				
	    if (agenttCount > 0) {
			return Const.TYPE_USER;
		}
		return Const.TYPE_ERROR;
	}

   @Override
	public GzhUserInfoVo selectUserInfoByOpenid(String openid) {
		// TODO Auto-generated method stub
		 GzhAgentInfoVo selectAgentInfo = agentInfoMapper.selectAgentInfoByOpenidNew(openid);
		 GzhUserInfoVo gzhUserInfoVo = new GzhUserInfoVo();
		 List<UserDicOptionVO> list = new ArrayList<UserDicOptionVO>();
		 if(selectAgentInfo!=null){
			// 国籍
			UserDicOptionVO nationalityId = new UserDicOptionVO();
			nationalityId.setKey("nationalityId");
			nationalityId.setValue(selectAgentInfo.getNationalityId() + "");
			nationalityId.setDisabled(Const.DISABLED_INEFFECTIVE);
			list.add(nationalityId);
			// 证件类型
			UserDicOptionVO idType = new UserDicOptionVO();
			idType.setKey("idType");
			idType.setValue(selectAgentInfo.getIdType() + "");
			idType.setDisabled(Const.DISABLED_INEFFECTIVE);
			list.add(idType);

			// 代理人名称
			UserDicOptionVO agentName = new UserDicOptionVO();
			agentName.setKey("agentName");
			agentName.setValue(selectAgentInfo.getAgentName());
			agentName.setDisabled(Const.DISABLED_INEFFECTIVE);
			list.add(agentName);

			// 身份证
			UserDicOptionVO agentIdcardno = new UserDicOptionVO();
			agentIdcardno.setKey("agentIdcardno");
			agentIdcardno.setValue(selectAgentInfo.getAgentIdcardno());
			agentIdcardno.setDisabled(Const.DISABLED_INEFFECTIVE);
			list.add(agentIdcardno);

			 // 手机号
			 UserDicOptionVO agentMobile = new UserDicOptionVO();
			 agentMobile.setKey("agentMobile");
			 agentMobile.setValue(selectAgentInfo.getAgentMobile());
			 agentMobile.setDisabled(Const.DISABLED_VALID);
			 list.add(agentMobile);

			// 银行代码
			UserDicOptionVO agentOpenBankCode = new UserDicOptionVO();
			agentOpenBankCode.setKey("agentOpenBankCode");
			agentOpenBankCode.setValue(selectAgentInfo.getAgentOpenBankCode());
			agentOpenBankCode.setDisabled(Const.DISABLED_VALID);
			list.add(agentOpenBankCode);
			
			// 银行卡号
			UserDicOptionVO agentOpenBankNo = new UserDicOptionVO();
			agentOpenBankNo.setKey("agentOpenBankNo");
			agentOpenBankNo.setValue(selectAgentInfo.getAgentOpenBankNo());
			agentOpenBankNo.setDisabled(Const.DISABLED_VALID);
			list.add(agentOpenBankNo);			
			UploadInfo  uploadInfo=sysConfigService.getUploadInfo(sysDicService.selectSysPaySwitch());

	
			// 身份证正面         
			UserDicOptionVO frontIdcardPicUrl = new UserDicOptionVO();
			frontIdcardPicUrl.setKey("frontIdcardPicUrl");	
			frontIdcardPicUrl.setValue(uploadInfo.getDomain()+selectAgentInfo.getFrontIdcardPicUrl());
			frontIdcardPicUrl.setPublicName(selectAgentInfo.getFrontIdcardPicUrl());
			frontIdcardPicUrl.setDisabled(Const.DISABLED_VALID);
			list.add(frontIdcardPicUrl);			
						
			// 身份证反面        
			UserDicOptionVO backIdcardPicUrl = new UserDicOptionVO();
			backIdcardPicUrl.setKey("backIdcardPicUrl");
			backIdcardPicUrl.setValue(uploadInfo.getDomain()+selectAgentInfo.getBackIdcardPicUrl());
			backIdcardPicUrl.setPublicName(selectAgentInfo.getBackIdcardPicUrl());
			backIdcardPicUrl.setDisabled(Const.DISABLED_VALID);
			list.add(backIdcardPicUrl);

			 // 省
			 UserDicOptionVO agentProvince = new UserDicOptionVO();
			 agentProvince.setKey("agentProvince");
			 agentProvince.setValue(selectAgentInfo.getAgentProvince());
			 agentProvince.setDisabled(Const.DISABLED_VALID);
			 list.add(agentProvince);

			 // 市
			 UserDicOptionVO agentCity = new UserDicOptionVO();
			 agentCity.setKey("agentCity");
			 agentCity.setValue(selectAgentInfo.getAgentCity());
			 agentCity.setDisabled(Const.DISABLED_VALID);
			 list.add(agentCity);

			 // 地址
			 UserDicOptionVO agentAddress = new UserDicOptionVO();
			 agentAddress.setKey("agentAddress");
			 agentAddress.setValue(selectAgentInfo.getAgentAddress());
			 agentAddress.setDisabled(Const.DISABLED_VALID);
			 list.add(agentAddress);

			gzhUserInfoVo.setInfo(list);
			gzhUserInfoVo.setStatus(Const.OLD_USER_STATUS);
			return gzhUserInfoVo;
		 }
		 gzhUserInfoVo.setInfo(list);
		 gzhUserInfoVo.setStatus(Const.NEW_USER_STATUS); 
	    return gzhUserInfoVo;
	}


	
	@Override
	public List<GzhAgentCustomerVo> selectAgentCustomerListByAgentId(int agentId) {
   		List<AgentCustomer> list = agentCustomerMapper.listByAgentId(agentId);
		List<GzhAgentCustomerVo> gzhAgentCustomerVos = agentInfoMapper.selectAgentCustomerListByAgentIdNew(agentId,list.get(0).getCustomerId());
		UploadInfo  uploadInfo=sysConfigService.getUploadInfo(sysDicService.selectSysPaySwitch());
		 gzhAgentCustomerVos.forEach(gzhAgentCustomerVo -> {
			 if (StringUtils.hasText(gzhAgentCustomerVo.getUrl())) {
				 gzhAgentCustomerVo.setUrl(uploadInfo.getDomain()+gzhAgentCustomerVo.getUrl());
			       }
			 });		
		   return gzhAgentCustomerVos;	
  }
	
	@Override
	public GzhAgentInfoVo selectAgentInfoByOpenid(String openid) {
		// TODO Auto-generated method stub	
          GzhAgentInfoVo gzhAgentInfoVo = agentInfoMapper.selectAgentInfoByOpenid(openid);
         List<GzhAgentCustomerVo> gzhCustomerVos = agentInfoMapper.selectAgentCustomerListByAgentId(gzhAgentInfoVo.getId());
         for (GzhAgentCustomerVo gzhCustomerVo : gzhCustomerVos) {
			if(Const.SIGN_STATUS_1==gzhCustomerVo.getSignStatus()||Const.SIGN_STATUS_3==gzhCustomerVo.getSignStatus()){
				gzhAgentInfoVo.setContractStatus(Const.SIGN_STATUS_1);
			}
		}
         return gzhAgentInfoVo;
	}
	
	@Override
	public void addAgentInfo(AgentInfoForm form) throws Exception {
		// TODO Auto-generated method stub
		Example  agentInfoExample = new Example(AgentInfo.class);
		agentInfoExample.createCriteria().andEqualTo("agentIdcardno",form.getAgentIdcardno());	
		AgentInfo selectAgentInfo = agentInfoMapper.selectOneByExample(agentInfoExample);
		
		//根据银行code查询银行名称
		Example  bankCodeInfoExample = new Example(BankCodeInfo.class);
		bankCodeInfoExample.createCriteria().andEqualTo("bankCode",form.getAgentOpenBankCode());
		BankCodeInfo selectBankCodeInfo = bankCodeInfoMapper.selectOneByExample(bankCodeInfoExample);
		if(selectAgentInfo==null){
			//录入代理人信息
			AgentInfo insertAgentInfo=new AgentInfo();
			insertAgentInfo.setNationalityId(form.getNationalityId());
			insertAgentInfo.setIdType(form.getIdType());
			insertAgentInfo.setAgentName(form.getAgentName());
			insertAgentInfo.setAgentIdcardno(form.getAgentIdcardno());
			insertAgentInfo.setAccount(form.getAgentIdcardno());
			insertAgentInfo.setAgentMobile(form.getAgentMobile());
			insertAgentInfo.setFrontIdcardPicUrl(form.getFrontIdcardPicUrl());
			insertAgentInfo.setBackIdcardPicUrl(form.getBackIdcardPicUrl());
			insertAgentInfo.setOpenid(form.getOpenid());
			insertAgentInfo.setAgentAddress(form.getAgentAddress());
			insertAgentInfo.setAgentProvince(form.getAgentProvince());
			insertAgentInfo.setAgentCity(form.getAgentCity());
			insertAgentInfo.setCreatetime(LocalDateTime.now());
			agentInfoMapper.insertSelective(insertAgentInfo);		
				
			//录入代理人客户中间表信息
			AgentCustomer insertAgentCustomer = new AgentCustomer();
			insertAgentCustomer.setCustomerId(form.getCustomerId());
			insertAgentCustomer.setAgentId(insertAgentInfo.getId());
			insertAgentCustomer.setCreatetime(LocalDateTime.now());
			agentCustomerMapper.insertSelective(insertAgentCustomer);
			
			//录入银行卡信息
			AgentBankNoInfo insertAgentBankNoInfo = new AgentBankNoInfo();
			insertAgentBankNoInfo.setAgentId(insertAgentInfo.getId());
			insertAgentBankNoInfo.setAgentOpenBankNo(form.getAgentOpenBankNo());
			insertAgentBankNoInfo.setAgentOpenBankName(selectBankCodeInfo.getBankName());
			insertAgentBankNoInfo.setAgentOpenBankCode(form.getAgentOpenBankCode());
			//insertAgentBankNoInfo.setAgentOpenBankMobile(form.getAgentMobile());
			insertAgentBankNoInfo.setCreatetime(LocalDateTime.now());
			agentBankNoInfoMapper.insertSelective(insertAgentBankNoInfo);

			//初始化contractpdf表以及新表数据
			LocalDateTime now = LocalDateTime.now();
			ContractPdf contractPdf = new ContractPdf();
			contractPdf.setAgentId(insertAgentInfo.getId());
			contractPdf.setDaiZhengId(daiZhengInfoService.getDaiZhengId());
			contractPdf.setDataStatus(Const.DATA_STATUS_1);
			contractPdf.setCreateoper(form.getAgentName());
			contractPdf.setCreatetime(now);
			contractPdfMapper.insertSelective(contractPdf);

			ContractPdfExtra contractPdfExtra1 = new ContractPdfExtra();
			contractPdfExtra1.setAgentId(insertAgentInfo.getId());
			contractPdfExtra1.setDaiZhengId(daiZhengInfoService.getDaiZhengId());
			contractPdfExtra1.setDataStatus(Const.DATA_STATUS_1);
			contractPdfExtra1.setCreateoper(form.getAgentName());
			contractPdfExtra1.setCreatetime(now);
			contractPdfExtra1.setType(ContractTypeEnum.CONTRACT_TYPE_1.getIndex());
			contractPdfExtra1.setStatus(Const.SIGN_STATUS_1);
			contractPdfExtraMapper.insertSelective(contractPdfExtra1);

			ContractPdfExtra contractPdfExtra2 = new ContractPdfExtra();
			contractPdfExtra2.setAgentId(insertAgentInfo.getId());
			contractPdfExtra2.setDaiZhengId(daiZhengInfoService.getDaiZhengId());
			contractPdfExtra2.setDataStatus(Const.DATA_STATUS_1);
			contractPdfExtra2.setCreateoper(form.getAgentName());
			contractPdfExtra2.setCreatetime(now);
			contractPdfExtra2.setType(ContractTypeEnum.CONTRACT_TYPE_2.getIndex());
			contractPdfExtra2.setStatus(Const.SIGN_STATUS_1);
			contractPdfExtraMapper.insertSelective(contractPdfExtra2);

			//申请个人证书需要的date
			 SignInfoVo signInfoVo = new SignInfoVo();
			 signInfoVo.setAgentId(insertAgentInfo.getId());
			 signInfoVo.setAgentAccount(form.getAgentIdcardno());
			 signInfoVo.setAgentIdcardno(form.getAgentIdcardno());
			 signInfoVo.setAgentName(form.getAgentName());
			//获取尚尚签基础配置信息
	        BestSignInfo bestSignInfo=sysConfigService.getBestSignInfo(sysDicService.selectSysPaySwitch());     
	        BestsignOpenApiClient openApiClient= new BestsignOpenApiClient(bestSignInfo.getDeveloperId(),bestSignInfo.getPrivateKey(),bestSignInfo.getServerHost());									
			//查询个人证书状态表
			Example example = new Example(PersonalCertificateStatus.class);
			example.createCriteria().andEqualTo("agentId",insertAgentInfo.getId());
			PersonalCertificateStatus selectPersonalCertificateStatus = personalCertificateStatusMapper.selectOneByExample(example);
			 //创建尚尚签个人状态
		    PersonalCertificateStatus  personalCertificateStatus= new PersonalCertificateStatus();
			if(null==selectPersonalCertificateStatus||null==selectPersonalCertificateStatus.getStatus()||Const.APPLY_CERT_STATUS4.equals(selectPersonalCertificateStatus.getStatus())||Const.APPLY_CERT_STATUS6.equals(selectPersonalCertificateStatus.getStatus())||Const.APPLY_CERT_STATUS0.equals(selectPersonalCertificateStatus.getStatus())){
			 personalCertificateStatus.setAgentId(insertAgentInfo.getId());
			//申请个人证书
			 BestsignClient.personalUserReg(openApiClient,signInfoVo,personalCertificateStatus,form.getIdType());	
			//修改个人证书申请接口状态表状态
			changePersonalCertificateStatus(selectPersonalCertificateStatus,personalCertificateStatus);
			}
		}else{		
			//判断代理人是否是从别的库copy过来的，如果有的话替换openid
			if(StringUtils.isEmpty(selectAgentInfo.getOpenid())||!form.getOpenid().equals(selectAgentInfo.getOpenid())){
				selectAgentInfo.setOpenid(form.getOpenid());
				//根据agentId查询所有的签约数据
				Example agentCustomerExample = new Example(AgentCustomer.class);
				agentCustomerExample.createCriteria()                     
				.andEqualTo("agentId", selectAgentInfo.getId());		
				List<AgentCustomer> agentCustomers = agentCustomerMapper.selectByExample(agentCustomerExample);
				for (AgentCustomer agentCustomer : agentCustomers) {
					agentCustomer.setSignStatus(Const.SIGN_STATUS_1);
					agentCustomerMapper.updateByPrimaryKeySelective(agentCustomer);
				}
			}	
			//身份证图片修改
			selectAgentInfo.setFrontIdcardPicUrl(form.getFrontIdcardPicUrl());
			selectAgentInfo.setBackIdcardPicUrl(form.getBackIdcardPicUrl());
			agentInfoMapper.updateByPrimaryKeySelective(selectAgentInfo);
			//判断是否是新客户，是绑定代理人跟客户关系
			Example agentCustomerExample = new Example(AgentCustomer.class);
			agentCustomerExample.createCriteria()                     
			.andEqualTo("customerId",form.getCustomerId())
			.andEqualTo("agentId", selectAgentInfo.getId());		
			AgentCustomer selectAgentCustomer = agentCustomerMapper.selectOneByExample(agentCustomerExample);
			if(selectAgentCustomer==null){	
			AgentCustomer insertAgentCustomer = new AgentCustomer();
			insertAgentCustomer.setCustomerId(form.getCustomerId());
			insertAgentCustomer.setAgentId(selectAgentInfo.getId());
			insertAgentCustomer.setCreatetime(LocalDateTime.now());				
			//根据customerId查询代征主体id
			CustomerInfo customerInfo = new CustomerInfo();
			customerInfo.setId(form.getCustomerId());
			CustomerInfo info = customerInfoMapper.selectOne(customerInfo);
		    //根据代理人id查询查询全部的客户代理人关系
			SignStatusInfoVo signStatusInfoVo = agentInfoMapper.selectSignStatus(selectAgentInfo.getId(), info.getDaiZhengId());
			if(null!=signStatusInfoVo&&null!=signStatusInfoVo.getSignStatus()){
				insertAgentCustomer.setSignStatus(signStatusInfoVo.getSignStatus());
				//是否默认认证成功
				if(signStatusInfoVo.getSignStatus()==Const.SIGN_STATUS_2){					
					insertAgentCustomer.setSignTime(signStatusInfoVo.getSignTime());
					insertAgentCustomer.setContractNumber(signStatusInfoVo.getContractNumber());
			    	insertAgentCustomer.setAuthStatus(customerInfoMapper.selectByPrimaryKey(form.getCustomerId()).getIsAuth());
				}
			}
			agentCustomerMapper.insertSelective(insertAgentCustomer);			
		 }
			//判断银行卡信息是否新增
			Example agentBankNoInfoExample = new Example(AgentBankNoInfo.class);
			agentBankNoInfoExample.createCriteria()
		    .andEqualTo("agentId",selectAgentInfo.getId())
			.andEqualTo("agentOpenBankNo",form.getAgentOpenBankNo());	
			int agentBankNoInfoCount= agentBankNoInfoMapper.selectCountByExample(agentBankNoInfoExample);
			if(agentBankNoInfoCount==0){
				//录入银行卡信息
				AgentBankNoInfo insertAgentBankNoInfo = new AgentBankNoInfo();
				insertAgentBankNoInfo.setAgentId(selectAgentInfo.getId());
				insertAgentBankNoInfo.setAgentOpenBankNo(form.getAgentOpenBankNo());
				insertAgentBankNoInfo.setAgentOpenBankName(selectBankCodeInfo.getBankName());
				insertAgentBankNoInfo.setAgentOpenBankCode(form.getAgentOpenBankCode());
				insertAgentBankNoInfo.setCreatetime(LocalDateTime.now());
				agentBankNoInfoMapper.insertSelective(insertAgentBankNoInfo);						
			}			
		}
	}


	@Override
	public int countByQuery(AgentInfoQuery query) {
		// TODO Auto-generated method stub
		return agentInfoMapper.countByQuery(query);
	}

	@Override
	public List<AgentVo> pageQuery(Pagination pagination,
			AgentInfoQuery query) {
		// TODO Auto-generated method stub
		  List<AgentVo> agentVos = agentInfoMapper.pageQuery(pagination,query);
		  UploadInfo  uploadInfo=sysConfigService.getUploadInfo(sysDicService.selectSysPaySwitch());
		  agentVos.forEach(agentVo -> {
			 agentVo.setFrontIdcardPicUrl((uploadInfo.getDomain()+agentVo.getFrontIdcardPicUrl()));
			 agentVo.setBackIdcardPicUrl((uploadInfo.getDomain()+agentVo.getBackIdcardPicUrl()));
			 agentVo.setBankList(agentBankNoInfoMapper.selectAgentBankNoListByAgentId(agentVo.getAgentId()));
			 agentVo.setContractUrl(uploadInfo.getDomain()+ agentVo.getContractUrl());
			 if(!StringUtils.isEmpty(agentVo.getXmsmqswjwtsUrl())) {
				 agentVo.setXmsmqswjwtsUrl(uploadInfo.getDomain() + agentVo.getXmsmqswjwtsUrl());
			 }
			  if(!StringUtils.isEmpty(agentVo.getGxjjhzhbxyUrl())) {
				  agentVo.setGxjjhzhbxyUrl(uploadInfo.getDomain() + agentVo.getGxjjhzhbxyUrl());
			  }
			});
		 return agentVos;
	}

	
	@Override
	public int edit(AgentInfoForm form) {
		// TODO Auto-generated method stub
		Example example = new Example(AgentCustomer.class);
		example.createCriteria().andEqualTo("agentId", form.getAgentId())
		.andEqualTo("customerId", form.getCustomerId());
		int count = agentCustomerMapper.selectCountByExample(example);
		if(count<1){
			return 0;
		}
		AgentCustomer record = new AgentCustomer();
		record.setAuthStatus(form.getAuthStatus());
		record.setSignStatus(form.getSignStatus());
		record.setModifyoper(form.getCurrUserName());
		record.setModifytime(LocalDateTime.now());
		record.setDataStatus(form.getDataStatus());
		agentCustomerMapper.updateByExampleSelective(record, example);	
		
		if(new Integer(Const.SIGN_STATUS_5).equals(form.getSignStatus())){	
			Example contractPdfExample = new Example(ContractPdf.class);
			contractPdfExample
					.createCriteria()
					.andEqualTo("agentId", form.getAgentId())
					.andEqualTo(
							"daiZhengId",
							customerInfoMapper.selectByPrimaryKey(
									form.getCustomerId()).getDaiZhengId());
			ContractPdf selectContractPdf = contractPdfMapper
					.selectOneByExample(contractPdfExample);
			ContractPdfHis contractPdfHis = new ContractPdfHis();
			BeanUtils.copyProperties(selectContractPdf, contractPdfHis);
			contractPdfHis.setId(null);
			contractPdfHisMapper.insertSelective(contractPdfHis);
			//todo
		}
		return 1;
	}
	
	   @Override
	   public Response addBankCardNo(AgentInfoForm form, TongLianInfo tongLianInfo) {
		   AgentInfo agentInfoQuery = new AgentInfo();
		   agentInfoQuery.setId(form.getAgentId());
		   AgentInfo agentInfo = agentInfoMapper.selectOne(agentInfoQuery);
		   form.setAgentIdcardno(agentInfo.getAgentIdcardno());
		   
		   //根据银行code查询银行名称
			Example  bankCodeInfoExample = new Example(BankCodeInfo.class);
		    bankCodeInfoExample.createCriteria().andEqualTo("bankCode",form.getAgentOpenBankCode());
			BankCodeInfo selectBankCodeInfo = bankCodeInfoMapper.selectOneByExample(bankCodeInfoExample);	
			//3要素填写
		    BankcardVerify3Allinpay bankcardVerify3Allinpay = new	BankcardVerify3Allinpay();   
		    bankcardVerify3Allinpay.setRealname(form.getAgentName());
		    bankcardVerify3Allinpay.setIdcard(form.getAgentIdcardno());
		    bankcardVerify3Allinpay.setBankname(selectBankCodeInfo.getBankName());
		    bankcardVerify3Allinpay.setBankcode(selectBankCodeInfo.getBankCode());
		    bankcardVerify3Allinpay.setBankcard(form.getAgentOpenBankNo());
			
		    //判断3要素是否齐全
	        if (!Bankcardverify3Util.intactBankcardVerify(bankcardVerify3Allinpay)) {
				return Response.factory().code(1).message("你的认证信息缺少,请补充完整!");
	        }

	        //判断3要素是否与数据库匹配
	        Example bankcardVerify3AllinpayExample = new Example(BankcardVerify3Allinpay.class);
	        bankcardVerify3AllinpayExample.createCriteria().andEqualTo("bankcard",bankcardVerify3Allinpay.getBankcard());
	        List<BankcardVerify3Allinpay> list = bankcardVerify3AllinpayMapper.selectByExample(bankcardVerify3AllinpayExample);
	        BankcardVerify3Allinpay v = null;
	        
	        if(list!=null && list.size()==1){
	            v = list.get(0);
	            boolean same = Bankcardverify3Util.sameBankcardVerify(bankcardVerify3Allinpay, v);
	            //1.判断信息是否一致,如果一致，返回数据库的验证结果
	            if(same){
	            	if(v.getVerifystatus()==0){
	            		return insertAgentBankNo(form, selectBankCodeInfo);
	            	}else{
	        			return Response.factory().code(v.getVerifystatus()).message("你的银行卡信息校验错误,请重新输入!");       	
	            	}
	            }
	            //2.如果不一致，并且数据库中的为验证通过的信息，直接返回false
	            if(!same &&  v.getVerifystatus()!=null && v.getVerifystatus()==0){
	    			return Response.factory().code(1).message("你的银行卡信息校验错误,请重新输入!");       	
	            }
	        }	
	        //调用通联3要素校验
	        
	        Bankcardverify3Util.verify_allinpay(bankcardVerify3Allinpay,tongLianInfo,agentInfo.getIdType());
	        if(v!=null){
	      	  bankcardVerify3Allinpay.setId(v.getId());
	      	  bankcardVerify3Allinpay.setGmtModified(LocalDateTime.now());
	      	  bankcardVerify3AllinpayMapper.updateByPrimaryKeySelective(bankcardVerify3Allinpay);
	        }else{
	      	  bankcardVerify3Allinpay.setGmtCreate(LocalDateTime.now());
	            bankcardVerify3AllinpayMapper.insertSelective(bankcardVerify3Allinpay);
	        }        
	        if(bankcardVerify3Allinpay.getVerifystatus()!=0){
	  		   return Response.factory().code(bankcardVerify3Allinpay.getVerifystatus()).message("你的银行卡信息校验错误,请重新输入!");       	
	        } 
	        return	insertAgentBankNo(form, selectBankCodeInfo);		
	}

 
	
	private Response insertAgentBankNo(AgentInfoForm form,BankCodeInfo selectBankCodeInfo) {		
		//录入银行卡信息
		Example agentBankNoInfoExample = new Example(AgentBankNoInfo.class);
		agentBankNoInfoExample.createCriteria()
	    .andEqualTo("agentId",form.getAgentId())
		.andEqualTo("agentOpenBankNo",form.getAgentOpenBankNo());	
		int count= agentBankNoInfoMapper.selectCountByExample(agentBankNoInfoExample);
		if(count<1){
			AgentBankNoInfo insertAgentBankNoInfo = new AgentBankNoInfo();
			insertAgentBankNoInfo.setAgentId(form.getAgentId());
			insertAgentBankNoInfo.setAgentOpenBankNo(form.getAgentOpenBankNo());
			insertAgentBankNoInfo.setAgentOpenBankName(selectBankCodeInfo.getBankName());
			insertAgentBankNoInfo.setAgentOpenBankCode(form.getAgentOpenBankCode());
			insertAgentBankNoInfo.setCreateoper(form.getCurrUserName());
			insertAgentBankNoInfo.setCreatetime(LocalDateTime.now());
			agentBankNoInfoMapper.insertSelective(insertAgentBankNoInfo);						
		}	
		return Response.ok();
	}
/*
	@Override
	public String agentSignCustomer(AgentCustomerForm form) throws Exception {
		  // TODO Auto-generated method stub	 
		    //获取签约信息
		    SignInfoVo signInfoVo=agentInfoMapper.getSignInfo(form.getId());	
		    List<AuthInfoVo>auths = agentInfoMapper.selectAgentCustomerIds( signInfoVo.getAgentId(), signInfoVo.getDaiZhengId());
		   //获取尚尚签基础配置信息
            BestSignInfo bestSignInfo=sysConfigService.getBestSignInfo(sysDicService.selectSysPaySwitch());     
            BestsignOpenApiClient openApiClient= new BestsignOpenApiClient(bestSignInfo.getDeveloperId(),bestSignInfo.getPrivateKey(),bestSignInfo.getServerHost());	
	        //企业共享经济合作伙伴协议合同签章配置信息    
	        GxjjhzhbxyInfo gxjjhzhbxyInfo = signatureChannelConfigService.getGxjjhzhbxyInfo(customerInfoMapper.selectByPrimaryKey(agentCustomerMapper.selectByPrimaryKey(form.getId()).getCustomerId()).getDaiZhengId(), signatureChannelMapper.selectAll().get(0).getId(), sysDicService.selectSysPaySwitch());									
	        //查询个人证书状态表
			Example example = new Example(PersonalCertificateStatus.class);
			example.createCriteria().andEqualTo("agentId",signInfoVo.getAgentId());
			PersonalCertificateStatus selectPersonalCertificateStatus = personalCertificateStatusMapper.selectOneByExample(example);
			 //创建尚尚签个人状态
		    PersonalCertificateStatus  personalCertificateStatus= new PersonalCertificateStatus();
			if(null==selectPersonalCertificateStatus||null==selectPersonalCertificateStatus.getStatus()||Const.APPLY_CERT_STATUS4.equals(selectPersonalCertificateStatus.getStatus())||Const.APPLY_CERT_STATUS6.equals(selectPersonalCertificateStatus.getStatus())||Const.APPLY_CERT_STATUS0.equals(selectPersonalCertificateStatus.getStatus())){
			 personalCertificateStatus.setAgentId(signInfoVo.getAgentId());
			//申请个人证书
			 AgentInfo agentInfo = agentInfoMapper.selectByPrimaryKey(signInfoVo.getAgentId());
			 BestsignClient.personalUserReg(openApiClient,signInfoVo,personalCertificateStatus,agentInfo.getIdType());	
			 if(Const.APPLY_CERT_STATUS1.equals(personalCertificateStatus.getStatus())||Const.APPLY_CERT_STATUS2.equals(personalCertificateStatus.getStatus())||Const.APPLY_CERT_STATUS3.equals(personalCertificateStatus.getStatus())){	 
			 BestsignClient.applyCertStatus(openApiClient, personalCertificateStatus);   
			 }
			 personalCertificateStatus.setModifytime(LocalDateTime.now());
			 personalCertificateStatusMapper.updateByPrimaryKeySelective(personalCertificateStatus);	  
		 }      
	      if(new Integer(Const.SIGN_STATUS_3).equals(signInfoVo.getSignStatus())||new Integer(Const.SIGN_STATUS_5).equals(signInfoVo.getSignStatus())){			  
		         //开始签章
		          return toSign(openApiClient,bestSignInfo,gxjjhzhbxyInfo,signInfoVo,auths);	
		   }else{	 
			       String  manualSigningUrl=contractPdfMapper.selectManualSigningUrl(signInfoVo.getAgentId(),signInfoVo.getDaiZhengId());
			   if(StringUtils.isEmpty(manualSigningUrl)){				  
				   //开始签章
				   return toSign(openApiClient,bestSignInfo,gxjjhzhbxyInfo,signInfoVo,auths);	
			   }
			   return manualSigningUrl;
		 }

  }
*/
/*
    private String toSign(BestsignOpenApiClient openApiClient,BestSignInfo bestSignInfo,GxjjhzhbxyInfo gxjjhzhbxyInfo, SignInfoVo signInfoVo, List<AuthInfoVo>auths)throws Exception {
	    //合同编号
	   long currentTimeMillis = System.currentTimeMillis();
	   //填好合同模板数据
		String gxjjhzhbxyUrl=new PdfUtil().gxjjhzhbxyFillValue(bestSignInfo,gxjjhzhbxyInfo,signInfoVo,currentTimeMillis);
		if( "500".equals(gxjjhzhbxyUrl) ){
			throw	new	BussinessException(BizExceptionEnum.GXJJHZHBXY_TO_FILLVALUE);
		}
		//企业签章
		logger.debug("***********代理人id:"+signInfoVo.getAgentId()+"与代征主体id:"+signInfoVo.getDaiZhengId()+"签章开始**********");
		com.alibaba.fastjson.JSONObject gxjjhzhbxyUploadRequestBody = PdfUtil.wrapUploadRequestBody(bestSignInfo.getAccount(),gxjjhzhbxyUrl,gxjjhzhbxyInfo.getGxjjhzhbxyName(),gxjjhzhbxyInfo.getGxjjhzhbxyPageSize());
		String gxjjhzhbxyId = BestsignClient.uploadContract(gxjjhzhbxyUploadRequestBody,openApiClient);
		//合同签章的位置
		com.alibaba.fastjson.JSONObject gxjjhzhbxyCertRequestBody = PdfUtil.wrapCertRequestBody(gxjjhzhbxyId,signInfoVo.getDaiZhengAccount(),gxjjhzhbxyInfo.getGxjjhzhbxyX(),gxjjhzhbxyInfo.getGxjjhzhbxyY(),gxjjhzhbxyInfo.getGxjjhzhbxyPageNum(),signInfoVo.getImageName());
		BestsignClient.cert(gxjjhzhbxyCertRequestBody,openApiClient);
		String returnUrl=bestSignInfo.getCallbackInterface()+"?contractId="+gxjjhzhbxyId;
	    //个人签名的位置
		com.alibaba.fastjson.JSONObject gxjjhzhbxySendRequestBody=PdfUtil.wrapSendRequestBody(gxjjhzhbxyId,signInfoVo.getAgentAccount(),gxjjhzhbxyInfo.getPersongxjjhzhbxyX(),gxjjhzhbxyInfo.getPersongxjjhzhbxyY(),gxjjhzhbxyInfo.getGxjjhzhbxyPageNum(),returnUrl);
		//发送合同（即手动签，指定图片大小）
		String manualSigningUrl = BestsignClient.send(gxjjhzhbxySendRequestBody, openApiClient);
		// 合同表
	   Example contractPdfExample = new Example(ContractPdf.class);
		  contractPdfExample.createCriteria()
		 .andEqualTo("agentId", signInfoVo.getAgentId())
		 .andEqualTo("daiZhengId", signInfoVo.getDaiZhengId())
		 .andEqualTo("dataStatus",Const.STATUS_1);
		ContractPdf selectContractPdf = contractPdfMapper.selectOneByExample(contractPdfExample);
		ContractPdf contractPdf = new ContractPdf();
		contractPdf.setAgentId(signInfoVo.getAgentId());
		contractPdf.setDaiZhengId(signInfoVo.getDaiZhengId());
		contractPdf.setContractNumber(signInfoVo.getContractNumber());
		contractPdf.setManualSigningUrl(manualSigningUrl);
		contractPdf.setManualSigningTime(LocalDateTime.now());
		contractPdf.setSignTime(signInfoVo.getSignTime());
		contractPdf.setGxjjhzhbxyId(gxjjhzhbxyId);
		contractPdf.setCreatetime(LocalDateTime.now());
		if (selectContractPdf == null) {
			contractPdfMapper.insertSelective(contractPdf);
		} else {
			contractPdfMapper.updateByExampleSelective(contractPdf, contractPdfExample);
		}
		return manualSigningUrl;
	}*/
	@Override
	public String agentSignCustomer(AgentCustomerForm form) throws Exception {
   		if(!ContractTypeEnum.contains(form.getType())){
   			throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
		}
		  // TODO Auto-generated method stub
		//获取签约信息
		SignInfoVo signInfoVo=agentInfoMapper.getSignInfo(form.getId(),form.getType());
		List<AuthInfoVo>auths = agentInfoMapper.selectAgentCustomerIds( signInfoVo.getAgentId(), signInfoVo.getDaiZhengId());
		//获取尚尚签基础配置信息
		BestSignInfo bestSignInfo=sysConfigService.getBestSignInfo(sysDicService.selectSysPaySwitch());
		BestsignOpenApiClient openApiClient= new BestsignOpenApiClient(bestSignInfo.getDeveloperId(),bestSignInfo.getPrivateKey(),bestSignInfo.getServerHost());

		//查询个人证书状态表
		Example example = new Example(PersonalCertificateStatus.class);
		example.createCriteria().andEqualTo("agentId",signInfoVo.getAgentId());
		PersonalCertificateStatus selectPersonalCertificateStatus = personalCertificateStatusMapper.selectOneByExample(example);
		//创建尚尚签个人状态
		PersonalCertificateStatus  personalCertificateStatus= new PersonalCertificateStatus();
		if(null==selectPersonalCertificateStatus||null==selectPersonalCertificateStatus.getStatus()||Const.APPLY_CERT_STATUS4.equals(selectPersonalCertificateStatus.getStatus())||Const.APPLY_CERT_STATUS6.equals(selectPersonalCertificateStatus.getStatus())||Const.APPLY_CERT_STATUS0.equals(selectPersonalCertificateStatus.getStatus())){
			 personalCertificateStatus.setAgentId(signInfoVo.getAgentId());
			//申请个人证书
			 AgentInfo agentInfo = agentInfoMapper.selectByPrimaryKey(signInfoVo.getAgentId());
			 BestsignClient.personalUserReg(openApiClient,signInfoVo,personalCertificateStatus,agentInfo.getIdType());
			 if(Const.APPLY_CERT_STATUS1.equals(personalCertificateStatus.getStatus())||Const.APPLY_CERT_STATUS2.equals(personalCertificateStatus.getStatus())||Const.APPLY_CERT_STATUS3.equals(personalCertificateStatus.getStatus())){
			 BestsignClient.applyCertStatus(openApiClient, personalCertificateStatus);
			 }
			 personalCertificateStatus.setModifytime(LocalDateTime.now());
			 personalCertificateStatusMapper.updateByPrimaryKeySelective(personalCertificateStatus);
		 }
	      if(new Integer(Const.SIGN_STATUS_3).equals(signInfoVo.getSignStatus())||new Integer(Const.SIGN_STATUS_5).equals(signInfoVo.getSignStatus())){
		      //开始签章
			  return sign(form,openApiClient,bestSignInfo,signInfoVo,auths);
		   }else{
			       String  manualSigningUrl=contractPdfExtraMapper.selectManualSigningUrl(signInfoVo.getAgentId(),signInfoVo.getDaiZhengId(),form.getType());
			   if(StringUtils.isEmpty(manualSigningUrl)){
				   //开始签章
				   return sign(form,openApiClient,bestSignInfo,signInfoVo,auths);
			   }
			   return manualSigningUrl;
		 }

  }
  private String sign (AgentCustomerForm form,BestsignOpenApiClient openApiClient,BestSignInfo bestSignInfo,SignInfoVo signInfoVo,List<AuthInfoVo>auths)throws Exception{
	  if(ContractTypeEnum.CONTRACT_TYPE_1.getIndex()==form.getType()) {
		  GxjjhzhbxyInfo gxjjhzhbxyInfo = signatureChannelConfigService.getGxjjhzhbxyInfo(customerInfoMapper.selectByPrimaryKey(agentCustomerMapper.selectByPrimaryKey(form.getId()).getCustomerId()).getDaiZhengId(), signatureChannelMapper.selectAll().get(0).getId(), sysDicService.selectSysPaySwitch());
		  return toSignNew(openApiClient, bestSignInfo, gxjjhzhbxyInfo, signInfoVo, auths);
	  }else if(ContractTypeEnum.CONTRACT_TYPE_2.getIndex()==form.getType()){
		  XmsmqswjwtsInfo xmsmqswjwtsInfo = signatureChannelConfigService.getXmsmqswjwtsInfo(customerInfoMapper.selectByPrimaryKey(agentCustomerMapper.selectByPrimaryKey(form.getId()).getCustomerId()).getDaiZhengId(), signatureChannelMapper.selectAll().get(0).getId(), sysDicService.selectSysPaySwitch());
		  return toSignNew(openApiClient, bestSignInfo, xmsmqswjwtsInfo, signInfoVo, auths);
	  }
	  return null;
  }

  private String toSignNew(BestsignOpenApiClient openApiClient, BestSignInfo bestSignInfo, XmsmqswjwtsInfo xmsmqswjwtsInfo, SignInfoVo signInfoVo, List<AuthInfoVo>auths) throws Exception{
	  long currentTimeMillis = System.currentTimeMillis();
	  String xmsmqswjwtsUrl= new PdfUtil().xmsmqswjwts(bestSignInfo,xmsmqswjwtsInfo,signInfoVo);
	  if( "500".equals(xmsmqswjwtsUrl) ){
		  throw	new	BussinessException(BizExceptionEnum.XMSMQSWJWTS_TO_FILLVALUE);
	  }
	  //企业签章
	  logger.debug("***********代理人id:"+signInfoVo.getAgentId()+"与代征主体id:"+signInfoVo.getDaiZhengId()+"签章开始**********");
	  logger.debug("厦门思明区税务局委托书");
	  com.alibaba.fastjson.JSONObject xmsmqswjwtsUploadRequestBody = PdfUtil.wrapUploadRequestBody(bestSignInfo.getAccount(),xmsmqswjwtsUrl,xmsmqswjwtsInfo.getXmsmqswjwtsName(),xmsmqswjwtsInfo.getXmsmqswjwtsPageSize());
	  String xmsmqswjwtsId = BestsignClient.uploadContract(xmsmqswjwtsUploadRequestBody,openApiClient);
	  //合同签章的位置

	  //不需要盖公司公章
	  //com.alibaba.fastjson.JSONObject xmsmqswjwtsCertRequestBody = PdfUtil.wrapCertRequestBody(xmsmqswjwtsId,signInfoVo.getDaiZhengAccount(),xmsmqswjwtsInfo.getXmsmqswjwtsX(),xmsmqswjwtsInfo.getXmsmqswjwtsY(),xmsmqswjwtsInfo.getXmsmqswjwtsPageNum(),signInfoVo.getImageName());
	  //BestsignClient.cert(xmsmqswjwtsCertRequestBody,openApiClient);

	  //回调地址
	  String returnUrl=bestSignInfo.getCallbackInterface()+"?contractId="+xmsmqswjwtsId;
	  //个人签名的位置
	  com.alibaba.fastjson.JSONObject xmsmqswjwtsSendRequestBody=PdfUtil.wrapSendRequestBody(xmsmqswjwtsId,signInfoVo.getAgentAccount(),xmsmqswjwtsInfo.getPersonxmsmqswjwtsX(),xmsmqswjwtsInfo.getPersonxmsmqswjwtsY(),xmsmqswjwtsInfo.getXmsmqswjwtsPageNum(),returnUrl);
	  //发送合同（即手动签，指定图片大小）
	  String manualSigningUrl = BestsignClient.send(xmsmqswjwtsSendRequestBody, openApiClient);
	  // 合同表
	  Example contractPdfExtraExample = new Example(ContractPdfExtra.class);
	  contractPdfExtraExample.createCriteria()
			  .andEqualTo("agentId", signInfoVo.getAgentId())
			  .andEqualTo("daiZhengId", signInfoVo.getDaiZhengId())
			  .andEqualTo("type", ContractTypeEnum.CONTRACT_TYPE_2.getIndex())
			  .andEqualTo("dataStatus",Const.STATUS_1);
	  ContractPdfExtra selectContractPdf = contractPdfExtraMapper.selectOneByExample(contractPdfExtraExample);
	  ContractPdfExtra contractPdfExtra = new ContractPdfExtra();
	  contractPdfExtra.setAgentId(signInfoVo.getAgentId());
	  contractPdfExtra.setDaiZhengId(signInfoVo.getDaiZhengId());
	  contractPdfExtra.setContractNumber(signInfoVo.getContractNumber());
	  contractPdfExtra.setManualSigningUrl(manualSigningUrl);
	  contractPdfExtra.setManualSigningTime(LocalDateTime.now());
	  contractPdfExtra.setSignTime(signInfoVo.getSignTime());
	  contractPdfExtra.setContractId(xmsmqswjwtsId);
	  contractPdfExtra.setCreatetime(LocalDateTime.now());
	  contractPdfExtra.setStatus(Const.SIGN_STATUS_4);
	  contractPdfExtra.setType(ContractTypeEnum.CONTRACT_TYPE_2.getIndex());
	  if (selectContractPdf == null) {
		  contractPdfExtraMapper.insertSelective(contractPdfExtra);
	  } else {
		  contractPdfExtraMapper.updateByExampleSelective(contractPdfExtra, contractPdfExtraExample);
	  }
	  return manualSigningUrl;
  }


    private String toSignNew(BestsignOpenApiClient openApiClient,BestSignInfo bestSignInfo,GxjjhzhbxyInfo gxjjhzhbxyInfo, SignInfoVo signInfoVo, List<AuthInfoVo>auths)throws Exception {
	    //合同编号
	   long currentTimeMillis = System.currentTimeMillis();
	   //填好合同模板数据
		String gxjjhzhbxyUrl=new PdfUtil().gxjjhzhbxyFillValue(bestSignInfo,gxjjhzhbxyInfo,signInfoVo,currentTimeMillis);
		if( "500".equals(gxjjhzhbxyUrl) ){
			throw	new	BussinessException(BizExceptionEnum.GXJJHZHBXY_TO_FILLVALUE);
		}
		//企业签章
		logger.debug("***********代理人id:"+signInfoVo.getAgentId()+"与代征主体id:"+signInfoVo.getDaiZhengId()+"签章开始**********");
		logger.debug("共享经济合作伙伴协议");
		com.alibaba.fastjson.JSONObject gxjjhzhbxyUploadRequestBody = PdfUtil.wrapUploadRequestBody(bestSignInfo.getAccount(),gxjjhzhbxyUrl,gxjjhzhbxyInfo.getGxjjhzhbxyName(),gxjjhzhbxyInfo.getGxjjhzhbxyPageSize());
		String gxjjhzhbxyId = BestsignClient.uploadContract(gxjjhzhbxyUploadRequestBody,openApiClient);
		//合同签章的位置
		com.alibaba.fastjson.JSONObject gxjjhzhbxyCertRequestBody = PdfUtil.wrapCertRequestBody(gxjjhzhbxyId,signInfoVo.getDaiZhengAccount(),gxjjhzhbxyInfo.getGxjjhzhbxyX(),gxjjhzhbxyInfo.getGxjjhzhbxyY(),gxjjhzhbxyInfo.getGxjjhzhbxyPageNum(),signInfoVo.getImageName());
		BestsignClient.cert(gxjjhzhbxyCertRequestBody,openApiClient);
		String returnUrl=bestSignInfo.getCallbackInterface()+"?contractId="+gxjjhzhbxyId;
	    //个人签名的位置
		com.alibaba.fastjson.JSONObject gxjjhzhbxySendRequestBody=PdfUtil.wrapSendRequestBody(gxjjhzhbxyId,signInfoVo.getAgentAccount(),gxjjhzhbxyInfo.getPersongxjjhzhbxyX(),gxjjhzhbxyInfo.getPersongxjjhzhbxyY(),gxjjhzhbxyInfo.getGxjjhzhbxyPageNum(),returnUrl);
		//发送合同（即手动签，指定图片大小）
		String manualSigningUrl = BestsignClient.send(gxjjhzhbxySendRequestBody, openApiClient);
		// 合同表
	   Example contractPdfExample = new Example(ContractPdf.class);
		  contractPdfExample.createCriteria()
		 .andEqualTo("agentId", signInfoVo.getAgentId())
		 .andEqualTo("daiZhengId", signInfoVo.getDaiZhengId())
		 .andEqualTo("dataStatus",Const.STATUS_1);
		ContractPdf selectContractPdf = contractPdfMapper.selectOneByExample(contractPdfExample);
		ContractPdf contractPdf = new ContractPdf();
		contractPdf.setAgentId(signInfoVo.getAgentId());
		contractPdf.setDaiZhengId(signInfoVo.getDaiZhengId());
		contractPdf.setContractNumber(signInfoVo.getContractNumber());
		contractPdf.setManualSigningUrl(manualSigningUrl);
		contractPdf.setManualSigningTime(LocalDateTime.now());
		contractPdf.setSignTime(signInfoVo.getSignTime());
		contractPdf.setGxjjhzhbxyId(gxjjhzhbxyId);
		contractPdf.setCreatetime(LocalDateTime.now());
		if (selectContractPdf == null) {
			contractPdfMapper.insertSelective(contractPdf);
		} else {
			contractPdfMapper.updateByExampleSelective(contractPdf, contractPdfExample);
		}
		Example contractPdfExtraExample = new Example(ContractPdfExtra.class);
		contractPdfExtraExample.createCriteria()
				.andEqualTo("agentId", signInfoVo.getAgentId())
				.andEqualTo("daiZhengId", signInfoVo.getDaiZhengId())
				.andEqualTo("type", ContractTypeEnum.CONTRACT_TYPE_1.getIndex())
				.andEqualTo("dataStatus",Const.STATUS_1);
		ContractPdfExtra selectContractPdfExtra = contractPdfExtraMapper.selectOneByExample(contractPdfExtraExample);
		ContractPdfExtra contractPdfExtra = new ContractPdfExtra();
		contractPdfExtra.setAgentId(signInfoVo.getAgentId());
		contractPdfExtra.setDaiZhengId(signInfoVo.getDaiZhengId());
		contractPdfExtra.setContractNumber(signInfoVo.getContractNumber());
		contractPdfExtra.setManualSigningUrl(manualSigningUrl);
		contractPdfExtra.setManualSigningTime(LocalDateTime.now());
		contractPdfExtra.setSignTime(signInfoVo.getSignTime());
		contractPdfExtra.setContractId(gxjjhzhbxyId);
		contractPdfExtra.setStatus(Const.SIGN_STATUS_4);
		contractPdfExtra.setType(ContractTypeEnum.CONTRACT_TYPE_1.getIndex());
		if (selectContractPdfExtra == null) {
			contractPdfExtraMapper.insertSelective(contractPdfExtra);
		} else {
			contractPdfExtraMapper.updateByExampleSelective(contractPdfExtra, contractPdfExtraExample);
		}
		return manualSigningUrl;
	}

	private void changePersonalCertificateStatus(PersonalCertificateStatus selectPersonalCertificateStatus,PersonalCertificateStatus personalCertificateStatus) {		
		if (selectPersonalCertificateStatus != null) {
			personalCertificateStatus.setId(selectPersonalCertificateStatus.getId());				
			personalCertificateStatus.setModifytime(LocalDateTime.now());
			personalCertificateStatusMapper.updateByPrimaryKeySelective(personalCertificateStatus);					
		} else {
			personalCertificateStatus.setCreatetime(LocalDateTime.now());
			personalCertificateStatusMapper.insertSelective(personalCertificateStatus);				
		}
	}

	@Override
	public GzhAgentCustomerVo selectAgentCustomerInfoById(int id) {
		// TODO Auto-generated method stub
		return agentInfoMapper.selectAgentCustomerInfoById(id);
	}

	@Override
	public void timingPersonalCertificateQuery(PersonalCertificateStatusVo personalCertificateStatusVo) {
		//获取尚尚签基础配置信息
       BestSignInfo bestSignInfo=sysConfigService.getBestSignInfo(sysDicService.selectSysPaySwitch());     
       BestsignOpenApiClient openApiClient= new BestsignOpenApiClient(bestSignInfo.getDeveloperId(),bestSignInfo.getPrivateKey(),bestSignInfo.getServerHost());	
       PersonalCertificateStatus personalCertificateStatus = new PersonalCertificateStatus();
       personalCertificateStatus.setId(personalCertificateStatusVo.getId());
       personalCertificateStatus.setAgentId(personalCertificateStatusVo.getAgentId());
       personalCertificateStatus.setAccount(personalCertificateStatusVo.getAccount());
       personalCertificateStatus.setTaskid(personalCertificateStatusVo.getTaskId());
       BestsignClient.applyCertStatus(openApiClient, personalCertificateStatus);   
	   personalCertificateStatus.setModifytime(LocalDateTime.now());
	   personalCertificateStatusMapper.updateByPrimaryKeySelective(personalCertificateStatus);	  
     }

	@Override
	public void allAuth(AgentInfoForm form) {
		// TODO Auto-generated method stub
		Example example = new Example(AgentCustomer.class);

		if(Const.ADMINISTRATOR_CUSTOMER_ID.equals(form.getUserCustomerId())){
		example.createCriteria()
	    .andEqualTo("signStatus", Const.SIGN_STATUS_2)
	    .andEqualTo("authStatus", Const.AUTH_STATUS_1);

		}else{
		example.createCriteria()
	    .andEqualTo("signStatus", Const.SIGN_STATUS_2)
	    .andEqualTo("authStatus", Const.AUTH_STATUS_1)
		.andEqualTo("customerId", form.getUserCustomerId());
		}
		List<AgentCustomer> selectAgentCustomers = agentCustomerMapper.selectByExample(example);
		selectAgentCustomers.forEach(selectAgentCustomer -> { 			
	    if(Const.AUTH_STATUS_1==selectAgentCustomer.getAuthStatus()||Const.AUTH_STATUS_3==selectAgentCustomer.getAuthStatus()){			
		AgentCustomer agentCustomer = new AgentCustomer();
		agentCustomer.setId(selectAgentCustomer.getId());
		agentCustomer.setAuthStatus(Const.AUTH_STATUS_2);
		agentCustomer.setModifyoper(form.getCurrUserName());
		agentCustomer.setModifytime(LocalDateTime.now());
		agentCustomerMapper.updateByPrimaryKeySelective(agentCustomer);
	   }
     });					
	}

    @Override
    public List<PaymentStatisticsVo> queryPaymentStatistics(Pagination pagination, PaymentStatisticsQuery query) {
        return agentCustomerMapper.queryPaymentStatistics(pagination, query);
    }

	@Override
	public long countByQueryPaymentStatistics(PaymentStatisticsQuery query) {
		return agentCustomerMapper.countByQueryPaymentStatistics(query);
	}

	@Override
	public List<PaymentStatisticsVo> queryPaymentStatisticsForExport(PaymentStatisticsQuery query) {
		return agentCustomerMapper.queryPaymentStatisticsForExport(query);
	}
/*
	@Override
	public String callbackSign(String contractId) {
		// TODO Auto-generated method stub
		 Example contractPdfExample = new Example(ContractPdf.class);
		 contractPdfExample.createCriteria()
		 .andEqualTo("gxjjhzhbxyId",contractId);
		 ContractPdf selectContractPdf = contractPdfMapper.selectOneByExample(contractPdfExample);	
		List<AuthInfoVo>auths = agentInfoMapper.selectAgentCustomerIds( selectContractPdf.getAgentId(), selectContractPdf.getDaiZhengId());	
         if(auths.size()>0&&auths!=null){
        	  AgentCustomer selectAgentCustomer = agentCustomerMapper.selectByPrimaryKey(auths.get(0).getAgentCustomerId());
        	if(selectAgentCustomer!=null) {
        		if(new Integer(Const.SIGN_STATUS_2).equals(selectAgentCustomer.getSignStatus())){
        			 return agentInfoMapper.selectByPrimaryKey(selectContractPdf.getAgentId()).getOpenid();
        		}	
			}
          }
			//获取尚尚签基础配置信息
			BestSignInfo bestSignInfo=sysConfigService.getBestSignInfo(sysDicService.selectSysPaySwitch());     
			BestsignOpenApiClient openApiClient= new BestsignOpenApiClient(bestSignInfo.getDeveloperId(),bestSignInfo.getPrivateKey(),bestSignInfo.getServerHost());	
			GxjjhzhbxyInfo gxjjhzhbxyInfo = signatureChannelConfigService.getGxjjhzhbxyInfo(selectContractPdf.getDaiZhengId(),signatureChannelMapper.selectAll().get(0).getId(), sysDicService.selectSysPaySwitch());									
			long currentTimeMillis = System.currentTimeMillis();	
			//生成的pdf路径
			String pafPath = bestSignInfo.getDownloadPdfPath()+"/"+currentTimeMillis;
			//生成的pdf压缩包路径
			String pafZipPath =  bestSignInfo.getDownloadPdfZipPath()+"/"+currentTimeMillis; 
    	try {
    		//锁合同
			BestsignClient.lock(contractId,openApiClient);
			BestsignClient.downloadContract(contractId, pafPath, gxjjhzhbxyInfo.getGxjjhzhbxyName(), openApiClient);
			String zip = pafZipPath + "/" + "contract" + ".zip";
			File zipfile = new File(pafZipPath);
			if (!zipfile.exists()) {
				zipfile.mkdirs();
			}
			ZipUtil.zipDir(pafPath, zip);
			selectContractPdf.setSignTime(LocalDateTime.now());
			selectContractPdf.setGxjjhzhbxy(pafPath + "/"+ gxjjhzhbxyInfo.getGxjjhzhbxyName());		
			selectContractPdf.setYbZip(zip);
			selectContractPdf.setModifytime(LocalDateTime.now());
			contractPdfMapper.updateByPrimaryKeySelective(selectContractPdf);
			auths.forEach(auth -> {
			     // 代理人客户签约表
				AgentCustomer record = new AgentCustomer();
				record.setId(auth.getAgentCustomerId());
				record.setContractNumber(selectContractPdf.getContractNumber());
				record.setSignTime(LocalDateTime.now());
				record.setAuthStatus(auth.getIsAuth());
				record.setSignStatus(Const.SIGN_STATUS_2);
				record.setModifytime(LocalDateTime.now());
				agentCustomerMapper.updateByPrimaryKeySelective(record);
			 });
		} catch (Exception e) {
			// TODO Auto-generated catch block
			auths.forEach(auth -> {
			     // 代理人客户签约表
				AgentCustomer record = new AgentCustomer();
				record.setId(auth.getAgentCustomerId());
				record.setContractNumber(selectContractPdf.getContractNumber());
				record.setSignTime(LocalDateTime.now());
				record.setAuthStatus(auth.getIsAuth());
				record.setSignStatus(Const.SIGN_STATUS_3);
				record.setModifytime(LocalDateTime.now());
				agentCustomerMapper.updateByPrimaryKeySelective(record);

			 });
			logger.error(e.toString());
		}
		logger.debug("***********代理人id:"+selectContractPdf.getAgentId()+"与代征主体id:"+selectContractPdf.getDaiZhengId()+"签章结束**********");
	 return agentInfoMapper.selectByPrimaryKey(selectContractPdf.getAgentId()).getOpenid();
	}*/

	@Override
	public String callbackSign(String contractId) {

		LocalDateTime now = LocalDateTime.now();
		Example contractPdfExtraExample = new Example(ContractPdfExtra.class);
		contractPdfExtraExample.createCriteria()
				.andEqualTo("contractId",contractId);
		ContractPdfExtra selectContractPdfExtra = contractPdfExtraMapper.selectOneByExample(contractPdfExtraExample);

		List<AuthInfoVo>auths = agentInfoMapper.selectAgentCustomerIds( selectContractPdfExtra.getAgentId(), selectContractPdfExtra.getDaiZhengId());
        if(auths.size()>0&&auths!=null){
        	  AgentCustomer selectAgentCustomer = agentCustomerMapper.selectByPrimaryKey(auths.get(0).getAgentCustomerId());
        	if(selectAgentCustomer!=null) {
        		if(new Integer(Const.SIGN_STATUS_2).equals(selectAgentCustomer.getSignStatus())){
        			 return agentInfoMapper.selectByPrimaryKey(selectContractPdfExtra.getAgentId()).getOpenid();
        		}
			}
		}
		//获取尚尚签基础配置信息
		BestSignInfo bestSignInfo=sysConfigService.getBestSignInfo(sysDicService.selectSysPaySwitch());
		BestsignOpenApiClient openApiClient= new BestsignOpenApiClient(bestSignInfo.getDeveloperId(),bestSignInfo.getPrivateKey(),bestSignInfo.getServerHost());
		//生成的pdf路径
		String pafPath = bestSignInfo.getDownloadPdfPath()+"/"+selectContractPdfExtra.getAgentId();
		//生成的pdf压缩包路径
		String pafZipPath =  bestSignInfo.getDownloadPdfZipPath()+"/"+selectContractPdfExtra.getAgentId();

		String contractName = null;
		if(ContractTypeEnum.CONTRACT_TYPE_1.getIndex()==selectContractPdfExtra.getType()){
			GxjjhzhbxyInfo gxjjhzhbxyInfo = signatureChannelConfigService.getGxjjhzhbxyInfo(selectContractPdfExtra.getDaiZhengId(),signatureChannelMapper.selectAll().get(0).getId(), sysDicService.selectSysPaySwitch());
			contractName=gxjjhzhbxyInfo.getGxjjhzhbxyName();
			logger.debug("共享经济伙伴合作协议");
		}else if(ContractTypeEnum.CONTRACT_TYPE_2.getIndex()==selectContractPdfExtra.getType()){
			XmsmqswjwtsInfo xmsmqswjwtsInfo = signatureChannelConfigService.getXmsmqswjwtsInfo(selectContractPdfExtra.getDaiZhengId(),signatureChannelMapper.selectAll().get(0).getId(), sysDicService.selectSysPaySwitch());
			contractName=xmsmqswjwtsInfo.getXmsmqswjwtsName();
			logger.debug("厦门思明区税务局委托书");
		}


		selectContractPdfExtra.setUrl(pafPath+"/"+contractName);
		selectContractPdfExtra.setSignTime(now);
		selectContractPdfExtra.setModifytime(now);
		try {
			//锁合同
			BestsignClient.lock(contractId,openApiClient);
			//下载合同
			BestsignClient.downloadContract(contractId, pafPath, contractName, openApiClient);
			selectContractPdfExtra.setStatus(Const.SIGN_STATUS_2);
			contractPdfExtraMapper.updateByPrimaryKeySelective(selectContractPdfExtra);

			boolean flag = true;
			Example contractPdfExtraExample1 = new Example(ContractPdfExtra.class);
			contractPdfExtraExample1.createCriteria()
					.andEqualTo("agentId",selectContractPdfExtra.getAgentId());
			List<ContractPdfExtra> selectContractPdfExtras = contractPdfExtraMapper.selectByExample(contractPdfExtraExample1);
			ContractPdfExtra contractPdfExtraType1 = new ContractPdfExtra();
			for(ContractPdfExtra item : selectContractPdfExtras){
				if(Const.SIGN_STATUS_2 != item.getStatus()){
					flag = false;
				}
				if(ContractTypeEnum.CONTRACT_TYPE_1.getIndex()==item.getType()){
					contractPdfExtraType1 = item;
				}
			}
			ContractPdfExtra finalContractPdfExtraType = contractPdfExtraType1;
			String zip = pafZipPath + "/" + "contract" + ".zip";
			if(flag) {
				File zipfile = new File(pafZipPath);
				if (!zipfile.exists()) {
					zipfile.mkdirs();
				}
				ZipUtil.zipDir(pafPath, zip);
				auths.forEach(auth -> {
					// 代理人客户签约表
					AgentCustomer record = new AgentCustomer();
					record.setId(auth.getAgentCustomerId());
					record.setContractNumber(finalContractPdfExtraType.getContractNumber());
					record.setSignTime(now);
					record.setAuthStatus(auth.getIsAuth());
					record.setSignStatus(Const.SIGN_STATUS_2);
					record.setModifytime(now);
					agentCustomerMapper.updateByPrimaryKeySelective(record);
				});
			}
			if(!StringUtils.isEmpty(contractPdfExtraType1.getContractId())) {
				Example contractPdfExample = new Example(ContractPdf.class);
				contractPdfExample.createCriteria()
						.andEqualTo("gxjjhzhbxyId", contractPdfExtraType1.getContractId());
				ContractPdf selectContractPdf = contractPdfMapper.selectOneByExample(contractPdfExample);
				if (ContractTypeEnum.CONTRACT_TYPE_1.getIndex() == selectContractPdfExtra.getType()) {
					selectContractPdf.setSignTime(now);
					selectContractPdf.setGxjjhzhbxy(pafPath + "/" + contractName);
				}
				if (flag) {
					selectContractPdf.setYbZip(zip);
				}
				selectContractPdf.setModifytime(now);
				contractPdfMapper.updateByPrimaryKeySelective(selectContractPdf);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			boolean flag = true;
			Example contractPdfExtraExample1 = new Example(ContractPdfExtra.class);
			contractPdfExtraExample1.createCriteria()
					.andEqualTo("agentId",selectContractPdfExtra.getAgentId());
			List<ContractPdfExtra> selectContractPdfExtras = contractPdfExtraMapper.selectByExample(contractPdfExtraExample1);
			ContractPdfExtra contractPdfExtraType1 = new ContractPdfExtra();
			for(ContractPdfExtra item : selectContractPdfExtras){
				if(Const.SIGN_STATUS_2 != item.getStatus()){
					flag = false;
				}
				if(ContractTypeEnum.CONTRACT_TYPE_1.getIndex()==item.getType()){
					contractPdfExtraType1 = item;
				}
			}
			ContractPdfExtra finalContractPdfExtraType = contractPdfExtraType1;
			auths.forEach(auth -> {
				// 代理人客户签约表
				AgentCustomer record = new AgentCustomer();
				record.setId(auth.getAgentCustomerId());
				record.setContractNumber(finalContractPdfExtraType.getContractNumber());
				record.setSignTime(now);
				record.setAuthStatus(auth.getIsAuth());
				record.setSignStatus(Const.SIGN_STATUS_3);
				record.setModifytime(now);
				agentCustomerMapper.updateByPrimaryKeySelective(record);

			});
			selectContractPdfExtra.setStatus(Const.SIGN_STATUS_3);
			contractPdfExtraMapper.updateByPrimaryKeySelective(selectContractPdfExtra);
			logger.error(e.toString());
		}

		logger.debug("***********代理人id:"+selectContractPdfExtra.getAgentId()+"与代征主体id:"+selectContractPdfExtra.getDaiZhengId()+"签章结束**********");
	 return agentInfoMapper.selectByPrimaryKey(selectContractPdfExtra.getAgentId()).getOpenid();
	}

	/**
	 * 补充添加代理人
	 */
	@Override
	@Transactional
	public Response addAgentInfoSingle(AgentInfoForm form, SysUser user) throws Exception {
		//代理人身份证号
		String agentIdcardno = StringUtil.formatBlankString(form.getAgentIdcardno());
		//代理人姓名
		String agentName = form.getAgentName();
		//代理人手机号
		String agentMobile = StringUtil.formatBlankString(form.getAgentMobile());
		//代理人银行卡号
		String agentOpenBankNo = StringUtil.formatBlankString(form.getAgentOpenBankNo());
		//代理人银行代码
		String agentOpenBankCode = form.getAgentOpenBankCode();
		//身份证正面
		String frontIdcardPicUrl = form.getFrontIdcardPicUrl();
		//身份证反面
		String backIdcardPicUrl = form.getBackIdcardPicUrl();
		//合同pdf
		String contractPicUrl = form.getContractPicUrl();
		//客户代码
		String customerId = form.getCustomerId();
		
		//判断数据是否为空
		if(org.apache.commons.lang.StringUtils.isBlank(agentIdcardno)) {
			return Response.error().message("代理人身份证号不能为空!");
		}else if(org.apache.commons.lang.StringUtils.isBlank(agentName)) {
			return Response.error().message("代理人姓名不能为空!");
		}else if(org.apache.commons.lang.StringUtils.isBlank(agentMobile)) {
			return Response.error().message("代理人手机号不能为空!");
		}else if(org.apache.commons.lang.StringUtils.isBlank(agentOpenBankNo)) {
			return Response.error().message("代理人银行卡号不能为空!");
		}else if(org.apache.commons.lang.StringUtils.isBlank(agentOpenBankCode)) {
			return Response.error().message("代理人银行代码不能为空!");
		}else if(org.apache.commons.lang.StringUtils.isBlank(frontIdcardPicUrl)) {
			return Response.error().message("代理人身份证正面图片不能为空!");
		}else if(org.apache.commons.lang.StringUtils.isBlank(backIdcardPicUrl)) {
			return Response.error().message("代理人身份证反面图片不能为空!");
		}else if(org.apache.commons.lang.StringUtils.isBlank(contractPicUrl)) {
			return Response.error().message("代理人合同pdf不能为空!");
		}
		
		Example  agentInfoExample = new Example(AgentInfo.class);
		agentInfoExample.createCriteria()
		.andEqualTo("agentIdcardno",agentIdcardno);	
		AgentInfo selectAgentInfo = agentInfoMapper.selectOneByExample(agentInfoExample);
		
		//根据银行code查询银行名称
		Example  bankCodeInfoExample = new Example(BankCodeInfo.class);
		bankCodeInfoExample.createCriteria().andEqualTo("bankCode",agentOpenBankCode);
		BankCodeInfo selectBankCodeInfo = bankCodeInfoMapper.selectOneByExample(bankCodeInfoExample);
		
		//线上环境 online/测试环境test  开关
		String sysPaySwitch = sysDicService.selectSysPaySwitch();                      
		//获取通联配置信息
	    TongLianInfo tongLianInfo=paymentChannelConfigService.getTongLianInfo(Const.SYS_PAYMENT_CHANNEL_TONGLIAN,sysPaySwitch);
			    
		//先校验三要素
	    AgentBankNoInfoForm bean = new AgentBankNoInfoForm();
	    bean.setCustomerId(customerId);
	    bean.setAgentIdcardno(agentIdcardno);
	    bean.setAgentName(agentName);
	    bean.setAgentOpenBankCode(agentOpenBankCode);
	    bean.setAgentOpenBankNo(agentOpenBankNo);
	    bean.setIdType(form.getIdType());
	    Response response = agentBankNoInfoService.verification(bean, tongLianInfo);
	    //三要素校验成功后 客户相关信息 入库
	    if(response.getCode() == ResponseCode.SUCCESS.value()) {
	    	//未注册代理人
			if(selectAgentInfo==null){
				//录入代理人信息
				AgentInfo insertAgentInfo=new AgentInfo();
				insertAgentInfo.setNationalityId(1);
				insertAgentInfo.setIdType(form.getIdType());
				insertAgentInfo.setAgentName(agentName);
				insertAgentInfo.setAgentIdcardno(agentIdcardno);
				insertAgentInfo.setAccount(agentIdcardno);
				insertAgentInfo.setAgentMobile(agentMobile);
				insertAgentInfo.setFrontIdcardPicUrl(frontIdcardPicUrl);
				insertAgentInfo.setBackIdcardPicUrl(backIdcardPicUrl);
				insertAgentInfo.setCreatetime(LocalDateTime.now());
				insertAgentInfo.setCreateoper(user.getNickname());
				agentInfoMapper.insertSelective(insertAgentInfo);		
					
				//录入代理人客户中间表信息
				AgentCustomer insertAgentCustomer = new AgentCustomer();
				insertAgentCustomer.setCustomerId(customerId);
				insertAgentCustomer.setAgentId(insertAgentInfo.getId());
				insertAgentCustomer.setAuthStatus(Const.AUTH_STATUS_2);
				insertAgentCustomer.setSignStatus(Const.SIGN_STATUS_2);
				insertAgentCustomer.setSignTime(LocalDateTime.now());
				insertAgentCustomer.setDataStatus(Const.DATA_STATUS_1);
				insertAgentCustomer.setCreateoper(user.getNickname());
				insertAgentCustomer.setCreatetime(LocalDateTime.now());
				agentCustomerMapper.insertSelective(insertAgentCustomer);
				
				//录入银行卡信息
				AgentBankNoInfo insertAgentBankNoInfo = new AgentBankNoInfo();
				insertAgentBankNoInfo.setAgentId(insertAgentInfo.getId());
				insertAgentBankNoInfo.setAgentOpenBankNo(agentOpenBankNo);
				insertAgentBankNoInfo.setAgentOpenBankName(selectBankCodeInfo.getBankName());
				insertAgentBankNoInfo.setAgentOpenBankCode(agentOpenBankCode);
				insertAgentBankNoInfo.setDataStatus(Const.DATA_STATUS_1);
				insertAgentBankNoInfo.setCreatetime(LocalDateTime.now());
				insertAgentBankNoInfo.setCreateoper(user.getNickname());
				agentBankNoInfoMapper.insertSelective(insertAgentBankNoInfo);			
					
				//录入合同信息
				ContractPdf contractPdfInfo = new ContractPdf();
				contractPdfInfo.setAgentId(insertAgentInfo.getId());
				contractPdfInfo.setDaiZhengId(daiZhengInfoService.getDaiZhengId());
				contractPdfInfo.setSignTime(LocalDateTime.now());
				contractPdfInfo.setGxjjhzhbxy(contractPicUrl);
				contractPdfInfo.setDataStatus(Const.DATA_STATUS_1);
				contractPdfInfo.setCreateoper(user.getNickname());
				contractPdfInfo.setCreatetime(LocalDateTime.now());
				contractPdfInfo.setGxjjhzhbxyId("");
				contractPdfMapper.insertSelective(contractPdfInfo);
				ContractPdfExtra contractPdfExtra1 = new ContractPdfExtra();
				contractPdfExtra1.setAgentId(insertAgentInfo.getId());
				contractPdfExtra1.setDaiZhengId(daiZhengInfoService.getDaiZhengId());
				contractPdfExtra1.setDataStatus(Const.DATA_STATUS_1);
				contractPdfExtra1.setCreateoper(user.getNickname());
				contractPdfExtra1.setCreatetime(LocalDateTime.now());
				contractPdfExtra1.setType(ContractTypeEnum.CONTRACT_TYPE_1.getIndex());
				contractPdfExtra1.setStatus(Const.SIGN_STATUS_2);
				contractPdfExtra1.setUrl(contractPicUrl);
				contractPdfExtraMapper.insertSelective(contractPdfExtra1);
			}else{//已注册代理人	
				//判断是否是新客户，是绑定代理人跟客户关系
				Example agentCustomerExample = new Example(AgentCustomer.class);
				agentCustomerExample.createCriteria()                     
				.andEqualTo("customerId",customerId)
				.andEqualTo("agentId", selectAgentInfo.getId())
				.andEqualTo("dataStatus", Const.DATA_STATUS_1);		
				AgentCustomer selectAgentCustomer = agentCustomerMapper.selectOneByExample(agentCustomerExample);
				if(selectAgentCustomer==null){
					AgentCustomer insertAgentCustomer = new AgentCustomer();
					insertAgentCustomer.setCustomerId(customerId);
					insertAgentCustomer.setAgentId(selectAgentInfo.getId());
					insertAgentCustomer.setAuthStatus(Const.AUTH_STATUS_2);
					insertAgentCustomer.setSignStatus(Const.SIGN_STATUS_2);
					insertAgentCustomer.setSignTime(LocalDateTime.now());
					insertAgentCustomer.setDataStatus(Const.DATA_STATUS_1);
					insertAgentCustomer.setCreateoper(user.getNickname());
					insertAgentCustomer.setCreatetime(LocalDateTime.now());
					agentCustomerMapper.insertSelective(insertAgentCustomer);		
				}
				
				//判断银行卡信息是否新增
				Example agentBankNoInfoExample = new Example(AgentBankNoInfo.class);
				agentBankNoInfoExample.createCriteria()
			    .andEqualTo("agentId",selectAgentInfo.getId())
				.andEqualTo("agentOpenBankNo",agentOpenBankNo)
				.andEqualTo("dataStatus", Const.DATA_STATUS_1);		
				int agentBankNoInfoCount= agentBankNoInfoMapper.selectCountByExample(agentBankNoInfoExample);
				if(agentBankNoInfoCount==0){
					//录入银行卡信息
					AgentBankNoInfo insertAgentBankNoInfo = new AgentBankNoInfo();
					insertAgentBankNoInfo.setAgentId(selectAgentInfo.getId());
					insertAgentBankNoInfo.setAgentOpenBankNo(agentOpenBankNo);
					insertAgentBankNoInfo.setAgentOpenBankName(selectBankCodeInfo.getBankName());
					insertAgentBankNoInfo.setAgentOpenBankCode(agentOpenBankCode);
					insertAgentBankNoInfo.setDataStatus(Const.DATA_STATUS_1);
					insertAgentBankNoInfo.setCreatetime(LocalDateTime.now());
					insertAgentBankNoInfo.setCreateoper(user.getNickname());
					agentBankNoInfoMapper.insertSelective(insertAgentBankNoInfo);						
				}	
				
				//判断合同表是否存在数据
				Example contractPdfExample = new Example(ContractPdf.class);
				contractPdfExample.createCriteria()
			    .andEqualTo("agentId",selectAgentInfo.getId())
				.andEqualTo("daiZhengId",daiZhengInfoService.getDaiZhengId())
				.andEqualTo("dataStatus", Const.DATA_STATUS_1);		
				int contractPdfCount= contractPdfMapper.selectCountByExample(contractPdfExample);
				if(contractPdfCount==0){
					ContractPdf contractPdfInfo = new ContractPdf();
					contractPdfInfo.setAgentId(selectAgentInfo.getId());
					contractPdfInfo.setDaiZhengId(daiZhengInfoService.getDaiZhengId());
					contractPdfInfo.setSignTime(LocalDateTime.now());
					contractPdfInfo.setGxjjhzhbxy(contractPicUrl);
					contractPdfInfo.setDataStatus(Const.DATA_STATUS_1);
					contractPdfInfo.setCreateoper(user.getNickname());
					contractPdfInfo.setCreatetime(LocalDateTime.now());
					contractPdfInfo.setGxjjhzhbxyId("");
					contractPdfMapper.insertSelective(contractPdfInfo);

				}
				Example contractPdfExtraExample1 = new Example(ContractPdfExtra.class);
				contractPdfExtraExample1.createCriteria()
						.andEqualTo("agentId",selectAgentInfo.getId())
						.andEqualTo("daiZhengId",daiZhengInfoService.getDaiZhengId())
						.andEqualTo("dataStatus", Const.DATA_STATUS_1)
						.andEqualTo("type",ContractTypeEnum.CONTRACT_TYPE_1.getIndex());
				int contractPdfExtraCount1= contractPdfExtraMapper.selectCountByExample(contractPdfExtraExample1);
				if(contractPdfExtraCount1==0) {
					ContractPdfExtra contractPdfExtra1 = new ContractPdfExtra();
					contractPdfExtra1.setAgentId(selectAgentInfo.getId());
					contractPdfExtra1.setDaiZhengId(daiZhengInfoService.getDaiZhengId());
					contractPdfExtra1.setDataStatus(Const.DATA_STATUS_1);
					contractPdfExtra1.setCreateoper(user.getNickname());
					contractPdfExtra1.setCreatetime(LocalDateTime.now());
					contractPdfExtra1.setType(ContractTypeEnum.CONTRACT_TYPE_1.getIndex());
					contractPdfExtra1.setUrl(contractPicUrl);
					contractPdfExtra1.setStatus(Const.SIGN_STATUS_2);
					contractPdfExtraMapper.insertSelective(contractPdfExtra1);
				}
			}
			return Response.ok();
		}else {
			return response;
		}
	}
}
