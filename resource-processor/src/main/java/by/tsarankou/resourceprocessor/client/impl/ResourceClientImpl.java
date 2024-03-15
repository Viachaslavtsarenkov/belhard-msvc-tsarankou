package by.tsarankou.resourceprocessor.client.impl;

import by.tsarankou.resourceprocessor.client.ResourceClient;
import by.tsarankou.resourceprocessor.config.ResourceClientProperties;
import by.tsarankou.resourceprocessor.config.SongsClientProperties;
import by.tsarankou.resourceprocessor.dto.IdDTO;
import by.tsarankou.resourceprocessor.dto.MetaDataDTO;
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
    private final ResourceClientProperties resourceClientConfig;

    @Override
    public byte[] getResource(Integer id) {
        log.info("Sending request to Resource with id: {}", id);
        byte[] response = webClient.get()
                .uri(uri -> uri.scheme(resourceClientConfig.getSchema())
                        .host(resourceClientConfig.getHost())
                        .port(resourceClientConfig.getPort())
                        .path(resourceClientConfig.getEndpoint() + "/" + id)
                        .build())
                .retrieve()
                .bodyToMono(byte[].class)
                .block();
        log.info("Received response from Resource: {}", response);
        return response;
    }

}
