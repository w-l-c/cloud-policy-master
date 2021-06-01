package cn.rebornauto.platform.sys.form;

import javax.validation.constraints.NotNull;

import lombok.Data;
import cn.rebornauto.platform.common.data.request.Form;

/** Title: 发送手机短信验证码
 * Description: 
 * Company: 
 * @author kgc
 * @date May 23, 2019 11:54:01 AM
 */
@Data
public class SysUserSmsForm extends Form {

	@NotNull
    private String mobile;
	/**
	 * 短信验证码 
	 */
	private String smscode;
	/**
	 * 类型  account
	 */
	private String type;
}
