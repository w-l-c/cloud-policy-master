package cn.rebornauto.platform.business.form;

import cn.rebornauto.platform.common.data.request.Form;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ligewei
 * @create 2020/06/08 10:20
 */
@Data
public class MissionForm extends Form {

    private String missionNo;

    private String missionName;

    private String missionRemark;

    private BigDecimal amount;

    private Integer status;

    private String excel;

    private String file;

    private String image;
    public void setExcel(List excel) {
        this.excel = getPicUrl(excel);
    }
    public void setFile(List file) {
        this.file = getPicUrl(file);
    }
    public void setImage(List image) {
        this.image = getPicUrl(image);
    }

    private String getPicUrl(List picUrls) {
        return String.join(",", picUrls);
    }

}
