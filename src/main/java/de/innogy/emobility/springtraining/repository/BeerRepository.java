package de.innogy.emobility.springtraining.repository;

import de.innogy.emobility.springtraining.model.BeerItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BeerRepository extends JpaRepository<BeerItem, String> {

    @Query("SELECT bi FROM BeerItem bi WHERE bi.alcoholVol = 0.0")
    public List<BeerItem> provideNonAlcoholicSortiment();

    public List<BeerItem> findAllByQuantityIsLessThanEqual(Integer quantity);

}
