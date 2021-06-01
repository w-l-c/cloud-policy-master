package cn.rebornauto.platform.business.entity;

import cn.rebornauto.platform.common.data.view.TableBody;
import lombok.Data;

@Data
public class PayInfoTable extends TableBody {
    
    /**
     * 余额
     */
    private String balance;
    
    public static PayInfoTable factory(){
        return new PayInfoTable();
    }
}