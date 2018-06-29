package de.innogy.emobility.springtraining.controller;

import de.innogy.emobility.springtraining.model.BeerItem;
import de.innogy.emobility.springtraining.model.BeerOrder;
import de.innogy.emobility.springtraining.service.BeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrdersController {
    @Autowired
    private BeerService beerService;
    
    @GetMapping(value = "getBeer", produces = MediaType.APPLICATION_JSON_VALUE)
    public BeerItem getBeer(@RequestParam String client, @RequestParam String beerName) {
        return beerService.getBeer(client, beerName);
    }
    
    @PostMapping(value = "placeOrder", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String placeOrder(@RequestBody BeerOrder beerOrder) {
        return beerService.placeOrder(beerOrder);
    }
    
    @GetMapping("getOrders")
    public List<BeerOrder> getOrders(@RequestParam String client) {
        return beerService.getOrdersForClient(client);
    }
    
    @GetMapping("getOrderById")
    public BeerOrder getOrderById(@RequestParam Integer id) {
        return beerService.findBeerOrderById(id);
    }
}
