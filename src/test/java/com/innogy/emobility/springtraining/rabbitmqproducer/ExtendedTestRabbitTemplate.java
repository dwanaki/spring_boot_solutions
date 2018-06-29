package com.innogy.emobility.springtraining.rabbitmqproducer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.test.TestRabbitTemplate;

import java.io.IOException;

/**
 * Class to test RabbitMQ fanout exchanges
 */
public class ExtendedTestRabbitTemplate extends TestRabbitTemplate {

    public ExtendedTestRabbitTemplate(ConnectionFactory connectionFactory) {
        super(connectionFactory);
    }

    /**
     * Extend method to be able to send messages to fanout exchanges in test.
     *
     * @param channel    Chanbel
     * @param exchange   Exchange
     * @param routingKey RoutingKey
     * @param mandatory  mandatory flag
     * @param message    Message
     *
     * @throws IOException Exception
     */
    @Override
    protected void sendToRabbit(Channel channel, String exchange, String routingKey, boolean mandatory, Message message)
            throws IOException {
        routingKey = (routingKey.isEmpty()) ? exchange : routingKey; // hook for routingKey: use exchange if empty
        super.sendToRabbit(channel, exchange, routingKey, mandatory, message);
    }
}