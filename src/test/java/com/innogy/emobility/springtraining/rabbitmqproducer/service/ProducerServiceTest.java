package com.innogy.emobility.springtraining.rabbitmqproducer.service;

import com.innogy.emobility.springtraining.rabbitmqproducer.config.RabbitTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.test.TestRabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import static com.innogy.emobility.springtraining.rabbitmqproducer.config.RabbitTestConfig.directQueue;
import static com.innogy.emobility.springtraining.rabbitmqproducer.config.RabbitTestConfig.fanoutQueue;
import static com.innogy.emobility.springtraining.rabbitmqproducer.config.RabbitTestConfig.sayHelloTestQueue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@Slf4j
@RunWith(SpringRunner.class)
@Import(RabbitTestConfig.class)
@SpringBootTest(webEnvironment = NONE)
public class ProducerServiceTest {

    private ProducerService producerServiceTestSubject;

    @Mock
    private Queue queue;

    @Mock
    private FanoutExchange fanoutExchange;

    @Mock
    private DirectExchange directExchange;

    @Autowired
    private TestRabbitTemplate testRabbitTemplate;

    @Autowired
    private RabbitTestConfig rabbitTestConfig;

    @Before
    public void setUp() {
        rabbitTestConfig.clearReceivedQueueData();
        when(fanoutExchange.getName()).thenReturn(fanoutQueue);
        when(queue.getName()).thenReturn(sayHelloTestQueue);
        when(directExchange.getName()).thenReturn(directQueue);
        producerServiceTestSubject = new ProducerService(testRabbitTemplate, queue, fanoutExchange, directExchange);
    }

    @Test
    public void sendUserToFanout(){
        producerServiceTestSubject.sendUserToFanout();
        assertThat(rabbitTestConfig.getFanoutUsers()).isNotEmpty();
        log.info(rabbitTestConfig.getFanoutUsers().get(0).toString());
    }

    @Test
    public void sendStringToHello(){
        producerServiceTestSubject.sendStringToQueue();
        assertThat(rabbitTestConfig.getSayhelloMessages()).isNotEmpty();
    }
}