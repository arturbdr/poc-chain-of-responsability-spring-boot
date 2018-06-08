package com.poc.chainofresponsability.usecase.impl;

import com.poc.chainofresponsability.domain.Step;
import com.poc.chainofresponsability.domain.exception.NoStepDefinedException;
import com.poc.chainofresponsability.usecase.ExecutionChain;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.util.Assert.notEmpty;
import static org.springframework.util.Assert.notNull;


@Component
@Slf4j
@RequiredArgsConstructor
@ConfigurationProperties("checkout.process")
public class ProcessConfiguration {

    @Setter
    private Map<Step, List<String>> steps;
    private Map<Step, List<ExecutionChain>> stepsBean;
    private final BeanFactory beanFactory;

    @PostConstruct
    public void setupStepsChain() {
        if (steps == null) {
            throw new NoStepDefinedException("There are no Processes defined. Its required to define it in a yml file");
        }

        log.info("loading steps defined in yml steps = {}", steps);
        stepsBean = new ConcurrentHashMap<>(steps.size());
        steps.forEach((processName, stepInProcess) -> {
            notNull(stepInProcess, "There are no steps defined for " + processName);
            notEmpty(stepInProcess, "Empty steps defined for " + processName);

            final List<ExecutionChain> beanSteps = new ArrayList<>(stepInProcess.size());
            stepInProcess.forEach(step -> {
                log.info("For process {} creating Bean {}", processName, step);
                beanSteps.add(beanFactory.getBean(step, ExecutionChain.class));
            });
            stepsBean.put(processName, beanSteps);
        });
        log.info("finished setting up steps {}", steps);
    }

    public Map<Step, List<ExecutionChain>> getStepsBean() {
        return Collections.unmodifiableMap(stepsBean);
    }
}