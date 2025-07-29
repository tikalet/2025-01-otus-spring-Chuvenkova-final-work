package ru.otus.adapter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String LIS_TO_ADAPTER_QUEUE = "lis.to.adapter.queue";

    private static final String LIS_EXCHANGE = "lis.adapter.exchange";

    private static final String DEAD_FROM_ADAPTER_QUEUE = "dead.letter.from.adapter";

    private static final String LIS_TO_ADAPTER_ROUTE_KEY = "lis.to.adapter.route.key";


    @Bean
    public Jackson2JsonMessageConverter jsonConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter jsonConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(LIS_EXCHANGE);
        rabbitTemplate.setMessageConverter(jsonConverter);
        return rabbitTemplate;
    }

    @Bean
    public DirectExchange lisExchange() {
        return new DirectExchange(LIS_EXCHANGE);
    }

    @Bean
    public Queue lisToAdapterQueue() {
        return QueueBuilder.durable(LIS_TO_ADAPTER_QUEUE)
                .deadLetterExchange(DEAD_FROM_ADAPTER_QUEUE)
                .build();
    }

    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(DEAD_FROM_ADAPTER_QUEUE)
                .build();
    }

    @Bean
    public Binding adapterToLisBinding() {
        return BindingBuilder.bind(lisToAdapterQueue())
                .to(lisExchange())
                .with(LIS_TO_ADAPTER_ROUTE_KEY);
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue())
                .to(lisExchange()).withQueueName();
    }
}
