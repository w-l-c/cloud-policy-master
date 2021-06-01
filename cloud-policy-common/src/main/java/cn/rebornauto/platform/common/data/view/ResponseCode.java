package cn.rebornauto.platform.common.data.view;

public enum ResponseCode {
    SUCCESS(200),
    BAD(400),
    ERROR(500);
    private final int code;

    ResponseCode(int code) {
        this.code = code;
    }
    public int value(){
        return code;
    }
}
