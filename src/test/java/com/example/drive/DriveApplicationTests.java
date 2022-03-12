package com.example.drive;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.drive.entity.*;
import com.example.drive.mapper.*;
import com.example.drive.service.IDrivingInformationService;
import com.example.drive.service.IUserService;
import com.example.drive.utills.FastDFSUtil;
import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.File;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@SpringBootTest
class DriveApplicationTests {
    @Autowired
    UserMapper userMapper;

    @Autowired
    PeachMapper peachMapper;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    TitleMapper titleMapper;
    @Autowired
    CarMapper carMapper;
    @Autowired
    DetailMapper detailMapper;
    @Autowired
    BrandMapper brandMapper;
    @Autowired
    UserHealthMapper userHealthMapper;
    @Autowired
    IDrivingInformationService iDrivingInformationService;
    @Autowired
    IUserService iUserService;
    @Autowired
    DrivingInformationMapper drivingInformationMapper;
    @Test
    void contextLoads() {

   DrivingInformation drivingInformation = null;

   LocalDateTime beginTime;
        LocalDateTime endTime;
   Random e =new Random();
   for(int i=0;i<27;i++){
       drivingInformation = new DrivingInformation();
       beginTime =LocalDateTime.of(2022,1,i+1,12,24);
       endTime = LocalDateTime.of(2022,1,i+1,12+e.nextInt(4),20+e.nextInt(30));
       drivingInformation.setAttention(2+e.nextInt(3));
       drivingInformation.setUid(1473527123812581377L);
       drivingInformation.setBegin(beginTime);
       drivingInformation.setEnd(endTime);
       drivingInformation.setCarId(3);
       drivingInformation.setCloseEye(1+e.nextInt(3));
       drivingInformation.setEndLatitude(31+e.nextInt(2)+"");
       drivingInformation.setStartLatitude(31+e.nextInt(2)+"");
       drivingInformation.setStartLongitude(31+e.nextInt(2)+"");
       drivingInformation.setEndLongitude(31+e.nextInt(2)+"");
       drivingInformationMapper.insert(drivingInformation);
   }



    }

}
