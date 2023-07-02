package com.example.wanandroid.bean;

/**
 * @className: MessageBean
 * @author: Voyager
 * @description: 储存用户登录后返回的cookie，用来进行收藏文章等请求的header
 * @date: 2023/6/25
 **/
public class MessageBean<T> {
    private T data;
    private int errorCode;
    String errorMsg;


    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
