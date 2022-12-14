package com.example.drive.service;

import com.example.drive.entity.Brand;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhulu
 * @since 2022-02-23
 */
public interface IBrandService extends IService<Brand> {

    HashMap<String, Object> getBrandLimit(Integer currentPage, Integer size);
}
