package cn.rebornauto.platform.pay.tonglian.allinpay;

import cn.rebornauto.platform.pay.tonglian.aipg.accttrans.AcctTransferReq;
import cn.rebornauto.platform.pay.tonglian.aipg.acctvalid.ValbSum;
import cn.rebornauto.platform.pay.tonglian.aipg.acctvalid.ValidBReq;
import cn.rebornauto.platform.pay.tonglian.aipg.acctvalid.VbDetail;
import cn.rebornauto.platform.pay.tonglian.aipg.acquery.AcNode;
import cn.rebornauto.platform.pay.tonglian.aipg.acquery.AcQueryRep;
import cn.rebornauto.platform.pay.tonglian.aipg.acquery.AcQueryReq;
import cn.rebornauto.platform.pay.tonglian.aipg.agrmqx.XQSignReq;
import cn.rebornauto.platform.pay.tonglian.aipg.agrmsync.SignInfoDetail;
import cn.rebornauto.platform.pay.tonglian.aipg.agrmsync.SignInfoSync;
import cn.rebornauto.platform.pay.tonglian.aipg.ahquery.AHQueryRep;
import cn.rebornauto.platform.pay.tonglian.aipg.ahquery.AHQueryReq;
import cn.rebornauto.platform.pay.tonglian.aipg.ahquery.BalNode;
import cn.rebornauto.platform.pay.tonglian.aipg.cash.CashRep;
import cn.rebornauto.platform.pay.tonglian.aipg.cash.CashReq;
import cn.rebornauto.platform.pay.tonglian.aipg.common.AipgFxReq;
import cn.rebornauto.platform.pay.tonglian.aipg.common.AipgReq;
import cn.rebornauto.platform.pay.tonglian.aipg.common.AipgRsp;
import cn.rebornauto.platform.pay.tonglian.aipg.common.InfoReq;
import cn.rebornauto.platform.pay.tonglian.aipg.common.XSUtil;
import cn.rebornauto.platform.pay.tonglian.aipg.downloadrsp.DownRsp;
import cn.rebornauto.platform.pay.tonglian.aipg.etdtlquery.EtQReq;
import cn.rebornauto.platform.pay.tonglian.aipg.etquery.EtNode;
import cn.rebornauto.platform.pay.tonglian.aipg.etquery.EtQueryRep;
import cn.rebornauto.platform.pay.tonglian.aipg.etquery.EtQueryReq;
import cn.rebornauto.platform.pay.tonglian.aipg.idverify.IdVer;
import cn.rebornauto.platform.pay.tonglian.aipg.loginrsp.LoginRsp;
import cn.rebornauto.platform.pay.tonglian.aipg.notify.Notify;
import cn.rebornauto.platform.pay.tonglian.aipg.payreq.Body;
import cn.rebornauto.platform.pay.tonglian.aipg.payreq.Trans_Detail;
import cn.rebornauto.platform.pay.tonglian.aipg.pos.PinVerifyReq;
import cn.rebornauto.platform.pay.tonglian.aipg.pos.PinVerifyRsp;
import cn.rebornauto.platform.pay.tonglian.aipg.pos.QPTrans;
import cn.rebornauto.platform.pay.tonglian.aipg.pos.QPTransRet;
import cn.rebornauto.platform.pay.tonglian.aipg.pos.QPTrf;
import cn.rebornauto.platform.pay.tonglian.aipg.pos.QPTrfret;
import cn.rebornauto.platform.pay.tonglian.aipg.pos.Trfer;
import cn.rebornauto.platform.pay.tonglian.aipg.pos.Trfret;
import cn.rebornauto.platform.pay.tonglian.aipg.qtd.QTDReq;
import cn.rebornauto.platform.pay.tonglian.aipg.qtd.QTDRsp;
import cn.rebornauto.platform.pay.tonglian.aipg.qtd.QTDRspDetail;
import cn.rebornauto.platform.pay.tonglian.aipg.qvd.QVDReq;
import cn.rebornauto.platform.pay.tonglian.aipg.refund.Refund;
import cn.rebornauto.platform.pay.tonglian.aipg.rev.TransRev;
import cn.rebornauto.platform.pay.tonglian.aipg.rev.TransRevRsp;
import cn.rebornauto.platform.pay.tonglian.aipg.rnp.Rnp;
import cn.rebornauto.platform.pay.tonglian.aipg.rnp.Rnpa;
import cn.rebornauto.platform.pay.tonglian.aipg.rnp.RnpaRet;
import cn.rebornauto.platform.pay.tonglian.aipg.rnp.Rnpc;
import cn.rebornauto.platform.pay.tonglian.aipg.rnp.Rnpr;
import cn.rebornauto.platform.pay.tonglian.aipg.rtreq.Fagra;
import cn.rebornauto.platform.pay.tonglian.aipg.rtreq.Fagrc;
import cn.rebornauto.platform.pay.tonglian.aipg.rtreq.Fasttrx;
import cn.rebornauto.platform.pay.tonglian.aipg.rtreq.Ledgerdtl;
import cn.rebornauto.platform.pay.tonglian.aipg.rtreq.Ledgers;
import cn.rebornauto.platform.pay.tonglian.aipg.rtreq.Trans;
import cn.rebornauto.platform.pay.tonglian.aipg.rtrsp.TransRet;
import cn.rebornauto.platform.pay.tonglian.aipg.signquery.NSignReq;
import cn.rebornauto.platform.pay.tonglian.aipg.signquery.QSignDetail;
import cn.rebornauto.platform.pay.tonglian.aipg.signquery.QSignReq;
import cn.rebornauto.platform.pay.tonglian.aipg.signquery.QSignRsp;
import cn.rebornauto.platform.pay.tonglian.aipg.singleacctvalid.ValidR;
import cn.rebornauto.platform.pay.tonglian.aipg.syncex.SyncReqEx;
import cn.rebornauto.platform.pay.tonglian.aipg.syncex.SyncReqExDetail;
import cn.rebornauto.platform.pay.tonglian.aipg.syncex.SyncRspEx;
import cn.rebornauto.platform.pay.tonglian.aipg.syncex.SyncRspExDetail;
import cn.rebornauto.platform.pay.tonglian.aipg.synreq.SCloseReq;
import cn.rebornauto.platform.pay.tonglian.aipg.synreq.SCloseRsp;
import cn.rebornauto.platform.pay.tonglian.aipg.synreq.SvrfReq;
import cn.rebornauto.platform.pay.tonglian.aipg.synreq.Sync;
import cn.rebornauto.platform.pay.tonglian.aipg.synreq.SyncDetail;
import cn.rebornauto.platform.pay.tonglian.aipg.transfer.TransferReq;
import cn.rebornauto.platform.pay.tonglian.aipg.transquery.BalReq;
import cn.rebornauto.platform.pay.tonglian.aipg.transquery.BalRet;
import cn.rebornauto.platform.pay.tonglian.aipg.transquery.QTDetail;
import cn.rebornauto.platform.pay.tonglian.aipg.transquery.QTransRsp;
import cn.rebornauto.platform.pay.tonglian.aipg.transquery.TransQueryReq;
import cn.rebornauto.platform.pay.tonglian.aipg.transquery.TransQuerySignReq;
import cn.rebornauto.platform.pay.tonglian.aipg.tunotify.TUNotifyRep;
import cn.rebornauto.platform.pay.tonglian.aipg.tunotify.TUNotifyReq;
import com.thoughtworks.xstream.XStream;

