package com.poc.chainofresponsability.gateway.http;

import com.poc.chainofresponsability.domain.ExecutionContext;
import org.assertj.core.api.BDDAssertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class FlowControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String url;

    @Before
    public void setupEach() {
        url = "http://localhost:" + port + "/api/flow/";
    }

    @Test
    public void shouldProcessStepsForADDRESS() {
        String step = "address";

        final URI uri = getURI(step);

        ResponseEntity<ExecutionContext> executionContextResponseEntity = restTemplate.postForEntity(uri, null, ExecutionContext.class);

        BDDAssertions.then(executionContextResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        BDDAssertions.then(executionContextResponseEntity.getBody()).isNotNull();
        BDDAssertions.then(executionContextResponseEntity.getBody().getExecutedBeans()).containsExactly(
                "Order 0 Bean Name SampleBeanA",
                "Order 1 Bean Name SampleBeanB",
                "Order 2 Bean Name SampleBeanC",
                "Order 3 Bean Name SampleBeanD");
    }

    @Test
    public void shouldProcessStepsForSELECT_ADDRESS() {
        String step = "SELECT_ADDRESS";

        final URI uri = getURI(step);

        ResponseEntity<ExecutionContext> executionContextResponseEntity = restTemplate.postForEntity(uri, null, ExecutionContext.class);

        BDDAssertions.then(executionContextResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        BDDAssertions.then(executionContextResponseEntity.getBody()).isNotNull();
        BDDAssertions.then(executionContextResponseEntity.getBody().getExecutedBeans()).containsExactly(
                "Order 0 Bean Name SampleBeanD",
                "Order 1 Bean Name SampleBeanB",
                "Order 2 Bean Name SampleBeanA",
                "Order 3 Bean Name SampleBeanC",
                "Order 4 Bean Name SampleBeanA",
                "Order 5 Bean Name SampleBeanB",
                "Order 6 Bean Name SampleBeanB");
    }

    @Test
    public void shouldProcessStepsForSHIPPING() {
        String step = "SHIPPING";

        final URI uri = getURI(step);

        ResponseEntity<ExecutionContext> executionContextResponseEntity = restTemplate.postForEntity(uri, null, ExecutionContext.class);

        BDDAssertions.then(executionContextResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        BDDAssertions.then(executionContextResponseEntity.getBody()).isNotNull();
        BDDAssertions.then(executionContextResponseEntity.getBody().getExecutedBeans()).containsExactly(
                "Order 0 Bean Name SampleBeanD",
                "Order 1 Bean Name SampleBeanC");
    }

    @Test
    public void shouldProcessStepsForSELECT_PAYMENT() {
        String step = "SELECT_PAYMENT";

        final URI uri = getURI(step);

        ResponseEntity<ExecutionContext> executionContextResponseEntity = restTemplate.postForEntity(uri, null, ExecutionContext.class);

        BDDAssertions.then(executionContextResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        BDDAssertions.then(executionContextResponseEntity.getBody()).isNotNull();
        BDDAssertions.then(executionContextResponseEntity.getBody().getExecutedBeans()).containsExactly(
                "Order 0 Bean Name SampleBeanA",
                "Order 1 Bean Name SampleBeanC",
                "Order 2 Bean Name SampleBeanD");
    }

    @Test
    public void shouldProcessStepsForPAYMENT_METHOD() {
        String step = "PAYMENT_METHOD";

        final URI uri = getURI(step);

        ResponseEntity<ExecutionContext> executionContextResponseEntity = restTemplate.postForEntity(uri, null, ExecutionContext.class);

        BDDAssertions.then(executionContextResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        BDDAssertions.then(executionContextResponseEntity.getBody()).isNotNull();
        BDDAssertions.then(executionContextResponseEntity.getBody().getExecutedBeans()).containsExactly(
                "Order 0 Bean Name SampleBeanB",
                "Order 1 Bean Name SampleBeanA",
                "Order 2 Bean Name SampleBeanA",
                "Order 3 Bean Name SampleBeanA",
                "Order 4 Bean Name SampleBeanB");
    }

    private URI getURI(String step) {
        return UriComponentsBuilder.fromHttpUrl(url)
                .path(String.valueOf(step))
                .build().encode().toUri();
    }


}