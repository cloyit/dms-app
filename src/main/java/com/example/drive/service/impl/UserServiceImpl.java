package com.example.drive.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.drive.entity.User;
import com.example.drive.entity.UserHealth;
import com.example.drive.mapper.UserMapper;
import com.example.drive.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhulu
 * @since 2021-12-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public void register(User u) {
        userMapper.insert(u);
    }

    @Override
    public void perfectInformation(User u) {
        UpdateWrapper<User> wrapper = new UpdateWrapper<User>();
        wrapper.eq("uid",u.getUid());
        userMapper.update(u,wrapper);
    }



    @Override
    public Long getUid() {
        String userName =(String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq("tel",userName);
        User u = userMapper.selectOne(queryWrapper);
        return u.getUid();
    }

    @Override
    public String getEmergencyNumber() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq("uid",getUid());
        User u = userMapper.selectOne(queryWrapper);
        return u.getEmergencyNumber();
    }
}
