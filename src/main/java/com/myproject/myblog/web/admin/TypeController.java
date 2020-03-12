package com.myproject.myblog.web.admin;

import com.myproject.myblog.po.Type;
import com.myproject.myblog.service.TypeService;
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

import javax.validation.Valid;


/**
 * @program: my-blog
 * @description:
 * @author: zhan
 * @create: 2020-02-27 11:21
 */
@RequestMapping("/admin")
@Controller
public class TypeController {

    @Autowired
    private TypeService typeService;


    @GetMapping("/listType")
    public String listType(@PageableDefault(size = 3,sort = "id",direction = Sort.Direction.DESC)
    //@PageableDefault 是给分页定义一些属性,size 按多少来进行分页,sort 排序的字段,direction = Sort.Direction.DESC 按降序来排
                                   Pageable pageable, Model model){//分页
        //把分页的结果放到page中，然后添加到 model 里面，从而传到前台
        model.addAttribute("page",typeService.listType(pageable));
        return "admin/typesManager";
    }

    //修改按钮
    @GetMapping("/{id}/editInput") //传递一个id参数过来
    public String editInput(@PathVariable Long id, Model model){
        //@PathVariable是用来赋予请求url中的动态参数，即：将请求URL中的模板变量映射到接口方法的参数上
        model.addAttribute("type", typeService.selectType(id));// 参数： 对象，封装的值
        return "admin/typesInput";
    }

    //新增按钮
    @GetMapping("/addInput")
    public String addInput(Model model){
        model.addAttribute("type",new Type());//目的为了保证前端可以拿到type，使其 可以和修改 共用一个页面
        return "admin/typesInput";
    }

    //新增保存
    @PostMapping("/saveOne") //@Valid 表示要校验该Type   BindingResult 接受校验的结果
    public String saveOne(@Valid Type type, BindingResult result, RedirectAttributes attributes){//因为是 redirect跳转,所以传递message只能用RedirectAttributes
        Type type1 = typeService.selectTypeByName(type.getName());
        //判断name是否有重复的
        if ( type1 != null ){
            result.rejectValue("name","nameError","不能添加重复的分类");
            //验证的值，验证结果，返回的消息
        }

        //判断校验结果中是否有错误
        if( result.hasErrors()){
            return "admin/typesInput"; //有错则还是跳转到那个新增的页面
        }
        Type t = typeService.saveType(type);
        if ( t == null){
            attributes.addFlashAttribute("message","新增失败");
        }else {
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/listType";
    }


    //修改保存
    @PostMapping("/editSave/{id}") //@Valid 表示要校验该Type   BindingResult 接受校验的结果
    public String saveOne(@Valid Type type, BindingResult result,@PathVariable Long id, RedirectAttributes attributes){//因为是 redirect跳转,所以传递message只能用RedirectAttributes
        Type type1 = typeService.selectTypeByName(type.getName());
        //判断name是否有重复的
        if ( type1 != null ){
            result.rejectValue("name","nameError","不能添加重复的分类");
            //验证的字段，验证结果，返回的消息
        }

        //判断校验结果中是否有错误
        if( result.hasErrors()){
            return "admin/typesInput"; //有错则还是跳转到那个新增的页面
        }
        Type t = typeService.updateType(id,type);
        if ( t == null){
            attributes.addFlashAttribute("message","修改失败");
        }else {
            attributes.addFlashAttribute("message","修改成功");
        }
        return "redirect:/admin/listType";
    }


    //删除按钮
    @GetMapping("/{id}/deleteType")
    public String deleteType(@PathVariable Long id, RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/listType";
    }

}
