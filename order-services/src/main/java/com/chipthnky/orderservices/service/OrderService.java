package com.chipthnky.orderservices.service;

import com.chipthnky.orderservices.dto.InventoryResponse;
import com.chipthnky.orderservices.dto.OrderLineItemsRequest;
import com.chipthnky.orderservices.dto.OrderPlacedResponse;
import com.chipthnky.orderservices.dto.OrderRequest;
import com.chipthnky.orderservices.exception.ProductNotFoundExceptionHandler;
import com.chipthnky.orderservices.model.Order;
import com.chipthnky.orderservices.model.OrderLineItems;
import com.chipthnky.orderservices.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    public OrderPlacedResponse placeOrder(OrderRequest request){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = request.getOrderLineItemsRequests()
                .stream()
                .map(this::mapToDto).toList();

        order.setOrderLineItems(orderLineItems);

        List<String> skuCodes = order.getOrderLineItems().stream()
                        .map(OrderLineItems::getSkuCode).toList();

        //Call Inventory Service, and Place order if the is in stock
        InventoryResponse[] inventoryResponses  = webClientBuilder
                .build()
                .get()
                        .uri("http://inventory-service/api/inventory",
                                uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                        .retrieve()
                        .bodyToMono(InventoryResponse[].class)
                        .block();

        assert inventoryResponses != null;
        boolean allProductsInStock = Arrays.stream(inventoryResponses)
                .allMatch(InventoryResponse::isInInStock);

        if(allProductsInStock && inventoryResponses.length > 0){
            orderRepository.save(order);
        }
        else {
            throw new ProductNotFoundExceptionHandler("Oops product not found in stock!");
        }

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
