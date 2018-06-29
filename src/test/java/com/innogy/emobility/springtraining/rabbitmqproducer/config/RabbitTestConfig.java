package com.innogy.emobility.springtraining.rabbitmqproducer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innogy.emobility.springtraining.rabbitmqproducer.ExtendedTestRabbitTemplate;
import com.innogy.emobility.springtraining.rabbitmqproducer.model.User;
import com.rabbitmq.client.Channel;
import lombok.Getter;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

/**
 * Config class for RabbitMQ Tests
 */
@EnableRabbit
@Configuration
public class RabbitTestConfig implements RabbitListenerConfigurer {

    public static final String sayHelloTestQueue ="test.sayhello";
    public static final String fanoutQueue ="test.training.fanout";
    public static final String directQueue ="test.training.direct";

    @Autowired
    private ObjectMapper objectMapper;

    @Getter
    private List<String> sayhellos = new ArrayList<>();

    @Getter
    private List<User> fanoutUsers = new ArrayList<>();

    @Getter
    private List<User> directUsers = new ArrayList<>();

    @Bean
    public ExtendedTestRabbitTemplate testRabbitTemplate() {
        ExtendedTestRabbitTemplate testRabbitTemplate = new ExtendedTestRabbitTemplate(connectionFactory());
        testRabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return testRabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        ConnectionFactory factory = mock(ConnectionFactory.class);
        Connection connection = mock(Connection.class);
        Channel channel = mock(Channel.class);
        willReturn(connection).given(factory).createConnection();
        willReturn(channel).given(connection).createChannel(anyBoolean());
        given(channel.isOpen()).willReturn(true);
        return factory;
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        MappingJackson2MessageConverter consumerConverter = new MappingJackson2MessageConverter();
        consumerConverter.setObjectMapper(objectMapper);
        return consumerConverter;
    }

    @Bean
    public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(consumerJackson2MessageConverter());
        return factory;
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }

    @RabbitListener(queues = directQueue)
    public void receiveDirectUserData(User user) {
        directUsers.add(user);
    }

    @RabbitListener(queues = fanoutQueue)
    public void receivedFanoutUserData(User user) {
        fanoutUsers.add(user);
    }

    @RabbitListener(queues = sayHelloTestQueue)
    public void receivedHellos(String in) {
        sayhellos.add(in);
    }

    /**
     * Clear the lists before running new tests.
     */
    public void clearReceivedQueueData() {
        fanoutUsers.clear();
        directUsers.clear();
        sayhellos.clear();
    }
}
