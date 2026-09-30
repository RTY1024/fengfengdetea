package com.example.teaserver.entity;

public class OrderItem {
    private Integer id;
    private Integer orderId;
    private Integer productId;
    private String productName;
    private Double price;
    private Integer count;
    private String cupSize;
    private String temperature;
    private String sugar;
    private String toppings;
    private String remark;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getOrderId() { return orderId; }
    public void setOrderId(Integer orderId) { this.orderId = orderId; }

    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Integer getCount() { return count; }
    public void setCount(Integer count) { this.count = count; }

    public String getCupSize() { return cupSize; }
    public void setCupSize(String cupSize) { this.cupSize = cupSize; }

    public String getTemperature() { return temperature; }
    public void setTemperature(String temperature) { this.temperature = temperature; }

    public String getSugar() { return sugar; }
    public void setSugar(String sugar) { this.sugar = sugar; }

    public String getToppings() { return toppings; }
    public void setToppings(String toppings) { this.toppings = toppings; }

    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
}