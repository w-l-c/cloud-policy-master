package cn.rebornauto.platform.sys.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysLogs implements Serializable {
    private Integer id;

    private Integer userid;

    private String username;

    private String logtype;

    private String remark;

    private Integer bussessid;

    private String sourceip;

    private String useragent;

    private Date createtime;

    private String parameters;

    private static final long serialVersionUID = 1L;


}