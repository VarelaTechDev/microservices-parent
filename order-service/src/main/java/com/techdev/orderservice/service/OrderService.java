package com.techdev.orderservice.service;

import com.techdev.orderservice.dto.InventoryResponse;
import com.techdev.orderservice.dto.OrderLineItemsDto;
import com.techdev.orderservice.dto.OrderRequest;
import com.techdev.orderservice.model.Order;
import com.techdev.orderservice.model.OrderLineItems;
import com.techdev.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    private final WebClient webClient;

    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(orderLineItemsDto -> mapToDto(orderLineItemsDto))
                .toList();


        order.setOrderLineItemsList(orderLineItemsList);

        List<String> skuCodes = order.getOrderLineItemsList().stream().map(orderLineItems -> orderLineItems.getSkuCode()).toList();

        // Call Inventory Service, and place order if product is in stock
        InventoryResponse[] inventoryResponsesArray =
            webClient.get()
                .uri("http://localhost:8082/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())

                .retrieve()
                //.bodyToMono(Boolean.class)
                .bodyToMono(InventoryResponse[].class)
                .block();


        boolean allProductsInStock = Arrays.stream(inventoryResponsesArray)
                .allMatch(inventoryResponse -> inventoryResponse.isInStock());



        if(allProductsInStock){
            orderRepository.save(order);
        }
        else{
            throw new IllegalArgumentException("Product is not in stock");
        }
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());

        return orderLineItems;
    }
}
