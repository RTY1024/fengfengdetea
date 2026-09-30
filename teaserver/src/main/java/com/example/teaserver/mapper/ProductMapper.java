package com.example.teaserver.mapper;

import com.example.teaserver.entity.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductMapper {

    // 用户端：只显示上架商品
    @Select("SELECT * FROM product WHERE status = 1 ORDER BY id DESC")
    List<Product> getAll();

    // 管理员端：显示全部（含下架）
    @Select("SELECT * FROM product ORDER BY id DESC")
    List<Product> getAllForAdmin();

    @Select("SELECT * FROM product WHERE id = #{id}")
    Product getById(Integer id);

    // ★ 多分类：FIND_IN_SET 支持 "本店热销,清爽果茶" 这种多值匹配
    @Select("SELECT * FROM product WHERE status = 1 AND FIND_IN_SET(#{category}, category) ORDER BY id DESC")
    List<Product> getByCategory(String category);

    @Insert("INSERT INTO product(name, price, image, description, category, status) " +
            "VALUES(#{name}, #{price}, #{image}, #{description}, #{category}, #{status})")
    int insert(Product p);

    // ★ 更新时带上 image
    @Update("UPDATE product SET name=#{name}, price=#{price}, image=#{image}, description=#{description}, category=#{category} WHERE id=#{id}")
    int update(Product p);

    @Delete("DELETE FROM product WHERE id=#{id}")
    int delete(Integer id);

    @Update("UPDATE product SET status = #{status} WHERE id = #{id}")
    int updateStatus(@Param("id") Integer id, @Param("status") Integer status);

    @Update("UPDATE product SET category = #{newCat} WHERE category = #{oldCat}")
    int updateCategory(@Param("oldCat") String oldCat, @Param("newCat") String newCat);
}