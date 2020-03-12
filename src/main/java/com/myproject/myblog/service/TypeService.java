package com.myproject.myblog.service;

import com.myproject.myblog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.Table;
import java.util.List;

/**
 * 分类删除
 */
public interface TypeService {

    //保存分类
    Type saveType(Type type);

    //查询分类
    Type selectType(Long id);

    Type selectTypeByName(String name);

    //分页查询
    Page<Type> listType(Pageable pageable);//Spring Data 中的一个借口，用于构造分页查询


    //查询所有数据
    List<Type> listType();

    //首页分类
    List<Type> listType(Integer size);

    //修改分类
    Type updateType(Long id,Type type);

    //删除分类
    void deleteType(Long id);


}
