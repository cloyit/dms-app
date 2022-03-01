package com.example.drive.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.drive.entity.Detail;
import com.example.drive.entity.Title;
import com.example.drive.mapper.DetailMapper;
import com.example.drive.mapper.TitleMapper;
import com.example.drive.response.RespBean;
import com.example.drive.service.IDetailService;
import com.example.drive.service.ITitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhulu
 * @since 2022-02-23
 */
@RestController
@RequestMapping("/title")
public class TitleController {



    @Autowired
    private TitleMapper titleMapper;
    @Autowired
    private DetailMapper detailMapper;

    /**
     *  获取所有的detail
     *  1 获取所有的title, 根据每一个title 获取detail
     * @return
     */
    @PostMapping("getAllDetail")
    public RespBean getAllDetail(){

        List<Title> titles = titleMapper.selectAllTitle();
        //查出所有的detail id 组合成title
        for(int i =0 ;i< titles.size();i++){
            titles.get(i).setDetails(detailMapper.getDetailsById(i));
        }
        return RespBean.ok("title is",titles);
    }

    @PostMapping("uploadTitle")
    public RespBean uploadTitle(Title title){
        titleMapper.insert(title);
        return RespBean.ok("success",title);

    }
}
