package com.innogy.emobility.springtraining.rabbitmqconsumer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innogy.emobility.springtraining.rabbitmqconsumer.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@Service
public class ConsumerService {

    @Autowired
    ObjectMapper objectMapper;

    @RabbitListener(queues = "sayhello")
    public void receiveHello(String msg) {
        log.info(LocalDateTime.now() + " received message: \"" + msg + "\"");
    }

    @RabbitListener(queues = "#{fanoutQueue.name}")
    public void receiveUser(Message message) {
        try {
            User user = objectMapper.readValue(message.getBody(), User.class);
            log.info(LocalDateTime.now() + " received user: \"" + user.toString() + "\"");
        } catch (IOException e) {
            log.error("Could not parse User", e);
        }
    }

    @RabbitListener(queues = "#{directQueue.name}")
    public void receiveUserFromDirectExchange(Message message) {
        try {
            User user = objectMapper.readValue(message.getBody(), User.class);
            log.info(LocalDateTime.now() + " received user: \"" + user.toString() + "\"");
        } catch (IOException e) {
            log.error("Could not parse User", e);
        }
    }
}
