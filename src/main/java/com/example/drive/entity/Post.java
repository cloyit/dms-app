package com.example.drive.entity;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 帖子实体类
 */
@Data
public class Post implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 帖子id
     */
    private Integer id;

    /**
     * 类别id
     */
    private Integer category;

    /**
     * 审核状态 0未通过、1已通过，0未通过
     */
    private Integer status;

    /**
     * 帖子内容
     */
    private String content;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer createUser;

    /**
     * 审核人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer updateUser;

    /**
     * 审核时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer updateTime;

}
