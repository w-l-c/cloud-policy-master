package cn.rebornauto.platform.common.data.view;

import java.io.Serializable;

public class Response<T> implements Serializable {

    private int code;

    private String message;

    private T body;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public Response() {
    }

    public Response(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Response code(int code) {
        this.code = code;
        return this;
    }

    public Response message(String message) {
        this.message = message;
        return this;
    }

    public Response<T> body(T t) {
        this.body = t;
        return this;
    }

    public static <T> Response<T> factory() {
        return new Response<T>();
    }

    public static <T> Response<T> ok() {
        Response<T> resp = new Response<T>();
        resp.setCode(ResponseCode.SUCCESS.value());
        return resp;
    }

    public static <T> Response<T> error() {
        Response<T> resp = new Response<T>();
        resp.setCode(ResponseCode.ERROR.value());
        return resp;
    }
    public static <T> Response<T> bad() {
        Response<T> resp = new Response<T>();
        resp.setCode(ResponseCode.BAD.value());
        return resp;
    }

}
