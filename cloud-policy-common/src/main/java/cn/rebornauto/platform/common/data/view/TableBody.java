package cn.rebornauto.platform.common.data.view;

import cn.rebornauto.platform.common.data.request.Pagination;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TableBody {

    //dictionary
    //extra
    //list
    //pagination

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

    public TableBody putDictionary(String key,Object value){
        this.dictionary.put(key,value);
        return this;
    }
    public static TableBody factory(){
        return new TableBody();
    }



}
