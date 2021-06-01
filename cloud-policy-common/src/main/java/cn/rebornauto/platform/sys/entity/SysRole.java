package cn.rebornauto.platform.sys.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class SysRole implements Serializable {
    private Integer id;

    private String rolename;

    private Byte status;

    private String remark;

    private Date createtime;

    private Date updatetime;

    List<Integer> menuids;

    private static final long serialVersionUID = 1L;


}