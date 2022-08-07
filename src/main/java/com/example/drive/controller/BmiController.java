package com.example.drive.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.drive.aop.LogAnnotation;
import com.example.drive.config.Cache;
import com.example.drive.entity.Bmi;
import com.example.drive.entity.User;
import com.example.drive.mapper.BmiMapper;
import com.example.drive.response.RespBean;
import com.example.drive.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhulu
 * @since 2022-04-10
 */
@RestController
@RequestMapping("/bmi")
@Api(tags = "Bmi信息相关")
public class BmiController {

    @Autowired
    BmiMapper bmiMapper;
    @Autowired
    IUserService iUserService;


    /**
     * 上传bmi
     */
    @PostMapping("uploadBmi")
    @LogAnnotation(module = "Bmi")
    @CacheEvict(value = "Brand", allEntries = true)
    @ApiOperation("通过名称更新Brand")
    public RespBean updateBrandByName(@RequestBody Bmi bmi) {
        return RespBean.error("weight is String not number");
    }


    /**
     * 获取用户所有的bmi历史数据
     */
    @GetMapping("getAllBmi")
    @LogAnnotation(module = "Bmi")
    @Cacheable(value = "Brand", key = "'All'")
    @ApiOperation("获取所有Brand列表")
    public RespBean getAllBmi() {
//        User u = iUserService.getUser();
        QueryWrapper<Bmi> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", 1473527123812581377L);
//        queryWrapper.eq("uid", u.getUid());
        List<Bmi> bmiList = bmiMapper.selectList(queryWrapper);
        return RespBean.ok("success and new brand is", bmiList);
    }
}
