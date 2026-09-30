package com.example.teaserver.mapper;

import com.example.teaserver.entity.Admin;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AdminMapper {

    @Select("SELECT * FROM admin WHERE username = #{username} AND password = #{password}")
    Admin login(@Param("username") String username, @Param("password") String password);

    @Update("UPDATE admin SET password = #{password} WHERE username = #{username}")
    int updatePassword(@Param("username") String username, @Param("password") String password);
}