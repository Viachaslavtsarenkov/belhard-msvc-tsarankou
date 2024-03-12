package by.tsarankou.resourceprocessor.client.impl;

import by.tsarankou.resourceprocessor.client.ResourceClient;
import by.tsarankou.resourceprocessor.config.ResourceClientProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.File;
@Component
@RequiredArgsConstructor
@Slf4j
public class ResourceClientImpl implements ResourceClient {

    private final WebClient webClient;
    private final ResourceClientProperties audioClientConfig;
    private final LoadBalancerClient loadBalancerClient;

    @Override
    public byte[] getResource(Integer id) {
        ServiceInstance instance = loadBalancerClient.choose(audioClientConfig.getId());
        log.info("Choose instance {} of {} with LoadBalancerClient", instance.getInstanceId(), instance.getServiceId());
        log.info("Sending request to Resource: {}", id);
        byte[] response = webClient.get()
                .uri(uri -> uri.scheme(instance.getScheme() + "/" + id)
                        .host(instance.getHost())
                        .port(instance.getPort())
                        .path(audioClientConfig.getEndpoint())
                        .build())
                .retrieve()
                .bodyToMono(byte[].class)
                .block();
        log.info("Received response from Resource: {}", response);
        return response;
    }
}
