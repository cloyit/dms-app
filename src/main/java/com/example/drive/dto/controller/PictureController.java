package com.example.drive.dto.controller;

import com.example.drive.aop.LogAnnotation;
import com.example.drive.entity.Picture;
import com.example.drive.mapper.PictureMapper;
import com.example.drive.response.RespBean;
import com.example.drive.service.IPictureService;
import com.example.drive.utills.FastDFSUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhulu
 * @since 2022-03-02
 */
@RestController
@RequestMapping("/picture")
@Api(tags = "图片信息相关")
public class PictureController {
    @Autowired
    PictureMapper pictureMapper;

    @Autowired
    IPictureService pictureService;

    @PostMapping("uploadPicture")
    @LogAnnotation(module = "Picture", operation = "Get")
    @ApiOperation("上传图片")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "文件描述",name = "description",required = true),
            @ApiImplicitParam(name = "file", value = "文件", required = true)}
    )
    public RespBean uploadPicture(MultipartFile file, String description) {
        Picture picture = pictureService.uploadPortrait(file,description);
        return RespBean.ok("success", picture);
    }
}
