package com.myproject.myblog.web;

import com.myproject.myblog.po.Tag;
import com.myproject.myblog.service.BlogService;
import com.myproject.myblog.service.TagService;
import com.myproject.myblog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @program: my-blog
 * @description:
 * @author: zhan
 * @create: 2020-03-06 13:41
 */
@Controller
public class TagShowController {

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;

    //分类页面展示
    @GetMapping("/tags/{id}")
    public String tags(@PageableDefault(size = 3, sort = "updateTime", direction = Sort.Direction.DESC) Pageable pageable,
                        @PathVariable Long id, Model model){
        //排序好了的tags
        List<Tag> tags = tagService.listTagTop(10000);
        //确定是否是进入分类首页
        if ( id == -1){//如果等于-1,则证明是第一次进入分类首页
            //第一次分类则是排序好了的tags集合中的第一个元素,从而得到tagId，来进行查询
            id = tags.get(0).getId();
        }
        model.addAttribute("tags", tags);
        model.addAttribute("page", blogService.listBlog(id,pageable));//根据BlogQuery中的tagId去查询 blogs
        //用来判断选取的哪一个id,来进行展示选中状态,传到前端
        model.addAttribute("activeTagId",id);
        return "tags";
    }

}
