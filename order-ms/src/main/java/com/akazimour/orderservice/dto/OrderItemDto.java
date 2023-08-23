package com.akazimour.orderservice.dto;
import jakarta.persistence.*;
import org.springframework.context.annotation.ComponentScan;
import java.util.Map;

@ComponentScan(basePackages = {"com.akazimour.catalogapi.CatalogApi"})
public class OrderItemDto {

    private Long id;
    private String shippingAddress;
    private String customerName;
    private Map<Integer, ProductSummaryDto> chartItems;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public OrderItemDto() {
    }

    public OrderItemDto(Long id, String shippingAddress, String customerName, Map<Integer, ProductSummaryDto> chartItems, OrderStatus orderStatus) {
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


    public Map<Integer, ProductSummaryDto> getChartItems() {
        return chartItems;
    }

    public void setChartItems(Map<Integer, ProductSummaryDto> chartItems) {
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



    @Override
    public String toString() {
        return "OrderItemDto{" +
                "id=" + id +
                ", shippingAddress='" + shippingAddress + '\'' +
                ", customerName='" + customerName + '\'' +
                ", chartItems=" + chartItems +
                ", orderStatus=" + orderStatus +
                '}';
    }
}
