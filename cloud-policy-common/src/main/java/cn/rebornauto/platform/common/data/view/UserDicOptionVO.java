package cn.rebornauto.platform.common.data.view;

import lombok.Data;


@Data
public class UserDicOptionVO {
    private String key; 
    private String value; 
	private String publicName; 
	private Integer disabled; // 0 有效 1 无效

    public UserDicOptionVO() {
    }

    public UserDicOptionVO(String key, String value,Integer disabled) {
        this.key = key;
        this.value = value;
        this.disabled = disabled;
    }
}
