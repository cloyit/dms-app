package com.example.drive.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.drive.entity.Brand;
import com.example.drive.entity.Peach;
import com.example.drive.mapper.PeachMapper;
import com.example.drive.response.RespBean;
import com.example.drive.service.IPeachService;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhulu
 * @since 2022-02-21
 */
@RestController
@RequestMapping("/peach")
public class PeachController {

    @Autowired
    PeachMapper peachMapper;
    @Autowired
    IPeachService iPeachService;
    //录入桃子
    @PostMapping("uploadPeach")
    public RespBean updatePeach(@RequestBody Peach p){
        peachMapper.insert(p);
        return RespBean.ok("successs",p.getId());
    }

    @GetMapping("getPeach")
    public RespBean getPeach(@RequestParam("peachId") Integer peachId){
        QueryWrapper<Peach> queryWrapper = new QueryWrapper<Peach>();
        queryWrapper.eq("id",peachId);
        Peach p = peachMapper.selectOne(queryWrapper);
        return RespBean.ok("success",p);
    }

    /**
     * 查询所有的桃子
     * @return
     */
    @GetMapping("getAllPeach")
    public RespBean getAllPeach(){
        List<Peach> resultList = peachMapper.selectList(null);
        return RespBean.ok("success",resultList);

    }

    /**
     * 分页查询桃子
     * @param currentPage
     * @param size
     * @return
     */
    @GetMapping("getPeachLimit")
    public RespBean getPeachLimit(Integer currentPage,Integer size){

        //先查询所有的，然后再组装就好
        List<Peach> peaches = peachMapper.selectList(null);
        int begin = (currentPage-1)*size;
        int end = begin+size;
        List<Peach> resultList = peaches.subList(begin, end);
       return RespBean.ok("success and peaches are",resultList);
    }

    /**
     * 根据id 批量删除桃子
     * @param Ids
     * @return
     */
    @PostMapping("deleteBrandByIds")
    public RespBean deletePeachById(@RequestBody List<Integer> Ids){
        //先判断有无数据
        if(!Ids.isEmpty()&&Ids.size()==0){
            return RespBean.error("empty");
        }
        QueryWrapper<Peach> queryWrapper = new QueryWrapper<Peach>();
        queryWrapper.in("id",Ids);
        peachMapper.delete(queryWrapper);
        return RespBean.ok("Batch delete success");
    }


}
