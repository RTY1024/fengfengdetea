package com.example.teaserver.mapper;

import com.example.teaserver.entity.Order;
import com.example.teaserver.entity.OrderItem;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {

    @Insert("INSERT INTO orders(order_no, member_id, total_price, freight, receiver, phone, address, status, create_time, rating) " +
            "VALUES(#{orderNo}, #{memberId}, #{totalPrice}, #{freight}, #{receiver}, #{phone}, #{address}, 0, #{createTime}, 0)")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertOrder(Order order);

    @Insert("INSERT INTO order_item(order_id, product_id, product_name, price, count, cup_size, temperature, sugar, toppings, remark) " +
            "VALUES(#{orderId}, #{productId}, #{productName}, #{price}, #{count}, #{cupSize}, #{temperature}, #{sugar}, #{toppings}, #{remark})")
    int insertOrderItem(OrderItem item);

    @Select("SELECT * FROM orders WHERE member_id = #{memberId} ORDER BY id DESC")
    List<Order> getByMember(Integer memberId);

    @Select("SELECT * FROM orders ORDER BY id DESC")
    List<Order> getAll();

    @Select("SELECT * FROM orders WHERE id = #{id}")
    Order getById(Integer id);

    @Select("SELECT * FROM order_item WHERE order_id = #{orderId}")
    List<OrderItem> getItems(Integer orderId);

    @Update("UPDATE orders SET status = #{status} WHERE id = #{id}")
    int updateStatus(@Param("id") Integer id, @Param("status") Integer status);

    @Update("UPDATE orders SET receiver = #{receiver}, phone = #{phone}, address = #{address} WHERE id = #{id}")
    int updateAddress(Order order);

    @Update("UPDATE member SET balance = balance - #{amount} WHERE id = #{memberId} AND balance >= #{amount}")
    int deductBalance(@Param("memberId") Integer memberId, @Param("amount") Double amount);

    @Update("UPDATE orders SET rating = #{rating}, comment = #{comment} WHERE id = #{id}")
    int saveRating(Order order);
    // 销量排行
    @Select("SELECT oi.product_name AS name, SUM(oi.count) AS total " +
            "FROM order_item oi LEFT JOIN orders o ON oi.order_id = o.id " +
            "WHERE IFNULL(o.status, 0) != 4 " +
            "GROUP BY oi.product_id, oi.product_name " +
            "ORDER BY total DESC LIMIT 20")
    List<Map<String, Object>> getTopSales();
}