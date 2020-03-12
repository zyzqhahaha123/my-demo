package com.myproject.myblog;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @program: my-blog
 * @description:
 * @author: zhan
 * @create: 2020-02-24 17:57
 */
//指定错误返回状态码,它还需指定状态, 必须指定NOT_FOUND作为一个资源找不到的状态,SpringBoot才会去找404页面
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
