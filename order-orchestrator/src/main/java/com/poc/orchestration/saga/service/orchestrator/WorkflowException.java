package com.poc.orchestration.saga.service.orchestrator;

public class WorkflowException extends RuntimeException {

    public WorkflowException(String message) {
        super(message);
    }

}
