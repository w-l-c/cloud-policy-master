package cn.rebornauto.platform.pay.tonglian.bean;

import java.time.LocalDateTime;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

@Data
public class AccountDetail {

	private String sn;	//递增序列
	private String acctno;	//账户号	C(30)	商户的通联帐户
	private String voucherno;	//凭证号	C(30)	通联凭证号
	private String crdr;	//借贷	N(1)	1.借2.贷
	private String amount;	//金额	N(30)	单位分
	private String trxid;	//交易流水	C(40)	如收付交易时为文件名
	private String paycode;	//交易代码	C(8)	交易代码，见附录
	
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime postdate;	//记账时间	C(14)	yyyyMMddhhmmss
	private String memo;	//备注	C(1024)	
	
	
}
