package com.example.drive.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhulu
 * @since 2022-02-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("品牌")
public class Brand implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "brand_id", type = IdType.AUTO)
    @ApiModelProperty("品牌ID")
    private Integer brandId;

    @ApiModelProperty("名称")
    private String name;

    //存储html页面
    @ApiModelProperty("HTML页面内容")
    private String content;
}
