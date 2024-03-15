package by.tsarankou.resourceprocessor.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;
@Configuration
@RequiredArgsConstructor
public class AppConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .codecs(codecs -> codecs
                        .defaultCodecs()
                        .maxInMemorySize(10 * 1024 * 1024))
                .build();
    }


}