package com.poc.chainofresponsability.config;

import com.poc.chainofresponsability.domain.Step;
import com.poc.chainofresponsability.domain.exception.NoStepsDefinedException;
import com.poc.chainofresponsability.usecase.ServiceChain;
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
    private Map<Step, List<ServiceChain>> stepsBean;
    private final BeanFactory beanFactory;

    @PostConstruct
    public void setupStepsChain() {
        if (steps != null) {
            stepsBean = new ConcurrentHashMap<>(steps.size());
            steps.forEach((processName, stepInProcess) -> {
                notNull(stepInProcess, "There are no steps defined for " + processName);
                notEmpty(stepInProcess, "Empty steps defined for " + processName);

                final List<ServiceChain> beanSteps = new ArrayList<>(stepInProcess.size());
                stepInProcess.forEach(step -> beanSteps.add(beanFactory.getBean(step, ServiceChain.class)));
                stepsBean.put(processName, beanSteps);
            });
        } else {
            throw new NoStepsDefinedException("There are no Processes defined. Its required to define it in a yml file");
        }
    }

    public Map<Step, List<ServiceChain>> getStepsBean() {
        return Collections.unmodifiableMap(stepsBean);
    }
}