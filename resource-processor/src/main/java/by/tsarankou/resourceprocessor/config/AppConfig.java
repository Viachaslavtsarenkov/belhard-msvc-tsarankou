package by.tsarankou.resourceprocessor.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
@Configuration
@RequiredArgsConstructor
public class AppConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }

}