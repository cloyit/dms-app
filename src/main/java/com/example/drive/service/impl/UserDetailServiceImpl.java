package com.example.drive.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.drive.entity.AuthUser;
import com.example.drive.entity.User;
import com.example.drive.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Slf4j
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        /**
         * 这个类的功能是通过名字返回一个userdetail对象,这个对象用于验证
         * 调用多个dao装配user
         */
        if(username == null  || "".equals(username)){
            throw new RuntimeException("用户不能为空");
        }
        ArrayList<String> role = new ArrayList<String>();
        role.add("ROLE_use");
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tel",username);
        User u = userMapper.selectOne(queryWrapper);
        AuthUser user =  new AuthUser();
        user.setUsername(username);
        user.setPassword(u.getPassword());
        user.setRoles(role);
        user.setUid(u.getUid());
        log.info(user.getPassword());
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在!");
        }

        return user;
    }
}

