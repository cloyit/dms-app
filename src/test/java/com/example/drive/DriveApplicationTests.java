package com.example.drive;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.drive.entity.*;
import com.example.drive.mapper.*;
import com.example.drive.utills.FastDFSUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.File;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

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
    @Test
    void contextLoads() {

        Date date = Date.valueOf("2022-03-10");
        //制作一些数据
        QueryWrapper<Peach> p = new QueryWrapper<Peach>();
       Peach peach = new Peach();
        peach.setName("夏季桃子");
        peach.setDescription("只在夏天才有的彩色桃子");
        peach.setPick(date);
        peach.setAddress("中国");
        peach.setList(date);
        for (int i=0;i<20;i++){
            peachMapper.insert(peach);
        }

    }

}
