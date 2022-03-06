package com.example.drive.driveController;


import com.example.drive.entity.Car;
import com.example.drive.response.RespBean;
import com.example.drive.service.ICarService;
import com.example.drive.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
public class CarController {
    @Autowired
    ICarService iCarService;
    @Autowired
    IUserService iUserService;

    /**
     * 根据当前用户增加车
     * @param car
     * @return
     */
    @PostMapping
    public RespBean addCar(@RequestBody Car car) {
        iCarService.addCar(car);
        return RespBean.ok("success");
    }

    /**
     * 根据token 获取所有的车
     * @return
     */
    @PostMapping("getAllCar")
    public RespBean getAllCar() {
        Long uid = iUserService.getUid();
        List<Car> cars = iCarService.getAllCarByUid(uid);
        return RespBean.ok("success and all cars are",cars);
    }



}
