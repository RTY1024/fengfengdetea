package com.example.teaserver.service;

import com.example.teaserver.entity.Member;
import com.example.teaserver.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberMapper memberMapper;

    public Member login(String phone, String password) {
        return memberMapper.login(phone, password);
    }

    public Member getById(Integer id) {
        return memberMapper.getById(id);
    }

    public String register(String phone, String password, String nickname) {
        if (phone == null || phone.isEmpty()) return "手机号不能为空";
        if (password == null || password.isEmpty()) return "密码不能为空";

        Member exist = memberMapper.getByPhone(phone);
        if (exist != null) return "该手机号已注册";

        Member m = new Member();
        m.setPhone(phone);
        m.setPassword(password);
        m.setNickname(nickname == null || nickname.isEmpty() ? "茶客" : nickname);
        m.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        int rows = memberMapper.register(m);
        return rows > 0 ? null : "注册失败";
    }

    public boolean recharge(Integer id, Double amount) {
        if (amount == null || amount == 0) return false;
        return memberMapper.recharge(id, amount) > 0;
    }

    public List<Member> getAll() {
        return memberMapper.getAll();
    }

    //删除会员
    public boolean deleteMember(Integer id) {
        if (id == null) return false;
        return memberMapper.deleteMember(id) > 0;
    }
}