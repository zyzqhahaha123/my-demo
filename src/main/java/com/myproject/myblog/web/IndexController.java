package com.myproject.myblog.web;

import com.myproject.myblog.NotFoundException;
import com.myproject.myblog.service.BlogService;
import com.myproject.myblog.service.TagService;
import com.myproject.myblog.service.TypeService;
import com.myproject.myblog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @program: my-blog
 * @author: zhan
 * @create: 2020-02-24 15:31
 */
@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

//    @GetMapping("/{id}/{name}") //@GetMapping("/{id}/{name}" 在url里面获取参数时, 接收参数的时候前面要加上注解 @PathVariable
    @GetMapping("/")
    public String index(@PageableDefault(size = 3, sort = "updateTime", direction = Sort.Direction.DESC) Pageable pageable,
                        Model model){
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types",typeService.listType(6));
        model.addAttribute("tags",tagService.listTagTop(8));
        model.addAttribute("recommendBlogs",blogService.listRecommendBlogTop(8));
//      public String index(@PathVariable int id, @PathVariable String name){
//        int i = 9/0;
//        String blog = null;
//        if (blog == null){
//            throw  new NotFoundException("博客找不到");
//        }
//        System.out.println("--------- index ---------");
//        System.out.println("id:" + id  + ",name:" + name);
        return "index";
    }

    //全局查询
    @PostMapping("/search")
    public String search(@PageableDefault(size = 4, sort = "updateTime", direction = Sort.Direction.DESC) Pageable pageable,
                         String query, Model model){
        model.addAttribute("page",blogService.listBlog("%" + query + "%", pageable));
        model.addAttribute("query",query);
        return "search";
    }



    @GetMapping("/blog/{id}")
    public String blogs(@PathVariable Long id, Model model){
        model.addAttribute("blog",blogService.getAndConvert(id));
        return "blogs";
    }

    
}
