package cn.rebornauto.platform.common.service.impl;

import cn.rebornauto.platform.business.dao.*;
import cn.rebornauto.platform.business.entity.CustomerInfo;
import cn.rebornauto.platform.common.Const;
import cn.rebornauto.platform.common.data.view.BankDicOptionVO;
import cn.rebornauto.platform.common.data.view.DaiZhengDicOptionVO;
import cn.rebornauto.platform.common.data.view.PaymentChannelConfigVO;
import cn.rebornauto.platform.common.data.view.SysDicOptionVO;
import cn.rebornauto.platform.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommonServiceImpl implements CommonService {
	
	//国籍
	private  String  NATIONALITY="nationality";
	
	//证件类型
	private  String  ID_TYPE="id_type";

	@Autowired
	private CustomerInfoMapper customerInfoMapper;
	
    @Autowired
    private DaiZhengInfoMapper daiZhengInfoMapper;
    
    @Autowired
    private BankCodeInfoMapper bankCodeInfoMapper;
    
    @Autowired
    private SysDicMapper  SysDicMapper;

    @Autowired
	private PaymentChannelMapper paymentChannelMapper;
	@Override
	public List<BankDicOptionVO> selectBankDics() {
		// TODO Auto-generated method stub
	 List<BankDicOptionVO> bankDics = bankCodeInfoMapper.selectBankDics();
	 bankDics.forEach(bankDic -> {
			if(bankDic.getDisabled()==Const.STATUS_1){
				bankDic.setDisabled(Const.DISABLED_VALID);
				}else{
				bankDic.setDisabled(Const.DISABLED_INEFFECTIVE);
			 }
		});
	return bankDics;
	}

	@Override
	public List<DaiZhengDicOptionVO> selectDaiZhengDics() {
		// TODO Auto-generated method stub
		List<DaiZhengDicOptionVO> daiZhengDics = daiZhengInfoMapper.selectDaiZhengDics();
		daiZhengDics.forEach(daiZhengDic -> {
			if(daiZhengDic.getDisabled()==Const.STATUS_1){
				daiZhengDic.setDisabled(Const.DISABLED_VALID);
				}else{
			    daiZhengDic.setDisabled(Const.DISABLED_INEFFECTIVE);
			 }
		});
       return daiZhengDics;
	}

	@Override
	public List<SysDicOptionVO> selectNationalityDics() {
		// TODO Auto-generated method stub
		return SysDicMapper.selectSysDics(NATIONALITY);
	}

	@Override
	public List<SysDicOptionVO> selectIdTypeDics() {
		// TODO Auto-generated method stub
		return SysDicMapper.selectSysDics(ID_TYPE);
	}

    @Override
    public List selectParamAll() {
		List list = new ArrayList();
		List<CustomerInfo> customerInfos = customerInfoMapper.selectAll();
		for (CustomerInfo customerInfo : customerInfos) {
			Map map = new HashMap();
			map.put("key",customerInfo.getId());
			map.put("value",customerInfo.getCustomerName());
			list.add(map);
		}
		return list;
    }

	@Override
	public List selectParam4CustomerId(String customerId) {
		List list = new ArrayList();
		CustomerInfo customerInfo = customerInfoMapper.selectByPrimaryKey(customerId);
		Map map = new HashMap();
		map.put("key",customerInfo.getId());
		map.put("value",customerInfo.getCustomerName());
		list.add(map);
		return list;
	}

	@Override
	public CustomerInfo selectCustomerInfo(String customerId) {
		return customerInfoMapper.selectByPrimaryKey(customerId);
	}
}
