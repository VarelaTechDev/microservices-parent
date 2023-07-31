package com.techdev.orderservice.controller;

import com.techdev.orderservice.dto.OrderRequest;
import com.techdev.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

//    {
//        "orderLineItemsDtoList": [
//        {
//            "skuCode": "iPhone_13",
//                "price": 1200,
//                "quantity": 1
//        }
//	]
//    }

    /*
    Will throw an error and tell us "Product is not in stock"
    {
        "orderLineItemsDtoList":[
            {
                "skuCode": "iphone_13",
                "price": 1200,
                "quantity": 1
            },
					{
						"skuCode": "iphone_13_red",
						"price": 1200,
						"quantity": 1
					}
        ]
    }

    The following will place the order successfully
    {
        "orderLineItemsDtoList":[
            {
                "skuCode": "iphone_13",
                "price": 1200,
                "quantity": 1
            }
        ]
    }

     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest){
        orderService.placeOrder(orderRequest);
        return "Order Placed Successfully";
    }
}
