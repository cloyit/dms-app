package com.example.drive.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.drive.entity.Post;
import com.example.drive.mapper.PostMapper;
import com.example.drive.service.PostService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

  /**
   * 更新帖子状态
   * @param status 要更新为的状态
   * @param ids 帖子id列表
   * @return
   */
  @Override
  public Boolean updatePostStatus(Integer status, List<Long> ids) {
    List<Post> postList = ids.stream().map((item)->{
      Post post = this.getById(item);
      post.setStatus(status);
      return post;
    }).collect(Collectors.toList());
    return this.updateBatchById(postList);
  }
}
