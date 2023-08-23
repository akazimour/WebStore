package com.akazimour.orderservice.controller;
import com.akazimour.catalogapi.dto.ProductDto;
import com.akazimour.orderservice.dto.CategoriesDto;
import com.akazimour.orderservice.dto.OrderItemDto;
import com.akazimour.orderservice.dto.OrderStatus;
import com.akazimour.orderservice.entity.OrderItem;

import com.akazimour.orderservice.entity.ProductSummary;
import com.akazimour.orderservice.mapper.OrderMapper;
import com.akazimour.orderservice.repository.OrderItemRepository;
import com.akazimour.orderservice.service.OrderService;


import com.akazimour.orderservice.wsclient.ProductSummaryDto;
import com.akazimour.userapi.api.dto.UserProfileDto;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


@RestController
@RequestMapping("/api")
public class OrderController {

@Autowired
OrderService orderService;
@Autowired
OrderItemRepository orderItemRepository;
@Autowired
OrderMapper orderMapper;


// Db-ben hozzáadott létező user nevének megadásával, és a többi paraméter megadásával lehet rendelést leadni
    @PostMapping("/order/product/purchase/{productId}")
    public OrderItem initiateNewOrder(@RequestParam String address, @PathVariable Long productId, @RequestParam Integer quantity, @RequestParam String userName){
        OrderItem orderItem = orderService.placeOrder(address, productId, quantity, userName);
       return orderItem;
    }

    //Rendelési tételek lekérése a vásárló vagy user neve alapján
    @PreAuthorize("#customerName == authentication.name")
    @GetMapping("/order/orderItem/customer")
    public List<OrderItemDto> findAllOrderItemByCustomerName(@RequestParam String customerName){
        List<OrderItem> orderItems = orderItemRepository.findByCustomerName(customerName).orElseThrow(() -> new IllegalArgumentException("Customer does not exist with this name: " + customerName));
    return orderMapper.orderItemsToOrderItemDtos(orderItems);
    }

}
