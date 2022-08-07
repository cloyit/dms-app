package com.example.drive.entity;

import java.time.LocalDateTime;
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
@ApiModel("驾驶信息类")
public class DrivingInformation implements Serializable {

    @ApiModelProperty("序列化ID")
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("信息ID")
    private Integer id;

    /**
     * 用户uid
     */
    @ApiModelProperty("用户ID")
    private Long uid;

    /**
     * 开始时间
     */
    @ApiModelProperty("开始时间")
    private LocalDateTime begin;

    /**
     * 结束时间
     */
    @ApiModelProperty("结束时间")
    private LocalDateTime end;

    /**
     * 打哈欠次数
     */
    @ApiModelProperty("打哈欠次数")
    private Integer yawn;

    /**
     * 闭眼次数
     */
    @ApiModelProperty("闭眼次数")
    private Integer closeEye;

    /**
     * 注意力不集中次数
     */
    @ApiModelProperty("注意力不集中次数")
    private Integer attention;

    /**
     * 记录驾驶哪辆车
     */
    @ApiModelProperty("驾驶车辆ID")
    private Integer carId;

    @ApiModelProperty("起始经度")
    private String startLongitude;

    @ApiModelProperty("终止经度")
    private String endLongitude;

    @ApiModelProperty("起始纬度")
    private String startLatitude;

    @ApiModelProperty("终止纬度")
    private String endLatitude;

    @ApiModelProperty("酒精浓度")
    private String alcohol;
}
