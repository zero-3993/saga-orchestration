package com.poc.orchestration.saga.service.orchestrator;

import java.util.List;

public interface Workflow {

    List<WorkflowStep> getSteps();

}
