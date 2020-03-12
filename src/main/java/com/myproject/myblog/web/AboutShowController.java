package com.myproject.myblog.web;

import com.myproject.myblog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @program: my-blog
 * @description:
 * @author: zhan
 * @create: 2020-02-29 10:21
 */
@Controller
public class AboutShowController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/about")
    public String about(){
        return "about";
    }


    @GetMapping("/footer/newblog")
    public String newBlogs(Model model){
        model.addAttribute("newBlogs", blogService.listRecommendBlogTop(3));
        return "_fragments :: newBlogList"; //是返回 _fragments 下面的 newBlogList模板
    }

}
