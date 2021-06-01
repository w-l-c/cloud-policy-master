package cn.rebornauto.platform.business.dao;

import java.util.List;

import cn.rebornauto.platform.business.entity.BankCodeInfo;
import cn.rebornauto.platform.common.data.view.BankDicOptionVO;
import tk.mybatis.mapper.common.Mapper;

public interface BankCodeInfoMapper extends Mapper<BankCodeInfo> {

	List<BankDicOptionVO> selectBankDics();
}