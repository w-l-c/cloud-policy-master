package cn.rebornauto.platform.business.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.rebornauto.platform.business.entity.DaiZhengRecharge;
import cn.rebornauto.platform.business.query.InvoiceQuery;
import cn.rebornauto.platform.business.query.RechargeQuery;
import cn.rebornauto.platform.business.vo.RechargeVo;
import cn.rebornauto.platform.common.data.request.Pagination;
import tk.mybatis.mapper.common.Mapper;

public interface DaiZhengRechargeMapper extends Mapper<DaiZhengRecharge> {
    long count(@Param("q") RechargeQuery query);

    List<RechargeVo> page(@Param("q")RechargeQuery query, @Param("p") Pagination pagination);

    int count4CheckList(@Param("q") RechargeQuery query);

    List<RechargeVo> page4CheckList(@Param("q") RechargeQuery query, @Param("p") Pagination pagination);

	BigDecimal selectTotalRechargeAmount(@Param("q") InvoiceQuery query);

	List<RechargeVo> selectRechargeExcelByQuery(@Param("q") RechargeQuery query);

}