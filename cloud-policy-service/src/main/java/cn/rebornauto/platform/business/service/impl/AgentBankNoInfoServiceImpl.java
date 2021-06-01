package cn.rebornauto.platform.business.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import cn.rebornauto.platform.business.dao.AgentBankNoInfoMapper;
import cn.rebornauto.platform.business.dao.AgentInfoMapper;
import cn.rebornauto.platform.business.dao.BankCodeInfoMapper;
import cn.rebornauto.platform.business.dao.BankcardVerify3AllinpayMapper;
import cn.rebornauto.platform.business.dao.BankcardVerify4AllinpayMapper;
import cn.rebornauto.platform.business.entity.AgentBankNoInfo;
import cn.rebornauto.platform.business.entity.AgentInfo;
import cn.rebornauto.platform.business.entity.BankCodeInfo;
import cn.rebornauto.platform.business.entity.BankcardVerify3Allinpay;
import cn.rebornauto.platform.business.entity.BankcardVerify4Allinpay;
import cn.rebornauto.platform.business.form.AgentBankNoInfoForm;
import cn.rebornauto.platform.business.service.AgentBankNoInfoService;
import cn.rebornauto.platform.business.vo.AgentBankNoVo;
import cn.rebornauto.platform.common.Const;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.common.util.StringUtil;
import cn.rebornauto.platform.pay.tonglian.entity.TongLianInfo;
import cn.rebornauto.platform.pay.tonglian.utils.Bankcardverify3Util;
import cn.rebornauto.platform.pay.tonglian.utils.Bankcardverify4Util;
/**
 * 
 * <p>Title: AgentBankNoInfoServiceImpl</p>  
 * <p>Description:</p>  
 * @author zjl  
 * @date 2019年5月1日
 */
@Service
public class AgentBankNoInfoServiceImpl implements AgentBankNoInfoService{
	
    @Autowired
      AgentBankNoInfoMapper  agentBankNoInfoMapper;
    
    @Autowired
     BankCodeInfoMapper  bankCodeInfoMapper;
    
    @Autowired
    BankcardVerify4AllinpayMapper  bankcardVerify4AllinpayMapper;
    
    @Autowired
    BankcardVerify3AllinpayMapper  bankcardVerify3AllinpayMapper;
    
    @Autowired
    AgentInfoMapper agentInfoMapper;

	@Override
	public List<AgentBankNoVo> selectAgentBankNoListByAgentId(int agentId) {
		// TODO Auto-generated method stub
		return agentBankNoInfoMapper.selectAgentBankNoListByAgentId(agentId);
	}

