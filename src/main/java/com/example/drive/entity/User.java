package com.example.drive.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author zhulu
 * @since 2021-12-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
@ApiModel("用户信息类")
public class User implements Serializable {

    @ApiModelProperty("序列化ID")
    private static final long serialVersionUID = 1L;

    /**
     * 用户uid，使用雪花算法生成
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty("用户ID")
    private Long uid;

    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    private String name;

    /**
     * 昵称
     */
    @ApiModelProperty("昵称")
    private String nickname;

    /**
     * 性别
     */
    @ApiModelProperty("性别")
    private String gender;

    /**
     * 电话号码
     */
    @ApiModelProperty("电话号码")
    private String tel;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String password;

    /**
     * 紧急联系人
     */
    @ApiModelProperty("紧急联系人")
    private String emergencyNumber;

    /**
     * 头像
     */
    @ApiModelProperty("头像")
    private String headPortrait;


    @ApiModelProperty("数据ID")
    private Integer Bracelet;

    /**
     * 年龄
     */
    @ApiModelProperty("年龄")
    private Integer year;

    /**
     * 团队ID
     */
    @ApiModelProperty("团队ID")
    private Integer teamId;
    
    /**
     * 职位
     */
    @ApiModelProperty("职位")
    private String position;

    /**
     * 用户身份
     */
    @ApiModelProperty("用户身份")
    private String role;
}
