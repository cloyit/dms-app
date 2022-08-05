package com.example.drive.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class Bmi implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long uid;

    private String height;

    private String weight;

    LocalDateTime time;

}
