package com.myproject.myblog.service;

import com.myproject.myblog.po.User;

public interface UserService {

    User checUser(String userName, String password);

}
