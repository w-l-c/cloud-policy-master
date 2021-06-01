package cn.rebornauto.platform.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MoneyUtil {

    /**
     * 元转换为万元
     *
     * @param amount
     * @return
     */
    public static BigDecimal yuanToWanYuan(Integer amount) {
        BigDecimal bigDecimal = new BigDecimal(amount);
        // 转换为万元（除以10000）
        BigDecimal decimal = bigDecimal.divide(new BigDecimal("10000"));
        // 保留两位小数
        DecimalFormat formater = new DecimalFormat("0.00");
        // 四舍五入
        formater.setRoundingMode(RoundingMode.HALF_UP);

        // 格式化完成之后得出结果
        String formatNum = formater.format(decimal);
        return new BigDecimal(formatNum);
    }

    /**
     * 万元转换为元
     *
     * @param amount
     * @return
     */
    public static Integer wanYuanToYuan(BigDecimal amount) {
        // 转换为元（乘以10000）
        BigDecimal decimal = amount.multiply(new BigDecimal("10000"));
        return decimal.intValue();
    }

}