public class XSUtilEx
{
	private static final String HEAD = "<?xml version=\"1.0\" encoding=\"GBK\"?>";
	public static AipgReq makeNotify(String qsn)
	{
		AipgReq req=new AipgReq();
		Notify notify=new Notify();
		notify.setNOTIFY_SN(qsn);
		req.setINFO(XSUtilEx.makeReq("200003",""+System.currentTimeMillis()));
		req.addTrx(notify);
		return req;
	}
	public static AipgReq makeNSignReq(String agrno,String contractno,String acct,String status)
	{
		return makeNSignReq(agrno,contractno,acct,status,"1");
	}
	public static AipgReq makeNSignReq(String agrno,String contractno,String acct,String status,String signtype)
	{
		AipgReq req=new AipgReq();
		QSignDetail qsd=new QSignDetail();
		qsd.setAGREEMENTNO(agrno);
		qsd.setACCT(acct);
		qsd.setCONTRACTNO(contractno);
		qsd.setSTATUS(status);
		//qsd.setSIGNTYPE(signtype);
		NSignReq nsr=new NSignReq();
		nsr.addDtl(qsd);
		req.setINFO(XSUtilEx.makeReq("210003",""+System.currentTimeMillis()));
		req.addTrx(nsr);
		return req;		
	}
	public static AipgReq xmlReq(String xmlMsg)
	{
		XStream xs=new XStreamIg();
		XSUtilEx.initXStream(xs, true);
		AipgReq req=(AipgReq) xs.fromXML(xmlMsg);
		return req;
	}
	public static AipgRsp xmlRsp(String xmlMsg)
	{
		XStream xs=new XStreamIg();
		XSUtilEx.initXStream(xs, false);
		AipgRsp rsp=(AipgRsp) xs.fromXML(xmlMsg);
		return rsp;
	}
	public static String reqXml(AipgReq req)
	{
		XStream xs=new XStreamIg();
		XSUtilEx.initXStream(xs, true);
		String xml=HEAD+xs.toXML(req);
		xml=xml.replace("__", "_");
		return xml;
	}
	public static String rspXml(AipgRsp rsp)
	{
		XStream xs=new XStreamIg();
		XSUtilEx.initXStream(xs, false);
		String xml=HEAD+xs.toXML(rsp);
		xml=xml.replace("__", "_");
		return xml;
	}
	public static void initXStream(XStream xs,boolean isreq)
	{
		if(isreq) 
			xs.alias("AIPG", AipgReq.class); 
		else 
			xs.alias("AIPG", AipgRsp.class);
	/*	xs.alias("AIPG", FtpXml.class);
		
		xs.alias("IN", InFTP.class);*/
		
		
		xs.alias("INFO", InfoReq.class);
		xs.addImplicitCollection(AipgReq.class, "trxData");
		xs.addImplicitCollection(AipgRsp.class, "trxData");
		xs.alias("BODY", Body.class) ;
		xs.alias("TRANS_DETAIL", Trans_Detail.class);
		xs.alias("TRANS_DETAIL", Trans_Detail.class);
		xs.alias("IDVER", IdVer.class);
		
		xs.alias("VALIDBREQ", ValidBReq.class) ;
		xs.alias("VALBSUM", ValbSum.class) ;
		xs.alias("VBDETAIL", VbDetail.class);
		xs.aliasField("VALIDBD", ValidBReq.class, "VALIDBD");
		xs.alias("VALIDR", ValidR.class) ;
		
		xs.alias("QTRANSREQ", TransQueryReq.class);
		xs.alias("QVDREQ", QVDReq.class);
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
		xs.alias("REVREQ", TransRev.class);
		xs.alias("REVRSP", TransRevRsp.class);
		xs.alias("LOGINRSP", LoginRsp.class);
		xs.alias("BALREQ", BalReq.class);
		xs.alias("BALRET", BalRet.class);
		xs.alias("SVRFREQ", SvrfReq.class);
		xs.alias("SCLOSEREQ", SvrfReq.class);
		xs.alias("SCLOSERSP", SCloseRsp.class);
		xs.alias("PINSIGNREQ", PinVerifyReq.class);
		xs.alias("PINSIGNRSP", PinVerifyRsp.class);
		
		//退款
		xs.alias("REFUND", Refund.class);
		xs.alias("ACCTTRANSFERREQ", AcctTransferReq.class);
		xs.alias("RNPA", Rnpa.class);
		xs.alias("RNPR", Rnpr.class);
		xs.alias("RNPC", Rnpc.class);
		xs.alias("RNPARET", RnpaRet.class);
		xs.alias("RNP", Rnp.class);
		
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
		xs.alias("TUNOTIFYREQ", TUNotifyReq.class);
		xs.alias("TUNOTIFYREP", TUNotifyRep.class);
		xs.alias("CASHREQ", CashReq.class);
		xs.alias("CASHREP", CashRep.class);
//		xs.alias("TUQNOTIFYREQ", NoticeReq.class);
//		xs.alias("TUNOTIFYREP", NoticeRep.class);
		xs.alias("ETQUERYREQ", EtQueryReq.class);
		xs.alias("ETQUERYREP", EtQueryRep.class);
		xs.alias("ETNODE", EtNode.class);
		xs.alias("ETQREQ", EtQReq.class);
		xs.addImplicitCollection(EtQueryRep.class, "details");
		xs.alias("SIGNINFODETAIL", SignInfoDetail.class);
		xs.alias("SIGNINFOSYNC", SignInfoSync.class);
		xs.alias("SCLOSEREQ", SCloseReq.class);
		xs.addImplicitCollection(SignInfoSync.class, "details");
		xs.alias("XQSIGNREQ", XQSignReq.class);

		/**
		 * 转账
		 */
		xs.alias("TRANSFERREQ",TransferReq.class);
		
		/**
		 * 分账清算
		 */
		xs.alias("LEDGERS", Ledgers.class) ;
		xs.alias("LEDGERDTL", Ledgerdtl.class);
		
		xs.alias("QTRANSREQ", TransQuerySignReq.class);
	}
	
