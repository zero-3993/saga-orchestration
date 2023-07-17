package com.poc.orchestration.saga.eventhandlers;

import com.poc.orchestration.dto.OrchestratorRequestDTO;
import com.poc.orchestration.dto.OrchestratorResponseDTO;
import com.poc.orchestration.saga.service.OrderEventUpdateService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Configuration
@AllArgsConstructor
public class OrderEventHandler {
    private final Sinks.Many<OrchestratorResponseDTO> sink;
    private final OrderEventUpdateService service;

    public void consumer() {
        sink.asFlux()
                .doOnNext(c -> System.out.println("Consuming :: " + c))
                .flatMap(this.service::updateOrder)
                .subscribe();
    }

    ;

}
