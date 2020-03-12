package com.myproject.myblog.dao;

import com.myproject.myblog.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

/**
 * @program: my-blog
 * @description: 自己定义的接口 必须继承JpaRepository 接口必须继承类型参数化为实体类型和实体类中的Id类型的Repository
 * @author: zhan
 * @create: 2020-02-27 10:31
 */
public interface TypeRepository extends JpaRepository<Type , Long> {

    Type findByName(String name);

    @Query("select t from Type t ")//自定义查询语句
    List<Type> findTop(Pageable pageable);

}
