package cn.rebornauto.platform.common.data.view;

import lombok.Data;


@Data
public class SysDicOptionVO {
    private Integer key;   //tb_sys_dic        id
    private String value;  //tb_sys_dic       label
	private Byte disabled;//tb_sys_dic    enabled  0 有效 1 无效

    public SysDicOptionVO() {
    }

    public SysDicOptionVO(Integer key, String value,Byte disabled) {
        this.key = key;
        this.value = value;
        this.disabled = disabled;
    }
}
