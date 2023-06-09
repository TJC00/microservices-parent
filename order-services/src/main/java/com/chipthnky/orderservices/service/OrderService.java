package com.chipthnky.orderservices.service;

import com.chipthnky.orderservices.dto.OrderLineItemsRequest;
import com.chipthnky.orderservices.dto.OrderPlacedResponse;
import com.chipthnky.orderservices.dto.OrderRequest;
import com.chipthnky.orderservices.model.Order;
import com.chipthnky.orderservices.model.OrderLineItems;
import com.chipthnky.orderservices.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    public OrderPlacedResponse placeOrder(OrderRequest request){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = request.getOrderLineItemsRequests()
                .stream()
                .map(this::mapToDto).toList();

        order.setOrderLineItems(orderLineItems);

        List<String> skuCodes = order.getOrderLineItems().stream()
                        .map(OrderLineItems::getSkuCode).toList();

        orderRepository.save(order);

        return OrderPlacedResponse.builder()
                .orderNumber(order.getOrderNumber())
                .createdAt(order.getCreatedAt())
                .message("Order Has Been Placed Successfully")
                .build();
    }

    private OrderLineItems mapToDto(OrderLineItemsRequest orderLineItemsRequest) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsRequest.getPrice());
        orderLineItems.setQuantity(orderLineItemsRequest.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsRequest.getSkuCode());

        return orderLineItems;
    }
}
