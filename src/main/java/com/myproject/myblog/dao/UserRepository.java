package com.myproject.myblog.dao;

import com.myproject.myblog.po.User;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * 在Spring Data JPA中使用repository来进行对数据库层操作（作用相当于dao层）。
 * 直接使用repository对象调用已经实现好的数据层操作方法进行CRUD、分页、排序等操作
 * 我们自己定义的repository接口 必须 继承 JpaRepository 才可以具有这些功能，但这仅限于简单的数据操作
 */
public interface UserRepository extends JpaRepository<User, Long> {

    //要遵循 JpaRepository 的命名规则，才可以调用
    User findByUsernameAndPassword(String username,String password);
}
