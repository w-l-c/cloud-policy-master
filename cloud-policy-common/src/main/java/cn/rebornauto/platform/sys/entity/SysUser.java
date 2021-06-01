package cn.rebornauto.platform.sys.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class SysUser implements Serializable {
    private Integer id;

    private String uid;

    private String account;

    private String username;

    private String password;

    private String mobile;

    private String nickname;

    private Byte status;

    private Byte issys;

    private String email;

    private Integer deptid;

    private String remark;

    private Date createtime;

    private Date updatetime;

    private SysDept sysDept;

    private List<SysRole> sysRoleList;

    private String avatar;

    private List<Integer> menuids;
    
    private String customerId;
    
    private String phones;
    
    private List<String> mobiles;
}