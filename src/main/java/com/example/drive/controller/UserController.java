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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;

/**
 * @author zhulu
 * @since 2021-12-22
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户信息相关")
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
     *注册
     */
    @PostMapping("/register")
    @LogAnnotation(module = "User",operation = "Add")
    @ApiOperation("注册")
    @ApiImplicitParam(name = "user",value = "用户信息",required = true)
    public RespBean register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.register(user);

        return RespBean.ok("success It is suggested to improve personal information. The information that can be improved is as follows", user);
    }


    /**
     * 更新信息
     */
    @PostMapping("perfectInformation")
    @LogAnnotation(module = "User",operation = "Update")
    @ApiOperation("更新信息")
    @ApiImplicitParam(name = "user",value = "用户信息",required = true)
    public RespBean perfectInformation(@RequestBody User user) {
        userService.perfectInformation(user);
        return RespBean.ok("success", user);
    }


    /**
     * 获取紧急联系人
     */
    @GetMapping("getEmergencyNumber")
    @Cacheable(cacheNames = "emergencyNumber")
    @LogAnnotation(module = "User",operation = "Get")
    @ApiOperation("获取紧急联系人")
    public RespBean getEmergencyNumber() {
        return RespBean.ok("Emergency contacts are as follows", userService.getEmergencyNumber());
    }


    /**
     * 上传头像
     */
    @PostMapping("uploadPortrait")
    @LogAnnotation(module = "User",operation = "Update")
    @ApiImplicitParam(name = "file",value = "文件",required = true)
    public RespBean uploadPortrait(MultipartFile file) {
        Picture picture = userService.uploadPortrait(file);
        return RespBean.ok("success", picture);
    }


    /**
     * 获取当前用户
     */
    @GetMapping("getUser")
    @LogAnnotation(module = "User",operation = "Get")
    @ApiOperation("获取当前用户")
    public RespBean getUser() {
        return RespBean.ok("user information is", userService.getUser());
    }


    /**
     * 绑定手环
     */
    @PostMapping("bindBracelet")
    @LogAnnotation(module = "User",operation = "Update")
    @ApiOperation("绑定手环")
    @ApiImplicitParam(name = "bracelet", value = "绑定手环", required = true)
    public RespBean bindBracelet(Integer bracelet) {
        User u = userService.getUser();
        u.setBracelet(bracelet);
        userService.perfectInformation(u);
        return RespBean.ok("user information is", userService.getUser());
    }


    /**
     * 获取手环信息
     */
    @GetMapping("getBracelet")
    @LogAnnotation(module = "User",operation = "Get")
    @ApiOperation("获取手环信息")
    public RespBean getBracelet() {
        User u = userService.getUser();
        int bracelet = u.getBracelet();
        if (bracelet == 0) {
            return RespBean.error("not band Bracelet");
        }
        return RespBean.ok("user bracelet id is", bracelet);
    }


    /**
     * 获取App
     */
    @GetMapping("getApp")
    @LogAnnotation(module = "User",operation = "Get")
    @ApiIgnore
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
     * 更新密码
     */
    @GetMapping("updatePassword")
    @LogAnnotation(module = "User",operation = "Update")
    @ApiOperation("更新密码")
    @ApiImplicitParam(name = "password", value = "更新后的密码", required = true)
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
