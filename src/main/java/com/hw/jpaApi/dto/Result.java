package com.hw.jpaApi.dto;

public class Result<T> {

    private String msg;
    private T data;
    private Integer totalPage;

    public Result(String msg) {
        this.msg = msg;
    }

    public Result(String msg, T data, Integer totalPage) {
        this.msg = msg;
        this.data = data;
        this.totalPage = totalPage;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public Integer getTotalPage() {
        return totalPage;
    }
}
