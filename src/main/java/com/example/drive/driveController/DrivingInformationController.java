package com.example.drive.driveController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.drive.aop.LogAnnotation;
import com.example.drive.entity.Dpicture;
import com.example.drive.entity.DrivingInformation;
import com.example.drive.entity.User;
import com.example.drive.mapper.DpictureMapper;
import com.example.drive.mapper.DrivingInformationMapper;
import com.example.drive.response.RespBean;
import com.example.drive.service.IDrivingInformationService;
import com.example.drive.service.IUserService;
import com.example.drive.utills.FastDFSUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
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
@RequestMapping("/drivingInformation")
@Api(tags = "驾驶信息相关")
public class DrivingInformationController {
    @Autowired
    private IDrivingInformationService iDrivingInformationService;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private DpictureMapper dpictureMapper;
    @Autowired
    private DrivingInformationMapper drivingInformationMapper;


    /**
     * 查看一段时间的驾驶记录
     */
    @GetMapping("getDrivingInformationByDay")
    @LogAnnotation(module = "DrivingInformation",operation = "Get")
    @ApiOperation("查看一段时间的驾驶记录")
    @ApiImplicitParam(name = "beginTimeS",value = "起始日期",required = true)
    public RespBean getDrivingInformationByDay(String beginTimeS) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime beginTime = LocalDateTime.parse(beginTimeS, formatter);

//        List<List<DrivingInformation>> result = iDrivingInformationService.
//                getDrivingInformationByDay(iUserService.getUid(), beginTime);
        List<List<DrivingInformation>> result = iDrivingInformationService.
                getDrivingInformationByDay(1473527123812581377L, beginTime);

        if (result != null) {
            return RespBean.ok("DrivingInformation are ", result);
        }
        else {
            return RespBean.error("is empty");
        }
    }


    /**
     * 查看一段时间的驾驶记录
     */
    @GetMapping("getDrivingInformationByTime")
    @LogAnnotation(module = "DrivingInformation",operation = "Get")
    @ApiOperation("查看一段时间的驾驶记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "beginTimeS", value = "起始时间", required = true),
            @ApiImplicitParam(name = "endTimeS", value = "结束时间", required = true)}
    )
    public RespBean getDrivingInformationByDay(String beginTimeS, String endTimeS) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime beginTime = LocalDateTime.parse(beginTimeS, formatter);
        LocalDateTime endTime = LocalDateTime.parse(endTimeS, formatter);

        List<List<DrivingInformation>> result = iDrivingInformationService.getDrivingInformationByDay(
                iUserService.getUid(), beginTime, endTime);

        if (result != null) {
            return RespBean.ok("DrivingInformation are ", result);
        }
        else {
            return RespBean.error("is empty");
        }
    }


    /**
     * 上传驾驶中不良驾驶的图片
     */
    @PostMapping("uploadDPicture")
    @LogAnnotation(module = "DrivingInformation",operation = "Upload")
    @ApiOperation("上传驾驶中不良驾驶的图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件", required = true),
            @ApiImplicitParam(name = "did", value = "信息ID", required = true)}
    )
    public RespBean uploadPicture(MultipartFile file, Integer did) {
        Dpicture dpicture = iDrivingInformationService.uploadPicture(file,did);
        return RespBean.ok("success", dpicture);
    }



    @GetMapping("getDPictureById")
    @LogAnnotation(module = "DrivingInformation", operation = "Get")
    @ApiOperation("通过ID获取图片")
    @ApiImplicitParam(name = "did", value = "图片ID", required = true)
    public RespBean getDPictureById(Integer did) {
        QueryWrapper<Dpicture> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("did", did);
        List<Dpicture> dpictureList = dpictureMapper.selectList(queryWrapper);
        if (dpictureList == null) {
            return RespBean.error("not have");
        }
        return RespBean.ok("success", dpictureList);
    }



    @GetMapping("getFace")
    @ApiOperation("获取面部数据")
    @LogAnnotation(module = "DrivingInformation",operation = "Get")
    public RespBean getFace() {
        //直接获取一个用户所有的人脸打卡记录
        User u = iUserService.getUser();
        QueryWrapper<DrivingInformation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", u.getUid());
        List<DrivingInformation> drivingInformationList = drivingInformationMapper.selectList(queryWrapper);
        return RespBean.ok("success", drivingInformationList);
    }


    /**
     * 上传人脸图像
     */
    @PostMapping("updateFace")
    @LogAnnotation(module = "DrivingInformation",operation = "Update")
    @ApiOperation("上传人脸图像")
    @ApiImplicitParam(name = "drivingInformation", value = "驾驶信息对象", required = true)
    public RespBean updateFace(@RequestBody DrivingInformation drivingInformation) {
        //直接获取一个用户所有的人脸打卡记录
        User u = iUserService.getUser();
        drivingInformation.setUid(u.getUid());
        drivingInformationMapper.insert(drivingInformation);
        return RespBean.ok("success", drivingInformation);
    }
}
