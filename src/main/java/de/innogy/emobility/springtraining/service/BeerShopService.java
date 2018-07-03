package de.innogy.emobility.springtraining.service;

import de.innogy.emobility.springtraining.exception.OutOfStockException;
import de.innogy.emobility.springtraining.exception.SorryAlcoholicOnlyDudeException;
import de.innogy.emobility.springtraining.model.BeerItem;
import de.innogy.emobility.springtraining.repository.BeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class BeerShopService {

    @Autowired
    private BeerRepository beerRepository;

    @PostConstruct
    public void initialize() {
        List<BeerItem> beerItems = new ArrayList<>();
        beerItems.add(new BeerItem("Innogy Premium Pils",500,4.8,100));
        beerItems.add(new BeerItem("Innogy Energy Stout",500,8.6,100));
        beerItems.add(new BeerItem("Innogy eMobility Export",500,6.2,100));
        beerItems.add(new BeerItem("Innogy Electric Charge",500,12.4,100));
        beerItems.add(new BeerItem("Innogy Diesel",500,4.9,100));
        beerItems.add(new BeerItem("Innogy Cool Water",500,0.0,100));
        beerRepository.saveAll(beerItems);
    }
    
    public void addToStock(List<BeerItem> beerItems) {
        for (BeerItem beerItem : beerItems) {
            BeerItem existing = beerRepository.findById(beerItem.getBeerName()).orElse(null);
            if (existing!=null) {
                existing.addQuantity(beerItem.getQuantity());
            } else {
                existing = beerItem;
            }
            beerRepository.save(existing);
        }
    }
    
    public BeerItem extractBeerItem(String beerName, int quantity) throws OutOfStockException {
        BeerItem beerItem = beerRepository.findById(beerName).orElse(null);
        if (beerItem==null) {
            throw new OutOfStockException(beerName);
        } else if (beerItem.getQuantity() <= quantity) {
            beerItem.setQuantity(0);
        } else {
            beerItem.addQuantity(quantity * -1);
        }
        beerRepository.save(beerItem);
        return new BeerItem(beerItem.getBeerName(), beerItem.getBottleSizeInMl(), beerItem.getAlcoholVol(), quantity);
    }

    public void clearFromStock(String beerName) {
        beerRepository.deleteById(beerName);
    }

    public List<BeerItem> provideNonAlcoholicBeer() throws SorryAlcoholicOnlyDudeException {
        List<BeerItem> beers = beerRepository.provideNonAlcoholicSortiment();
        if (beers.isEmpty()) {
            throw new SorryAlcoholicOnlyDudeException();
        }
        return beers;
    }

    public List<BeerItem> lookupBeerSortimentDueForRestock() {
        return beerRepository.findAllByQuantityIsLessThanEqual(5);
    }

}
