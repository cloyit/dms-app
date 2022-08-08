package com.example.dmsapp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dmsapp.entity.Post;
import com.example.dmsapp.mapper.PostMapper;
import com.example.dmsapp.service.PostService;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {
}
