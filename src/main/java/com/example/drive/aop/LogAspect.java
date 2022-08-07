package com.example.drive.aop;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect //定义切面类，定义通知与切点的关系
@Component
@Slf4j
public class LogAspect {

    @Autowired
    HttpServletRequest request;

    @Pointcut("@annotation(com.example.drive.aop.LogAnnotation)") //定义切点
    public void logPointCut() {
    }


    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();

        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
        log.info("========================log start=============================");
        //函数所处模块及其相关操作
        log.info("module : {}", logAnnotation.module());
//        log.info("operation : {}", logAnnotation.operation());
        log.info("requestMethod : {}", request.getMethod());
        log.info("requestPath : "+ request.getServletPath());

        //请求的方法名
        String className = point.getTarget().getClass().getName();
        String methodName = signature.getName();
        log.info("class : {}", className);
        log.info("methodName : {}", methodName + "()");

        //请求的参数
        Object[] args = point.getArgs();
        if (args.length != 0) {
            String params = JSON.toJSONString(args[0]);
            log.info("params : {}", params);
        }

        try {
            //执行方法
            Object result = point.proceed();
            log.info("result : {}", result);
            return result;
        }
        catch (Throwable e) {
            log.info("Throw Error : " + e.getMessage());
            throw e;
        }
        finally {
            //执行时长
            long time = System.currentTimeMillis() - beginTime;
            log.info("execute time : {} ms", time);
            log.info("========================log end===============================");
        }
    }

}
