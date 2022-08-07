package com.example.drive.service;

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

public interface IUserService  {
    void register(User u);
    void perfectInformation(User u);
    Long getUid();
    String getEmergencyNumber();
    User getUser();
    Picture uploadPortrait(MultipartFile file);
}
