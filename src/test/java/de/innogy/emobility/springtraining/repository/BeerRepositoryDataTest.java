package de.innogy.emobility.springtraining.repository;


import de.innogy.emobility.springtraining.model.BeerItem;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest
public class BeerRepositoryDataTest {

    @Autowired
    private BeerRepository beerRepository;

    @Before
    public void initialize() {
        List<BeerItem> beerItems = new ArrayList<>();
        beerItems.add(new BeerItem("Innogy Premium Pils",500,4.8,100));
        beerItems.add(new BeerItem("Innogy Energy Stout",500,8.6,100));
        beerRepository.saveAll(beerItems);
    }

    @Test
    public void testRepositoryBeerItemContents() {
        List<BeerItem> beerItems = beerRepository.findAll();
        for (BeerItem beerItem : beerItems) {
            log.info("Beer Item: "+beerItem);
        }
        Assert.notEmpty(beerItems, "Empty Beer Item List!");
    }

}
