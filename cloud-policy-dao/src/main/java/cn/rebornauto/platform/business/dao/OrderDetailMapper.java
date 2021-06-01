package cn.rebornauto.platform.business.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import tk.mybatis.mapper.common.Mapper;
import cn.rebornauto.platform.business.entity.OrderDetail;
import cn.rebornauto.platform.business.entity.OrderDetailVO;
import cn.rebornauto.platform.business.query.OrderDetailQuery;
import cn.rebornauto.platform.common.data.request.Pagination;

public interface OrderDetailMapper extends Mapper<OrderDetail> {
	OrderDetail selectSumAmountByOrderId(@Param("orderId") Integer orderId);
	
	OrderDetailVO selectOrderInfoByOrderId(@Param("orderId") Integer orderId,@Param("agentId") Integer agentId,@Param("openBankNo") String openBankNo);
	
	List<OrderDetailVO> selectUnpayOrderInfoByOrderId(@Param("orderId") Integer orderId);
	
	List<OrderDetailVO> selectByDetailQuery(@Param("p") Pagination pagination, @Param("q") OrderDetailQuery query);

    long countByDetailQuery(@Param("q") OrderDetailQuery query);
    
    /**
	 * 批量新增
	 * @param list
	 * @return
	 */
	int insertBatch(@Param("list") List<OrderDetail> list);
	/**
	 *  导出支付失败数据 
	 * @param orderId
	 * @return
	 */
	List<OrderDetailVO> exportPayErrorDataExcel(@Param("orderId") Integer orderId);
	/**
	 * 根据明细id更新合并编号
	 * @param params
	 * @return
	 */
	int updateDetailByIds(Map<String, Object> params);
	
}