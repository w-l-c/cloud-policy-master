package cn.rebornauto.platform.sys.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysUserRole implements Serializable {
    private Integer id;

    private Integer userid;

    private Integer roleid;

    private Date createtime;

    private static final long serialVersionUID = 1L;
}