package cn.rebornauto.platform.pay.tonglian.aipg.payresp;

import cn.rebornauto.platform.pay.tonglian.aipg.common.InfoRsp;

public class AIPG {
	private InfoRsp INFO;
	private Body BODY;

	public InfoRsp getINFO() {
		return INFO;
	}

	public void setINFO(InfoRsp info) {
		INFO = info;
	}

	public Body getBODY() {
		return BODY;
	}

	public void setBODY(Body body) {
		BODY = body;
	}
}
