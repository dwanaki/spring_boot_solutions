package com.innogy.emobility.springtraining.rabbitmqproducer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Configuration class for RabbitMQ
 */
@Configuration
@EnableScheduling
public class RabbitConfig {

    public static final String DIRECT_EXCHANGE_ROUTING_KEY = "test-direct";

    private ObjectMapper objectMapper;

    @Autowired
    public RabbitConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Create a Bean for RabbitTemplate to be able to autowire it.
     * Just needed to set the messageConverter or other properties
     *
     * @param connectionFactory ConnectionFactory
     *
     * @return {@link RabbitTemplate}
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(amqpJsonConverter());
        return rabbitTemplate;
    }

    /**
     * Queue as Bean to be able to autowire it
     *
     * @param sayHelloQueue name of the Queue from properties
     *
     * @return {@link Queue}
     */
    @Bean
    public Queue sayhello(@Value("${queue.sayhello}") String sayHelloQueue) {
        return new Queue(sayHelloQueue);
    }

    /**
     * FanoutExchange as Bean to be able to autowire it
     *
     * @param fanoutExchange name of the exchange from properties
     *
     * @return {@link FanoutExchange}
     */
    @Bean
    public FanoutExchange fanout(@Value("${exchange.training.fanout}") String fanoutExchange) {
        return new FanoutExchange(fanoutExchange);
    }

    /**
     * DirectExchange as Bean to be able to autowire it
     *
     * @param directExchangeName name of the exchange from properties
     *
     * @return {@link DirectExchange}
     */
    @Bean
    public DirectExchange direct(@Value("${exchange.training.direct}") String directExchangeName) {
        return new DirectExchange(directExchangeName);
    }

    /**
     * Jackson MessageConverter to send Objects as Json
     *
     * @return Jackson2JsonMessageConverter
     */
    @Bean
    public Jackson2JsonMessageConverter amqpJsonConverter() {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

}
