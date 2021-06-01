package cn.rebornauto.platform.common.exception;

public class BizException extends RuntimeException {
    public BizException() {
    }

    public BizException(String message) {
        super(message);
    }
    public BizException(Throwable cause) {
        super(cause);
    }
}