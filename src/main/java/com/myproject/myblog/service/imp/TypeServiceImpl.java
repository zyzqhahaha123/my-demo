package com.myproject.myblog.service.imp;

import com.myproject.myblog.NotFoundException;
import com.myproject.myblog.dao.TypeRepository;
import com.myproject.myblog.po.Type;
import com.myproject.myblog.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: my-blog
 * @description:
 * @author: zhan
 * @create: 2020-02-27 10:30
 */
@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeRepository typeRepository;

    @Transactional //事务注解
    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    @Transactional
    @Override
    public Type selectType(Long id) {
        return typeRepository.findOne(id);
    }

    @Transactional
    @Override
    public Type selectTypeByName(String name) {
        return typeRepository.findByName(name);
    }

    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {//在使用Pageable的时,使用 findAll()方法，
        return typeRepository.findAll(pageable);   //Spring data JPA会自动把查出来的数据封装成Page<Type> 类型的对象
    }

    @Override
    @Transactional
    public List<Type> listType(Integer size) {
        //给Pageable定义查询参数
        Sort sort = new Sort(Sort.Direction.DESC, "blogs.size");//这里的blogs.size 是指type属性中的blossize
        Pageable pageable = new PageRequest(0,size , sort);
        return typeRepository.findTop(pageable);
    }

    @Transactional
    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        //先判断是否有 这个 id 的数据
        Type t = typeRepository.findOne(id);
        if( t == null){
            throw new NotFoundException("不存在该条数据");
        }
        BeanUtils.copyProperties(type,t);//把type 里面的值 复制到 t 里面来，省的去一个个的set、get
        return typeRepository.save(t); //因为t里面有id，所以这里它进行的是更新操作
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        typeRepository.delete(id);
    }
}
