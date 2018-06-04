package com.poc.chainofresponsability.configuration;

import com.poc.chainofresponsability.domain.Step;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ConfigurationProperties("checkout.process")
@Component
public class ProcessConfigurationProperties {

    private Map<Step, List<String>> steps;
}