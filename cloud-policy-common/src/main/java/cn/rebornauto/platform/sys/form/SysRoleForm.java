package cn.rebornauto.platform.sys.form;

import cn.rebornauto.platform.common.data.request.Form;
import cn.rebornauto.platform.sys.entity.SysRole;

import javax.validation.constraints.NotNull;
import java.util.List;

public class SysRoleForm extends Form{

    @NotNull
    private String roleName;

    @NotNull
    private int status;

    @NotNull
    private List<String> menuids;

    private String remark;


    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<String> getMenuids() {
        return menuids;
    }

    public void setMenuids(List<String> menuids) {
        this.menuids = menuids;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean Changed(SysRole sysRole) {
        boolean cname = this.roleName.equals(sysRole.getRolename());
        boolean cmark = this.remark.equals(sysRole.getRemark());
        boolean cstatus = this.status == sysRole.getStatus();
        return !(cname && cmark && cstatus);
    }
}
