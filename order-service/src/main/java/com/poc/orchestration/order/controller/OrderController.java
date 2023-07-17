package com.poc.orchestration.order.controller;

import com.poc.orchestration.dto.OrderRequestDTO;
import com.poc.orchestration.dto.OrderResponseDTO;
import com.poc.orchestration.order.entity.PurchaseOrder;
import com.poc.orchestration.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService service;

    @PostMapping("/create")
    public Mono<PurchaseOrder> createOrder(@RequestBody Mono<OrderRequestDTO> mono){
        return mono
                .flatMap(this.service::createOrder);
    }

    @GetMapping("/all")
    public Flux<OrderResponseDTO> getOrders(){
        return this.service.getAll();
    }

}
