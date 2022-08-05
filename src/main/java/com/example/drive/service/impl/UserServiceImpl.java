package com.example.drive.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.drive.entity.Picture;
import com.example.drive.entity.User;
import com.example.drive.mapper.PictureMapper;
import com.example.drive.mapper.UserMapper;
import com.example.drive.service.IUserService;
import com.example.drive.utills.FastDFSUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhulu
 * @since 2021-12-22
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    PictureMapper pictureMapper;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;


    @Override
    public void register(@RequestBody User u) {
        userMapper.insert(u);
    }


    @Override
    public void perfectInformation(@RequestBody User u) {
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("uid", getUid());
        userMapper.update(u, wrapper);
    }


    /**
     * 根据token获取uid
     */
    @Override
    public Long getUid() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        log.debug("username "+userName);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tel", userName);
        User u = userMapper.selectOne(queryWrapper);
        return u.getUid();
    }


    /**
     * 根据token获取紧急联系人
     */
    @Override
    @Transactional
    public String getEmergencyNumber() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", getUid());
        User u = userMapper.selectOne(queryWrapper);
        return u.getEmergencyNumber();
    }


    @Override
    public User getUser() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", getUid());
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    @Transactional
    public Picture uploadPortrait(MultipartFile file) {
        Picture picture = new Picture();

        String[] result = null;
        int num = pictureMapper.selectCount(null);

        try {
            result = FastDFSUtil.upload(file.getBytes(), "" + (num + 1) + ".jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        picture.setUrl("http://47.102.99.215/" + result[0] + "/" + result[1]);
        picture.setHref("http://47.102.99.215/" + result[0] + "/" + result[1]);

        pictureMapper.insert(picture);

        User user = this.getUser();
        user.setHeadPortrait(picture.getHref());
        this.perfectInformation(user);
        return picture;
    }
}
