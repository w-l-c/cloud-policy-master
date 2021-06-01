package cn.rebornauto.platform.business.dao;

import cn.rebornauto.platform.business.entity.BusiLog;
import cn.rebornauto.platform.business.query.BusiLogQuery;
import cn.rebornauto.platform.business.vo.BusiLogVo;
import cn.rebornauto.platform.common.data.request.Pagination;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BusiLogMapper extends Mapper<BusiLog> {
    List<BusiLogVo> list(@Param("q") BusiLogQuery query,@Param("p") Pagination pagination);

    int count(@Param("q") BusiLogQuery query);

	List<BusiLogVo> selectBusiLogExcelByQuery(@Param("q") BusiLogQuery query);
}