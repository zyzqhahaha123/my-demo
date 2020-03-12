package com.myproject.myblog.util;

import com.myproject.myblog.po.Blog;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

/**
 * @program: my-blog
 * @description:遍历对象属性，取出属性值为null的属性，返回值 String[]
 * @author: zhan
 * @create: 2020-03-02 16:13
 */
public class NatureUitls{

    //获取对象中,属性值为Null的属性
    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for(PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            // 此处判断可根据需求修改
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
