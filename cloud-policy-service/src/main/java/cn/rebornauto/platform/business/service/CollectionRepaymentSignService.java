package cn.rebornauto.platform.business.service;

import java.util.List;

import cn.rebornauto.platform.business.entity.CollectionRepaymentSignVO;
import cn.rebornauto.platform.business.query.CollectionRepaymentSignQuery;
import cn.rebornauto.platform.common.data.request.Pagination;

/** Title: 
 * Description: 
 * Company: 
 * @author kgc
 * @date May 3, 2019 8:08:10 AM
 */
public interface CollectionRepaymentSignService {

	List<CollectionRepaymentSignVO> pageQuery(Pagination pagination, CollectionRepaymentSignQuery query);
	
	long countQuery(CollectionRepaymentSignQuery query);
	
	List<CollectionRepaymentSignVO> selectInProcessedFromSandPay();
}
