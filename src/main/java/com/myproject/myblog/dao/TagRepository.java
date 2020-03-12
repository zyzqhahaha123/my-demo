package com.myproject.myblog.dao;

import com.myproject.myblog.po.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag , Long> {

    Tag findByName(String name);//通过name查询

    @Query("SELECT t from Tag t")
    List<Tag> findTop(Pageable pageable);
}
