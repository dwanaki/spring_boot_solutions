package de.innogy.emobility.springtraining.repository;

import de.innogy.emobility.springtraining.model.BeerItem;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = NONE)
@Slf4j
public class BeerRepositoryIntegrationTest {

    @Autowired
    private BeerRepository beerRepository;

    @Test
    public void testRepositoryBeerItemContents() {
        List<BeerItem> beerItems = beerRepository.findAll();
        for (BeerItem beerItem : beerItems) {
            log.info("Beer Item: "+beerItem);
        }
        Assert.notEmpty(beerItems, "Empty Beer Item List!");
    }
}
