package cn.rebornauto.platform.common.data.view;

import lombok.Data;


@Data
public class DaiZhengDicOptionVO {
    private Integer key;//代征主体id
    private String value;//代征主体名称
	private Integer disabled; // 0 有效 1 无效

    public DaiZhengDicOptionVO() {
    }

    public DaiZhengDicOptionVO(Integer key, String value,Integer disabled) {
        this.key = key;
        this.value = value;
        this.disabled = disabled;
    }
}
