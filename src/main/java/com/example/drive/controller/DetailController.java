package com.example.drive.controller;


import com.example.drive.entity.Detail;
import com.example.drive.entity.Title;
import com.example.drive.mapper.DetailMapper;
import com.example.drive.mapper.TitleMapper;
import com.example.drive.response.RespBean;
import com.example.drive.service.impl.DetailServiceImpl;
import com.example.drive.utills.FastDFSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
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
 * @since 2022-02-23
 */
@RestController
@RequestMapping("/detail")
public class DetailController {
    @Autowired
    DetailMapper detailMapper;
    @Autowired
    TitleMapper titleMapper;
    /**
     * 上传具体详情的接口
     * 将图片存储，插入数据库即可
     * @param detail
     * @param picture
     * @return
     */
    @PostMapping("uploadDetail")
    public RespBean uploadDetail(Detail detail, MultipartFile picture){
        String[] result = null;
        int num = detailMapper.selectCount(null);

        try {
            result = FastDFSUtil.upload(picture.getBytes(),detail.getType()+(num+1)+".jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        detail.setPictureUrl("http://47.102.99.215/"+result[0]+"/"+result[1]);

        detailMapper.insert(detail);

        return RespBean.ok("success",detail);
    }


}
