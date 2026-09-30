package com.example.teaserver.controller;

import com.example.teaserver.common.Result;
import com.example.teaserver.entity.Product;
import com.example.teaserver.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public Result list(@RequestParam(required = false) String category) {
        return Result.ok(productService.list(category));
    }

    @GetMapping("/listAll")
    public Result listAll() {
        return Result.ok(productService.listAll());
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        Product p = productService.detail(id);
        if (p == null) return Result.error("商品不存在");
        return Result.ok(p);
    }

    @GetMapping("/add")
    public Result add(@RequestParam String name,
                      @RequestParam Double price,
                      @RequestParam(required = false) String image,
                      @RequestParam(required = false) String description,
                      @RequestParam(required = false) String category) {
        productService.add(name, price, image, description, category);
        return Result.ok();
    }

    // ★ update 加 image 参数
    @GetMapping("/update")
    public Result update(@RequestParam Integer id,
                         @RequestParam String name,
                         @RequestParam Double price,
                         @RequestParam(required = false) String description,
                         @RequestParam(required = false) String category,
                         @RequestParam(required = false) String image) {
        productService.update(id, name, price, description, category, image);
        return Result.ok();
    }

    @GetMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        productService.delete(id);
        return Result.ok();
    }

    @GetMapping("/status")
    public Result status(@RequestParam Integer id, @RequestParam Integer status) {
        productService.setStatus(id, status);
        return Result.ok();
    }
}