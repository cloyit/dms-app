package com.example.drive.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.drive.common.R;
import com.example.drive.entity.Post;
import com.example.drive.service.PostService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
@Slf4j
/**
 * 帖子相关处理接口
 */
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

    /**
     * 管理端审核帖子
     * @param status
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public R<String> updateStatus(@PathVariable Integer status, @RequestParam List<Long> ids){
        Boolean updateStatus = postService.updatePostStatus(status,ids);
        return updateStatus ? R.success("帖子状态修改成功"):R.error("帖子状态修改失败");
    }

    /**
     * 管理员端分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        log.info("page={},pageSize={},name={}",page,pageSize,name);
        //构造分页构造器
        Page pageInfo = new Page(page,pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Post> queryWrapper = new LambdaQueryWrapper<Post>();
        //添加过滤条件 如果content参数值不为空则构造该查询条件 可以构造多个
        //content like '%content%'
        queryWrapper.like(StringUtils.isNotEmpty(name),Post::getContent,name);
        //添加排序条件
        queryWrapper.orderByDesc(Post::getCreateTime);
        //执行查询
        postService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

}
