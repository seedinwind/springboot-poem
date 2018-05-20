package com.seed.poem;

/**
 * Created by Yuicon on 2017/7/2.
 * https://segmentfault.com/u/yuicon
 */
public class JsonResult<T> {

    private  int resCode;

    private final T data;

    private  String error;

    private JsonResult(JsonResultBuilder<T> builder) {
        this.resCode = builder.resCode;
        this.data = builder.data;
        this.error = builder.error;
    }

    public static <T>JsonResult.JsonResultBuilder<T> builder(){
        return new JsonResultBuilder<T>();
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

    public static final class JsonResultBuilder<T> {

        private int resCode;

        private T data;

        private String error;

        private JsonResultBuilder() {

        }

        public JsonResultBuilder error(int code,String error) {
            this.error = error;
            this.resCode = code;
            return this;
        }

        public JsonResultBuilder data(T data) {
            this.data = data;
            this.resCode = 0;
            return this;
        }

        public JsonResult build() {
            return new JsonResult<T>(this);
        }
    }

    public static void main(String[] args) {
        System.out.print(JsonResult.<String>builder().data("asd").build().toString());
    }

}
