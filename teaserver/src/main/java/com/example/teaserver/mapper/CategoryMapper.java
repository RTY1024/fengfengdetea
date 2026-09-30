package com.example.teaserver.mapper;

import com.example.teaserver.entity.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @Select("SELECT * FROM category ORDER BY sort ASC, id ASC")
    List<Category> getAll();

    @Select("SELECT * FROM category WHERE id = #{id}")
    Category getById(Integer id);

    @Select("SELECT * FROM category WHERE name = #{name}")
    Category getByName(String name);

    @Select("SELECT MAX(sort) FROM category")
    Integer getMaxSort();

    @Insert("INSERT INTO category(name, sort) VALUES(#{name}, #{sort})")
    int insert(Category c);

    @Delete("DELETE FROM category WHERE id = #{id}")
    int delete(Integer id);
}