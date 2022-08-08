package com.example.drive.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 通用返回结果，服务端响应的数据最终都会封装到这个对象里
 */
@Data
@ApiModel("返回结果")
public class R<T> implements Serializable {
    //1为成功，0或其他数字均为失败
    @ApiModelProperty("")
    private Integer code;
    //错误信息
    private String msg;
    //返回的数据
    private T data;
    //动态数据,用于存储未知类型或者经常变化的数据。以kv形式保存
    private Map map =new HashMap();

    //创建成功返回结果
    public static <T> R<T> success(T object){
        R<T> r=new R<T>();
        r.data=object;
        r.code=1;
        return r;
    }

    //创建错误返回结果
    public static <T> R<T> error(String msg){
        R<T> r=new R<T>();
        r.msg=msg;
        r.code=0;
        return r;
    }

    //添加动态数据。该方法是一个链式调用设计
    public R<T> add(String key,Object value)
    {
        this.map.put(key,value);
        return this;
    }
}
