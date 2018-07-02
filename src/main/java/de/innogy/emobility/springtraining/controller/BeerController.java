package de.innogy.emobility.springtraining.controller;

import de.innogy.emobility.springtraining.exception.OutOfStockException;
import de.innogy.emobility.springtraining.model.BeerItem;
import de.innogy.emobility.springtraining.service.BeerShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BeerController {

    @Autowired
    private BeerShopService beerShopService;

    @GetMapping("/orderBeer")
    public BeerItem orderBeer(@RequestParam String beerName, @RequestParam Integer quantity) throws OutOfStockException {
        return beerShopService.extractBeerItem(beerName, quantity);
    }

}
