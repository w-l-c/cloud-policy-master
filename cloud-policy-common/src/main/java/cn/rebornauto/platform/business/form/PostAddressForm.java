package cn.rebornauto.platform.business.form;

import cn.rebornauto.platform.common.data.request.Form;
import lombok.Data;

@Data
public class PostAddressForm extends Form {
    private String address;
    private String receiver;
    private String mobile;
}
