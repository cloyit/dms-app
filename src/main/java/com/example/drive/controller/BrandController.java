package com.example.drive.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.example.drive.entity.Brand;
import com.example.drive.entity.Title;
import com.example.drive.mapper.BrandMapper;
import com.example.drive.mapper.DetailMapper;
import com.example.drive.mapper.TitleMapper;
import com.example.drive.response.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *品牌信息接口
 *
 * @author zhulu
 * @since 2022-02-23
 */
@RestController
@RequestMapping("/brand")
public class BrandController {


    @Autowired
    private BrandMapper brandMapper;

    /**
     * 上传Brand
     * @param brand
     * @return
     */
    @PostMapping("uploadBrand")
    public RespBean updateBrand(@RequestBody Brand brand){
        brandMapper.insert(brand);
        return RespBean.ok("success and brand is",brand);
    }

    /**
     * 获取当前所有的Brand
     * @return
     */
    @GetMapping("getAllBrand")
    public RespBean getAllBrand(){
        return RespBean.ok("success",brandMapper.selectList(null));
    }

    /**
     * 根据名称修改品牌信息
     * 提供名字就行，不需要写id
     * @param
     * @return
     */
    @PostMapping("updateBrandById")
    public RespBean updateBrandByName(@RequestBody Brand brand){
        //先根据name获取id
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<Brand>();
        queryWrapper.eq("brandId",brand.getBrandId());
        brandMapper.update(brand,queryWrapper);
        return RespBean.ok("success and new brand is",brand);
    }

    /**
     * 根据name 删除
     * @param Ids
     * @return
     */
    @PostMapping("deleteBrandByIds")
    public RespBean deleteBrandById(List<Integer> Ids){
        //先判断有无数据
        if(!Ids.isEmpty()&&Ids.size()==0){
            return RespBean.error("empty");
        }
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<Brand>();
        queryWrapper.in("brandId",Ids);
        brandMapper.delete(queryWrapper);
        return RespBean.ok("Batch delete success");
    }

    /**
     * 根据名字模糊查询
     * @param LikeName
     * @return
     */
    @PostMapping("selectBrandLike")
    public RespBean selectBrandLike(String LikeName){
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<Brand>();
        queryWrapper.like("name",LikeName);
        return RespBean.ok("success",brandMapper.selectOne(queryWrapper));

    }


}
