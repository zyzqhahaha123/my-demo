package com.myproject.myblog.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: my-blog
 * @description:
 * @author: zhan
 * @create: 2020-02-26 16:56
 */
//定义拦截器
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override //在地址还未被正式访问时，提前进行拦截
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        if (request.getSession().getAttribute("user") == null) {//通过获取session里面的user是否为空，来判断是否是登录状态
            //如果没有登录过,通过 response.sendRedirect() 方法，来重定向一个路径 到 登录页面
            response.sendRedirect("/admin/loginPage");
            return false;//然后 false,结束操作
        }

        return true;//正常 则 true ，直接进行下一步操作
    }
}
