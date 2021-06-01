package cn.rebornauto.platform.pay.tonglian.utils;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import cn.rebornauto.platform.business.entity.BankcardVerify3Allinpay;
import cn.rebornauto.platform.pay.tonglian.aipg.singleacctvalid.ValidR;
import cn.rebornauto.platform.pay.tonglian.entity.TongLianInfo;
public class Bankcardverify3Util {

   private  static SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * 通联验证三要素
     * @param bankcardVerify
     */
    public static void verify_allinpay(BankcardVerify3Allinpay bankcardVerify3Allinpay,TongLianInfo tongLianInfo,Integer idType) {
        if (bankcardVerify3Allinpay == null) {
            return;
        }
        if (!intactBankcardVerify(bankcardVerify3Allinpay)) {
        	bankcardVerify3Allinpay.setVerifystatus(1);
        	bankcardVerify3Allinpay.setVerifymsg("信息不全");
            return;
        }
        // 通联支付校验
        try {
            ValidR valid = new ValidR();
            valid.setACCOUNT_NAME(bankcardVerify3Allinpay.getRealname());
            //2.银行卡或存折号码
            valid.setACCOUNT_NO(bankcardVerify3Allinpay.getBankcard());
            //00银行卡，01存折，02信用卡。不填默认为银行卡00
            valid.setACCOUNT_TYPE("00");
            //银行代码
            valid.setBANK_CODE(bankcardVerify3Allinpay.getBankcode());
            //证件号
            valid.setID(bankcardVerify3Allinpay.getIdcard());
            if(idType.intValue()==4) {
            	//0：身份证,
                valid.setID_TYPE("0");
            }else {
            	//2：护照,
                valid.setID_TYPE("2");
            }
            //0私人，1公司
            valid.setACCOUNT_PROP("0");
            //商户ID
            valid.setMERCHANT_ID(tongLianInfo.getMerchantId());
            //提交时间YYYYMMDDHHMMSS
            valid.setSUBMIT_TIME(df.format(new Date()));
            valid.setREMARK("单笔实时身份验证");
            //调用三要素接口211003
            String xml = VerificationUtils.singleAcctVerify("211003",valid,tongLianInfo);
            //valid.setREMARK(xml);
            Document doc = Jsoup.parse(xml);
            String retcode = doc.selectFirst("AIPG INFO RET_CODE").text();
            if("0000".equals(retcode)){
                String vretcode = doc.selectFirst("AIPG VALIDRET RET_CODE").text();
                String vermessage = doc.selectFirst("AIPG VALIDRET ERR_MSG").text();
                if("0000".equals(vretcode)){
                	bankcardVerify3Allinpay.setVerifystatus(0);
                	bankcardVerify3Allinpay.setVerifymsg(vermessage);
                }else{
                	bankcardVerify3Allinpay.setVerifystatus(1);
                	bankcardVerify3Allinpay.setVerifymsg(vermessage);
                }
            }else{
                String infomsg = doc.selectFirst("AIPG INFO ERR_MSG").text();
                bankcardVerify3Allinpay.setVerifystatus(1);
                bankcardVerify3Allinpay.setVerifymsg(infomsg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            bankcardVerify3Allinpay.setVerifystatus(1);
            bankcardVerify3Allinpay.setVerifymsg("校验失败");
        }
    }

    //判断卡信息是否完整
    public static boolean intactBankcardVerify(BankcardVerify3Allinpay bankcardVerify) {
        if (bankcardVerify == null
                || StringUtils.isEmpty(bankcardVerify.getBankcard())
                || StringUtils.isEmpty(bankcardVerify.getIdcard())
                || StringUtils.isEmpty(bankcardVerify.getRealname())
                || StringUtils.isEmpty(bankcardVerify.getBankcode())
                ) {
            return false;
        }
        return true;
    }

    // 判断两张卡是否完全相同
    public static boolean sameBankcardVerify(BankcardVerify3Allinpay b1, BankcardVerify3Allinpay b2) {
        if (intactBankcardVerify(b1) && intactBankcardVerify(b2)
                && b1.getBankcard().equalsIgnoreCase(b2.getBankcard())
                && b1.getIdcard().equalsIgnoreCase(b2.getIdcard())
                && b1.getRealname().equalsIgnoreCase(b2.getRealname())
                && b1.getBankcode().equalsIgnoreCase(b2.getBankcode())
                ) {
            return true;
        }
        return false;
    }
}
