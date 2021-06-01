package cn.rebornauto.platform.business.vo;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class SignStatusInfoVo {

	private Integer signStatus;
	private LocalDateTime signTime;
	private String contractNumber;
}
