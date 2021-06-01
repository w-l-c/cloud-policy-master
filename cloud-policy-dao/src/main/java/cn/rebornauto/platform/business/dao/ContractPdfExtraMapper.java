package cn.rebornauto.platform.business.dao;

import cn.rebornauto.platform.business.entity.ContractPdfExtra;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface ContractPdfExtraMapper extends Mapper<ContractPdfExtra> {
    String selectManualSigningUrl(@Param("agentId") Integer agentId,@Param("daiZhengId") Integer daiZhengId,@Param("type") int type);
}