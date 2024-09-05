package com.sc.e_commerce_platform.entity;

//统一响应结果

public class Result<T> {
    private Boolean flag;//业务状态码  0-成功  1-失败
    private String message;//提示信息
    private T data;//响应数据

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "flag=" + flag +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public Result(Boolean flag, String message, T data) {
        this.flag = flag;
        this.message = message;
        this.data = data;
    }


    //快速返回操作成功响应结果(带响应数据)
    public static <E> Result<E> success(E data) {
        return new Result<>(true, "操作成功", data);
    }

    //快速返回操作成功响应结果
    public static Result success() {
        return new Result(true, "操作成功", null);
    }

    public static Result error(String message) {
        return new Result(false, message, null);
    }
}
