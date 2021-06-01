package cn.rebornauto.platform.common.utils.json;

import com.alibaba.fastjson.serializer.ValueFilter;

public class JsonNullValueFilter implements ValueFilter {

    private Object n;

    public JsonNullValueFilter(Object n) {
        this.n = n;
    }

    @Override
    public Object process(Object object, String name, Object value) {
        if(value==null){
            return n;
        }
        return value;
    }
}
