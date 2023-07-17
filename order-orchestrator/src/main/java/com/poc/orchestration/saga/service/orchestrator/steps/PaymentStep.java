package com.poc.orchestration.saga.service.orchestrator.steps;

import com.poc.orchestration.dto.PaymentRequestDTO;
import com.poc.orchestration.dto.PaymentResponseDTO;
import com.poc.orchestration.enums.PaymentStatus;
import com.poc.orchestration.saga.service.orchestrator.WorkflowStep;
import com.poc.orchestration.saga.service.orchestrator.WorkflowStepStatus;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class PaymentStep implements WorkflowStep {

    private final WebClient webClient;
    private final PaymentRequestDTO requestDTO;
    private WorkflowStepStatus stepStatus = WorkflowStepStatus.PENDING;

    public PaymentStep(WebClient webClient, PaymentRequestDTO requestDTO) {
        this.webClient = webClient;
        this.requestDTO = requestDTO;
    }

    @Override
    public WorkflowStepStatus getStatus() {
        return this.stepStatus;
    }

    @Override
    public Mono<Boolean> process() {
        return this.webClient
                .post()
                .uri("/payment/debit")
                .body(BodyInserters.fromValue(this.requestDTO))
                .retrieve()
                .bodyToMono(PaymentResponseDTO.class)
                .map(r -> r.getStatus().equals(PaymentStatus.PAYMENT_APPROVED))
                .onErrorMap(e -> {
                    System.err.println(e);
                    return e;

                })
                .doOnNext(b -> {
                    this.stepStatus = b ? WorkflowStepStatus.COMPLETE : WorkflowStepStatus.FAILED;
                    System.out.println(this.stepStatus);
                });
    }

    @Override
    public Mono<Boolean> revert() {
        return this.webClient
                .post()
                .uri("/payment/credit")
                .body(BodyInserters.fromValue(this.requestDTO))
                .retrieve()
                .bodyToMono(Void.class)
                .map(r -> true)
                .onErrorReturn(false);
    }

}
