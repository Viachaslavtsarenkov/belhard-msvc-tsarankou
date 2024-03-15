package by.tsarankou.resourceprocessor.client.impl;

import by.tsarankou.resourceprocessor.client.SongClient;
import by.tsarankou.resourceprocessor.config.ResourceClientProperties;
import by.tsarankou.resourceprocessor.config.SongsClientProperties;
import by.tsarankou.resourceprocessor.dto.IdDTO;
import by.tsarankou.resourceprocessor.dto.MetaDataDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
@Slf4j
public class SongClientImpl implements SongClient {

    private final WebClient webClient;
    private final SongsClientProperties songsClientProperties;
    private final LoadBalancerClient loadBalancerClient;
    @Override
    public void sentMetadataToSongsService(MetaDataDTO metaDataDTO) {
        ServiceInstance instance = loadBalancerClient.choose(songsClientProperties.getId());
        log.info("Choose instance {} of {} with LoadBalancerClient", instance.getInstanceId(), instance.getServiceId());
        log.info("Sending request to AUDIO: {}", metaDataDTO.getResourceId());
        IdDTO idDTO = webClient.post().uri(uri -> uri.scheme((instance.getScheme()))
                        .host(instance.getHost())
                        .port(instance.getPort())
                        .path(songsClientProperties.getEndpoint())
                        .build())
                .bodyValue(metaDataDTO).retrieve()
                .bodyToMono(IdDTO.class)
                .block();
        log.info("Received response from Audio:" + idDTO);
    }
}
