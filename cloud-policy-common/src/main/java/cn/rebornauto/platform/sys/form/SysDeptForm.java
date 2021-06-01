package cn.rebornauto.platform.sys.form;

import cn.rebornauto.platform.common.data.request.Form;
import cn.rebornauto.platform.sys.entity.SysDept;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SysDeptForm extends Form {

    @NotNull
    private String name;

    @NotNull
    private Integer parentid;

    @NotNull
    private Byte status;

    @NotNull
    private String remark;

    private Short deptorder;

    public SysDept getDept() {
        SysDept dept = new SysDept();
        dept.setId(this.getId());
        dept.setName(this.name);
        dept.setStatus(this.status);
        dept.setRemark(this.remark);
        dept.setParentid(this.parentid);
        dept.setDeptorder(this.deptorder);
        return dept;
    }
}
