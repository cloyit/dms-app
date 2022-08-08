package com.example.dmsapp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.dmsapp.entity.Post;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostMapper extends BaseMapper<Post> {
}
