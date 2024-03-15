package by.tsarankou.resourceprocessor.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class MessagingConfig {
    private final ResourceRabbitProperties resourceRabbitProperties;

    @Bean
    public Queue createQueue() {
        return new Queue(resourceRabbitProperties.getQueue(), false);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(resourceRabbitProperties.getTopic());
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange topicExchange) {
        return BindingBuilder.bind(queue)
                .to(topicExchange)
                .with(resourceRabbitProperties.getRouting());
    }

}
