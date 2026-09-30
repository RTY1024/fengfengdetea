package com.example.teaserver.controller;

import com.example.teaserver.common.Result;
import com.example.teaserver.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // 分类列表
    @GetMapping("/list")
    public Result list() {
        return Result.ok(categoryService.list());
    }

    // 新增分类
    @GetMapping("/add")
    public Result add(@RequestParam String name) {
        String err = categoryService.add(name);
        if (err != null) return Result.error(err);
        return Result.ok();
    }

    // 删除分类
    @GetMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        return categoryService.delete(id) ? Result.ok() : Result.error("删除失败（本店热销不能删）");
    }
}