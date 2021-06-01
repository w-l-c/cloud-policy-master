package cn.rebornauto.platform.pay.tonglian.aipg.common;

import cn.rebornauto.platform.pay.tonglian.aipg.acctvalid.ValbSum;
import cn.rebornauto.platform.pay.tonglian.aipg.acctvalid.ValidBD;
import cn.rebornauto.platform.pay.tonglian.aipg.acctvalid.ValidBReq;
import cn.rebornauto.platform.pay.tonglian.aipg.acctvalid.VbDetail;
import cn.rebornauto.platform.pay.tonglian.aipg.acquery.AcNode;
import cn.rebornauto.platform.pay.tonglian.aipg.acquery.AcQueryRep;
import cn.rebornauto.platform.pay.tonglian.aipg.acquery.AcQueryReq;
import cn.rebornauto.platform.pay.tonglian.aipg.agrmqx.XQSDetail;
import cn.rebornauto.platform.pay.tonglian.aipg.agrmqx.XQSignReq;
import cn.rebornauto.platform.pay.tonglian.aipg.agrmqx.XQSignRsp;
import cn.rebornauto.platform.pay.tonglian.aipg.agrmsync.SignInfoDetail;
import cn.rebornauto.platform.pay.tonglian.aipg.agrmsync.SignInfoSync;
import cn.rebornauto.platform.pay.tonglian.aipg.ahquery.AHQueryRep;
import cn.rebornauto.platform.pay.tonglian.aipg.ahquery.AHQueryReq;
import cn.rebornauto.platform.pay.tonglian.aipg.ahquery.BalNode;
import cn.rebornauto.platform.pay.tonglian.aipg.cash.CashRep;
import cn.rebornauto.platform.pay.tonglian.aipg.cash.CashReq;
import cn.rebornauto.platform.pay.tonglian.aipg.downloadrsp.DownRsp;
import cn.rebornauto.platform.pay.tonglian.aipg.etdtlquery.EtDtl;
import cn.rebornauto.platform.pay.tonglian.aipg.etdtlquery.EtQReq;
import cn.rebornauto.platform.pay.tonglian.aipg.etdtlquery.EtQRsp;
import cn.rebornauto.platform.pay.tonglian.aipg.etdtlquery.EtSum;
import cn.rebornauto.platform.pay.tonglian.aipg.etquery.EtNode;
import cn.rebornauto.platform.pay.tonglian.aipg.etquery.EtQueryRep;
import cn.rebornauto.platform.pay.tonglian.aipg.etquery.EtQueryReq;
import cn.rebornauto.platform.pay.tonglian.aipg.loginrsp.LoginRsp;
import cn.rebornauto.platform.pay.tonglian.aipg.netbank.NetBankReq;
import cn.rebornauto.platform.pay.tonglian.aipg.notify.Notify;
import cn.rebornauto.platform.pay.tonglian.aipg.payresp.Body;
import cn.rebornauto.platform.pay.tonglian.aipg.payresp.Ret_Detail;
import cn.rebornauto.platform.pay.tonglian.aipg.pos.*;
import cn.rebornauto.platform.pay.tonglian.aipg.qtd.QTDReq;
import cn.rebornauto.platform.pay.tonglian.aipg.qtd.QTDRsp;
import cn.rebornauto.platform.pay.tonglian.aipg.qtd.QTDRspDetail;
import cn.rebornauto.platform.pay.tonglian.aipg.qvd.QVDReq;
import cn.rebornauto.platform.pay.tonglian.aipg.qvd.QVDRsp;
import cn.rebornauto.platform.pay.tonglian.aipg.qvd.QVDRspDetail;
import cn.rebornauto.platform.pay.tonglian.aipg.refund.Refund;
import cn.rebornauto.platform.pay.tonglian.aipg.rev.TransRev;
import cn.rebornauto.platform.pay.tonglian.aipg.rev.TransRevRsp;
import cn.rebornauto.platform.pay.tonglian.aipg.rnp.RnpaRet;
import cn.rebornauto.platform.pay.tonglian.aipg.rtreq.Trans;
import cn.rebornauto.platform.pay.tonglian.aipg.rtrsp.Fagraret;
import cn.rebornauto.platform.pay.tonglian.aipg.rtrsp.TransRet;
import cn.rebornauto.platform.pay.tonglian.aipg.signquery.NSignReq;
import cn.rebornauto.platform.pay.tonglian.aipg.signquery.QSignDetail;
import cn.rebornauto.platform.pay.tonglian.aipg.signquery.QSignReq;
import cn.rebornauto.platform.pay.tonglian.aipg.signquery.QSignRsp;
import cn.rebornauto.platform.pay.tonglian.aipg.singleacctvalid.ValidR;
import cn.rebornauto.platform.pay.tonglian.aipg.singleacctvalid.ValidRet;
import cn.rebornauto.platform.pay.tonglian.aipg.syncex.SyncReqEx;
import cn.rebornauto.platform.pay.tonglian.aipg.syncex.SyncReqExDetail;
import cn.rebornauto.platform.pay.tonglian.aipg.syncex.SyncRspEx;
import cn.rebornauto.platform.pay.tonglian.aipg.syncex.SyncRspExDetail;
import cn.rebornauto.platform.pay.tonglian.aipg.synreq.*;
import cn.rebornauto.platform.pay.tonglian.aipg.transfer.TransferReq;
import cn.rebornauto.platform.pay.tonglian.aipg.transfer.TransferRsp;
import cn.rebornauto.platform.pay.tonglian.aipg.transquery.*;
import cn.rebornauto.platform.pay.tonglian.aipg.tunotify.TUNotifyRep;
import cn.rebornauto.platform.pay.tonglian.aipg.tunotify.TUNotifyReq;
import com.thoughtworks.xstream.XStream;

