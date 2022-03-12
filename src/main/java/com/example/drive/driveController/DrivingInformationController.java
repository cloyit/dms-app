package com.example.drive.driveController;


import com.example.drive.entity.DrivingInformation;
import com.example.drive.response.RespBean;
import com.example.drive.service.IDrivingInformationService;
import com.example.drive.service.IUserService;
import lombok.AllArgsConstructor;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/drivingInformation")
public class DrivingInformationController {
    @Autowired
    private IDrivingInformationService iDrivingInformationService;
    @Autowired
    private IUserService iUserService;

    /**
     * 查看一段时间的健康报表
     * @return
     */
    @GetMapping("test")
    public RespBean getDrivingInformationByDay()  {
        List<DrivingInformation> result = null;
        if(result!=null && result.size()!=0){
            return RespBean.ok("DrivingInformation are ",result);
        }else {
            return RespBean.error("is empty");
        }

    }

    /**
     * 查看一段时间的健康报表
     * @return
     */
    @GetMapping("getDrivingInformationByDay")
    public RespBean getDrivingInformationByDay(String beginTimeS)  {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime beginTime = LocalDateTime.parse(beginTimeS, formatter);
        List<DrivingInformation> result = iDrivingInformationService.getDrivingInformationByDay(iUserService.getUid(),beginTime);
        if(result!=null && result.size()!=0){
            return RespBean.ok("DrivingInformation are ",result);
        }else {
            return RespBean.error("is empty");
        }

    }

    /**
     * 查看一段时间的健康报表
     * @return
     */
    @GetMapping("getDrivingInformationByTime")
    public RespBean getDrivingInformationByDay(String beginTimeS,String endTimeS)  {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime beginTime = LocalDateTime.parse(beginTimeS, formatter);
        LocalDateTime endTime = LocalDateTime.parse(endTimeS, formatter);
        List<DrivingInformation> result = iDrivingInformationService.getDrivingInformationByDay(iUserService.getUid(),beginTime,endTime);
        if(result!=null && result.size()!=0){
            return RespBean.ok("DrivingInformation are ",result);
        }else {
            return RespBean.error("is empty");
        }
    }
}
