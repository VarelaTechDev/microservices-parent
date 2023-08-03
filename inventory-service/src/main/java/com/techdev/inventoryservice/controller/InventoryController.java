package com.techdev.inventoryservice.controller;

import com.techdev.inventoryservice.dto.InventoryResponse;
import com.techdev.inventoryservice.service.InventoryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

// https://localhost:8082/api/inventory/iphone-13,iphone13-red

//    @GetMapping("/{sku-code}")
//    @ResponseStatus(HttpStatus.OK)
//    public boolean isInStock(@PathVariable("sku-code") String skuCode){
//        return inventoryService.isInStock(skuCode);
//    }

    // https://localhost:8082/api/inventory?iphone-13&iphone13-red
//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public boolean isInStock(@RequestParam List<String> skuCode){
//        return inventoryService.isInStock(skuCode);
//    }

    // http://localhost:8082/api/inventory?skuCode=iPhone-13&skuCode=iPhone13-red
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode){
        return inventoryService.isInStock(skuCode);
    }



//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode, HttpServletRequest request) {
//        String fullURL = request.getRequestURL().toString() + "?" + request.getQueryString();
//        System.out.println("URL: " + fullURL);
//        return inventoryService.isInStock(skuCode);
//    }

}
