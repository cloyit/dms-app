package com.example.drive.controller;


import com.example.drive.entity.Picture;
import com.example.drive.mapper.PictureMapper;
import com.example.drive.response.RespBean;
import com.example.drive.utills.FastDFSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhulu
 * @since 2022-03-02
 */
@RestController
@RequestMapping("/picture")
public class PictureController {
    @Autowired
    PictureMapper pictureMapper;

    @PostMapping("uploadPicture")
    public RespBean uploadPicture(MultipartFile file,String description){
        Picture picture = new Picture();
        picture.setDescription(description);


        String[] result = null;
        int num = pictureMapper.selectCount(null);

        try {
            result = FastDFSUtil.upload(file.getBytes(),""+(num+1)+".jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        picture.setUrl("http://47.102.99.215/"+result[0]+"/"+result[1]);
        picture.setHref("http://47.102.99.215/"+result[0]+"/"+result[1]);

        pictureMapper.insert(picture);

        return RespBean.ok("success",picture);


    }

}
