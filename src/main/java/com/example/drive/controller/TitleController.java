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
     *
     *  1 获取所有的title, 根据每一个title 获取detail
     * @return
     */
    @PostMapping("getAllTitle")
    public RespBean getAllTitle(){

        List<Title> titles = titleMapper.selectAllTitle();
        //查出所有的detail id 组合成title
        for(int i =0 ;i< titles.size();i++){
            titles.get(i).setDetails(detailMapper.getDetailsById(i));
        }
        return RespBean.ok("title is",titles);
    }

    /**
     * 上传title
     * @param title
     * @return
     */
    @PostMapping("uploadTitle")
    public RespBean uploadTitle(Title title){
        titleMapper.insert(title);
        return RespBean.ok("success",title);

    }

    /**
     * 根据Id删除title
     * @param id
     * @return
     */
    @PostMapping("deleteTitle")
    public RespBean deleteTitle(Integer id){
        //查询id是否合法
        List<Title> titles = titleMapper.selectList(null);
        if(id>titles.get(titles.size()-1).getTitleId()){
            return RespBean.error("id is out of boundry",null);
        }
        QueryWrapper<Title> queryWrapper = new QueryWrapper<Title>();
        queryWrapper.eq("titleId",id);
        titleMapper.delete(queryWrapper);
        return RespBean.ok("success delete",id);
    }

    /**
     * 更新title
     * @param
     * @return
     */
    public RespBean updateTitle(Title title){
        //查看title id 是否合法
        List<Title> titles = titleMapper.selectList(null);
        QueryWrapper<Title> queryWrapper = new QueryWrapper<Title>();
        queryWrapper.eq("titleId",title.getTitleId());
        for(Title t:titles){
            if(t.getTitleId().equals(title.getTitleId())){
                // title id 合法
                titleMapper.update(title,queryWrapper);
                return RespBean.ok("success and titile is",title);
            }
        }
        return RespBean.error("error not found title id please insert");
    }


}
