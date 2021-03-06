package cn.rebornauto.platform.business.service.impl;

import cn.rebornauto.platform.bestSign.BestsignClient;
import cn.rebornauto.platform.bestSign.BestsignOpenApiClient;
import cn.rebornauto.platform.bestSign.entity.BestSignInfo;
import cn.rebornauto.platform.business.dao.CertificateStatusMapper;
import cn.rebornauto.platform.business.dao.DaiZhengInfoMapper;
import cn.rebornauto.platform.business.entity.CertificateStatus;
import cn.rebornauto.platform.business.entity.DaiZhengInfo;
import cn.rebornauto.platform.business.form.DaiZhengForm;
import cn.rebornauto.platform.business.service.DaiZhengService;
import cn.rebornauto.platform.business.service.SysConfigService;
import cn.rebornauto.platform.business.service.SysDicService;
import cn.rebornauto.platform.business.vo.DaiZhengInfoVo;
import cn.rebornauto.platform.common.Const;
import cn.rebornauto.platform.common.data.request.Pagination;
import cn.rebornauto.platform.common.data.request.Query;
import cn.rebornauto.platform.upload.entity.UploadInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import tk.mybatis.mapper.entity.Example;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class DaiZhengServiceImpl implements DaiZhengService {
    @Autowired
    private DaiZhengInfoMapper daiZhengInfoMapper;
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private SysDicService sysDicService;
    @Autowired
    private CertificateStatusMapper certificateStatusMapper;

    @Override
    public int save(DaiZhengForm form) throws Exception {	
		DaiZhengInfo record = getParam(form);
		record.setImageName(UUID.randomUUID().toString().replace("-",""));
		record.setAccount(form.getDaiZhengLinkMobile());
		record.setUserType(Const.USER_TYPE2);
		record.setLogo(form.getLogo());
		record.setSealImgPicUrl(form.getSealImgPicUrl());
		record.setDataStatus(1);
		record.setCreateoper(form.getCurrUserName());
		record.setCreatetime(LocalDateTime.now());      
        return daiZhengInfoMapper.insertSelective(record);
    }

    @Override
    public long count(Query query) {
        return daiZhengInfoMapper.count(query);
    }

    @Override
    public List<DaiZhengInfoVo> list(Pagination pagination) {
        List<DaiZhengInfoVo> list = daiZhengInfoMapper.list(pagination);
        UploadInfo uploadInfo=sysConfigService.getUploadInfo(sysDicService.selectSysPaySwitch());
        list.forEach(daiZhengInfo -> {
            if (StringUtils.isNotEmpty(daiZhengInfo.getLogo())) {
                daiZhengInfo.setLogo(uploadInfo.getDomain()+daiZhengInfo.getLogo());
            } else {
                daiZhengInfo.setLogo("");
            }
            
            if (StringUtils.isNotEmpty(daiZhengInfo.getSealImgPicUrl())) {
                daiZhengInfo.setSealImgPicUrl(uploadInfo.getDomain()+daiZhengInfo.getSealImgPicUrl());
            } else {
                daiZhengInfo.setSealImgPicUrl("");
            }
        });
        return list;
    }

    @Override
    public int edit(DaiZhengForm form) throws Exception {
        Assert.isTrue(form.getId() > 0, "id????????????0");
        DaiZhengInfo selectDaiZhengInfo = daiZhengInfoMapper.selectByPrimaryKey(form.getId());
    	if(selectDaiZhengInfo==null){
			return 0;
		}       
    	DaiZhengInfo record = getParam(form);
        record.setId(form.getId());
        UploadInfo uploadInfo = sysConfigService.getUploadInfo(sysDicService.selectSysPaySwitch());
	    String absoluteLogo=uploadInfo.getDomain()+selectDaiZhengInfo.getLogo();
	    if(!absoluteLogo.equals(form.getLogo())){
	        record.setLogo(form.getLogo()); 
	    }	    
	    String absoluteSealImgPicUrl=uploadInfo.getDomain()+selectDaiZhengInfo.getSealImgPicUrl();
	    
	    
		Example example = new Example(CertificateStatus.class);
    	example.createCriteria().andEqualTo("daiZhengId", form.getId());
    	CertificateStatus selectCertificateStatus = certificateStatusMapper.selectOneByExample(example);
    	 if (selectCertificateStatus==null||selectCertificateStatus.getStatus() == null
					|| Const.APPLY_CERT_STATUS4.equals(selectCertificateStatus.getStatus())
					|| Const.APPLY_CERT_STATUS6.equals(selectCertificateStatus.getStatus())
					||  Const.APPLY_CERT_STATUS0.equals(selectCertificateStatus.getStatus())) {
				record.setAccount(form.getDaiZhengLinkMobile());
		}    	
	    if(!absoluteSealImgPicUrl.equals(form.getSealImgPicUrl())){
			 getCompanyUserRegParam(form,selectDaiZhengInfo);
	    	//??????????????????
	        record.setSealImgPicUrl(form.getSealImgPicUrl());	        	    
	    	if(selectCertificateStatus!=null&&Const.APPLY_CERT_STATUS5.equals(selectCertificateStatus.getStatus())){
	    		//?????????????????????????????????
		        BestSignInfo bestSignInfo=sysConfigService.getBestSignInfo(sysDicService.selectSysPaySwitch());     
		        BestsignOpenApiClient openApiClient= new BestsignOpenApiClient(bestSignInfo.getDeveloperId(),bestSignInfo.getPrivateKey(),bestSignInfo.getServerHost());	
		        form.setImageName(selectDaiZhengInfo.getImageName()); 
		        //????????????
		        BestsignClient.uploadCert(openApiClient,uploadInfo,form);
			}
	    }
	    record.setDataStatus(form.getDataStatus());
        record.setModifyoper(form.getCurrUserName());
        record.setModifytime(LocalDateTime.now());
        return daiZhengInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<DaiZhengInfo> all() {
        return daiZhengInfoMapper.selectAll();
    }

    public DaiZhengInfo getParam(DaiZhengForm form) {
        DaiZhengInfo record = new DaiZhengInfo();
        record.setDaiZhengSimpleCompanyName(form.getDaiZhengSimpleCompanyName());
        record.setCityAdcode(form.getCityAdcode());
        record.setProvinceAdcode(form.getProvinceAdcode());
        record.setDaiZhengAddress(form.getDaiZhengAddress());
        record.setDaiZhengCompanyName(form.getDaiZhengName());
        record.setDaiZhengName(form.getDaiZhengName());
        record.setDaiZhengOpenBank(form.getDaiZhengOpenBank());
        record.setDaiZhengLinkMan(form.getDaiZhengLinkMan());
        record.setDaiZhengOpenBankNo(form.getDaiZhengOpenBankNo());
        record.setDaiZhengOpenName(form.getDaiZhengOpenName());
        record.setExtraTax(form.getExtraTax());
        record.setPersonalTax(form.getPersonalTax());
        record.setValueAddedTax(form.getValueAddedTax());
        record.setDaiZhengLinkMobile(form.getDaiZhengLinkMobile());
        record.setRegCode(form.getRegCode());
        record.setOrgCode(form.getOrgCode());
        record.setTaxCode(form.getTaxCode());
        record.setLegalPerson(form.getLegalPerson());
        record.setLegalPersonIdentityType(form.getLegalPersonIdentityType());
        record.setLegalPersonIdentity(form.getLegalPersonIdentity());
        return record;
    }

	@Override
 	public CertificateStatus register(DaiZhengForm form) throws Exception {
		// TODO Auto-generated method stub
		  DaiZhengInfo daiZhengInfo = daiZhengInfoMapper.selectByPrimaryKey(form.getId());
		  getCompanyUserRegParam(form,daiZhengInfo);
		  form.setUploadSealImgPicUrl(daiZhengInfo.getSealImgPicUrl());
		  //?????????????????????????????????
	       BestSignInfo bestSignInfo=sysConfigService.getBestSignInfo(sysDicService.selectSysPaySwitch());     
	       UploadInfo uploadInfo = sysConfigService.getUploadInfo(sysDicService.selectSysPaySwitch());
	   	   BestsignOpenApiClient openApiClient= new BestsignOpenApiClient(bestSignInfo.getDeveloperId(),bestSignInfo.getPrivateKey(),bestSignInfo.getServerHost());
           CertificateStatus CertificateStatus = new CertificateStatus();	   	  
           //??????????????????
	    	BestsignClient.companyUserReg(openApiClient,form,CertificateStatus);  	
	        CertificateStatus.setDaiZhengId(form.getId());
	    	Example example = new Example(CertificateStatus.class);
	    	example.createCriteria().andEqualTo("daiZhengId", form.getId());
	    	CertificateStatus selectCertificateStatus = certificateStatusMapper.selectOneByExample(example);
	    	if(selectCertificateStatus==null){
	    		CertificateStatus.setCreatetime(LocalDateTime.now());
	    		CertificateStatus.setCreateoper(form.getCurrUserName());
	    		certificateStatusMapper.insertSelective(CertificateStatus);
	    	}else{
	    		CertificateStatus.setId(selectCertificateStatus.getId());
	    		CertificateStatus.setModifytime(LocalDateTime.now());
	    		CertificateStatus.setModifyoper(form.getCurrUserName());
	    		certificateStatusMapper.updateByPrimaryKeySelective(CertificateStatus);
	    	}
	    	//????????????????????????????????????????????????
	    	if(CertificateStatus.getStatus().equals(Const.APPLY_CERT_STATUS5)){
	    		 //????????????
		        BestsignClient.uploadCert(openApiClient,uploadInfo,form);				
	    	}  
	    	return CertificateStatus;
	}
	
	

	@Override
	public CertificateStatus registerQuery(
			DaiZhengForm form) throws Exception {
		// TODO Auto-generated method stub
		  DaiZhengInfo daiZhengInfo = daiZhengInfoMapper.selectByPrimaryKey(form.getId());
		  getCompanyUserRegParam(form,daiZhengInfo); 
		  form.setUploadSealImgPicUrl(daiZhengInfo.getSealImgPicUrl());
		  //?????????????????????????????????
	       BestSignInfo bestSignInfo=sysConfigService.getBestSignInfo(sysDicService.selectSysPaySwitch());     
	       UploadInfo uploadInfo = sysConfigService.getUploadInfo(sysDicService.selectSysPaySwitch());
	   	   BestsignOpenApiClient openApiClient= new BestsignOpenApiClient(bestSignInfo.getDeveloperId(),bestSignInfo.getPrivateKey(),bestSignInfo.getServerHost());
         	Example example = new Example(CertificateStatus.class);
    	   example.createCriteria().andEqualTo("daiZhengId", form.getId());
           CertificateStatus selectCertificateStatus = certificateStatusMapper.selectOneByExample(example);
           if(selectCertificateStatus==null){
        	   throw new Exception("????????????????????????");           
        	   
           }
           CertificateStatus certificateStatus = new CertificateStatus();
           certificateStatus.setAccount(selectCertificateStatus.getAccount());
           certificateStatus.setTaskid(selectCertificateStatus.getTaskid());
           BestsignClient.applyCertStatus(openApiClient, certificateStatus);
	       certificateStatus.setId(selectCertificateStatus.getId());
	       certificateStatus.setModifytime(LocalDateTime.now());
	       certificateStatus.setModifyoper(form.getCurrUserName());
	       certificateStatusMapper.updateByPrimaryKeySelective(certificateStatus);
         	//????????????????????????????????????????????????
	    	if(certificateStatus.getStatus().equals(Const.APPLY_CERT_STATUS5)){
	    		 //????????????
		        BestsignClient.uploadCert(openApiClient,uploadInfo,form);				
	    	}  
	    	return certificateStatus;
	 }

	  private void getCompanyUserRegParam(DaiZhengForm form,
			DaiZhengInfo daiZhengInfo) {
		      form.setAccount(daiZhengInfo.getAccount());//??????
		      form.setDaiZhengName(daiZhengInfo.getDaiZhengName());//????????????
		      form.setRegCode(daiZhengInfo.getRegCode()); //???????????????
		      form.setOrgCode(daiZhengInfo.getOrgCode()); //??????????????????
		      form.setTaxCode(daiZhengInfo.getTaxCode()); //??????????????????
		      form.setLegalPerson(daiZhengInfo.getLegalPerson()); //?????????????????????
		      form.setLegalPersonIdentityType(daiZhengInfo.getLegalPersonIdentityType()); //????????????????????????
		      form.setLegalPersonIdentity(daiZhengInfo.getLegalPersonIdentity()); //????????????????????????		    
		      form.setDaiZhengLinkMobile(daiZhengInfo.getDaiZhengLinkMobile()); //????????????
		      form.setImageName(daiZhengInfo.getImageName());
		 }
}
