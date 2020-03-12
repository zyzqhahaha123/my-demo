package com.myproject.myblog.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: my-blog
 * @description:
 * @author: zhan
 * @create: 2020-02-24 15:47
 */
//@ControllerAdvice 会拦截有@Controller这个注解的控制器
@ControllerAdvice
public class ControllerExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());//获取日志对象

    //ModelAndView 可以控制我们返回一个页面和后台要传到前台的信息
    @ExceptionHandler(Exception.class)  //@ExceptionHandler 标识该方法可以作为异常方法处理,并使之有效
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) throws Exception {
        logger.error("Request URL: {} , Exception : {}", request.getRequestURL() , e);//把错误信息在日志中显示

        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null){
            //如果不存在则证明是没有指定状态,直接交给SpringBoot来处理
            throw e;
        }


        ModelAndView mv = new ModelAndView();
        //把 错误路径 和 异常信息 放到 ModelAndView的对象中. 来把这些信息传到前台
        mv.addObject("url", request.getRequestURL());

        System.out.println("url:" + request.getRequestURL());
        System.out.println("走了这里");
        mv.addObject("exception", e);
        mv.setViewName("error/error");//默认是在templates目录下  error目录下 的 error 页面
        return mv;
    }
}
