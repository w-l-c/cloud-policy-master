package cn.rebornauto.platform.sys.form;

import cn.rebornauto.platform.common.data.request.Form;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class LoginForm extends Form {

    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String code;

    @Min(0)
    @Max(1)
    private int rememberMe;

}
