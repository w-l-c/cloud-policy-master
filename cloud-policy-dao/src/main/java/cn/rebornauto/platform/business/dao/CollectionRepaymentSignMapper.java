package cn.rebornauto.platform.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import tk.mybatis.mapper.common.Mapper;
import cn.rebornauto.platform.business.entity.CollectionRepaymentSign;
import cn.rebornauto.platform.business.entity.CollectionRepaymentSignVO;
import cn.rebornauto.platform.business.query.CollectionRepaymentSignQuery;
import cn.rebornauto.platform.common.data.request.Pagination;

public interface CollectionRepaymentSignMapper extends Mapper<CollectionRepaymentSign> {
	CollectionRepaymentSignVO selectListByRequestSn(@Param("requestSn")String requestSn);
	
	List<CollectionRepaymentSignVO> selectByQuery(@Param("p") Pagination pagination, @Param("q") CollectionRepaymentSignQuery query);

    long countByQuery(@Param("q") CollectionRepaymentSignQuery query);
    
    /**
     * 查询半小时内，处理中的杉徳支付数据
     * @return
     */
    List<CollectionRepaymentSignVO> selectInProcessedFromSandPay();
}