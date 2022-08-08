package com.example.drive.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.drive.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
