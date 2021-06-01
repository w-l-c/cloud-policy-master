package cn.rebornauto.platform.business.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import tk.mybatis.mapper.common.Mapper;
import cn.rebornauto.platform.business.entity.DaiZhengInfo;
import cn.rebornauto.platform.business.query.CertificateStatusQuery;
import cn.rebornauto.platform.business.vo.DaiZhengInfoVo;
import cn.rebornauto.platform.common.data.request.Pagination;
import cn.rebornauto.platform.common.data.request.Query;
import cn.rebornauto.platform.common.data.view.DaiZhengDicOptionVO;

public interface DaiZhengInfoMapper extends Mapper<DaiZhengInfo> {
	List<DaiZhengInfoVo> list(@Param("p") Pagination pagination);

    long count(@Param("q") Query query);

	List<DaiZhengDicOptionVO> selectDaiZhengDics();
	
	int cancelDaiZhengQuotaById(@Param("daiZhengId") Integer daiZhengId,@Param("amount") BigDecimal amount);
	
	int addDaiZhengQuotaById(@Param("daiZhengId") Integer daiZhengId,@Param("amount") BigDecimal amount);
}