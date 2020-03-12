package com.myproject.myblog.web;

import com.myproject.myblog.po.Type;
import com.myproject.myblog.service.BlogService;
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

import java.util.List;

/**
 * @program: my-blog
 * @description:
 * @author: zhan
 * @create: 2020-03-06 13:41
 */
@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    //分类页面展示
    @GetMapping("/types/{id}")
    public String types(@PageableDefault(size = 3, sort = "updateTime", direction = Sort.Direction.DESC) Pageable pageable,
                        @PathVariable Long id, Model model){
        //排序好了的types
        List<Type> types = typeService.listType(10000);
        //确定是否是进入分类首页
        if ( id == -1){//如果等于-1,则证明是第一次进入分类首页
            //第一次分类则是排序好了的types集合中的第一个元素,从而得到typeId，来进行查询
            id = types.get(0).getId();
        }
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);
        model.addAttribute("types", types);
        model.addAttribute("page", blogService.listBlog(pageable,blogQuery));//根据BlogQuery中的typeId去查询 blogs
        //用来判断选取的哪一个id,来进行展示选中状态,传到前端
        model.addAttribute("activeTypeId",id);
        return "types";
    }

}
