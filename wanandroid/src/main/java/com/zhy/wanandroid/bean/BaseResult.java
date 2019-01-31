package com.zhy.wanandroid.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author Jenly <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class BaseResult implements Serializable {

    public static final int RESULT_SUCCESS = 0;

    @SerializedName("errorCode")
    private int resultCode;

    private String errorMsg;


    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }


    public boolean isSuccess(){
        return RESULT_SUCCESS == resultCode;
    }

    @Override
    public String toString() {
        return "BaseResult{" +
                "resultCode=" + resultCode +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
