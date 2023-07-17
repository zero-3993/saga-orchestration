package com.poc.orchestration.saga.config;

import com.poc.orchestration.dto.OrchestratorRequestDTO;
import com.poc.orchestration.dto.OrchestratorResponseDTO;
import com.poc.orchestration.saga.service.orchestrator.OrchestratorService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Function;

@Configuration
@AllArgsConstructor
public class OrchestratorConfig {

    private final OrchestratorService orchestratorService;
    @Bean
    public Sinks.Many<OrchestratorRequestDTO> sinkOrchestraRequest(){
        return Sinks.many().unicast().onBackpressureBuffer();
    }
    @Bean
    public Sinks.Many<OrchestratorResponseDTO> sinkOrchestraResponse(){
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    public Function<Flux<OrchestratorRequestDTO>, Flux<OrchestratorResponseDTO>> processor(){
        return flux -> flux
                .flatMap(dto -> this.orchestratorService.orderProduct(dto))
                .doOnNext(dto -> System.out.println("Status : " + dto.getStatus()));
    }

}
