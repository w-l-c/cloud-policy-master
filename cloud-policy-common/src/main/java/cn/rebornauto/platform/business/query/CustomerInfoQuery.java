package cn.rebornauto.platform.business.query;

import cn.rebornauto.platform.common.data.request.Query;
import lombok.Data;


@Data
public class CustomerInfoQuery extends Query {
	
	private String  id;

	private String  customerName;

}
