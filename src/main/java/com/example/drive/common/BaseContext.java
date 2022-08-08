package com.example.drive.common;

/**
 * 基于ThreadLocal封装一个工具类，用户保存和获取当前登录用户的ID
 */
public class BaseContext {

    private static ThreadLocal<Long> threadLocal =new ThreadLocal<>();
    //设置员工ID,并与当前处理线程绑定
    public static void setCurrentId(long id){
        threadLocal.set(id);
    }
    //获取员工ID，获取的是与当前处理线程绑定的那员员工ID
    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
