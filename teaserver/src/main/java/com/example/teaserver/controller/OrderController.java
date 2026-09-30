package com.example.teaserver.controller;

import com.example.teaserver.common.Result;
import com.example.teaserver.entity.Order;
import com.example.teaserver.entity.OrderItem;
import com.example.teaserver.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public Result create(@RequestBody OrderRequest req) {
        Order order = new Order();
        order.setMemberId(req.getMemberId());
        order.setTotalPrice(req.getTotalPrice());
        order.setFreight(2.0);
        order.setReceiver(req.getReceiver());
        order.setPhone(req.getPhone());
        order.setAddress(req.getAddress());

        Order created = orderService.create(order, req.getItems());
        if (created == null) return Result.error("余额不足，请先充值");
        return Result.ok(created);
    }

    @GetMapping("/list")
    public Result list(@RequestParam Integer memberId) {
        return Result.ok(orderService.getByMember(memberId));
    }

    @GetMapping("/all")
    public Result all() {
        return Result.ok(orderService.getAll());
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        Order o = orderService.detail(id);
        if (o == null) return Result.error("订单不存在");
        return Result.ok(o);
    }

    @GetMapping("/{id}/items")
    public Result items(@PathVariable Integer id) {
        return Result.ok(orderService.getItems(id));
    }

    @GetMapping("/status")
    public Result status(@RequestParam Integer id, @RequestParam Integer status) {
        return orderService.updateStatus(id, status) ? Result.ok() : Result.error("修改失败");
    }

    @GetMapping("/address")
    public Result address(@RequestParam Integer id,
                          @RequestParam String receiver,
                          @RequestParam String phone,
                          @RequestParam String address) {
        Order o = new Order();
        o.setId(id);
        o.setReceiver(receiver);
        o.setPhone(phone);
        o.setAddress(address);
        return orderService.updateAddress(o) ? Result.ok() : Result.error("修改失败");
    }

    @GetMapping("/rating")
    public Result rating(@RequestParam Integer id,
                         @RequestParam Integer rating,
                         @RequestParam(required = false) String comment) {
        return orderService.saveRating(id, rating, comment == null ? "" : comment)
                ? Result.ok() : Result.error("评价失败");
    }

    @GetMapping("/cancel")
    public Result cancel(@RequestParam Integer id) {
        return orderService.cancelOrder(id)
                ? Result.ok()
                : Result.error("取消失败（只有待接单状态可取消）");
    }

    // 销量排行 ← 新增
    @GetMapping("/topSales")
    public Result topSales() {
        return Result.ok(orderService.getTopSales());
    }

    public static class OrderRequest {
        private Integer memberId;
        private Double totalPrice;
        private String receiver;
        private String phone;
        private String address;
        private List<OrderItem> items;

        public Integer getMemberId() { return memberId; }
        public void setMemberId(Integer memberId) { this.memberId = memberId; }
        public Double getTotalPrice() { return totalPrice; }
        public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }
        public String getReceiver() { return receiver; }
        public void setReceiver(String receiver) { this.receiver = receiver; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
        public List<OrderItem> getItems() { return items; }
        public void setItems(List<OrderItem> items) { this.items = items; }
    }
}