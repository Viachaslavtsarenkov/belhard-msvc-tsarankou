package by.tsarankou.servicesong.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("broker.songs")
public class SongsRabbitProperties {
    private String deletingResourceQueue;
    private String topic;
    private String deletingResourceRouting;
}
