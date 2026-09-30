package com.example.teaserver.mapper;

import com.example.teaserver.entity.Member;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MemberMapper {

    @Select("SELECT * FROM member WHERE phone = #{phone} AND password = #{password}")
    Member login(@Param("phone") String phone, @Param("password") String password);

    @Select("SELECT * FROM member WHERE id = #{id}")
    Member getById(Integer id);

    @Insert("INSERT INTO member(phone, password, nickname, balance, create_time) " +
            "VALUES(#{phone}, #{password}, #{nickname}, 0, #{createTime})")
    int register(Member member);

    @Select("SELECT * FROM member WHERE phone = #{phone}")
    Member getByPhone(String phone);

    @Update("UPDATE member SET balance = balance + #{amount} WHERE id = #{id}")
    int recharge(@Param("id") Integer id, @Param("amount") Double amount);

    @Select("SELECT * FROM member ORDER BY id DESC")
    List<Member> getAll();

    // 删除会员
    @Delete("DELETE FROM member WHERE id = #{id}")
    int deleteMember(Integer id);
}