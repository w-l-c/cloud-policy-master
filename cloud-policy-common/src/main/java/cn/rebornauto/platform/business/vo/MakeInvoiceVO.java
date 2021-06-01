package cn.rebornauto.platform.business.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import cn.rebornauto.platform.business.entity.MakeInvoiceDetail;
import lombok.Data;

@Data
public class MakeInvoiceVO implements Serializable {
	private static final long serialVersionUID = -5139282055786169603L;

	private List<MakeInvoiceDetail> detailList;

    private BigDecimal invoiceAmount;
}