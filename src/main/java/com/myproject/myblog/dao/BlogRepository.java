package com.myproject.myblog.dao;

import com.myproject.myblog.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//参数类型，主键类型
//JpaSpecificationExecutor
public interface BlogRepository extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {

    Blog findByTitle(String title);

    @Query("select t from Blog t where t.recommend=true")
    List<Blog> findTop(Pageable pageable);

    //全局查询
    @Query("select t from Blog t where t.title like ?1 or t.content like ?1")
    Page<Blog> findByQuery(String query, Pageable pageable);


    //更新浏览次数
    @Modifying //修改必须加上这个注解
    @Query("update Blog b set b.views = b.views+1 where b.id = ?1")
    int updateViews(Long id);


    //首页归档，查询年份   function( date_formate函数, 字段， 格式 )   在jpq语句中使用函数 都要用 function() 这样来调用
    @Query("select function('date_format',t.updateTime,'%Y') as Year from Blog t group by function('date_format',t.updateTime,'%Y') order by Year desc ")
    List<String> findGroupYear();

    //通过年份查询Blog
    @Query("select t from Blog  t where function('date_format',t.updateTime,'%Y') = ?1")
    List<Blog> findByYear(String year);

}
