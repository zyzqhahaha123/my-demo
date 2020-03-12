package com.myproject.myblog.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @program: my-blog
 * @description:
 * @author: zhan
 * @create: 2020-02-26 17:14
 */
//给拦截器定义参数 告知拦截器要拦截哪些，哪些不拦截  WebMvcConfigurerAdapter 内置配置器
@Configuration //表示Spring Boot 这是一个配置类，才会起作用
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override //需要拦截哪些东西
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())//把定义好的 拦截器 加到里面来
                .addPathPatterns("/admin/**") //添加匹配的路径，即要拦截的路径
                .excludePathPatterns("/admin/loginPage")  //哪些不拦截
                .excludePathPatterns("/admin/login");

    }
}
