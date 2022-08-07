package com.example.drive.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.drive.entity.DrivingInformation;
import com.example.drive.entity.UserHealth;
import com.example.drive.mapper.DrivingInformationMapper;
import com.example.drive.mapper.UserHealthMapper;
import com.example.drive.mapper.UserMapper;
import com.example.drive.service.IUserHealthService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.drive.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhulu
 * @since 2021-12-22
 */
@Service
public class UserHealthServiceImpl extends ServiceImpl<UserHealthMapper, UserHealth> implements IUserHealthService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    UserHealthMapper userHealthMapper;
    @Autowired
    IUserService iUserService;
    @Autowired
    DrivingInformationMapper drivingInformationMapper;

    @Override
    @Transactional
    public List<List<UserHealth>> getHealthByTime(LocalDateTime beginTime, LocalDateTime endTime) {
        QueryWrapper<UserHealth> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", iUserService.getUid());
        queryWrapper.between("time", beginTime, endTime);
        List<UserHealth> UserHealthList = userHealthMapper.selectList(queryWrapper);

        List<List<UserHealth>> result = new ArrayList<>();
        //拆分数据,设置标识，循环遍历
        int flag = 0;
        for (UserHealth userHealth : UserHealthList) {
            if (flag == userHealth.getTime().getDayOfYear()) {
                result.get(result.size() - 1).add(userHealth);
            } else {
                //新建并且更新flag
                flag = userHealth.getTime().getDayOfYear();
                List<UserHealth> r = new ArrayList<>();
                r.add(userHealth);
                result.add(r);
            }
        }
        return result;
    }

    @Override
    public void uploadHealth(UserHealth userHealth) {
        userHealthMapper.insert(userHealth);
    }

    @Override
    public void uploadDriving(DrivingInformation drivingInformation) {
        drivingInformationMapper.insert(drivingInformation);
    }

    @Override
    public List<List<UserHealth>> getLastUserHealth(Long uid) {

        QueryWrapper<UserHealth> queryWrapper = new QueryWrapper<>();

        List<UserHealth> userHealth = userHealthMapper.selectList(queryWrapper);
        List<UserHealth> userHealthList = userHealth;
        if (userHealth.size() > 15) {
            userHealthList = userHealth.subList(userHealth.size() - 15, userHealth.size() - 1);
        }

        List<List<UserHealth>> result = new ArrayList<List<UserHealth>>();
        //拆分数据,设置标识，循环遍历
        int flag = 0;
        for (UserHealth health : userHealthList) {
            if (flag == health.getTime().getDayOfYear()) {
                result.get(result.size() - 1).add(health);
            } else {
                //新建并且更新flag
                flag = health.getTime().getDayOfYear();
                List<UserHealth> r = new ArrayList<>();
                r.add(health);
                result.add(r);
            }
        }
        return result;
    }
}

