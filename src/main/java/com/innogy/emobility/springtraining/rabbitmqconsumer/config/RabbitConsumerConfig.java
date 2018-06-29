package com.innogy.emobility.springtraining.rabbitmqconsumer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@Configuration
public class RabbitConsumerConfig implements RabbitListenerConfigurer {

    private ObjectMapper objectMapper;

    @Autowired
    public RabbitConsumerConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    public FanoutExchange fanout() {
        return new FanoutExchange("training.fanout");
    }

    @Bean
    public Queue fanoutQueue(){
        return new Queue("training.fanout.queue2");
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("training.direct");
    }

    @Bean
    public Queue directQueue(){
        return new Queue("training.direct.queue2");
    }

    @Bean
    Binding fanoutBinding(){
        return BindingBuilder.bind(fanoutQueue()).to(fanout());
    }


    @Bean
    Binding directBinding(){
        return BindingBuilder.bind(directQueue()).to(directExchange()).with("test-direct");
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
        messageConverter.setObjectMapper(objectMapper);
        return messageConverter;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new MappingJackson2MessageConverter();
    }

    @Bean
    public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(consumerJackson2MessageConverter());
        return factory;
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {
        rabbitListenerEndpointRegistrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }
}
