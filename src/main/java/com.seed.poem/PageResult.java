package com.seed.poem;

public class PageResult<T> extends JsonResult<T> {

    private int total;
    private int pageNo;

    public PageResult(T data, int total, int pageNo) {
        super(data);
        this.pageNo = pageNo;
        this.total = total;
    }

    public PageResult(int resCode, String error) {
        super(resCode, error);
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
}
