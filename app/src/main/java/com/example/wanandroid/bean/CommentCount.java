package com.example.wanandroid.bean;

/**
 * @version 1.0
 * @className: CommentCount
 * @author: Voyager
 * @description: 未读消息数量数据类
 * @date: 2023/8/9 1:07
 **/
public class CommentCount {


    /**
     * data : 0
     * errorCode : 0
     * errorMsg :
     */

    private int data;
    private int errorCode;
    private String errorMsg;

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

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
}
