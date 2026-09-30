package com.example.teaserver.service;

import com.example.teaserver.entity.Product;
import com.example.teaserver.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryService categoryService;

    public List<Product> list(String category) {
        if (category == null || category.isEmpty() || category.equals("本店热销")) {
            return productMapper.getAll();
        }
        return productMapper.getByCategory(category);
    }

    public List<Product> listAll() {
        return productMapper.getAllForAdmin();
    }

    public Product detail(Integer id) {
        return productMapper.getById(id);
    }

    public void add(String name, Double price, String image, String description, String category) {
        Product p = new Product();
        p.setName(name);
        p.setPrice(price);

        if (image == null || image.trim().isEmpty()) {
            p.setImage("default_tea");
        } else {
            p.setImage(image.trim());
        }

        p.setDescription(description == null ? "" : description);

        String cat = (category == null || category.isEmpty()) ? "本店热销" : category.trim();
        p.setCategory(cat);
        p.setStatus(1);
        productMapper.insert(p);

        // 逐个分类确保存在（支持逗号分隔的多分类）
        for (String c : cat.split(",")) {
            categoryService.ensureExists(c.trim());
        }
    }

    // ★ 更新：支持 image + 多分类
    public void update(Integer id, String name, Double price, String description, String category, String image) {
        Product p = new Product();
        p.setId(id);
        p.setName(name);
        p.setPrice(price);
        p.setDescription(description == null ? "" : description);

        String cat = (category == null || category.trim().isEmpty()) ? "本店热销" : category.trim();
        p.setCategory(cat);

        if (image == null || image.trim().isEmpty()) {
            Product old = productMapper.getById(id);
            if (old != null && old.getImage() != null && !old.getImage().isEmpty()) {
                p.setImage(old.getImage());
            } else {
                p.setImage("default_tea");
            }
        } else {
            p.setImage(image.trim());
        }
        productMapper.update(p);

        // 逐个分类确保存在
        for (String c : cat.split(",")) {
            categoryService.ensureExists(c.trim());
        }
    }

    public void delete(Integer id) {
        productMapper.delete(id);
    }

    public void setStatus(Integer id, Integer status) {
        productMapper.updateStatus(id, status);
    }
}