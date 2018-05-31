package com.seed.poem;

/**
 * Created by Yuicon on 2017/7/2.
 * https://segmentfault.com/u/yuicon
 */
public class JsonResult<T> {

    private  int resCode;

    private  T data;

    private  String error;

    public JsonResult(T data) {
        this.resCode=0;
        this.data = data;
    }

    public JsonResult(int resCode, String error) {
        this.resCode = resCode;
        this.error = error;
    }

    public int getResCode() {
        return resCode;
    }

    public T getData() {
        return data;
    }

    public String getError() {
        return error;
    }

    @Override
    public String toString() {
        return "JsonResult{" +
                "resCode=" + resCode +
                ", data=" + data +
                ", error='" + error + '\'' +
                '}';
    }



}
