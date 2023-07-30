package com.techdev.productservice.controller;

import com.techdev.productservice.dto.ProductRequest;
import com.techdev.productservice.dto.ProductResponse;
import com.techdev.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

//    {
//        "name": "iPhone 13",
//            "description": "iPhone 13 is Apple latest device",
//            "price": 1200
//    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest){
        productService.createProduct(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts(){
        return productService.getAllProducts();
    }
}
