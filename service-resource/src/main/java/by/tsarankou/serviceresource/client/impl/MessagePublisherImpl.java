package by.tsarankou.serviceresource.client.impl;

import by.tsarankou.serviceresource.client.MessagePublisher;
import by.tsarankou.serviceresource.config.ResourceRabbitProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MessagePublisherImpl implements MessagePublisher {

    private final RabbitTemplate rabbitTemplate;
    private final ResourceRabbitProperties resourceRabbitProperties;

    @Override
    public void postMessage(Object message) {
        log.info("Sending message: {}", message);
        rabbitTemplate.convertAndSend(resourceRabbitProperties.getResourceTopic(),
                resourceRabbitProperties.getCreateResourceRouting(), message);
    }

    @Override
    public void deleteMessage(Object message) {
        log.info("Sending message about deleting: {}", message);
        rabbitTemplate.convertAndSend(resourceRabbitProperties.getResourceTopic(),
                resourceRabbitProperties.getDeleteResourceRouting(), message);
    }
}
