package com.example.drive.controller;


import com.example.drive.entity.Brand;
import com.example.drive.entity.Title;
import com.example.drive.mapper.BrandMapper;
import com.example.drive.mapper.DetailMapper;
import com.example.drive.mapper.TitleMapper;
import com.example.drive.response.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/brand")
@CrossOrigin
public class BrandController {


    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private DetailMapper detailMapper;

    /**
     *  获取所有的brand
     *  1 获取所有的title, 根据每一个title 获取detail
     * @return
     */
    @PostMapping("getAllBrand")
    public RespBean getAllBrand(){

        List<Brand> brands = brandMapper.selectAllBrand();
        //查出所有的detail id 组合成title
        for(int i =0 ;i< brands.size();i++){
            brands.get(i).setDetails(detailMapper.getDetailsByBId(i+1));
        }
        return RespBean.ok("brand is ",brands);
    }

    /**
     * 上传brand
     * @param brand
     * @return
     */
    @PostMapping("addBrand")
    public RespBean addBrand(Brand brand){

        brandMapper.insert(brand);
        return RespBean.ok("success",brand);
    }


}
