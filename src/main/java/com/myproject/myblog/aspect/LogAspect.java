package com.myproject.myblog.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @program: my-blog
 * @description:
 * @author: zhan
 * @create: 2020-02-25 09:48
 */
@Aspect //SpringAOP特定注解，加了这个注解才能进行切面的操作
@Component //开启组件扫描，SpringBoot才能通过注解扫描到这个类，相当于把这个类加到了IOC容器中
public class LogAspect {

    //通过反射来拿到日志记录器 logger
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //@Pointcut通过这个注解来声明 它是一个切面,execution() 是来规定拦截哪些类
    @Pointcut("execution(* com.myproject.myblog.web.*.*(..))")//拦截web下面所有的控制器
    public void log() {

    }

    //定义了切面之后，才可以围绕切面的前后来进行操作
    @Before("log()") //在哪个切面之前执行
    public void doBefore(JoinPoint joinPoint){
        //url需要在request中获取，
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        //oinPoint.getSignature().getDeclaringTypeName() 获取全类名
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(url, ip, classMethod, args);

        logger.info("Request123：{}", requestLog);
    }

    @After("log()") ////在 log() 这个切面之后执行
    public void doAfter() {
        logger.info("-------- before ----------");
    }

    @AfterReturning(returning = "result" , pointcut = "log()")
    public void doAfterReturn( Object result ) { // result 返回的结果 是 你拦截的类中方法的返回结果
        logger.info("result : " + result);
    }

    private class RequestLog{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getClassMethod() {
            return classMethod;
        }

        public void setClassMethod(String classMethod) {
            this.classMethod = classMethod;
        }

        public Object[] getArgs() {
            return args;
        }

        public void setArgs(Object[] args) {
            this.args = args;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }


}
