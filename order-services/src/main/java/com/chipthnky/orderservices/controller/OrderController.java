package com.chipthnky.orderservices.controller;

import com.chipthnky.orderservices.dto.OrderPlacedResponse;
import com.chipthnky.orderservices.dto.OrderRequest;
import com.chipthnky.orderservices.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderPlacedResponse placeOrder(@RequestBody OrderRequest request){
        return orderService.placeOrder(request);
    }
}
