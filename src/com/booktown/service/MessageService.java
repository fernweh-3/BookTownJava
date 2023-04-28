package com.booktown.service;

import com.booktown.pojo.Result;

public class MessageService<T> {
    public MessageService() {
    }

    public Result<T> error(String message) {
        Result<T> result = new Result<>(404, message, null);
        return result;
    }

    public Result<T> success(String message) {
        Result<T> result = new Result<>(200, message, null);
        return result;
    }

    public Result<T> success(String message, T object) {
        Result<T> result = new Result<>(200, message, object);
        return result;
    }


    public Result<T> createResponse(int code, String message, T object) {
        return new Result<>(code, message, object);
    }

}
