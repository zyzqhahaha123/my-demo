package com.myproject.myblog.service.imp;

import com.myproject.myblog.NotFoundException;
import com.myproject.myblog.dao.TagRepository;
import com.myproject.myblog.po.Tag;
import com.myproject.myblog.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: my-blog
 * @description: 数据层面的逻辑在这里写
 * @author: zhan
 * @create: 2020-02-28 10:36
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;


    @Override //新增
    @Transactional
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override //修改
    @Transactional
    public Tag editTag(Long id, Tag tag) {
        Tag t = tagRepository.findOne(id);
        if( t == null ){
            //判断数据库里是否有这条数据 则直接抛出异常
            throw new NotFoundException("不存在该条数据");
        }else { //有,这直接复制添加
            BeanUtils.copyProperties(tag , t);
        }
        return tagRepository.save(t);
    }

    @Override//删除
    @Transactional
    public void deleteTag(Long id) {
        tagRepository.delete(id);
    }

    @Override//分页查询
    @Transactional
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Tag selectTagById(Long id) {
        return tagRepository.findOne(id);
    }

    @Override
    @Transactional
    public Tag selectTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Transactional
    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Transactional
    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "blogs.size");//tag里面有blogs
        Pageable pageable = new PageRequest(0, size, sort);
        return tagRepository.findTop(pageable);
    }

    @Override
    public List<Tag> listTag(String ids) {//1,2,3
        return tagRepository.findAll(toLong(ids));
    }
    //把字符串id转换成Long类型的数组
    private List<Long> toLong(String ids){
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null){
            String[] s = ids.split(",");
            for (int i=0 ; i<s.length; i++){
                list.add(Long.parseLong(s[i]));
            }
        }
        return list;
    }


}
