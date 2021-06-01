package cn.rebornauto.platform.common.utils.json;

import com.alibaba.fastjson.serializer.NameFilter;

import java.util.HashMap;

/**
 * jsonKey修改:modifyStr[id>key;name>value]
 * {"id":1,"name":"x"} ->   {"key":1,"value":"x"}
 * for example:
 * SimplePropertyPreFilter spp = new SimplePropertyPreFilter("id", "name", "parentid","children");
 * JsonNameFilter namefilter = new JsonNameFilter("id>key;name>value;parentid>parentKey");
 * String str = JSONObject.toJSONString(trees, new SerializeFilter[]{spp, namefilter});
 */
public class JsonNameFilter implements NameFilter {
    HashMap<String, String> Names = new HashMap<>();
    //"a>b;c>d"
    public JsonNameFilter(String modifyStr) {
        String[] names = modifyStr.split(";");
        for (String n : names) {
            String[] kk = n.split(">");
            Names.put(kk[0], kk[1]);
        }
    }

    @Override
    public String process(Object object, String name, Object value) {
        if (Names.containsKey(name)) {
            return Names.get(name);
        }
        return name;
    }
}
