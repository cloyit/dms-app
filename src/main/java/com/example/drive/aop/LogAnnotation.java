package com.example.drive.aop;

import java.lang.annotation.*;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
/*
  module : 可以放在方法上
  type : 可放置于类上
 */
public @interface LogAnnotation {
    String module() default "";

    String operation() default "";
}
