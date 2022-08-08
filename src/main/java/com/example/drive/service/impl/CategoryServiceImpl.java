package com.example.drive.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.drive.common.CustomException;
import com.example.drive.entity.Category;
import com.example.drive.mapper.CategoryMapper;
import com.example.drive.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    /*private DishService dishService;
    private SetMealService setMealService;*/

    /**
     * 根据id删除分类，删除之前需要进行判断
     */
    /*@Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件，根据分类id进行查询
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        long count1 = dishService.count(dishLambdaQueryWrapper);

        //查询当前分类是否关联了菜品
        if (count1 > 0) {
            // 已经关联菜品
            throw new CustomException("当前分类下关联了菜品！不能删除");
        }


        //查询当前分类是否关联了套餐
        LambdaQueryWrapper<SetMeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件，根据分类id进行查询
        setmealLambdaQueryWrapper.eq(SetMeal::getCategoryId, id);
        long count2 = setMealService.count();
        if (count2 > 0) {
            //已经关联套餐
            throw new CustomException("当前分类下关联了套餐！不能删除");
        }
        //正常删除分类
        super.removeById(id);
    }*/






}
