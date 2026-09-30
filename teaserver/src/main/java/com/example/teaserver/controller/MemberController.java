package com.example.teaserver.controller;

import com.example.teaserver.common.Result;
import com.example.teaserver.entity.Member;
import com.example.teaserver.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    // 登录：GET /api/member/login?phone=xxx&password=xxx
    @GetMapping("/login")
    public Result login(@RequestParam String phone, @RequestParam String password) {
        Member m = memberService.login(phone, password);
        if (m == null) return Result.error("手机号或密码错误");
        return Result.ok(m);
    }

    // 注册：GET /api/member/register?phone=xxx&password=xxx&nickname=xxx
    @GetMapping("/register")
    public Result register(@RequestParam String phone,
                           @RequestParam String password,
                           @RequestParam(required = false) String nickname) {
        String err = memberService.register(phone, password, nickname);
        if (err != null) return Result.error(err);
        return Result.ok();
    }

    // 查询会员：GET /api/member/1
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        Member m = memberService.getById(id);
        if (m == null) return Result.error("会员不存在");
        return Result.ok(m);
    }

    // 充值：GET /api/member/recharge?id=1&amount=100
    @GetMapping("/recharge")
    public Result recharge(@RequestParam Integer id, @RequestParam Double amount) {
        boolean ok = memberService.recharge(id, amount);
        if (!ok) return Result.error("充值失败");
        return Result.ok();
    }

    // 全部会员：GET /api/member/list
    @GetMapping("/list")
    public Result list() {
        return Result.ok(memberService.getAll());
    }
    // ★ 新增：删除会员
    @GetMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        return memberService.deleteMember(id) ? Result.ok() : Result.error("删除失败");
    }
}