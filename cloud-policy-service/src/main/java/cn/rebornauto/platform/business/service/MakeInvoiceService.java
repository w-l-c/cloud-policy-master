package cn.rebornauto.platform.business.service;

import java.math.BigDecimal;

import cn.rebornauto.platform.business.entity.MakeInvoice;

/** Title: 
 * Description: 
 * Company: 
 * @author kgc
 * @date Mar 17, 2020 3:06:45 PM
 */
public interface MakeInvoiceService {

	BigDecimal checkDataUnExistToCreate(String custmerId,String createoper);
	
	MakeInvoice getInvoiceAmount(String customerId);
}
