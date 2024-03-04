package by.tsarankou.serviceresource.client.impl;

import by.tsarankou.serviceresource.client.AudioClient;
import by.tsarankou.serviceresource.config.AudioClientProperties;
import by.tsarankou.serviceresource.dto.IdDTO;
import by.tsarankou.serviceresource.dto.IdsDTO;
import by.tsarankou.serviceresource.dto.MetaDataDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
@Slf4j
public class AudioClientImpl implements AudioClient {

    private final WebClient webClient;
    private final AudioClientProperties audioClientConfig;
    @Override
    public IdDTO ping(MetaDataDTO metaDataDTO) {
        log.info("Sending request to AUDIO: {}", metaDataDTO);
        IdDTO response = webClient.post()
                .uri(uri -> uri.scheme(audioClientConfig.getScheme())
                        .host(audioClientConfig.getHost())
                        .port(audioClientConfig.getPort())
                        .path(audioClientConfig.getEndpoint())
                        .build())
                .bodyValue(metaDataDTO)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(IdDTO.class)
                .block();
        log.info("Received response from AUDIO: {}", response);
        return response;
    }

    @Override
    public IdsDTO deleteMetaData(String ids) {
        log.info("Sending request to AUDIO: {}", ids);
        IdsDTO response = webClient.delete()
                .uri(uri -> uri.scheme(audioClientConfig.getScheme())
                        .host(audioClientConfig.getHost())
                        .port(audioClientConfig.getPort())
                        .queryParam(ids)
                        .path(audioClientConfig.getEndpoint())
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(IdsDTO.class)
                .block();
        log.info("Received response from AUDIO: {}", response);
        return response;
    }
}
