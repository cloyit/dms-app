package com.example.drive.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author zhulu
 * @since 2022-04-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
@ApiModel("BMI信息")
public class Bmi implements Serializable {

    @ApiModelProperty("序列化ID")
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    @ApiModelProperty("信息ID")
    private Long id;

    @ApiModelProperty("用户ID")
    private Long uid;

    @ApiModelProperty("身高")
    private String height;

    @ApiModelProperty("体重")
    private String weight;

    @ApiModelProperty("时间")
    LocalDateTime time;

}
