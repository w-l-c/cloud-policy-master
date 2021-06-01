package cn.rebornauto.platform.common.enums;

import java.util.HashMap;
import java.util.Map;

/** Title: 通联代付类型
 * Description: 
 * Company: 
 * @author kgc
 * @date May 1, 2019 4:25:22 PM
 */
public enum TongLianDaiFuTypeEnum {
		/**
		 * 单笔代付1
		 */
		TONGLIAN_DAI_FU_TYPE_1("单笔代付", 1),
		/**
		 * 批量代付2
		 */
		TONGLIAN_DAI_FU_TYPE_2("批量代付", 2);
	    private String name;  
	    private int index;  
	    private TongLianDaiFuTypeEnum(String name, int index) {  
	        this.name = name;  
	        this.index = index;  
	    }  
	    public static String getName(int index) {  
	        for (TongLianDaiFuTypeEnum c : TongLianDaiFuTypeEnum.values()) {  
	            if (c.getIndex() == index) {  
	                return c.name;  
	            }  
	        }  
	        return null;  
	    }  
	    public static Map<String, String> getMap(){
	    	Map<String, String> resultsMap=new HashMap<String, String>();  
	    	resultsMap.put("1", getName(1));  
	    	resultsMap.put("2", getName(2));
	        return resultsMap;
	    }
	    public String getName() {  
	        return name;  
	    }  
	    public void setName(String name) {  
	        this.name = name;  
	    }  
	    public int getIndex() {  
	        return index;  
	    }  
	    public void setIndex(int index) {  
	        this.index = index;  
	    }
}
