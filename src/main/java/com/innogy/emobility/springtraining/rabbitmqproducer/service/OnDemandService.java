package com.innogy.emobility.springtraining.rabbitmqproducer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OnDemandService {

    private ProducerService producerService;

    @Autowired
    public OnDemandService(ProducerService producerService) {
        this.producerService = producerService;
    }

    @GetMapping(value = "/userdata")
    public ResponseEntity sendDataToExchange(){
        producerService.sendUserToFanout();
        return new ResponseEntity("Userdata will be sent to Queue", HttpStatus.OK);
    }
}
