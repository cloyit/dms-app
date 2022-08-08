package com.example.drive.service.impl;

import com.example.drive.entity.Picture;
import com.example.drive.entity.User;
import com.example.drive.mapper.PictureMapper;
import com.example.drive.service.IPictureService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.drive.utills.FastDFSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhulu
 * @since 2022-03-02
 */
@Service
public class PictureServiceImpl extends ServiceImpl<PictureMapper, Picture> implements IPictureService {

    @Autowired
    PictureMapper pictureMapper;


    @Override
    public Picture uploadPortrait(MultipartFile file, String description) {
        Picture picture = new Picture();
        picture.setDescription(description);

        String[] result = null;
        int num = pictureMapper.selectCount(null);

//        try {
//            result = FastDFSUtil.upload(file.getBytes(), "" + (num + 1) + ".jpg");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        picture.setUrl("http://47.102.99.215/" + result[0] + "/" + result[1]);
        picture.setHref("http://47.102.99.215/" + result[0] + "/" + result[1]);

        pictureMapper.insert(picture);
        return picture;
    }
}
