package com.myproject.myblog.service;

import com.myproject.myblog.po.Comment;

import java.util.List;

public interface CommentService {

    //查询评论信息列表
    List<Comment> listCommentByBlogId(Long blogId);

    //保存评论
    Comment saveComment(Comment comment);

}
