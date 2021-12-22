package com.example.drive.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
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
public class DrivingInformation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户uid
     */
    private Integer uid;

    /**
     * 开始时间
     */
    private LocalDateTime begin;

    /**
     * 结束时间
     */
    private LocalDateTime end;

    /**
     * 打哈欠次数
     */
    private Integer yawn;

    /**
     * 闭眼次数
     */
    private Integer closeEye;

    /**
     * 注意力不集中次数
     */
    private Integer attention;

    /**
     * 记录驾驶哪辆车
     */
    private Integer carId;


}
