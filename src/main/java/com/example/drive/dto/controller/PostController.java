package com.example.drive.dto.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.drive.common.R;
import com.example.drive.entity.Post;
import com.example.drive.service.PostService;
import com.example.drive.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
@Slf4j
public class PostController {
    @Autowired
    private PostService postService;

    /**
     * 新增帖子
     */
    @PostMapping
    public R<String> save(@RequestBody Post post) {
        log.info(post.toString());

        postService.save(post);

        return R.success("新增帖子成功");
    }

    /**
     * 用户端社区功能 根据类型id查询帖子信息
     * */
    @GetMapping("/list")
    public R<List<Post>> list(Post post){
        LambdaQueryWrapper<Post> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(post.getCategory()!=null,Post::getId,post.getCategory());
        queryWrapper.eq(post.getStatus()!=null,Post::getStatus,post.getStatus());
        queryWrapper.orderByDesc(Post::getCreateTime);

        final List<Post> postList = postService.list(queryWrapper);
        return R.success(postList);
    }

    /**
     * 用户端展现帖子内容详情
     * */
    @GetMapping("/post/{postId}")
    public R<Post> get(@PathVariable Long postId){
        LambdaQueryWrapper<Post> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(postId!=null, Post::getId,postId);
        Post post=postService.getOne(queryWrapper);
        return R.success(post);
    }
}
