package cn.rebornauto.platform.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import tk.mybatis.mapper.common.Mapper;
import cn.rebornauto.platform.business.entity.Order;
import cn.rebornauto.platform.business.entity.OrderVO;
import cn.rebornauto.platform.business.query.OrderQuery;
import cn.rebornauto.platform.common.data.request.Pagination;

public interface OrderMapper extends Mapper<Order> {
	List<OrderVO> selectByQuery(@Param("p") Pagination pagination, @Param("q") OrderQuery query);

    long countByQuery(@Param("q") OrderQuery query);
    
    int updateCheckinfoByOrderId(@Param("orderId") Integer orderId,@Param("checkoper") String checkoper,@Param("checkStatus") Integer checkStatus,@Param("remark") String remark);
    
    OrderVO selectStatByOrderId(@Param("orderId") Integer orderId);
    
    int updateSuccCountByOrderId(@Param("orderId") Integer orderId);
    
    int updateFailCountByOrderId(@Param("orderId") Integer orderId);
    
    int updatePayStatByOrderId(@Param("orderId") Integer orderId);
    
    int updateOrderStatusByOrderId(@Param("orderId") Integer orderId);
    
    int updateMergeCountByOrderId(@Param("orderId") Integer orderId);

	List<OrderVO> selectPaySuccessExcelByQuery(@Param("q") OrderQuery query);
}