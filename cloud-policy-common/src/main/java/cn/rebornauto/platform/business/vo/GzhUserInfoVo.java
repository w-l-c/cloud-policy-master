package cn.rebornauto.platform.business.vo;

import java.util.List;

import cn.rebornauto.platform.common.data.view.UserDicOptionVO;
import lombok.Data;

@Data
public class GzhUserInfoVo {

     List<UserDicOptionVO> info;
		
	private Integer  status;//0：新用户  1：老用户"
}
