package by.tsarankou.servicesong.client.impl;

import by.tsarankou.servicesong.client.ResourceClient;
import by.tsarankou.servicesong.config.ResourceClientProperties;
import by.tsarankou.servicesong.dto.IdsDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
@Slf4j
public class ResourceClientImpl implements ResourceClient {

    private final WebClient webClient;
    private final ResourceClientProperties resourceClientProperties;
    @Override
    public IdsDTO ping(String ids) {
        log.info("Sending request to Resource: {}",ids);
        IdsDTO response = webClient.delete()
                .uri(uri -> uri.scheme(resourceClientProperties.getScheme())
                        .host(resourceClientProperties.getHost())
                        .port(resourceClientProperties.getPort())
                        .queryParam("ids", ids)
                        .path(resourceClientProperties.getEndpoint())
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(IdsDTO.class)
                .block();
        log.info("Received response from Resource: {}", response);
        return response;
    }
}
