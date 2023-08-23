package com.akazimour.orderservice.entity;

import com.akazimour.orderservice.dto.OrderStatus;

import jakarta.persistence.*;


import java.io.Serializable;
import java.util.*;

@Entity
public class OrderItem {
    @Id
    @GeneratedValue
    private Long id;
    private String shippingAddress;
    private String customerName;
    @OneToMany
    private Map<Integer,ProductSummary> chartItems;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public OrderItem() {
    }


    public OrderItem(Long id, String shippingAddress, String customerName, Map<Integer, ProductSummary> chartItems, OrderStatus orderStatus) {
        this.id = id;
        this.shippingAddress = shippingAddress;
        this.customerName = customerName;
        this.chartItems = chartItems;
        this.orderStatus = orderStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Map<Integer, ProductSummary> getChartItems() {
        return chartItems;
    }

    public void setChartItems(Map<Integer, ProductSummary> chartItems) {
        this.chartItems = chartItems;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void addElementsToChartItem(Integer quantity, ProductSummary productSummary){
        if (this.chartItems==null) {
            this.chartItems = new HashMap<>();
            this.chartItems.put(quantity, productSummary);
        }else
            this.chartItems.put(quantity, productSummary);
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", shippingAddress='" + shippingAddress + '\'' +
                ", customerName='" + customerName + '\'' +
                ", chartItems=" + chartItems +
                ", orderStatus=" + orderStatus +
                '}';
    }

}