	@Override
	@Transactional
	public Response verification(AgentBankNoInfoForm form,
			TongLianInfo tongLianInfo) {
		// TODO Auto-generated method stub
		//根据银行code查询银行名称
		Example  bankCodeInfoExample = new Example(BankCodeInfo.class);
	    bankCodeInfoExample.createCriteria().andEqualTo("bankCode",StringUtil.formatBlankString(form.getAgentOpenBankCode()));
		BankCodeInfo selectBankCodeInfo = bankCodeInfoMapper.selectOneByExample(bankCodeInfoExample);	
		//3要素填写
	    BankcardVerify3Allinpay bankcardVerify3Allinpay = new	BankcardVerify3Allinpay();   
	    bankcardVerify3Allinpay.setRealname(form.getAgentName());
	    bankcardVerify3Allinpay.setIdcard(form.getAgentIdcardno());
	    bankcardVerify3Allinpay.setBankname(selectBankCodeInfo.getBankName());
	    bankcardVerify3Allinpay.setBankcode(selectBankCodeInfo.getBankCode());
	    bankcardVerify3Allinpay.setBankcard(StringUtil.formatBlankString(form.getAgentOpenBankNo()));
		
	    //判断3要素是否齐全
        if (!Bankcardverify3Util.intactBankcardVerify(bankcardVerify3Allinpay)) {
			return Response.factory().code(1).message("你的认证信息缺少,请补充完整!");
        }
        
       
        //判断3要素是否与数据库匹配
        Example bankcardVerify3AllinpayExample = new Example(BankcardVerify3Allinpay.class);
        bankcardVerify3AllinpayExample.createCriteria()
        .andEqualTo("bankcard",bankcardVerify3Allinpay.getBankcard())
        .andEqualTo("idcard",bankcardVerify3Allinpay.getIdcard())
        .andEqualTo("realname",bankcardVerify3Allinpay.getRealname());
        List<BankcardVerify3Allinpay> list = bankcardVerify3AllinpayMapper.selectByExample(bankcardVerify3AllinpayExample);
        BankcardVerify3Allinpay v = null;
        
        if(list!=null && list.size()==1){
            v = list.get(0);
//            boolean same = Bankcardverify3Util.sameBankcardVerify(bankcardVerify3Allinpay, v);
//            //1.判断信息是否一致,如果一致，返回数据库的验证结果
//            if(same){
            	if(v.getVerifystatus()==null) {
            		return Response.factory().code(1).message("你的三要素未提交成功,请重新提交!"); 
            	}else if(v.getVerifystatus()!=null && v.getVerifystatus()==0){
            		return Response.ok();
            	}else{
        			return Response.factory().code(v.getVerifystatus()).message(bankcardVerify3Allinpay.getVerifymsg());       	
            	}
//            }
            //2.如果不一致，并且数据库中的为验证通过的信息，直接返回false
//            if(!same &&  v.getVerifystatus()!=null && v.getVerifystatus()==0){
//    			return Response.factory().code(1).message("你的三要素信息不一致,请重新输入!");       	
//            }
        }
        //调用通联3要素校验
        Bankcardverify3Util.verify_allinpay(bankcardVerify3Allinpay,tongLianInfo,form.getIdType());
//        if(v!=null){
//      	  bankcardVerify3Allinpay.setId(v.getId());
//      	  bankcardVerify3Allinpay.setGmtModified(LocalDateTime.now());
//      	  bankcardVerify3AllinpayMapper.updateByPrimaryKeySelective(bankcardVerify3Allinpay);
//        }else{
      	  bankcardVerify3Allinpay.setGmtCreate(LocalDateTime.now());
          bankcardVerify3AllinpayMapper.insertSelective(bankcardVerify3Allinpay);
//        }        
        if(bankcardVerify3Allinpay.getVerifystatus()!=0){
  		   return Response.factory().code(bankcardVerify3Allinpay.getVerifystatus()).message(bankcardVerify3Allinpay.getVerifymsg());       	
        } 
  		return Response.ok();
	}
	
