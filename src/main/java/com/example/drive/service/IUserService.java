package com.example.drive.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.drive.entity.Picture;
import com.example.drive.entity.User;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhulu
 * @since 2021-12-22
 */

public interface IUserService  extends IService<User> {
    void register(User u);
    void perfectInformation(User u);
    Long getUid();
    String getEmergencyNumber();
    User getUser();
    Picture uploadPortrait(MultipartFile file);
}
