package com.example.drive.dto;

import com.example.drive.entity.Car;
import com.example.drive.entity.DrivingInformation;
import lombok.Data;

import java.util.List;

@Data
public class CarDto extends Car {

    List<DrivingInformation> drivingInformations;

}
