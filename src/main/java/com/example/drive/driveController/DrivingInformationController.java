package com.example.drive.driveController;


import com.example.drive.entity.DrivingInformation;
import com.example.drive.entity.UserHealth;
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
import java.util.ArrayList;
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
    @GetMapping("getDrivingInformationByDay")
    public RespBean getDrivingInformationByDay(String beginTimeS)  {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime beginTime = LocalDateTime.parse(beginTimeS, formatter);
        List<DrivingInformation> DriveList = iDrivingInformationService.getDrivingInformationByDay(iUserService.getUid(),beginTime);
        if(DriveList!=null && DriveList.size()!=0){

            List<List<DrivingInformation>> result = new ArrayList<List<DrivingInformation>>();
            //拆分数据,设置标识，循环遍历
            int flag =0;
            for(int i =0;i<DriveList.size();i++){
                if(flag == DriveList.get(i).getBegin().getDayOfYear()){
                    result.get(result.size()-1).add(DriveList.get(i));
                }else {
                    //新建并且更新flag
                    flag = DriveList.get(i).getBegin().getDayOfYear();
                    List<DrivingInformation> r = new ArrayList<DrivingInformation>();
                    r.add(DriveList.get(i));
                    result.add(r);
                }
            }
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
        List<DrivingInformation> DriveList = iDrivingInformationService.getDrivingInformationByDay(iUserService.getUid(),beginTime,endTime);
        if(DriveList!=null && DriveList.size()!=0){

            List<List<DrivingInformation>> result = new ArrayList<List<DrivingInformation>>();
            //拆分数据,设置标识，循环遍历
            int flag =0;
            for(int i =0;i<DriveList.size();i++){
                if(flag == DriveList.get(i).getBegin().getDayOfYear()){
                    result.get(result.size()-1).add(DriveList.get(i));
                }else {
                    //新建并且更新flag
                    flag = DriveList.get(i).getBegin().getDayOfYear();
                    List<DrivingInformation> r = new ArrayList<DrivingInformation>();
                    r.add(DriveList.get(i));
                    result.add(r);
                }
            }

            return RespBean.ok("DrivingInformation are ",result);
        }else {
            return RespBean.error("is empty");
        }
    }
}
