package com.myproject.myblog.web.admin;

import com.myproject.myblog.po.Blog;
import com.myproject.myblog.po.User;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @program: my-blog
 * @description:
 * @author: zhan
 * @create: 2020-02-26 16:45
 */
@RequestMapping("/admin")
@Controller
public class BlogsController {

    private static final String LIST="admin/blogsManager";
    private static final String INPUT="admin/blogsInput";
    private static final String REDIRECT_LIST="redirect:/admin/listBlog";

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/listBlog")
    public String listBlog(@PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                           BlogQuery blog,
                           Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("blogList", blogService.listBlog(pageable , blog));
        return LIST;
    }

    @PostMapping("/listBlog/search")
    public String search(@PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                           BlogQuery blog,
                           Model model){
        model.addAttribute("blogList", blogService.listBlog(pageable , blog));
        return "admin/blogsManager :: list";
    }

    //新增按钮
    @GetMapping("/blog/input")
    public String input(Model model){
        setTypeAndTag(model);
        Blog blog = new Blog();
        model.addAttribute("blog",blog);//这里向前端传一个blog是为了可以和修改页面共用
        return INPUT;
    }
    private void setTypeAndTag(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags", tagService.listTag());
    }
    //编辑按钮
    @GetMapping("/blog/editBlog/{id}")
    public String editBlog(@PathVariable Long id, Model model){
        setTypeAndTag(model);
        Blog blog = blogService.selectBlogById(id);
        blog.init();
        model.addAttribute("blog",blog);//这里向前端传一个blog是为了可以和修改页面共用
        return INPUT;
    }


    //保存按钮
    @PostMapping("/saveBlog")
    public String saveBlog(Blog blog, RedirectAttributes attributes,HttpSession session){
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.selectType(blog.getType().getId()));//从分类中找到type，然后再存到blog中
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog b;
        if (blog.getId() == null){
            b = blogService.saveBlog(blog);
        }else {
            b = blogService.editBlog(blog.getId(),blog);
        }
        if ( b == null ){
            attributes.addFlashAttribute("message","操作失败");
        }else {
            attributes.addFlashAttribute("message","操作成功");
        }
        return REDIRECT_LIST;
    }


    //删除按钮
    @GetMapping("/blog/deleteBlog/{id}")
    public String deleteBlog(@PathVariable Long id, RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message","删除成功");
        return REDIRECT_LIST;
    }


}
