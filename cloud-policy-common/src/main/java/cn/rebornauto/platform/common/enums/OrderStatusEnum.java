package cn.rebornauto.platform.common.enums;

/** Title: 订单状态
 * Description: 
 * Company: 
 * @author kgc
 * @date May 1, 2019 4:25:22 PM
 */
public enum OrderStatusEnum {
		/**
		 * 未完成0
		 */
		ORDER_STATUS_0("未完成", 0),
		/**
		 * 已完成1
		 */
		ORDER_STATUS_1("已完成", 1)
		;
	    private String name;  
	    private int index;  
	    private OrderStatusEnum(String name, int index) {  
	        this.name = name;  
	        this.index = index;  
	    }  
	    public static String getName(int index) {  
	        for (OrderStatusEnum c : OrderStatusEnum.values()) {  
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
