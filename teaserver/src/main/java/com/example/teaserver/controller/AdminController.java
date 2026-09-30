package com.example.teaserver.controller;

import com.example.teaserver.common.Result;
import com.example.teaserver.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminMapper adminMapper;

    // 登录
    @GetMapping("/login")
    public Result login(@RequestParam String username, @RequestParam String password) {
        return adminMapper.login(username, password) != null
                ? Result.ok()
                : Result.error("账号或密码错误");
    }

    // 修改密码
    @GetMapping("/password")
    public Result password(@RequestParam String username, @RequestParam String password) {
        adminMapper.updatePassword(username, password);
        return Result.ok();
    }
}