package com.example.drive.driveController;


import com.example.drive.entity.Car;
import com.example.drive.response.RespBean;
import com.example.drive.service.ICarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping
    public RespBean addCar(Car car) {
        iCarService.addCar(car);
        return RespBean.ok("success");
    }

}
