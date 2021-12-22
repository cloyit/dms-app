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
public class UserHealth implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long uid;

    /**
     * 采样时间
     */
    private LocalDateTime time;

    /**
     * 心跳
     */
    private String heartbeat;

    /**
     * 血压
     */
    private String bloodPressure;

    /**
     * 酒精浓度
     */
    private String alcohol;


}
