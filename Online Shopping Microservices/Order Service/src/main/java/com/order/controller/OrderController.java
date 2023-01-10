package com.order.controller;

import com.order.dto.OrderLineItemsDto;
import com.order.dto.OrderRequest;
import com.order.service.OrderService;
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
    public String placeOrder(@RequestBody OrderRequest orderRequest){
        System.out.println(orderRequest.toString());
        //System.out.println("order: code=" + orderRequest.getSkuCode());
        orderService.placeOrder(orderRequest);
        return "Order Placed Successfully";
    }
}
