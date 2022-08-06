package com.example.drive.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@ApiModel("车辆信息")
public class Car implements Serializable {

    @ApiModelProperty("序列化ID")
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    @ApiModelProperty("信息ID")
    private int id;

    /**
     * 车辆类型
     */
    @ApiModelProperty("车辆类型")
    private String type;

    /**
     * 车牌号
     */
    @ApiModelProperty("车牌号")
    private String carNumber;


    @ApiModelProperty("用户ID")
    private Long masterId;


    @ApiModelProperty("设备")
    private int equipment;
}
