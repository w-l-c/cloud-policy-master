package cn.rebornauto.platform.pay.tonglian.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.rebornauto.platform.pay.tonglian.bean.AccountDetail;
import lombok.Data;

/** Title: 充值明细查询
 * Company: 
 * @author kgc
 * @date Mar 27, 2020 3:16:20 PM
 */
@Data
public class QueryDetailResponse implements Serializable{

	private static final long serialVersionUID = 3730669613870402501L;
	/**
	 * 明细列表
	 */
	private List<AccountDetail> list = new ArrayList<AccountDetail>();
	/**
	 * 查到数量
	 */
	private Integer sum = 0;
	/**
	 * 查询成功失败信息
	 */
	private String status;
	/**
	 * 返回信息
	 */
	private String msg;
	
}
