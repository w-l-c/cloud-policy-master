package cn.rebornauto.platform.pay.tonglian.utils;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import cn.rebornauto.platform.business.entity.BankcardVerify4Allinpay;
import cn.rebornauto.platform.pay.tonglian.aipg.singleacctvalid.ValidR;
import cn.rebornauto.platform.pay.tonglian.entity.TongLianInfo;
public class Bankcardverify4Util {

   private  static SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * 通联验证四要素
     * @param bankcardVerify
     */
    public static void verify_allinpay(BankcardVerify4Allinpay bankcardVerify4Allinpay,TongLianInfo tongLianInfo) {
        if (bankcardVerify4Allinpay == null) {
            return;
        }
        if (!intactBankcardVerify(bankcardVerify4Allinpay)) {
        	bankcardVerify4Allinpay.setVerifystatus(1);
        	bankcardVerify4Allinpay.setVerifymsg("信息不全");
            return;
        }
        // 通联支付校验
        try {
            ValidR valid = new ValidR();
            valid.setACCOUNT_NAME(bankcardVerify4Allinpay.getRealname());
            //2.银行卡或存折号码
            valid.setACCOUNT_NO(bankcardVerify4Allinpay.getBankcard());
            //00银行卡，01存折，02信用卡。不填默认为银行卡00
            valid.setACCOUNT_TYPE("00");
            //银行代码
            valid.setBANK_CODE(bankcardVerify4Allinpay.getBankcode());
            //证件号
            valid.setID(bankcardVerify4Allinpay.getIdcard());
            //手机号/小灵通
            valid.setTEL(bankcardVerify4Allinpay.getMobile());
            //0：身份证,
            valid.setID_TYPE("0");
            //0私人，1公司
            valid.setACCOUNT_PROP("0");
            //商户ID
            valid.setMERCHANT_ID(tongLianInfo.getMerchantId());
            //提交时间YYYYMMDDHHMMSS
            valid.setSUBMIT_TIME(df.format(new Date()));
            valid.setREMARK("单笔实时身份验证");
            String xml = VerificationUtils.singleAcctVerify("211004",valid,tongLianInfo);
            //valid.setREMARK(xml);
            Document doc = Jsoup.parse(xml);
            String retcode = doc.selectFirst("AIPG INFO RET_CODE").text();
            if("0000".equals(retcode)){
                String vretcode = doc.selectFirst("AIPG VALIDRET RET_CODE").text();
                String vermessage = doc.selectFirst("AIPG VALIDRET ERR_MSG").text();
                if("0000".equals(vretcode)){
                	bankcardVerify4Allinpay.setVerifystatus(0);
                	bankcardVerify4Allinpay.setVerifymsg(vermessage);
                }else{
                	bankcardVerify4Allinpay.setVerifystatus(1);
                	bankcardVerify4Allinpay.setVerifymsg(vermessage);
                }
            }else{
                String infomsg = doc.selectFirst("AIPG INFO ERR_MSG").text();
                bankcardVerify4Allinpay.setVerifystatus(1);
                bankcardVerify4Allinpay.setVerifymsg(infomsg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            bankcardVerify4Allinpay.setVerifystatus(1);
            bankcardVerify4Allinpay.setVerifymsg("校验失败");
        }
    }

    //判断卡信息是否完整
    public static boolean intactBankcardVerify(BankcardVerify4Allinpay bankcardVerify) {
        if (bankcardVerify == null
                || StringUtils.isEmpty(bankcardVerify.getBankcard())
                || StringUtils.isEmpty(bankcardVerify.getIdcard())
                || StringUtils.isEmpty(bankcardVerify.getMobile())
                || StringUtils.isEmpty(bankcardVerify.getRealname())
                || StringUtils.isEmpty(bankcardVerify.getBankcode())
                ) {
            return false;
        }
        return true;
    }

    // 判断两张卡是否完全相同
    public static boolean sameBankcardVerify(BankcardVerify4Allinpay b1, BankcardVerify4Allinpay b2) {
        if (intactBankcardVerify(b1) && intactBankcardVerify(b2)
                && b1.getBankcard().equalsIgnoreCase(b2.getBankcard())
                && b1.getIdcard().equalsIgnoreCase(b2.getIdcard())
                && b1.getMobile().equalsIgnoreCase(b2.getMobile())
                && b1.getRealname().equalsIgnoreCase(b2.getRealname())
                && b1.getBankcode().equalsIgnoreCase(b2.getBankcode())
                ) {
            return true;
        }
        return false;
    }
}
