package cn.rebornauto.platform.pay.tonglian.aipg.common;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: 分序代收</p>
 * <p>Description: </p>
 * <p>Company: </p>
 * @author kgc
 * @date Jan 3, 2018 1:10:17 PM
 */
//@SuppressWarnings("unchecked")
public class AipgFxReq
{
	private InfoReq INFO;
	private List trxData;
	private List LEDGERS;
	public InfoReq getINFO()
	{
		return INFO;
	}
	public void setINFO(InfoReq iNFO)
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
	
	public List getLEDGERS() {
		return LEDGERS;
	}
	public void setLEDGERS(List lEDGERS) {
		LEDGERS = lEDGERS;
	}
	public void addTrx(Object o)
	{
		if(o==null) return ;
		if(trxData==null) trxData=new ArrayList();
		trxData.add(o);
	}
	
	public void addLedgers(Object o)
	{
		if(o==null) return ;
		if(LEDGERS==null) LEDGERS=new ArrayList();
		LEDGERS.add(o);
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
	public Object trxObj()
	{
		if(trxData==null) return null;
		if(!trxData.isEmpty()) return trxData.iterator().next();
		return null;
	}

	@Override
	public String toString() {
		return "AipgReq{" +
				"INFO=" + INFO +
				", trxData=" + trxData +
				'}';
	}
}
