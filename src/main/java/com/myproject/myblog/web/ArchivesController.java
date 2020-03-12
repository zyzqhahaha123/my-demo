package com.myproject.myblog.web;

import com.myproject.myblog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @program: my-blog
 * @description: 归档首页
 * @author: zhan
 * @create: 2020-03-07 11:22
 */
@Controller
public class ArchivesController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/archives")
    public String archives(Model model){
        model.addAttribute("archivesMap",blogService.archivesBlog());
        model.addAttribute("blogCount",blogService.countBlog());
        return "archives";
    }

}
