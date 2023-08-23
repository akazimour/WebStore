package com.akazimour.orderservice.service;
import com.akazimour.catalogapi.dto.ProductDto;
import com.akazimour.orderservice.dto.CategoriesDto;
import com.akazimour.orderservice.dto.OrderStatus;
import com.akazimour.orderservice.dto.ShipmentIdWithStatusDto;
import com.akazimour.orderservice.entity.OrderItem;
import com.akazimour.orderservice.entity.ProductSummary;
import com.akazimour.orderservice.mapper.OrderMapper;
import com.akazimour.orderservice.repository.OrderItemRepository;
import com.akazimour.orderservice.repository.ProductSummaryRepository;
import com.akazimour.orderservice.wsclient.*;
import com.akazimour.userapi.api.UserApi;
import com.akazimour.userapi.api.dto.UserProfileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class OrderService {
    @Autowired
    UserApi userApi;
    @Autowired
    OrderItemRepository orderItemRepository;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    ProductSummaryRepository productSummaryRepository;
    @Autowired
    JmsTemplate jmsTemplate;

    @Transactional
    public OrderItem placeOrder(String address, Long id, Integer quantity, String userName){
        OrderItem orderItem = new OrderItem();
        orderItem.setShippingAddress(address);
        UserProfileDto userByName = userApi.findUserByName(userName);
        orderItem.setCustomerName(userByName.getUserName());
        ProductDto productInfo = getProductDto(id);
        ProductSummary productSummary = new ProductSummary();
            if (quantity > 1){
                Integer productPrice = productInfo.getProductPrice();
                productInfo.setProductPrice(productPrice * quantity);
                System.out.println(productInfo.getProductPrice());
            }
            productSummary.setProductName(productInfo.getProductName());
            productSummary.setProductPrice(productInfo.getProductPrice());
            productInfo.getCategories().forEach(c-> {
                String categoryName = c.getCategoryName();
                String consumerSegment = c.getConsumerSegment();
                CategoriesDto categoriesDto = new CategoriesDto(categoryName,consumerSegment);
                productSummary.setCategories(List.of(categoriesDto)); {
                }
            });
            productSummary.setQuantity(quantity);
            productSummaryRepository.save(productSummary);
            orderItem.addElementsToChartItem(quantity,productSummary);
        orderItem.setOrderStatus(OrderStatus.PENDING);
        orderItemRepository.save(orderItem);
        return orderItem;
    }
    private static ProductDto getProductDto(Long id) {
        ProductDto productInfo = WebClient.create("http://localhost:8060")
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/catalog/product/{id}")
                        .build(id))
                .retrieve()
                .bodyToMono(ProductDto.class)
                .block();

        return productInfo;
    }
    public ShipmentIdWithStatusDto confirmedOrDeclined(OrderStatus status, Long orderItemId, String pickUpAddress) throws InterruptedException, ExecutionException {
        OrderItem orderItem = orderItemRepository.findById(orderItemId).orElseThrow(() -> new IllegalArgumentException("OrderItem does not exist with this id: " + orderItemId));
        String latestStatus = "";
        if (orderItem.getOrderStatus().equals(OrderStatus.PENDING)) {
            orderItem.setOrderStatus(status);
            orderItemRepository.save(orderItem);
            if (orderItem.getOrderStatus().equals(OrderStatus.DECLINED)) {
                return new ShipmentIdWithStatusDto(UUID.randomUUID(),OrderStatus.DECLINED.toString());
            }
            else if (orderItem.getOrderStatus().equals(OrderStatus.CONFIRMED)) {
                com.akazimour.orderservice.wsclient.OrderItemDto orderItemDto = new com.akazimour.orderservice.wsclient.OrderItemDto();
                orderItemDto.setId(orderItem.getId());
                System.out.println(orderItem.getOrderStatus());
                com.akazimour.orderservice.wsclient.OrderStatus orderStatus = null;
                com.akazimour.orderservice.wsclient.OrderStatus confirmed = orderStatus.CONFIRMED;
                orderItemDto.setOrderStatus(confirmed);
                System.out.println(orderItem.getShippingAddress());
                orderItemDto.setShippingAddress(orderItem.getShippingAddress());
                System.out.println(orderItem.getCustomerName());
                orderItemDto.setCustomerName(orderItem.getCustomerName());
                com.akazimour.orderservice.wsclient.OrderItemDto.ChartItems chartItems = new com.akazimour.orderservice.wsclient.OrderItemDto.ChartItems();
                ProductSummaryDto productSummaryDto = new ProductSummaryDto();
                Set<Map.Entry<Integer, ProductSummary>> entries = orderItem.getChartItems().entrySet();
                for (var ent : entries) {
                    System.out.println(ent.getKey());
                    chartItems.getEntry().forEach(entry -> entry.setKey(ent.getKey()));
                    System.out.println("Id: "+ent.getValue().getId());
                    productSummaryDto.setId(ent.getValue().getId());
                    System.out.println("Product_name: "+ent.getValue().getProductName());
                    productSummaryDto.setProductName(ent.getValue().getProductName());
                    System.out.println("Quantity: " + ent.getValue().getQuantity());
                    productSummaryDto.setQuantity(ent.getValue().getQuantity());
                    System.out.println("Product_price :" + ent.getValue().getProductPrice());
                    productSummaryDto.setProductPrice(ent.getValue().getProductPrice());
                    List<com.akazimour.orderservice.wsclient.CategoriesDto> categories1 = productSummaryDto.getCategories();
                    List<CategoriesDto> categories = ent.getValue().getCategories();
                    for (int i = 0; i < categories.size() - 1; i++) {
                        categories1.get(i).setCategoryName(categories.get(i).getCategoryName());
                        categories1.get(i).setConsumerSegment(categories.get(i).getConsumerSegment());
                    }
                    chartItems.getEntry().forEach(entry -> entry.setValue(productSummaryDto));
                }
                orderItemDto.setChartItems(chartItems);
                ShipmentApprover shipmentApprover = new ShipmentApprover();
                shipmentApprover.setArg0(pickUpAddress);
                shipmentApprover.setArg1(orderItemDto);
                String dummy = "";
                ShippingApi shippingApiImplPort = new ShippingApiImplService().getShippingApiImplPort();
                latestStatus = shippingApiImplPort.shipmentApprover(shipmentApprover);
                System.out.println(latestStatus);
                orderItemDto.setOrderStatus(com.akazimour.orderservice.wsclient.OrderStatus.valueOf(latestStatus));
                orderItemRepository.save(orderItem);
                jmsTemplate.convertAndSend("orderStatus",latestStatus);
            }
            return returnMessage(latestStatus);
            } else
                throw new RuntimeException("The status of the requested order is NOT PENDING it was changed already!");
        }

        public ShipmentIdWithStatusDto returnMessage (String status){
            ShipmentIdWithStatusDto shipmentIdWithStatusDto = new ShipmentIdWithStatusDto();
            shipmentIdWithStatusDto.setStatus(status);
            return shipmentIdWithStatusDto;
        }
    }


