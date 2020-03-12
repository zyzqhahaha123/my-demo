package com.myproject.myblog.web.admin;

import com.myproject.myblog.po.User;
import com.myproject.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @program: my-blog
 * @description:
 * @author: zhan
 */
@RequestMapping("/admin")
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/loginPage")
    public String loginPage(){
        return "admin/login";
    }


    @PostMapping("/login")
    public String login(String username,
                        String password,
                        HttpSession session,
                        //重定向的穿的参数 用 redirectAttributes 来传, 不能用 Model 来传，这样才跳转之后的页面拿不
                        RedirectAttributes attributes){
        User user = userService.checUser(username,password);
        if( user != null ){
            //把登录的信息放到session中，但不要把密码放进去，这样不安全
            user.setPassword("null");
            session.setAttribute("user",user);
            return "admin/index";
        } else { //账号密码不对,重新跳转到登录页面，并 给前段页面提示
            //密码不对，则需要继续到 admin/login 页面，这里不要直接return 到页面，这样下次登录时，页面路劲会有问题
            //因此这里需要用 重定向到 admin/login 页面, 通过 路径 来调用 Controller 里面的方法的来进行跳转
            attributes.addFlashAttribute("message","账号密码有误");
            //model.addAttribute();
            return "redirect:/admin/loginPage";
        }
    }

    @GetMapping("/login")
    public String loginTest(){
        return "redirect:/admin/loginPage";
    }

    //注销登录
    @GetMapping("/logout")
    public String logout(HttpSession session){//注销登录：清空 session，重定向到登录页面
        session.removeAttribute("user");
        return "redirect:/admin/loginPage";
    }


}
