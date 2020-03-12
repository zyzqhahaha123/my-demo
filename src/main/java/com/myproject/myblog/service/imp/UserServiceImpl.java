package com.myproject.myblog.service.imp;

import com.myproject.myblog.dao.UserRepository;
import com.myproject.myblog.po.User;
import com.myproject.myblog.service.UserService;
import com.myproject.myblog.util.MD5Uitls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: my-blog
 * @description:
 * @author: zhan
 * @create: 2020-02-26 11:00
 */
@Service
public class UserServiceImpl implements UserService {

    //UserRepository继承了 JpaRepository接口，所以 UserRepository可以直接调用一些数据库操作方法，不用写实现
    @Autowired
    private UserRepository userRepository;

    @Override
    public User checUser(String userName, String password) {
        //这里把加密后的密码再拿来查询
        User user = userRepository.findByUsernameAndPassword(userName, MD5Uitls.code(password));

        return user;
    }
}
