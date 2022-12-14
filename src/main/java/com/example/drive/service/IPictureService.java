package com.example.drive.service;

import com.example.drive.entity.Picture;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhulu
 * @since 2022-03-02
 */
public interface IPictureService extends IService<Picture> {

    Picture uploadPortrait(MultipartFile file, String description);
}
