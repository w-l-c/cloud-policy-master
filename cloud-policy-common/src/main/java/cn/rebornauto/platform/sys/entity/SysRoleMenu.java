package cn.rebornauto.platform.sys.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysRoleMenu implements Serializable {
    private Integer id;

    private Integer roleid;

    private Integer menuid;

    private Date createtime;

    private static final long serialVersionUID = 1L;


}