public class XSUtil
{
	public static final String HEAD = "<?xml version=\"1.0\" encoding=\"GBK\"?>";
	private static final XStream xsreq=initXStream(new XStreamEx(), true);
	private static final XStream xsrsp=initXStream(new XStreamEx(), false);
	public static AipgReq makeNotify(String qsn)
	{
		AipgReq req=new AipgReq();
		Notify notify=new Notify();
		notify.setNOTIFY_SN(qsn);
		req.setINFO(XSUtil.makeReq("200003",""+System.currentTimeMillis()));
		req.addTrx(notify);
		return req;
	}
	public static AipgReq parseReq(String xml)
	{
		return (AipgReq) xsreq.fromXML(xml);
	}
	public static AipgRsp parseRsp(String xml)
	{
		return (AipgRsp) xsrsp.fromXML(xml);
	}
	public static String toXml(Object o)
	{
		boolean isreq=(o instanceof AipgReq);
		if(isreq) return XSUtil.toXml(xsreq, o);
		else return XSUtil.toXml(xsrsp, o);		
	}
	public static String toXml(XStream xs,Object o)
	{
		String xml;
		xml=xs.toXML(o);
		xml = xml.replaceAll("__", "_");
		xml = HEAD + xml;
		return xml;
	}		
	public static AipgReq makeNSignReq(String agrno,String contractno,String acct,String acctname,String status,String errmsg)
	{
		return makeNSignReq(agrno,contractno,acct,acctname,status,"1",errmsg);
	}
	public static AipgReq makeNSignReq(String agrno,String contractno,String acct,String acctname,String status,String signtype,String errmsg)
	{
		AipgReq req=new AipgReq();
		QSignDetail qsd=new QSignDetail();
		qsd.setAGREEMENTNO(agrno);
		qsd.setACCT(acct);
		qsd.setACCTNAME(acctname);
		qsd.setCONTRACTNO(contractno);
		qsd.setSTATUS(status);
		qsd.setSIGNTYPE(signtype);
		qsd.setERRMSG(errmsg);
		NSignReq nsr=new NSignReq();
		nsr.addDtl(qsd);
		req.setINFO(XSUtil.makeReq("210003",""+System.currentTimeMillis()));
		req.addTrx(nsr);
		return req;		
	}
	public static AipgReq makeNSignReqEx(String merno,String agrno,String contractno,String acct,String acctname,String status,String signtype,String errmsg)
	{
		AipgReq req=new AipgReq();
		QSignDetail qsd=new QSignDetail();
		qsd.setAGREEMENTNO(agrno);
		qsd.setACCT(acct);
		qsd.setACCTNAME(acctname);
		qsd.setCONTRACTNO(contractno);
		qsd.setSTATUS(status);
		qsd.setSIGNTYPE(signtype);
		qsd.setERRMSG(errmsg);
		NSignReq nsr=new NSignReq();
		nsr.addDtl(qsd);
		req.setINFO(XSUtil.makeReq("210003",""+System.currentTimeMillis()));
		req.getINFO().setMERCHANT_ID(merno);
		req.addTrx(nsr);
		return req;		
	}
	public static AipgReq xmlReq(String xmlMsg)
	{
		XStream xs=new XStreamEx();
		XSUtil.initXStream(xs, true);
		AipgReq req=(AipgReq) xs.fromXML(xmlMsg);
		return req;
	}
	public static AipgRsp xmlRsp(String xmlMsg)
	{
		XStream xs=new XStreamEx();
		XSUtil.initXStream(xs, false);
		AipgRsp rsp=(AipgRsp) xs.fromXML(xmlMsg);
		return rsp;
	}
	public static String reqXml(AipgReq req)
	{
		XStream xs=new XStreamEx();
		XSUtil.initXStream(xs, true);
		String xml=HEAD+xs.toXML(req);
		xml=xml.replace("__", "_");
		return xml;
	}
	public static String rspXml(AipgRsp rsp)
	{
		XStream xs=new XStreamEx();
		XSUtil.initXStream(xs, false);
		String xml=HEAD+xs.toXML(rsp);
		xml=xml.replace("__", "_");
		return xml;
	}
	public static XStream initXStream(XStream xs,boolean isreq)
	{
		if(isreq) xs.alias("AIPG", AipgReq.class); else xs.alias("AIPG", AipgRsp.class);
		xs.alias("INFO", InfoReq.class);
		xs.addImplicitCollection(AipgReq.class, "trxData");
		xs.addImplicitCollection(AipgRsp.class, "trxData");
		xs.alias("QTRANSREQ", TransQueryReq.class);
		xs.alias("QTRANSRSP", QTransRsp.class);
		xs.alias("QTDETAIL", QTDetail.class);
		xs.alias("DOWNRSP", DownRsp.class);
		xs.alias("NOTIFY", Notify.class);
		xs.alias("SYNC", Sync.class);
		xs.alias("QSIGNRSP", QSignRsp.class);
		xs.alias("QSDETAIL", QSignDetail.class);
		xs.alias("NSIGNREQ", NSignReq.class);
		xs.alias("QSIGNREQ", QSignReq.class);
		xs.alias("TRANS", Trans.class);
		xs.alias("TRANSRET", TransRet.class);
		//xs.alias("TRXPAY", Trans.class);
		//xs.alias("TRXPAYRET", TransRet.class);
		xs.alias("REVREQ", TransRev.class);
		xs.alias("REVRSP", TransRevRsp.class);
		xs.alias("LOGINRSP", LoginRsp.class);
		xs.alias("BALREQ", BalReq.class);
		xs.alias("BALRET", BalRet.class);
		xs.alias("SVRFREQ", SvrfReq.class);
		xs.alias("SVRFRSP", SvrfRsp.class);
		xs.alias("SCLOSEREQ", SCloseReq.class);
		xs.alias("SCLOSERSP", SCloseRsp.class);
		xs.alias("PINSIGNREQ", PinVerifyReq.class);
		xs.alias("PINSIGNRSP", PinVerifyRsp.class);

		xs.alias("VALIDR", ValidR.class);
		xs.alias("VALIDRET", ValidRet.class);
		xs.alias("RNPARET", RnpaRet.class);

		
		xs.alias("TRANSFERREQ", TransferReq.class);
		xs.alias("TRANSFERRSP", TransferRsp.class);
		
		xs.alias("QVDREQ", QVDReq.class);
		xs.alias("QVDRSP", QVDRsp.class);
		xs.alias("QVDRSPDETAIL", QVDRspDetail.class);
		xs.addImplicitCollection(QVDRsp.class, "details");
		
		xs.alias("VALIDBREQ", ValidBReq.class);
		xs.alias("VALBSUM", ValbSum.class);
		xs.alias("VALIDBD", ValidBD.class);
		xs.alias("VBDETAIL", VbDetail.class);
		xs.addImplicitCollection(ValidBD.class, "details");
		
		
		xs.alias("TRFER", Trfer.class);
		xs.alias("TRFRET", Trfret.class);
		xs.alias("QPTRF", QPTrf.class);
		xs.alias("QPTRFRET", QPTrfret.class);
		xs.alias("QPTRANS", QPTrans.class);
		xs.alias("QPTRANSRET", QPTransRet.class);
		
		xs.addImplicitCollection(Sync.class, "details");
		xs.addImplicitCollection(QTransRsp.class, "details");
		xs.addImplicitCollection(QSignRsp.class, "details");
		xs.alias("SYNCDETAIL", SyncDetail.class);
		
		xs.alias("SYNCREQEX", SyncReqEx.class);
		xs.alias("SYNCRSPEX", SyncRspEx.class);
		xs.alias("SYNCREQEXDETAIL", SyncReqExDetail.class);
		xs.alias("SYNCRSPEXDETAIL", SyncRspExDetail.class);
		xs.addImplicitCollection(SyncReqEx.class, "details");
		xs.addImplicitCollection(SyncRspEx.class, "details");
		xs.alias("QTDREQ", QTDReq.class);
		xs.alias("QTDRSP", QTDRsp.class);
		xs.alias("QTDRSPDETAIL", QTDRspDetail.class);
		xs.addImplicitCollection(QTDRsp.class, "details");	
		xs.alias("ACQUERYREQ", AcQueryReq.class);
		xs.alias("ACQUERYREP", AcQueryRep.class);
		xs.alias("ACNODE", AcNode.class);
		xs.addImplicitCollection(AcQueryRep.class, "details");
		xs.alias("AHQUERYREQ", AHQueryReq.class);
		xs.alias("AHQUERYREP", AHQueryRep.class);
		xs.alias("BALNODE", BalNode.class);
		xs.addImplicitCollection(AHQueryRep.class, "details");
		xs.alias("TUQNOTIFYREQ", TUNotifyReq.class);
		xs.alias("TUNOTIFYREQ", TUNotifyReq.class);
		xs.alias("TUNOTIFYREP", TUNotifyRep.class);
		xs.alias("CASHREQ", CashReq.class);
		xs.alias("CASHREP", CashRep.class);
		xs.alias("ETQUERYREQ", EtQueryReq.class);
		xs.alias("ETQUERYREP", EtQueryRep.class);
		xs.alias("ETNODE", EtNode.class);
		xs.addImplicitCollection(EtQueryRep.class, "details");
		
		xs.alias("BODY", Body.class);
		xs.alias("BODY", Body.class);
		xs.aliasField("RET_DETAILS", Body.class, "details");
		xs.alias("RET_DETAIL", Ret_Detail.class);
		xs.alias("NETBANKREQ", NetBankReq.class);
		
		xs.alias("ETQREQ", EtQReq.class);
		xs.alias("ETQRSP", EtQRsp.class);
		xs.alias("ETSUM", EtSum.class);
		xs.alias("ETDTL", EtDtl.class);
		xs.aliasField("ETDTLS", EtQRsp.class, "details");
		
		
		xs.alias("SIGNINFOSYNC", SignInfoSync.class);
		xs.addImplicitCollection(SignInfoSync.class, "details");
		xs.alias("SIGNINFODETAIL",SignInfoDetail.class);
		
		xs.alias("XQSIGNREQ", XQSignReq.class);
		xs.alias("XQSIGNRSP", XQSignRsp.class);
		xs.addImplicitCollection(XQSignRsp.class, "details");
		xs.alias("XQSDETAIL", XQSDetail.class);
		
		xs.alias("REFUND", Refund.class);
		xs.alias("FAGRARET", Fagraret.class);
		xs.alias("FAGRCRET", Fagraret.class);
		xs.alias("FASTTRXRET", Fagraret.class);
		return xs;
	}
	public static InfoReq makeReq(String trxcod, String sn)
	{
		InfoReq ir=new InfoReq();
		ir.setTRX_CODE(trxcod);
		ir.setDATA_TYPE("2");
		ir.setVERSION("03");
		ir.setSIGNED_MSG("");
		ir.setREQ_SN(sn);
		ir.setLEVEL(null);
		ir.setUSER_NAME(null);
		ir.setUSER_PASS(null);
		return ir;
	}
	public static InfoReq makeReq(String trxcod, String sn,String user,String pass,int level)
	{
		InfoReq ir=new InfoReq();
		ir.setTRX_CODE(trxcod);
		ir.setDATA_TYPE("2");
		ir.setVERSION("03");
		ir.setSIGNED_MSG("");
		ir.setREQ_SN(sn);
		ir.setLEVEL(""+level);
		ir.setUSER_NAME(user);
		ir.setUSER_PASS(pass);
		return ir;
	}
}
