package com.poc.orchestration.saga.controller;

import com.poc.orchestration.dto.OrderRequestDTO;
import com.poc.orchestration.dto.OrderResponseDTO;
import com.poc.orchestration.saga.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;

@Configuration
public class OrderController {

    @Autowired
    private OrderService service;

    @Bean
    public RouterFunction<ServerResponse> order() {
        return RouterFunctions.
                nest(path("/order"), RouterFunctions.route()
                        .GET("all", this::handle)
                        .POST("create", this::handleCreateOrder)
                        .build());
    }

    private Mono<ServerResponse> handleCreateOrder(ServerRequest request) {
        return request.bodyToMono(OrderRequestDTO.class)
                .flatMap(service::createOrder)
                .flatMap(order -> ServerResponse.created(URI.create("/order/" + order.getId())).build());
    }


    private Mono<ServerResponse> handle(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.service.getAll(), OrderResponseDTO.class);
    }

}
