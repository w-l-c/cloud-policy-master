package cn.rebornauto.platform.common.utils.json;

import com.alibaba.fastjson.serializer.ValueFilter;

import java.util.Arrays;
import java.util.HashSet;

public class JsonInt2StrValueFilter implements ValueFilter {

    private HashSet<String> keys = new HashSet<>();

    public JsonInt2StrValueFilter(String... key) {
        this.keys.addAll(Arrays.asList(key));
    }

    @Override
    public Object process(Object object, String name, Object value) {
        if (keys.contains(name)) {
            return "" + value;
        }
        return value;
    }
}
