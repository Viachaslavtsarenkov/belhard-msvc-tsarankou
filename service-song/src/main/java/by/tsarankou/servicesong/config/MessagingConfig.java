package by.tsarankou.servicesong.config;

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
    private final SongsRabbitProperties songsRabbitProperties;

    @Bean
    public Queue createQueue() {
        return new Queue(songsRabbitProperties.getDeletingResourceQueue(), false);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(songsRabbitProperties.getTopic());
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange topicExchange) {
        return BindingBuilder.bind(queue)
                .to(topicExchange)
                .with(songsRabbitProperties.getDeletingResourceRouting());
    }

}
