package com.example.drive.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.drive.entity.Peach;
import com.example.drive.mapper.PeachMapper;
import com.example.drive.response.RespBean;
import com.example.drive.service.IPeachService;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@CrossOrigin
public class PeachController {

    @Autowired
    PeachMapper peachMapper;
    //录入桃子
    @PostMapping("updatePeach")
    public RespBean updatePeach(Peach p){
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

}
