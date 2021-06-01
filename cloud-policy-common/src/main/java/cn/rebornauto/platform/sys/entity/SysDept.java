package cn.rebornauto.platform.sys.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class SysDept implements Serializable {

    private Integer id;

    private String name;

    private Integer parentid;

    private Byte status;

    private Date createtime;

    private Date updatetime;

    private String remark;

    private Short deptorder;

    private List<SysDept> children;

    private static final long serialVersionUID = 1L;
}