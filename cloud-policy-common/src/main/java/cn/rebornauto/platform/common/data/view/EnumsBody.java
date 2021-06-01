package cn.rebornauto.platform.common.data.view;

import java.util.ArrayList;
import java.util.List;

public class EnumsBody {

    private List<OptionVO> list = new ArrayList<OptionVO>();

	public List<OptionVO> getList() {
		return list;
	}

	public void setList(List<OptionVO> list) {
		this.list = list;
	}
	

	public static EnumsBody factory(){
        return new EnumsBody();
    }

}
