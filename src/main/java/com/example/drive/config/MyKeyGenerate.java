package com.example.drive.config;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @ClassName KeyGenerater
 * @Description TODO
 * @Author Phyme1
 * @CreateTime 2022/7/27 17:33:59
 * @Version 1.0
 */
@Component("myKeyGenerate")
public class MyKeyGenerate implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {
        return target.toString() + ":" + method.getName() + ":" + Arrays.toString(params);
    }
}