	@Override
	@Transactional
	public Response addAgentBankNoInfo(AgentBankNoInfoForm form,
			TongLianInfo tongLianInfo) {
		form.setAgentOpenBankNo(StringUtil.formatBlankString(form.getAgentOpenBankNo()));
		// TODO Auto-generated method stub		
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
        			return Response.factory().code(v.getVerifystatus()).message(bankcardVerify3Allinpay.getVerifymsg());       	
            	}
            }
            //2.如果不一致，并且数据库中的为验证通过的信息，直接返回false
            if(!same &&  v.getVerifystatus()!=null && v.getVerifystatus()==0){
    			return Response.factory().code(1).message("你的三要素信息不一致,请重新输入!");      	
            }
        }		
        //调用通联3要素校验
        AgentInfo agentInfoQuery = new AgentInfo();
        agentInfoQuery.setAgentIdcardno(form.getAgentIdcardno());
        AgentInfo agentInfo = agentInfoMapper.selectOne(agentInfoQuery);
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
  		   return Response.factory().code(bankcardVerify3Allinpay.getVerifystatus()).message(bankcardVerify3Allinpay.getVerifymsg());      	
        } 
        return	insertAgentBankNo(form, selectBankCodeInfo);			
	}
	
	/*
	 * 通联4要素校验
	 * @Override
	public Response verification(AgentBankNoInfoForm form,
			TongLianInfo tongLianInfo) {
		// TODO Auto-generated method stub
		//根据银行code查询银行名称
		Example  bankCodeInfoExample = new Example(BankCodeInfo.class);
		bankCodeInfoExample.createCriteria().andEqualTo("bankCode",form.getAgentOpenBankCode());
		BankCodeInfo selectBankCodeInfo = bankCodeInfoMapper.selectOneByExample(bankCodeInfoExample);	
				
		//4要素填写
	    BankcardVerify4Allinpay bankcardVerify4Allinpay = new	BankcardVerify4Allinpay();   
	    bankcardVerify4Allinpay.setRealname(form.getAgentName());
	    bankcardVerify4Allinpay.setIdcard(form.getAgentIdcardno());
	    bankcardVerify4Allinpay.setMobile(form.getAgentMobile());
	    bankcardVerify4Allinpay.setBankname(selectBankCodeInfo.getBankName());
	    bankcardVerify4Allinpay.setBankcode(selectBankCodeInfo.getBankCode());
	    bankcardVerify4Allinpay.setBankcard(form.getAgentOpenBankNo());
	    
        //判断4要素是否齐全
        if (!Bankcardverify4Util.intactBankcardVerify(bankcardVerify4Allinpay)) {
			return Response.factory().code(1).message("你的认证信息缺少,请补充完整!");
        }
        //判断4要素是否与数据库匹配
        Example bankcardVerify4AllinpayExample = new Example(BankcardVerify4Allinpay.class);
        bankcardVerify4AllinpayExample.createCriteria().andEqualTo("bankcard",bankcardVerify4Allinpay.getBankcard());
        List<BankcardVerify4Allinpay> list = bankcardVerify4AllinpayMapper.selectByExample(bankcardVerify4AllinpayExample);
        BankcardVerify4Allinpay v = null;
        
        if(list!=null && list.size()==1){
            v = list.get(0);
            boolean same = Bankcardverify4Util.sameBankcardVerify(bankcardVerify4Allinpay, v);
            //1.判断信息是否一致,如果一致，返回数据库的验证结果
            if(same){
            	if(v.getVerifystatus()==0){
            		return Response.ok();
            	}else{
        			return Response.factory().code(v.getVerifystatus()).message("你的银行卡信息校验错误,请重新输入!");       	
            	}
            }
            //2.如果不一致，并且数据库中的为验证通过的信息，直接返回false
            if(!same &&  v.getVerifystatus()!=null && v.getVerifystatus()==0){
    			return Response.factory().code(1).message("你的银行卡信息校验错误,请重新输入!");       	
            }
        }
      
       //调用通联4要素校验
      Bankcardverify4Util.verify_allinpay(bankcardVerify4Allinpay,tongLianInfo);
      if(v!=null){
    	  bankcardVerify4Allinpay.setId(v.getId());
    	  bankcardVerify4Allinpay.setGmtModified(LocalDateTime.now());
    	  bankcardVerify4AllinpayMapper.updateByPrimaryKeySelective(bankcardVerify4Allinpay);
      }else{
    	  bankcardVerify4Allinpay.setGmtCreate(LocalDateTime.now());
          bankcardVerify4AllinpayMapper.insertSelective(bankcardVerify4Allinpay);
      }        
      if(bankcardVerify4Allinpay.getVerifystatus()!=0){
		   return Response.factory().code(bankcardVerify4Allinpay.getVerifystatus()).message("你的银行卡信息校验错误,请重新输入!");       	
      } 
		return Response.ok();

	}	 */
	
