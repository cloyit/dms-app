package com.example.drive.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.drive.entity.Post;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostMapper extends BaseMapper<Post> {
}
