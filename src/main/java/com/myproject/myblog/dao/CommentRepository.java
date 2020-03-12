package com.myproject.myblog.dao;

import com.myproject.myblog.po.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//评论
public interface CommentRepository extends JpaRepository<Comment, Long> {

    //查询评论列表
    List<Comment> findByBlogIdAndParentCommentNull(Long id, Sort sort);


}