/*	校验4要素添加银行卡
 * @Override
	public Response addAgentBankNoInfo(AgentBankNoInfoForm form,TongLianInfo tongLianInfo) {
		// TODO Auto-generated method stub
		//根据银行code查询银行名称
		Example  bankCodeInfoExample = new Example(BankCodeInfo.class);
		bankCodeInfoExample.createCriteria().andEqualTo("bankCode",form.getAgentOpenBankCode());
		BankCodeInfo selectBankCodeInfo = bankCodeInfoMapper.selectOneByExample(bankCodeInfoExample);	
				
		//4要素填写
	    BankcardVerify4Allinpay bankcardVerify4Allinpay = new	BankcardVerify4Allinpay();   
	    bankcardVerify4Allinpay.setRealname(form.getAgentName());
	    bankcardVerify4Allinpay.setIdcard(form.getAgentIdcardno());
	    bankcardVerify4Allinpay.setMobile(form.getAgentMobile());
	    bankcardVerify4Allinpay.setBankname(selectBankCodeInfo.getBankName());
	    bankcardVerify4Allinpay.setBankcode(selectBankCodeInfo.getBankCode());
	    bankcardVerify4Allinpay.setBankcard(form.getAgentOpenBankNo());
	    
        //判断4要素是否齐全
        if (!Bankcardverify4Util.intactBankcardVerify(bankcardVerify4Allinpay)) {
			return Response.factory().code(1).message("你的银行卡信息缺少,请补充完整!");
        }
        //判断4要素是否与数据库匹配
        Example bankcardVerify4AllinpayExample = new Example(BankcardVerify4Allinpay.class);
        bankcardVerify4AllinpayExample.createCriteria().andEqualTo("bankcard",bankcardVerify4Allinpay.getBankcard());
        List<BankcardVerify4Allinpay> list = bankcardVerify4AllinpayMapper.selectByExample(bankcardVerify4AllinpayExample);
        BankcardVerify4Allinpay v = null;
        
        if(list!=null && list.size()==1){
            v = list.get(0);
            boolean same = Bankcardverify4Util.sameBankcardVerify(bankcardVerify4Allinpay, v);
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
      
       //调用通联4要素校验
      Bankcardverify4Util.verify_allinpay(bankcardVerify4Allinpay,tongLianInfo);
      if(v!=null){
    	  bankcardVerify4Allinpay.setId(v.getId());
    	  bankcardVerify4Allinpay.setGmtModified(LocalDateTime.now());
    	  bankcardVerify4AllinpayMapper.updateByPrimaryKeySelective(bankcardVerify4Allinpay);
      }else{
    	  bankcardVerify4Allinpay.setGmtCreate(LocalDateTime.now());
          bankcardVerify4AllinpayMapper.insertSelective(bankcardVerify4Allinpay);
      }        
      if(bankcardVerify4Allinpay.getVerifystatus()!=0){
		   return Response.factory().code(bankcardVerify4Allinpay.getVerifystatus()).message("你的银行卡信息校验错误,请重新输入!");       	
      } 
           return	insertAgentBankNo(form, selectBankCodeInfo);			
	  }*/

	private Response insertAgentBankNo(AgentBankNoInfoForm form,BankCodeInfo selectBankCodeInfo) {			
		//录入银行卡信息
		Example agentBankNoInfoExample = new Example(AgentBankNoInfo.class);
		agentBankNoInfoExample.createCriteria()
	    .andEqualTo("agentId",form.getId())
		.andEqualTo("agentOpenBankNo",form.getAgentOpenBankNo());	
		int agentBankNoInfoCount= agentBankNoInfoMapper.selectCountByExample(agentBankNoInfoExample);
		if(agentBankNoInfoCount<1){
			AgentBankNoInfo insertAgentBankNoInfo = new AgentBankNoInfo();
			insertAgentBankNoInfo.setAgentId(form.getId());
			insertAgentBankNoInfo.setAgentOpenBankNo(form.getAgentOpenBankNo());
			insertAgentBankNoInfo.setAgentOpenBankName(selectBankCodeInfo.getBankName());
			insertAgentBankNoInfo.setAgentOpenBankCode(form.getAgentOpenBankCode());
/*			insertAgentBankNoInfo.setAgentOpenBankMobile(form.getAgentMobile());*/
			insertAgentBankNoInfo.setCreatetime(LocalDateTime.now());
			agentBankNoInfoMapper.insertSelective(insertAgentBankNoInfo);						
		}
		return Response.ok();
	}
	
	@Override
	public AgentBankNoInfo selectAgentBankNoInfoByOpenBankNo(String  agentOpenBankNo) {
		AgentBankNoInfo info = new AgentBankNoInfo();
		info.setAgentOpenBankNo(agentOpenBankNo);
		info.setDataStatus(Const.DATA_STATUS_1);
		return agentBankNoInfoMapper.selectOne(info);
	}


	@Override
	public List<AgentBankNoVo> selectAgentBankNoListAuthByAgentId(int agentId) {
		return agentBankNoInfoMapper.selectAgentBankNoListAuthByAgentId(agentId);
	}

}
