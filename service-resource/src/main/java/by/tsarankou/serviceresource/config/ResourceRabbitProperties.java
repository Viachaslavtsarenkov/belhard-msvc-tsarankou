package by.tsarankou.serviceresource.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties("broker.resource")
public class ResourceRabbitProperties {
    private String resourceTopic;
    private String createResourceRouting;
    private String deleteResourceRouting;
}
