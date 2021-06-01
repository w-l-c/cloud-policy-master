package cn.rebornauto.platform.business.service;
import java.util.List;

import cn.rebornauto.platform.business.form.CustomerInfoForm;
import cn.rebornauto.platform.business.query.CustomerInfoQuery;
import cn.rebornauto.platform.business.vo.CustomerVo;
import cn.rebornauto.platform.common.data.request.Pagination;


/**
 * 
 * <p>Title: CustomerInfoService</p>  
 * <p>Description:</p>  
 * @author zjl  
 * @date 2019年4月30日
 */
public interface CustomerInfoService {
	

   /**
    * 根据客户编号获得代征主体Id
    * @param customerId
    * @return
    */
	Integer selectDaiZhengIdByCustomerId(String customerId);
	
     /**
      * 获取总条数
      * @param query
      * @return
      */
	int countByQuery(CustomerInfoQuery query);
	
    /**
     * 分页列表查询
     * @param pagination
     * @param query
     * @return
     */
	List<CustomerVo> pageQuery(Pagination pagination,CustomerInfoQuery query);
	
	
   /**
    * 录入客户信息
    * @param form
 * @throws Exception 
    */
	void  add(CustomerInfoForm form) throws Exception;
	
    /**
     * 修改客户信息
     * @param form
     * @return
     * @throws Exception 
     */
    int edit(CustomerInfoForm form) throws Exception;
    
			
}
