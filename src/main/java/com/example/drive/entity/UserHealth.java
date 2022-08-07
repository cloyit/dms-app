package com.example.drive.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhulu
 * @since 2021-12-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("用户健康数据")
public class UserHealth implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)
    @ApiModelProperty("数据ID")
    private Integer id;

    /**
     * 用户id
     */
    @ApiModelProperty("用户ID")
    private Long uid;

    /**
     * 采样时间
     */
    @ApiModelProperty("采样时间")
    private LocalDateTime time;

    /**
     * 心跳
     */
    @ApiModelProperty("心跳")
    private String heartbeat;

    /**
     * 血氧
     */
    @ApiModelProperty("血氧")
    private String bloodOxygen;

    /**
     * 温度
     */
    @ApiModelProperty("温度")
    private String temperature;

}
