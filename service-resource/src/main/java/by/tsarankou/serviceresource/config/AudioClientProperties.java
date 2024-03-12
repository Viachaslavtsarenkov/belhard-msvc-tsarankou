package by.tsarankou.serviceresource.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("my.client.audio")
public class AudioClientProperties {
    private String id;
    private String endpoint;
}
