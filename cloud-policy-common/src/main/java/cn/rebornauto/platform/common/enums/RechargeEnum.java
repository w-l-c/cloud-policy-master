package cn.rebornauto.platform.common.enums;

/** Title: 充值类型
 * Description: 
 * Company: 
 * @author kgc
 * @date May 1, 2019 4:25:22 PM
 */
public enum RechargeEnum {
		/**
		 * 已申请1
		 */
		RECHARGE_1("已申请", 1),
		/**
		 * 待审核2
		 */
		RECHARGE_2("待审核", 2),
		/**
		 * 已到账3
		 */
		RECHARGE_3("已到账", 3),
		/**
		 * 已驳回4
		 */
		RECHARGE_4("已驳回", 4)
		;
	    private String name;  
	    private int index;  
	    private RechargeEnum(String name, int index) {  
	        this.name = name;  
	        this.index = index;  
	    }  
	    public static String getName(int index) {  
	        for (RechargeEnum c : RechargeEnum.values()) {  
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
