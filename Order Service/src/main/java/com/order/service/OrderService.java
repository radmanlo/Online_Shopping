package com.order.service;

import com.order.dto.OrderLineItemsDto;
import com.order.dto.OrderRequest;
import com.order.model.Order;
import com.order.model.OrderLineItems;
import com.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private WebClient webClient;
    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        order.setOrderLineItemsList(
                orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToModelOLI).toList()
                );

        /**
         * Call Inventory Service in order to
         * check the availability of product
         */
        boolean isInStock = webClient.get()
                .uri("http://localhost:8082/api/inventory")
                .retrieve()
                .bodyToMono(boolean.class)
                .block();
        if (isInStock)
            orderRepository.save(order);
        else
            throw new IllegalArgumentException("Product is Not Available");
    }

    private OrderLineItems mapToModelOLI(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        return orderLineItems;
    }


}
