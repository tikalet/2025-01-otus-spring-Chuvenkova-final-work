package ru.otus.laboratory.config;

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

    public static final String ADAPTER_TO_LIS_QUEUE = "adapter.to.lis.queue";

    public static final String ADAPTER_ERROR_QUEUE = "adapter.error.queue";

    private static final String LIS_EXCHANGE = "lis.adapter.exchange";

    private static final String DEAD_FROM_ADAPTER_QUEUE = "dead.letter.from.adapter";

    private static final String ADAPTER_TO_LIS_ROUTE_KEY = "adapter.to.lis.route.key";

    private static final String ADAPTER_ERROR_ROUTE_KEY = "adapter.error.route.key";


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
    public Queue adapterToLisQueue() {
        return QueueBuilder.durable(ADAPTER_TO_LIS_QUEUE)
                .deadLetterExchange(DEAD_FROM_ADAPTER_QUEUE)
                .build();
    }


    @Bean
    public Queue adapterErrorToLisQueue() {
        return QueueBuilder.durable(ADAPTER_ERROR_QUEUE)
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
        return BindingBuilder.bind(adapterToLisQueue())
                .to(lisExchange())
                .with(ADAPTER_TO_LIS_ROUTE_KEY);
    }

    @Bean
    public Binding adapterErrorBinding() {
        return BindingBuilder.bind(adapterErrorToLisQueue())
                .to(lisExchange())
                .with(ADAPTER_ERROR_ROUTE_KEY);
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue())
                .to(lisExchange()).withQueueName();
    }
}
