package com.innogy.emobility.springtraining.rabbitmqconsumer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innogy.emobility.springtraining.rabbitmqconsumer.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class ConsumerService {

    @Autowired
    ObjectMapper objectMapper;

    @RabbitListener(queues = "#{helloQueue.name}")
    public void receiveHello(String msg) {
        log.info(LocalDateTime.now() + " received message: \"" + msg + "\"");
    }

    @RabbitListener(queues = "#{fanoutQueue.name}")
    public void receiveUser(User user) {
        log.info(LocalDateTime.now() + " received user: \"" + user.toString() + "\" from FanoutExchange");
    }

    @RabbitListener(queues = "#{directQueue.name}")
    public void receiveUserFromDirectExchange(User user) {
        log.info(LocalDateTime.now() + " received user: \"" + user.toString() + "\" from DirectExchange");

    }
}
