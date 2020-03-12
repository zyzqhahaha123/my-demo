package com.myproject.myblog.service;

import com.myproject.myblog.po.Blog;
import com.myproject.myblog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {
    //转换markdown
    Blog getAndConvert(Long id);

    //根据ID单条查询
    Blog selectBlogById(Long id);
    //根据Name单条查询
    Blog selectBlogByTitle(String title);

    //分页查询  //这里除了分页的参数，还有上面查询的参数
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    //首页分页
    Page<Blog> listBlog(Pageable pageable);

    //标签首页，根据标签展示Blog
    Page<Blog> listBlog(Long tagId, Pageable pageable);

    //全局查询
    Page<Blog> listBlog(String query, Pageable pageable);

    //首页推荐
    List<Blog> listRecommendBlogTop(Integer size);

    //新增
    Blog saveBlog(Blog blog);

    //修改
    Blog editBlog(Long id,Blog blog);

    //删除
    void deleteBlog(Long id);


    //归档首页blog查询
    Map<String,List<Blog>> archivesBlog();

    //查询一共有多少blog
    Long countBlog();


}
