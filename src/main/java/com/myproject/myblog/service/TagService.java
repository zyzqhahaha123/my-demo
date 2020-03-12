package com.myproject.myblog.service;

import com.myproject.myblog.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {

    //新增标签
    Tag saveTag(Tag tag);

    //修改标签
    Tag editTag(Long id,Tag tag);

    //删除标签
    void deleteTag(Long id);

    //分页查询  返回的是一个分页对象
    Page<Tag> listTag(Pageable pageable);

    //获取所有tag
    List<Tag> listTag();
    //查询tag集合
    List<Tag> listTag(String ids);//这个ids是前端传过来的一串 id 字符串，用 , 进行隔开的
    //首页标签
    List<Tag> listTagTop(Integer size);

    //单条查询
    Tag selectTagById(Long id);

    Tag selectTagByName(String name);



}
