package cn.rebornauto.platform.common.data.view;

import lombok.Data;


@Data
public class OptionVO {
    private Integer key;
    private String value;

    public OptionVO() {
    }

    public OptionVO(Integer key, String value) {
        this.key = key;
        this.value = value;
    }
}
