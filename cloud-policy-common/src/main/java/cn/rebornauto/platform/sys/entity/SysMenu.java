package cn.rebornauto.platform.sys.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class SysMenu implements Serializable {
    private Integer id;

    private String menuname;

    private String permission;

    private String icon;

    private String router;

    private Byte menutype;

    private Integer parentid;

    private Byte status;

    private Integer menulevel;

    private Integer menuorder;

    private Date createtime;

    private Date updatetime;

    private String remark;

    private List<SysMenu> children;


}