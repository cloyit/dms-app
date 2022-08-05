package com.example.drive.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.drive.aop.LogAnnotation;
import com.example.drive.entity.Picture;
import com.example.drive.entity.User;
import com.example.drive.mapper.PictureMapper;
import com.example.drive.mapper.UserMapper;
import com.example.drive.response.RespBean;
import com.example.drive.service.IUserService;
import com.example.drive.utills.FastDFSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhulu
 * @since 2021-12-22
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    IUserService userService;

    @Autowired
    PictureMapper pictureMapper;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    UserMapper userMapper;


    /**
     * 注册
     */
    @PostMapping("/register")
    @LogAnnotation(module = "User",operation = "Add")
    public RespBean register(@RequestBody User u) {
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        userService.register(u);

        return RespBean.ok("success It is suggested to improve personal information. The information that can be improved is as follows", u);
    }


    /**
     * 完善个人信息
     */
    @PostMapping("perfectInformation")
    @LogAnnotation(module = "User",operation = "Update")
    public RespBean perfectInformation(@RequestBody User u) {
        userService.perfectInformation(u);
        return RespBean.ok("success", u);
    }


    /**
     * 获取紧急电话号码
     */
    @GetMapping("getEmergencyNumber")
    @Cacheable(cacheNames = "emergencyNumber")
    @LogAnnotation(module = "User",operation = "Get")
    public RespBean getEmergencyNumber() {
        return RespBean.ok("Emergency contacts are as follows", userService.getEmergencyNumber());
    }


    /**
     * 上传头像
     */
    @PostMapping("uploadPortrait")
    @LogAnnotation(module = "User",operation = "Update")
    public RespBean uploadPortrait(MultipartFile file) {
        Picture picture = userService.uploadPortrait(file);
        return RespBean.ok("success", picture);
    }


    /**
     * 获取当前user的信息
     */
    @GetMapping("getUser")
    @LogAnnotation(module = "User",operation = "Get")
    public RespBean getUser() {
        return RespBean.ok("user information is", userService.getUser());
    }


    /**
     * 绑定手环
     */
    @PostMapping("bindBracelet")
    @LogAnnotation(module = "User",operation = "Update")
    public RespBean bindBracelet(Integer bracelet) {
        User u = userService.getUser();
        u.setBracelet(bracelet);
        userService.perfectInformation(u);
        return RespBean.ok("user information is", userService.getUser());
    }


    /**
     * 获取用户手环
     */
    @GetMapping("getBracelet")
    @LogAnnotation(module = "User",operation = "Get")
    public RespBean getBracelet() {
        User u = userService.getUser();
        int bracelet = u.getBracelet();
        if (bracelet == 0) {
            return RespBean.error("not band Bracelet");
        }
        return RespBean.ok("user bracelet id is", bracelet);
    }


    /**
     * 修改密码
     */
    @GetMapping("getApp")
    @LogAnnotation(module = "User",operation = "Get")
    public ResponseEntity<FileSystemResource> getApp() {
        String contentDisposition = ContentDisposition
                .builder("attachment")
                .filename("/home/zhulu02/anzhixing.apk")
                .build().toString();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .contentType(MediaType.IMAGE_JPEG)
                .body(new FileSystemResource("/home/zhulu02/anzhixing.apk"));
    }


    /**
     * 修改密码
     */
    @GetMapping("updatePassword")
    @LogAnnotation(module = "User",operation = "Update")
    public RespBean updatePassword(String password) {
        User user = userService.getUser();
        if (passwordEncoder.matches(user.getPassword(), password)) {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("uid", user.getUid());
            user.setPassword(passwordEncoder.encode(password));
            userMapper.update(user, queryWrapper);
            return RespBean.ok("success");
        } else {
            return RespBean.error("passowrd error");
        }
    }
}
