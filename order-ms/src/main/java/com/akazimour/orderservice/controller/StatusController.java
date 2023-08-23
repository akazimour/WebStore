package com.akazimour.orderservice.controller;

import com.akazimour.orderservice.dto.OrderItemDto;
import com.akazimour.orderservice.dto.OrderStatus;
import com.akazimour.orderservice.dto.ShipmentIdWithStatusDto;
import com.akazimour.orderservice.entity.OrderItem;
import com.akazimour.orderservice.mapper.OrderMapper;
import com.akazimour.orderservice.repository.OrderItemRepository;
import com.akazimour.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@RestController
public class StatusController {

    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemRepository orderItemRepository;
    @Autowired
    OrderMapper orderMapper;

//A jóváhagyni vagy elutasítani kívánt order item id-jének megadása a path ban minden más request param-ként
    @PutMapping("/api/order/orderItem/{id}")
    public ShipmentIdWithStatusDto updateStatusOfOrderItem(@RequestParam OrderStatus status, @PathVariable Long id, @RequestParam String pickUpAddress) throws InterruptedException, ExecutionException {
        ShipmentIdWithStatusDto shipmentIdWithStatusDto = orderService.confirmedOrDeclined(status, id, pickUpAddress);
        OrderItem orderItem = orderItemRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("OrderItem does not exist with this id: " + id));
        orderItem.setOrderStatus(OrderStatus.valueOf(shipmentIdWithStatusDto.getStatus()));
        orderItemRepository.save(orderItem);
        return shipmentIdWithStatusDto;
    }
}
