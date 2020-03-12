package com.myproject.myblog.web.admin;

import com.myproject.myblog.po.Tag;
import com.myproject.myblog.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @program: my-blog
 * @description:
 * @author: zhan
 * @create: 2020-02-28 11:20
 */
@RequestMapping("/admin")
@Controller
public class TagController {

    @Autowired
    private TagService tagService;

    //跳转标签管理后台
    @GetMapping("/listTag")
    public String listTag(@PageableDefault( size = 3, sort = "id" , direction = Sort.Direction.DESC) Pageable pageable,
                          Model model){
        model.addAttribute("page", tagService.listTag(pageable));
        return "admin/tagsManager";
    }

    //新增按钮
    @GetMapping("/tagsInput")
    public String tagsInput(Model model){//新增的时候，需要向前台传一个type
        model.addAttribute("tags",new Tag());
        return "admin/tagsInput";
    }

    //新增保存
    @PostMapping("/addTag")
    public String addTag(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){
        /**
         * 保存前先进行验证
         * 1.名字是否重复，有问题则发送反正结果和消息
         * 2.验证中是否出现错误，出现错误则还是调到新增页面
         * 3.都没问题则正常提交，1.提交成功，2.提交失败
         */
        Tag t = tagService.selectTagByName(tag.getName());
        if ( t != null){
            result.rejectValue("name","error","列表名称已存在");//这里的error是自己定义的错误
        }
        if(result.hasErrors()){
            return "admin/tagsInput";
        }

        Tag tag1 = tagService.saveTag(tag);
        if ( tag1 == null){
            attributes.addFlashAttribute("message","新增失败");
            return "redirect:/admin/tagsInput";
        }else {
            attributes.addFlashAttribute("message","新增成功");
            return "redirect:/admin/listTag";
        }

    }


    //修改按钮
    @GetMapping("/editTag")
    public String editTag(HttpServletRequest request, Model model){
            //通过id来找到 数据 ，把数据封装到 model中传到前台
        Tag tag=tagService.selectTagById(Long.parseLong(request.getParameter("id")));
        model.addAttribute("tag",tag);
        return "admin/tagsInput";
    }

    //修改保存
    @PostMapping("/editSaveTag/{id}")
    public String editSave(@PathVariable Long id, @Valid Tag tag,BindingResult result, RedirectAttributes attributes){
        /**
         * 保存前先进行验证
         * 1.名字是否重复，有问题则发送反正结果和消息
         * 2.验证中是否出现错误，出现错误则还是跳到新增页面
         * 3.都没问题则正常提交，1.提交成功，2.提交失败
         */
        Tag t1 = tagService.selectTagByName(tag.getName());
        if ( t1 != null){
            result.rejectValue("name", "nameError", "不能添加重复的标签");
        }
        if (result.hasErrors()){
            return "admin/tagsInput";
        }

        Tag t = tagService.saveTag(tag);
        if ( t == null){
            attributes.addFlashAttribute("message","修改失败");
            return "redirect:/admin/tagsInput";
        }else {
            attributes.addFlashAttribute("message","修改成功");
            return "redirect:/admin/listTag";
        }
    }


    //删除
    @GetMapping("/deleteTag/{id}")
    public String deleteTag(@PathVariable Long id , RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/listTag";
    }

}
