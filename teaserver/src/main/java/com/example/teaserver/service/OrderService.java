package com.example.teaserver.service;

import com.example.teaserver.entity.Order;
import com.example.teaserver.entity.OrderItem;
import com.example.teaserver.mapper.MemberMapper;
import com.example.teaserver.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private MemberMapper memberMapper;

    public Order create(Order order, List<OrderItem> items) {
        int rows = orderMapper.deductBalance(order.getMemberId(), order.getTotalPrice());
        if (rows <= 0) return null;

        order.setOrderNo("T" + System.currentTimeMillis());
        order.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        orderMapper.insertOrder(order);

        for (OrderItem item : items) {
            item.setOrderId(order.getId());
            orderMapper.insertOrderItem(item);
        }
        return order;
    }

    public List<Order> getByMember(Integer memberId) {
        return orderMapper.getByMember(memberId);
    }

    public List<Order> getAll() {
        return orderMapper.getAll();
    }

    public Order detail(Integer orderId) {
        return orderMapper.getById(orderId);
    }

    public List<OrderItem> getItems(Integer orderId) {
        return orderMapper.getItems(orderId);
    }

    public boolean updateStatus(Integer orderId, Integer status) {
        return orderMapper.updateStatus(orderId, status) > 0;
    }

    public boolean updateAddress(Order order) {
        return orderMapper.updateAddress(order) > 0;
    }

    public boolean saveRating(Integer orderId, Integer rating, String comment) {
        Order o = new Order();
        o.setId(orderId);
        o.setRating(rating);
        o.setComment(comment);
        return orderMapper.saveRating(o) > 0;
    }

    public boolean cancelOrder(Integer orderId) {
        Order o = orderMapper.getById(orderId);
        if (o == null) return false;
        if (o.getStatus() != 0) return false;

        orderMapper.updateStatus(orderId, 4);
        memberMapper.recharge(o.getMemberId(), o.getTotalPrice());
        return true;
    }

    // 销量排行 ← 新增
    public List<Map<String, Object>> getTopSales() {
        return orderMapper.getTopSales();
    }
}