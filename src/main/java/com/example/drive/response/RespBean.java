package com.example.drive.response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * json 返回的对象
 */
@Data
@ToString
@Api("响应数据类")
public class RespBean implements Serializable {
    @ApiModelProperty("返回码")
    private Integer status;
    @ApiModelProperty("响应信息")
    private String msg;
    @ApiModelProperty("返回数据")
    private Object obj;


    private RespBean() {
    }
    private RespBean(Integer status, String msg, Object obj) {
        this.status = status;
        this.msg = msg;
        this.obj = obj;
    }

    public static RespBean ok(String msg) {
        return new RespBean(200, msg, null);
    }
    public static RespBean ok(String msg, Object obj) {
        return new RespBean(200, msg, obj);
    }


    public static RespBean error(String msg) {
        return new RespBean(500, msg, null);
    }

    public static RespBean error(String msg, Object obj) {
        return new RespBean(500, msg, obj);
    }

    public static RespBean error(ResultCode resultCode) {
        RespBean result = new RespBean();
        result.setStatus(resultCode.getCode());
        result.setMsg(resultCode.getMessage());
        return result;
    }

}
