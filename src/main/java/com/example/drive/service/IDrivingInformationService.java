package com.example.drive.service;

import com.example.drive.entity.Dpicture;
import com.example.drive.entity.DrivingInformation;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhulu
 * @since 2021-12-22
 */
public interface IDrivingInformationService extends IService<DrivingInformation> {
    //查询一天的驾驶记录
    List<List<DrivingInformation>> getDrivingInformationByDay(Long uid, LocalDateTime beginTime);

    //查询几天的
    List<List<DrivingInformation>> getDrivingInformationByDay(Long uid, LocalDateTime beginTime, LocalDateTime endTime);

    Dpicture uploadPicture(MultipartFile file, Integer did);
}
