package com.example.drive.service.impl;

import com.example.drive.entity.Brand;
import com.example.drive.mapper.BrandMapper;
import com.example.drive.response.RespBean;
import com.example.drive.service.IBrandService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhulu
 * @since 2022-02-23
 */
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements IBrandService {

    @Autowired
    BrandMapper brandMapper;

    @Override
    public HashMap<String, Object> getBrandLimit(Integer currentPage, Integer size) {

        int begin = (currentPage - 1) * size;
        int count = brandMapper.selectCount(null);

        List<Brand> brands = null;
        brandMapper.getBrandLimit(begin, size);
        if ((currentPage - 1) * size > count) {
//            return RespBean.error("no much page are total is" + count);
            return null;
        }
        else if (currentPage * size > count) {
            size = count - (currentPage - 1) * size;
        }
        brands = brandMapper.getBrandLimit(begin, size);

        HashMap<String, Object> result = new HashMap<>();
        result.put("peaches", brands);
        result.put("total", count);
        return result;
    }
}
