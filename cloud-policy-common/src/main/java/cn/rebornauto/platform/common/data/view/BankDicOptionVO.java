package cn.rebornauto.platform.common.data.view;

import lombok.Data;


@Data
public class BankDicOptionVO {
    private String key; //银行代码
    private String value; //银行名称
	private Integer disabled; // 0 有效 1 无效

    public BankDicOptionVO() {
    }

    public BankDicOptionVO(String key, String value,Integer disabled) {
        this.key = key;
        this.value = value;
        this.disabled = disabled;
    }
}
