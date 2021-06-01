package cn.rebornauto.platform.sys.form;

import cn.rebornauto.platform.common.Const;
import cn.rebornauto.platform.common.data.request.Form;
import cn.rebornauto.platform.sys.entity.SysUser;
import lombok.Data;

import javax.validation.constraints.NotNull;

import java.util.List;

@Data
public class SysUserForm extends Form {

    @NotNull
    private List<String> roleids;

    @NotNull
    private String username;

    @NotNull
    private String nickname;

    @NotNull
    private String password;

    private String newpassword;

    @NotNull
    private String mobile;

    @NotNull
    private Byte status;

    private String email;

    @NotNull
    private Integer deptid;

    private String remark;

    private String avatar;
    
    private String customerId;
    
    private List<String> mobiles;


    public SysUser getUser() {
        SysUser user = new SysUser();
        user.setNickname(nickname);
        user.setPassword(password);
        user.setMobile(mobile);
        user.setStatus(status);
        user.setDeptid(deptid);
        user.setIssys(Const.ISNOTSYS);
        user.setAccount(username);
        user.setUsername(username);
        user.setEmail(email);
        user.setRemark(remark);
        user.setAvatar(avatar);
        user.setCustomerId(customerId);
        user.setPhones(String.join(",", mobiles));
        return user;
    }

}
