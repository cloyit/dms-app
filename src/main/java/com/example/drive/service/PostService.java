package com.example.drive.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.drive.entity.Post;
import java.util.List;

/**
 * 帖子服务层接口
 */
public interface PostService extends IService<Post> {

  /**
   * 修改帖子审核状态
   * @param status
   * @param ids 帖子id列表
   * @return
   */
  Boolean updatePostStatus(Integer status, List<Long> ids);
}
