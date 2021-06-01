package cn.rebornauto.platform.common.data.view;

import cn.rebornauto.platform.common.data.request.Pagination;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MakeInvoiceTableBody {

    //dictionary
    //extra
    //list
    //pagination
	private BigDecimal invoiceAmount;

    private List list = new ArrayList();

    private Pagination pagination;

    private Map extra = new LinkedHashMap();

    private Map dictionary= new LinkedHashMap();

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public Map getExtra() {
        return extra;
    }

    public void setExtra(Map extra) {
        this.extra = extra;
    }

    public Map getDictionary() {
        return dictionary;
    }

    public void setDictionary(Map dictionary) {
        this.dictionary = dictionary;
    }

    public MakeInvoiceTableBody putDictionary(String key,Object value){
        this.dictionary.put(key,value);
        return this;
    }
    public static MakeInvoiceTableBody factory(){
        return new MakeInvoiceTableBody();
    }

	public BigDecimal getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(BigDecimal invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}



}
