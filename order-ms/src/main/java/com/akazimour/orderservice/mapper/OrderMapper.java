package com.akazimour.orderservice.mapper;

import com.akazimour.catalogapi.dto.ProductDto;
import com.akazimour.orderservice.dto.OrderItemDto;
import com.akazimour.orderservice.dto.ProductSummaryDto;
import com.akazimour.orderservice.entity.OrderItem;
import com.akazimour.orderservice.entity.ProductSummary;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    ProductSummaryDto productDtoToProdDto (ProductDto productDto);

   List<OrderItemDto> orderItemsToOrderItemDtos (List<OrderItem> orderItems);

    OrderItemDto orderItemToOrderItemDto(OrderItem orderItem);


}
