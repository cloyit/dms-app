package com.example.drive.driveController;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.drive.aop.LogAnnotation;
import com.example.drive.entity.DrivingInformation;
import com.example.drive.entity.Report;
import com.example.drive.entity.User;
import com.example.drive.entity.UserHealth;
import com.example.drive.mapper.ReportMapper;
import com.example.drive.mapper.UserHealthMapper;
import com.example.drive.response.RespBean;
import com.example.drive.service.IUserHealthService;
import com.example.drive.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhulu
 * @since 2021-12-22
 */
@RestController
@RequestMapping("/health")
@Api(tags = "用户健康相关")
public class UserHealthController {
    @Autowired
    private IUserHealthService iUserHealthService;
    @Autowired
    private IUserService iUserService;

    @Autowired
    ReportMapper reportMapper;


    /**
     * 查看一段时间的健康报表
     */
    @GetMapping("getHealthByTime")
    @LogAnnotation(module = "Health",operation = "Get")
    @ApiOperation("查看一段时间的健康报表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "beginTimeS", value = "起始时间", required = true),
            @ApiImplicitParam(name = "endTimeS", value = "结束时间", required = true)}
    )
    public RespBean getHealthByTime(String beginTimeS, String endTimeS) {
//        String dateStr = "2021-09-03 21:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime beginTime = LocalDateTime.parse(beginTimeS, formatter);
        LocalDateTime endTime = LocalDateTime.parse(endTimeS, formatter);

        List<List<UserHealth>> result = iUserHealthService.getHealthByTime(beginTime, endTime);

        return RespBean.ok("success", result);
    }


    /**
     * 传感器上传健康报表
     */
    @PostMapping("uploadHealth")
    @LogAnnotation(module = "Health",operation = "Upload")
    @ApiOperation("传感器上传健康报表")
    @ApiImplicitParam(name = "userHealth",value = "用户健康信息",required = true)
    public RespBean uploadHealth(@RequestBody UserHealth userHealth) {
        Long uid = iUserService.getUid();
        userHealth.setUid(uid);
        iUserHealthService.uploadHealth(userHealth);
        return RespBean.ok("success", userHealth);
    }


    /**
     * 根据uid 获取最新的15个健康报表
     */
    @GetMapping("getLastUserHealth")
    @LogAnnotation(module = "Health",operation = "Get")
    @Cacheable(value = "UserHealth",key = "'Last'")
    @ApiOperation("根据uid获取最新的15个健康报表")
    public RespBean getLastUserHealth() {
        Long uid = iUserService.getUid();
        List<List<UserHealth>> result = iUserHealthService.getLastUserHealth(uid);

        return RespBean.ok("success and detail", result);
    }


    /**
     * 上传摄像头采集的数据
     */
    @PostMapping("uploadDriving")
    @LogAnnotation(module = "Health",operation = "Upload")
    @CacheEvict(value = "UserHealth",allEntries = true)
    @ApiOperation("上传摄像头采集的数据")
    @ApiImplicitParam(name = "drivingInformation", value = "驾驶信息对象", required = true)
    public RespBean uploadDriving(@RequestBody DrivingInformation drivingInformation) {
        iUserHealthService.uploadDriving(drivingInformation);
        return RespBean.ok("success", drivingInformation);
    }


    /**
     * 上传摄像头采集的数据
     */
    @GetMapping("getReport")
    @LogAnnotation(module = "Health",operation = "Get")
    @CacheEvict(value = "UserHealth",allEntries = true)
    @ApiOperation("上传摄像头采集的数据")
    public RespBean getReport() {
        User user = iUserService.getUser();
        QueryWrapper<Report> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", user.getUid());
        Report report = reportMapper.selectOne(queryWrapper);
        return RespBean.ok("success report is ", report);
    }
}
