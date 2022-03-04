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
        //判断下，名字不允许相同
        //先查询出所有的，这是就体现出service层的好处，能复用代码
        //小项目，就不遵循三层模型了
        List<Brand> brandList = brandMapper.selectList(null);
        for (Brand b : brandList){
            if(brand.getName().equals(b.getName())){
                return RespBean.error("error because exist same name brand");
            }
        }
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
        queryWrapper.eq("brand_id",brand.getBrandId());
        brandMapper.update(brand,queryWrapper);
        return RespBean.ok("success and new brand is",brand);
    }

    /**
     * 根据name 删除
     * @param Ids
     * @return
     */
    @PostMapping("deleteBrandByIds")
    public RespBean deleteBrandById(@RequestBody List<Integer> Ids){
        //先判断有无数据
        if(!Ids.isEmpty()&&Ids.size()==0){
            return RespBean.error("empty");
        }
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<Brand>();
        queryWrapper.in("brand_id",Ids);
        brandMapper.delete(queryWrapper);
        return RespBean.ok("Batch delete success");
    }

    /**
     * 根据名字模糊查询
     * @param LikeName
     * @return
     */
    @GetMapping("selectBrandLike")
    public RespBean selectBrandLike(String LikeName){

        QueryWrapper<Brand> queryWrapper = new QueryWrapper<Brand>();
        queryWrapper.like("name",LikeName);
        return RespBean.ok("success",brandMapper.selectList(queryWrapper));

    }


}
