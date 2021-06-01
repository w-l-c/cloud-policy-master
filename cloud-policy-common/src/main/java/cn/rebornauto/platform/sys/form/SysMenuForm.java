package cn.rebornauto.platform.sys.form;

import cn.rebornauto.platform.common.data.request.Form;
import cn.rebornauto.platform.sys.entity.SysMenu;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class SysMenuForm extends Form {

    @NotNull
    private Integer parentid;

    @NotNull
    private int menutype;

    @Min(0)
    @Max(2)
    private int status;

    @NotNull
    private String menuname;

    private String icon;

    private String router;

    private String permission;

    private int menuorder;

    private String remark;

    public SysMenu toSysMenu() {
        SysMenu sysMenu = new SysMenu();
        sysMenu.setId(getId());
        sysMenu.setParentid(parentid);
        sysMenu.setMenuname(menuname);
        sysMenu.setMenutype((byte) menutype);
        sysMenu.setStatus((byte) status);
        sysMenu.setIcon(icon);
        sysMenu.setMenuorder(menuorder);
        sysMenu.setRouter(router);
        sysMenu.setPermission(permission);
        sysMenu.setRemark(remark);
        return sysMenu;
    }
}
