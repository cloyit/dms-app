package com.example.drive.driveController;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.drive.aop.LogAnnotation;
import com.example.drive.dto.CarDto;
import com.example.drive.entity.Brand;
import com.example.drive.entity.Car;
import com.example.drive.entity.DrivingInformation;
import com.example.drive.response.RespBean;
import com.example.drive.service.ICarService;
import com.example.drive.service.IDrivingInformationService;
import com.example.drive.service.IUserCarRalationService;
import com.example.drive.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhulu
 * @since 2021-12-22
 */
@RestController
@RequestMapping("/car")
@Api(tags = "车辆信息相关")
public class CarController {
    @Autowired
    ICarService iCarService;

    @Autowired
    IUserService iUserService;

    @Autowired
    IDrivingInformationService iDrivingInformationService;


    /**
     * 根据当前用户增加车
     */
    @PostMapping("addCar")
    @LogAnnotation(module = "Car",operation = "Add")
    @ApiOperation("新增车辆信息")
    @ApiImplicitParam(name = "car",value = "车辆对象",required = true)
    public RespBean addCar(@RequestBody Car car) {
        //增加master_id
        Long uid = iUserService.getUid();
        car.setMasterId(uid);
        iCarService.addCar(car);
        return RespBean.ok("success and car is ", car);
    }





    /**
     * 根据token 获取所有的车
     *
     * @return
     */
//    @GetMapping("getAllCar")
//    @Transactional
//    @LogAnnotation(module = "Car",operation = "Get")
//    @ApiOperation("获取全部车辆信息")
//    public RespBean getAllCar() {
//
//        Long uid = iUserService.getUid();
//
////        List<Car> cars = iCarService.getAllCarByUid(1473527123812581377L);
//        List<Car> cars = iCarService.getAllCarByUid(uid);
//        return RespBean.ok("success and all cars are", cars);
//    }



    @GetMapping("getAllCar")
    @Transactional
    @LogAnnotation(module = "Car",operation = "Get")
    @ApiOperation("获取页面车辆信息及驾驶信息")
    public RespBean getAllCar(Long page, Long pageSize) {

        Long uid = iUserService.getUid();

//        List<Car> cars = iCarService.getAllCarByUid(1473527123812581377L);
        List<Car> cars = iCarService.getAllCarByUid(uid);



        Page<Car> pageInfo = new Page<>(page,pageSize);

        LambdaQueryWrapper<Car> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Car::getMasterId, uid);

        iCarService.page(pageInfo,queryWrapper);

        List<Car> records = pageInfo.getRecords();

        List<CarDto> carDtos = records.stream().map((item) ->{
            CarDto carDto = new CarDto();

            LambdaQueryWrapper<DrivingInformation> queryWrapper2 = new LambdaQueryWrapper<>();
            queryWrapper2.eq(DrivingInformation::getCarId, item.getId());

            List<DrivingInformation> orderDetailList = iDrivingInformationService.list(queryWrapper2);
            BeanUtils.copyProperties(item, carDto);
            carDto.setDrivingInformations(orderDetailList);
            return carDto;

        }).collect(Collectors.toList());

        Page<CarDto> dtoPage = new Page<>(page,pageSize);
        BeanUtils.copyProperties(pageInfo, dtoPage,"records");
        dtoPage.setRecords(carDtos);


        return RespBean.ok("success and all cars are", dtoPage);
    }



    /**
     * 根据car id 删除
     */
    @PostMapping("deleteCarByIds")
    @LogAnnotation(module = "Car",operation = "Delete")
    @ApiOperation("根据name删除Brand")
    @ApiImplicitParam(name = "Ids", value = "待删除ID列表", required = true)
    public RespBean deleteBrandById(@RequestBody List<Integer> Ids) {
        iCarService.deleteCar(Ids);
        return RespBean.ok("Batch delete success");
    }


    /**
     * 绑定设备
     */
    @PostMapping("bandEquipment")
    @LogAnnotation(module = "Car",operation = "Get")
    @ApiOperation("绑定设备")
    @ApiImplicitParam(name = "c",value = "车辆对象",required = true)
    public RespBean bandEquipment(@RequestBody Car c) {
        QueryWrapper<Car> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", c.getId());
        iCarService.update(c, queryWrapper);
        return RespBean.ok("success", c);
    }
}
