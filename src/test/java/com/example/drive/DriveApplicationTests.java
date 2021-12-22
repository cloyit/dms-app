package com.example.drive;

import com.example.drive.entity.User;
import com.example.drive.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class DriveApplicationTests {
    @Autowired
    UserMapper userMapper;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Test
    void contextLoads() {
        User u = new User();
        u.setName("沈");
        u.setPassword(passwordEncoder.encode("123"));
        userMapper.insert(u);
    }

}
