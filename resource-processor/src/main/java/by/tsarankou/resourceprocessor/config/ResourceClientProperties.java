package by.tsarankou.resourceprocessor.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties("my.client.resource")
public class ResourceClientProperties {
    private String schema;
    private String host;
    private String port;
    private String endpoint;
}
