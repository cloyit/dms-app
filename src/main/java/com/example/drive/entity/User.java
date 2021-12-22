package com.example.drive.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhulu
 * @since 2021-12-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户uid，使用雪花算法生成
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long uid;

    /**
     * 姓名
     */
    private String name;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 性别
     */
    private String gender;

    /**
     * 居住地址
     */
    private String address;

    /**
     * 出生日期
     */
    private String birth;

    /**
     * 电话号码
     */
    private String tel;

    /**
     * 身高
     */
    private Double height;

    /**
     * 体重
     */
    private Double weight;
    
    private String password;

    private String emergencyNumber;

    private String information_face;


}
