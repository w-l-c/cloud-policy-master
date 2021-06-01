package cn.rebornauto.platform.utils;

public class MathUtil {

    public static final int twoPow(int digit) {
        return (int) Math.pow(2, digit);
    }

    public static final int generatorStep(int old, int step) {
        return old | step;
    }

}
