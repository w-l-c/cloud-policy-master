package cn.rebornauto.platform.pay.tonglian.aipg.common;

import java.util.ArrayList;
import java.util.List;

import cn.rebornauto.platform.pay.tonglian.aipg.acquery.AcNode;
import cn.rebornauto.platform.pay.tonglian.aipg.etdtlquery.EtQRsp;

@SuppressWarnings({"unchecked","rawtypes"})
public class AipgRsp
{
	private InfoRsp INFO;
	private List trxData;
	private EtQRsp ETQRSP;
	private List<AcNode> ACQUERYREP;
	public static AipgRsp packRsp(AipgReq req, String errcode,String strMsg)
	{
		return packRsp(req.getINFO(),errcode,strMsg);
	}
	public static AipgRsp packRsp(InfoReq reqInf, String errcode,String strMsg)
	{
		AipgRsp rsp=new AipgRsp();
		rsp.setINFO(InfoRsp.packRsp(reqInf, errcode, strMsg));
		return rsp;
	}
	public InfoRsp getINFO()
	{
		return INFO;
	}
	public void setINFO(InfoRsp iNFO)
	{
		INFO = iNFO;
	}
	public List getTrxData()
	{
		return trxData;
	}
	public void setTrxData(List trxData)
	{
		this.trxData = trxData;
	}
	public void addTrx(Object o)
	{
		if(o==null) return ;
		if(trxData==null) trxData=new ArrayList();
		trxData.add(o);
	}
	public Object trxObj()
	{
		if(trxData==null) return null;
		if(!trxData.isEmpty()) return trxData.iterator().next();
		return null;
	}
	public Object findObj(Class clzx)
	{
		if(trxData==null) return null;
		for(Object ox:trxData)
		{
			if(clzx.isInstance(ox)) return ox;
		}
		return trxObj();
	}

	public EtQRsp getETQRSP() {
		return ETQRSP;
	}
	public void setETQRSP(EtQRsp eTQRSP) {
		ETQRSP = eTQRSP;
	}
	
	public List<AcNode> getACQUERYREP() {
		return ACQUERYREP;
	}
	public void setACQUERYREP(List<AcNode> aCQUERYREP) {
		ACQUERYREP = aCQUERYREP;
	}
	@Override
	public String toString() {
		return "AipgRsp{" +
				"INFO=" + INFO +
				", trxData=" + trxData +
				'}';
	}
}