	public static void initXStream2(XStream xs,boolean isreq)
	{
		if(isreq) 
			xs.alias("AIPG", AipgFxReq.class); 
		else 
			xs.alias("AIPG", AipgRsp.class);
	/*	xs.alias("AIPG", FtpXml.class);
		
		xs.alias("IN", InFTP.class);*/
		
		
		xs.alias("INFO", InfoReq.class);
		xs.addImplicitCollection(AipgFxReq.class, "trxData");
		xs.addImplicitCollection(AipgRsp.class, "trxData");
		xs.alias("BODY", Body.class) ;
		xs.alias("TRANS_DETAIL", Trans_Detail.class);
		xs.alias("TRANS_DETAIL", Trans_Detail.class);
		xs.alias("IDVER", IdVer.class);
		
		xs.alias("VALIDBREQ", ValidBReq.class) ;
		xs.alias("VALBSUM", ValbSum.class) ;
		xs.alias("VBDETAIL", VbDetail.class);
		xs.aliasField("VALIDBD", ValidBReq.class, "VALIDBD");
		xs.alias("VALIDR", ValidR.class) ;
		
		xs.alias("QTRANSREQ", TransQueryReq.class);
		xs.alias("QVDREQ", QVDReq.class);
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
		xs.alias("REVREQ", TransRev.class);
		xs.alias("REVRSP", TransRevRsp.class);
		xs.alias("LOGINRSP", LoginRsp.class);
		xs.alias("BALREQ", BalReq.class);
		xs.alias("BALRET", BalRet.class);
		xs.alias("SVRFREQ", SvrfReq.class);
		xs.alias("SCLOSEREQ", SvrfReq.class);
		xs.alias("SCLOSERSP", SCloseRsp.class);
		xs.alias("PINSIGNREQ", PinVerifyReq.class);
		xs.alias("PINSIGNRSP", PinVerifyRsp.class);
		
		//退款
		xs.alias("REFUND", Refund.class);
		xs.alias("ACCTTRANSFERREQ", AcctTransferReq.class);
		xs.alias("RNPA", Rnpa.class);
		xs.alias("RNPR", Rnpr.class);
		xs.alias("RNPC", Rnpc.class);
		xs.alias("RNPARET", RnpaRet.class);
		xs.alias("RNP", Rnp.class);
		
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
		xs.alias("TUNOTIFYREQ", TUNotifyReq.class);
		xs.alias("TUNOTIFYREP", TUNotifyRep.class);
		xs.alias("CASHREQ", CashReq.class);
		xs.alias("CASHREP", CashRep.class);
//		xs.alias("TUQNOTIFYREQ", NoticeReq.class);
//		xs.alias("TUNOTIFYREP", NoticeRep.class);
		xs.alias("ETQUERYREQ", EtQueryReq.class);
		xs.alias("ETQUERYREP", EtQueryRep.class);
		xs.alias("ETNODE", EtNode.class);
		xs.alias("ETQREQ", EtQReq.class);
		xs.addImplicitCollection(EtQueryRep.class, "details");
		xs.alias("SIGNINFODETAIL", SignInfoDetail.class);
		xs.alias("SIGNINFOSYNC", SignInfoSync.class);
		xs.alias("SCLOSEREQ", SCloseReq.class);
		xs.addImplicitCollection(SignInfoSync.class, "details");
		xs.alias("XQSIGNREQ", XQSignReq.class);

		/**
		 * 转账
		 */
		xs.alias("TRANSFERREQ",TransferReq.class);
		
		/**
		 * 分账清算
		 */
//		xs.alias("LEDGERS", Ledgers.class) ;
		xs.alias("LEDGERDTL", Ledgerdtl.class);
		xs.alias("FAGRA", Fagra.class);
		xs.alias("FAGRC", Fagrc.class);
		xs.alias("FASTTRX", Fasttrx.class);
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
	
	public static Object parseXml(String xml)
	{
		XStream xs=new XStreamIg();
		XSUtil.initXStream(xs,true);
		return xs.fromXML(xml);
	}
}
