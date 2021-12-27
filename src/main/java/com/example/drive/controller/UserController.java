package com.example.drive.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.example.drive.entity.User;
import com.example.drive.response.RespBean;
import com.example.drive.service.IUserService;
import com.example.drive.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
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

    /**
     * 注册
     * @param u
     * @return
     */
    @PostMapping("/register")
    public RespBean register(User u){
        userService.register(u);
        return RespBean.ok("success");
    }

    /**
     * 完善个人信息
     * @return
     */
    @PostMapping("perfectInformation")
    public RespBean perfectInformation(User u){
        userService.perfectInformation(u);
        return RespBean.ok("");
    }

    /**
     *获取紧急电话号码
     * @return
     */
    @GetMapping("getEmergencyNumber")
    public RespBean getEmergencyNumber(){
        return RespBean.ok("sucess",userService.getEmergencyNumber());
    }




}
