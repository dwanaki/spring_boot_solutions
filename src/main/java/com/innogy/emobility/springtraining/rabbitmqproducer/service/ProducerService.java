package com.innogy.emobility.springtraining.rabbitmqproducer.service;

import com.innogy.emobility.springtraining.rabbitmqproducer.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * Service class to send messages to RabbitMQ
 */
@Slf4j
@Service
public class ProducerService {

    private RabbitTemplate rabbitTemplate;
    private Queue queue;
    private FanoutExchange fanoutExchange;
    private DirectExchange directExchange;

    @Value("${direct.exchange.routingkey}")
    private String directRoutingKey;

    @Autowired
    public ProducerService(RabbitTemplate rabbitTemplate, Queue queue, FanoutExchange fanoutExchange,
                           DirectExchange directExchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.queue = queue;
        this.fanoutExchange = fanoutExchange;
        this.directExchange = directExchange;
    }

    /**
     * Send String to Queue
     */
    @Scheduled(fixedDelay = 10000) // Run method every 10 Sec.
    public void sendStringToQueue() {
        String message = "hello";
        log.info("Sending message " + message + " to Queue: " + queue.getName());
        rabbitTemplate.convertAndSend(queue.getName(), message);
    }

    /**
     * Send a {@link User} object to fanout exchange
     */
    @Scheduled(fixedDelay = 10000, initialDelay = 5000)
    public void sendUserToFanout() {
        User user = User.builder().birthday(LocalDate.now()).username("FanoutUser").firstname("Firstname")
                        .lastname("Lastname").build();
        log.info("Sending User: \"" + user.toString() + "\" to exchange: " + fanoutExchange.getName());
        rabbitTemplate.convertAndSend(fanoutExchange.getName(), "", user);
    }

    /**
     * Send a {@link User} object to direct exchange
     */
    @Scheduled(fixedDelay = 10000, initialDelay = 7500)
    public void sendUserToDirect() {
        User user = User.builder().birthday(LocalDate.now()).username("direct-exchange-user").firstname("Firstname")
                        .lastname("Lastname").build();
        log.info("Sending User: \"" + user
                .toString() + "\" with routingKey: " + directRoutingKey + " to exchange: " + directExchange.getName());
        rabbitTemplate.convertAndSend(directExchange.getName(), directRoutingKey, user);
    }

}
