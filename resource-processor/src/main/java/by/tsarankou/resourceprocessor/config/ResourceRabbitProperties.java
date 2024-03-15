package by.tsarankou.resourceprocessor.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("broker.resource")
public class ResourceRabbitProperties {
    private String createQueue;
    private String topic;
    private String createRouting;
}
