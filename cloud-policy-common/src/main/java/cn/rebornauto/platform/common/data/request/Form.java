package cn.rebornauto.platform.common.data.request;

import lombok.Data;

@Data
public class Form {
    private int id;

    private Integer currUserId;

    private String currUserName;

    private Byte enabled;

    private Byte isDeleted;
    /**
	 *  用户表客户编号 
	 */
    private String userCustomerId;
}
