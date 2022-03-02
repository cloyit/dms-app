package com.example.drive.driveController;


import com.example.drive.entity.DrivingInformation;
import com.example.drive.entity.UserHealth;
import com.example.drive.response.RespBean;
import com.example.drive.service.IUserHealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhulu
 * @since 2021-12-22
 */
@RestController
@RequestMapping("/health")
public class UserHealthController {
    @Autowired
    private IUserHealthService iUserHealthService;

    /**
     * 查看一段时间的健康报表
     * @param beginTime
     * @param endTime
     * @return
     */
    @GetMapping("getHealthByTime")
    public RespBean getHealthByTime(LocalDateTime beginTime, LocalDateTime endTime){
       return RespBean.ok("success",iUserHealthService.getHealthByTime(beginTime, endTime));
    }

    /**
     * 传感器上传健康报表
     * @param userHealth
     * @return
     */
    @PostMapping("uploadHealth")
    public RespBean uploadHealth(UserHealth userHealth){
        iUserHealthService.uploadHealth(userHealth);
        return RespBean.ok("success",userHealth);
    }

    /**
     * 上传摄像头采集的数据
     * @param drivingInformation
     * @return
     */
    @PostMapping("uploadDriving")
    public RespBean uploadDriving(DrivingInformation drivingInformation){
        iUserHealthService.uploadDriving(drivingInformation);
        return RespBean.ok("success",drivingInformation);
    }

}
