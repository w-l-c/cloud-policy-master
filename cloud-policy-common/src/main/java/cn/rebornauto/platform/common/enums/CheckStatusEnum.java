package cn.rebornauto.platform.common.enums;

/** Title: 审核状态
 * Description: 
 * Company: 
 * @author kgc
 * @date May 1, 2019 4:25:22 PM
 */
public enum CheckStatusEnum {
		/**
		 * 已申请1
		 */
		CHECK_STATUS_1("已申请", 1),
		/**
		 * 2已上传
		 */
		CHECK_STATUS_2("已上传", 2),
		/**
		 * 3待审核
		 */
		CHECK_STATUS_3("待审核", 3),
		/**
		 * 已审核4
		 */
		CHECK_STATUS_4("已审核", 4),
		/**
		 * 已驳回5
		 */
		CHECK_STATUS_5("已驳回", 5)
		;
	    private String name;  
	    private int index;  
	    private CheckStatusEnum(String name, int index) {  
	        this.name = name;  
	        this.index = index;  
	    }  
	    public static String getName(int index) {  
	        for (CheckStatusEnum c : CheckStatusEnum.values()) {  
	            if (c.getIndex() == index) {  
	                return c.name;  
	            }  
	        }  
	        return null;  
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
