package com.myproject.myblog.web;

import com.myproject.myblog.po.Comment;
import com.myproject.myblog.po.User;
import com.myproject.myblog.service.BlogService;
import com.myproject.myblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

/**
 * @program: my-blog
 * @description:
 * @author: zhan
 * @create: 2020-03-04 13:28
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    //取评论的头像
    @Value("${comment.avatar}")
    private String avatar;

    //评论区的列表
    @GetMapping("/comments/{blogId}")
    public String comment(@PathVariable Long blogId, Model model){
        model.addAttribute("comments",commentService.listCommentByBlogId(blogId));
        return "blogs :: commentList"; //blogs页面下的 commentList区域，即 页面中 th:_fragment="commentList" 定义的区域
    }

    //发布或回复评论
    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session){
        //把这条评论跟blog关联起来  //通过传入记录的blog.id.来找blog,然后再把blog放到comment对象中
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.selectBlogById(blogId));
        User user = (User) session.getAttribute("user");
        if ( user != null){
            comment.setAdminComment(true);
            comment.setAvatar(user.getAvatar());
//            comment.setNickname(user.getNickname());
        }else {
            comment.setAvatar(avatar);

        }
        commentService.saveComment(comment);
        return "redirect:/comments/" + blogId;
    }

}
