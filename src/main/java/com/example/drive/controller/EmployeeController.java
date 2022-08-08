package com.example.drive.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.drive.common.R;
import com.example.drive.entity.Employee;
import com.example.drive.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 员工功能模块控制器
 */
@Slf4j
@RestController
@RequestMapping("/employee")
@Api(tags="员工信息管理相关接口")
public class EmployeeController {
    //注入服务接口实现类对象，用于调用服务方法，完成请求
    @Autowired
    private EmployeeService employeeService;
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        //1.将页面提交的密码password进行md5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //2.根据页面提交的用户名username查询数据库
        //创建MyBatis-Plus的查询包装器（通用结构）
        final LambdaQueryWrapper<Employee> queryWrapper =  new LambdaQueryWrapper<>();
        //where username = 'admin'
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        final Employee emp = employeeService.getOne(queryWrapper);

        //3.如果没有查询到则返回登录失败
        if(emp == null)
        {
            return R.error("登录失败");
        }
        //4.密码比对，如不一致则返回失败
        if(!emp.getPassword().equals(password)){
            return R.error("登录失败");
        }
        //5.查看员工状态，如果为已禁用，则返回员工已禁用结果
        if(emp.getStatus() == 0)
        {
            return R.error("账号已禁用");
        }
        //6.登录成功，将员工id存入session并返回登录成功结果
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        //退出登录要清除session中与用户相关的信息
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    @PostMapping
    @ApiOperation(value = "新增员工接口")
    public R<String> save(HttpServletRequest request,@RequestBody Employee employee)
    {
        log.info("新增员工，员工信息:{}",employee.toString());
        //1.设置初始化密码为123456，密码用MD5加密处理
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));


        //4.调用服务方法保存员工数据
        employeeService.save(employee);
        return R.success("新增员工成功");
    }

    /*分页查询员工信息*/
    @GetMapping("/page")
    @ApiOperation(value = "分页查询员工信息接口")
    public R<Page> page(int page, int pageSize, String name){
        log.info("page={},pageSize={},name={}",page,pageSize,name);
        Page pageInfo=new Page(page,pageSize);

        final LambdaQueryWrapper<Employee> queryWrapper= new LambdaQueryWrapper<>();

        queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);

        queryWrapper.orderByDesc(Employee::getUpdateTime);

        employeeService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }

    /*根据id修改员工信息，可以修改任何信息——一个通用的员工信息相应处理方法
    * 要修改的目标是什么，根据传入的Employee对象中的属性设置情况决定。
    */
    @PutMapping
    @ApiOperation(value = "修改员工信息接口")
    public R<String> update(HttpServletRequest request,@RequestBody Employee employee)
    {
        log.info(employee.toString());

        employeeService.updateById(employee);

        return R.success("员工信息修改成功");
    }

    /*根据ID查询员工信息*/
    @GetMapping("/{id}")
    @ApiOperation(value = "查询员工信息接口")
    public R<Employee> getById(@PathVariable Long id){
        log.info("根据id查询员工信息....");
        final Employee employee=employeeService.getById(id);
        if(employee!=null){
            return R.success(employee);
        }
        return R.error("没有查询到对应员工信息");
    }

}
