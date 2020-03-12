package com.myproject.myblog.po;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: my-blog
 * @description:
 * @author: zhan
 * @create: 2020-02-25 16:16
 */
@Entity
@Table(name = "t_type")
public class Type{

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "分类名称不能为空")//在该name为空时，会抛出一个异常
    private String name;

    // One的一端作为关系被维护端，  即 一对多，多的一方进行维护
    @OneToMany(mappedBy = "type")  //表示type 被维护 了
    private List<Blog> blogs=new ArrayList<>();



    public Type() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
