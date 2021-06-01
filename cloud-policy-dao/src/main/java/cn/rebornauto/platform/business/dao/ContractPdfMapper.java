package cn.rebornauto.platform.business.dao;

import org.apache.ibatis.annotations.Param;

import cn.rebornauto.platform.business.entity.ContractPdf;
import tk.mybatis.mapper.common.Mapper;

public interface ContractPdfMapper extends Mapper<ContractPdf> {

	String selectManualSigningUrl(@Param("agentId") Integer agentId, @Param("daiZhengId")Integer daiZhengId);
}