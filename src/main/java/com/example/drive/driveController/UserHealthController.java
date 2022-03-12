package com.example.drive.driveController;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.drive.entity.DrivingInformation;
import com.example.drive.entity.UserHealth;
import com.example.drive.mapper.UserHealthMapper;
import com.example.drive.response.RespBean;
import com.example.drive.service.IUserHealthService;
import com.example.drive.service.IUserService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
    @Autowired
    private IUserService iUserService;
    @Autowired
    private UserHealthMapper userHealthMapper;

    /**
     * 查看一段时间的健康报表
     * @return
     */
    @GetMapping("getHealthByTime")
    public RespBean getHealthByTime(String beginTimeS,String endTimeS)  {

      // String 转换为 LocalDateTime
        String dateStr = "2021-09-03 21:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime beginTime = LocalDateTime.parse(beginTimeS, formatter);
        LocalDateTime endTime = LocalDateTime.parse(endTimeS, formatter);
       return RespBean.ok("success",iUserHealthService.getHealthByTime(beginTime, endTime));
    }

    /**
     * 传感器上传健康报表
     * @param userHealth
     * @return
     */
    @PostMapping("uploadHealth")
    public RespBean uploadHealth(@RequestBody UserHealth userHealth){
        Long uid = iUserService.getUid();
        userHealth.setUid(uid);
        iUserHealthService.uploadHealth(userHealth);
        return RespBean.ok("success",userHealth);
    }
    /**
     * 根据uid 获取最新的15个健康报表
     * @return
     */
    @GetMapping("getLastUserHealth")
    public RespBean getLastUserHealth(){
        Long uid = iUserService.getUid();
        QueryWrapper<UserHealth> queryWrapper = new QueryWrapper<UserHealth>();
        //iUserHealthService.uploadHealth(userHealth);
       List<UserHealth>  userHealth = userHealthMapper.selectList(queryWrapper);
        List<UserHealth> results = userHealth;
       if(userHealth.size()>15){
           results = userHealth.subList(userHealth.size()-15,userHealth.size()-1);
       }
        return RespBean.ok("success and detail",results);
    }

    /**
     * 上传摄像头采集的数据
     * @param drivingInformation
     * @return
     */
    @PostMapping("uploadDriving")
    public RespBean uploadDriving(@RequestBody DrivingInformation drivingInformation){
        iUserHealthService.uploadDriving(drivingInformation);
        return RespBean.ok("success",drivingInformation);
    }

}
