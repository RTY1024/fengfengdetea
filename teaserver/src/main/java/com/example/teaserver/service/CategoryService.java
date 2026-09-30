package com.example.teaserver.service;

import com.example.teaserver.entity.Category;
import com.example.teaserver.mapper.CategoryMapper;
import com.example.teaserver.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ProductMapper productMapper;

    public List<Category> list() {
        return categoryMapper.getAll();
    }

    // 新增分类
    public String add(String name) {
        if (name == null || name.trim().isEmpty()) return "分类名不能为空";
        name = name.trim();
        if (categoryMapper.getByName(name) != null) return "分类已存在";
        Integer maxSort = categoryMapper.getMaxSort();
        int sort = (maxSort == null ? 0 : maxSort) + 1;
        Category c = new Category();
        c.setName(name);
        c.setSort(sort);
        categoryMapper.insert(c);
        return null;
    }

    // 删除分类：把该分类下的商品移到"本店热销"
    public boolean delete(Integer id) {
        Category c = categoryMapper.getById(id);
        if (c == null) return false;
        if ("本店热销".equals(c.getName())) return false;  // 本店热销不能删
        // 商品转移到本店热销
        productMapper.updateCategory(c.getName(), "本店热销");
        // 删除分类
        categoryMapper.delete(id);
        return true;
    }

    // 供 ProductService 调用：分类不存在则自动新建
    public void ensureExists(String name) {
        if (name == null || name.trim().isEmpty()) return;
        name = name.trim();
        if (categoryMapper.getByName(name) != null) return;
        Integer maxSort = categoryMapper.getMaxSort();
        int sort = (maxSort == null ? 0 : maxSort) + 1;
        Category c = new Category();
        c.setName(name);
        c.setSort(sort);
        categoryMapper.insert(c);
    }
}