package com.example.drive.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.drive.aop.LogAnnotation;
import com.example.drive.config.Cache;
import com.example.drive.entity.Brand;
import com.example.drive.mapper.BrandMapper;
import com.example.drive.response.RespBean;
import com.example.drive.service.IBrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 * 品牌信息接口
 *
 * @author zhulu
 * @since 2022-02-23
 */
@RestController
@RequestMapping("/brand")
@Api(tags = "品牌信息相关")
public class BrandController {

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    IBrandService service;



    /**
     * 获取当前所有的Brand
     */
    @GetMapping("getAllBrand")
    @Cacheable(value = "all", key = "0")
    @LogAnnotation(module = "Brand",operation = "Get")
    @ApiOperation("获取所有Brand信息")
    public RespBean getAllBrand() {
        return RespBean.ok("success", brandMapper.selectList(null));
    }


    /**
     * 上传Brand
     */
    @PostMapping("uploadBrand")
    @LogAnnotation(module = "Brand",operation = "Upload")
    @ApiOperation("上传Brand信息")
    public RespBean uploadBrand(@RequestBody Brand brand) {
        List<Brand> brandList = brandMapper.selectList(null);
        for (Brand b : brandList) {
            if (brand.getName().equals(b.getName())) {
                return RespBean.error("error because exist same name brand");
            }
        }
        brandMapper.insert(brand);
        return RespBean.ok("success and brand is", brand);
    }


    /**
     * 根据名称修改品牌信息
     * 提供名字就行，不需要写id
     */
    @PostMapping("updateBrandById")
    @LogAnnotation(module = "Brand",operation = "Update")
    @ApiOperation("根据名称修改品牌信息")
    public RespBean updateBrandByName(@RequestBody Brand brand) {
        //先根据name获取id
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("brand_id", brand.getBrandId());
        brandMapper.update(brand, queryWrapper);
        return RespBean.ok("success and new brand is", brand);
    }


    /**
     * 根据name 删除
     */
    @PostMapping("deleteBrandByIds")
    @LogAnnotation(module = "Brand",operation = "Delete")
    @ApiOperation("根据name删除Brand")
    @ApiImplicitParam(name = "Ids", value = "待删除ID列表", required = true)
    public RespBean deleteBrandById(@RequestBody List<Integer> Ids) {
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("brand_id", Ids);
        brandMapper.delete(queryWrapper);
        return RespBean.ok("Batch delete success");
    }


    /**
     * 根据名字模糊查询
     */
    @GetMapping("selectBrandLike")
    @Cacheable(value = "Like", key = "#LikeName")
    @LogAnnotation(module = "Brand",operation = "Get")
    @ApiOperation("根据名字模糊查询Brand")
    @ApiImplicitParam(name = "LikeName", value = "待查询名称", required = true)
    public RespBean selectBrandLike(String LikeName) {
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", LikeName);
        return RespBean.ok("success", brandMapper.selectList(queryWrapper));
    }


    /**
     * 分页查询品牌信息
     */
    @GetMapping("getBrandLimit")
    @Cacheable(value = "Pages", key = "#currentPage * #size")
    @LogAnnotation(module = "Brand",operation = "Get")
    @ApiOperation("分页查询品牌信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage", value = "当前页数", required = true),
            @ApiImplicitParam(name = "size", value = "数据总量", required = true)}
    )
    public RespBean getBrandLimit(Integer currentPage, Integer size) {
        HashMap<String, Object> result = service.getBrandLimit(currentPage,size);
        int count = brandMapper.selectCount(null);

        if (result==null) {
            return RespBean.error("no much page are total is" + count);
        }
        return RespBean.ok("success and peaches are total is" + count, result);
    }
}
