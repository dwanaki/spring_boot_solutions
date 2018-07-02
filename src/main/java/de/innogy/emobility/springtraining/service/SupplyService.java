package de.innogy.emobility.springtraining.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class SupplyService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void refillStock(String beerName) {
        storeOutgoingOrder(beerName, 100);
    }

    private void storeOutgoingOrder(String beerName, int quantity) {
        jdbcTemplate.update("INSERT INTO BEER_STOCK_ORDER (BEER_NAME, QUANTITY) VALUES (?,?)",beerName,quantity);
    }
}
