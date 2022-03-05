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

        LocalDateTime localDateTime = LocalDateTime.now();
        //制作一些数据
        Peach peach = new Peach();
        peach.setName("恩施圣桃子");
        peach.setDescription("很大很大的桃子");
        peach.setPick(localDateTime);
        peach.setAddress("武汉理工大学");
        peach.setList(localDateTime);
        for (int i=0;i<20;i++){
            peachMapper.insert(peach);
        }

    }

}
