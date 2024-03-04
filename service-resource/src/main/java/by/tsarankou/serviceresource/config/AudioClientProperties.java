package by.tsarankou.serviceresource.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("my.client.audio")
public class AudioClientProperties {
    private String scheme;
    private String host;
    private String port;
    private String endpoint;
}
