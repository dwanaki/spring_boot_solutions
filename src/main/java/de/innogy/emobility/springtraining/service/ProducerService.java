package de.innogy.emobility.springtraining.service;

import de.innogy.emobility.springtraining.model.BeerItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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
     * Send a {@link BeerItem} object to fanout exchange
     */
    @Scheduled(fixedDelay = 10000, initialDelay = 5000)
    public void sendBeerItemToFanout() {
        BeerItem beerItem = BeerItem.builder().beerName("Sink the Bismarck").alcoholVol(41.0D).bottleSizeInMl(375).build();
        log.info("Sending BeerItem: \"" + beerItem.toString() + "\" to exchange: " + fanoutExchange.getName());
        rabbitTemplate.convertAndSend(fanoutExchange.getName(), "", beerItem);
    }

    /**
     * Send a {@link BeerItem} object to direct exchange
     */
    @Scheduled(fixedDelay = 10000, initialDelay = 7500)
    public void sendBeerItemToDirect() {
        BeerItem beerItem = BeerItem.builder().beerName("Schmackofatz Pilsener").alcoholVol(8.7D).bottleSizeInMl(500).build();
        log.info("Sending Beer: \"" + beerItem.toString() + "\" with routingKey: " + directRoutingKey + " to exchange: " + directExchange.getName());
        rabbitTemplate.convertAndSend(directExchange.getName(), directRoutingKey, beerItem);
    }

}
