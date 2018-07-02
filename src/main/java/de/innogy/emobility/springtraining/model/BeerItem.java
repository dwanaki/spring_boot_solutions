package de.innogy.emobility.springtraining.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="BEER_ITEM")
public class BeerItem {

    @Id
    @Column(name="BEER_NAME")
    private String beerName;

    @Column(name="BOTTLE_SIZE_ML")
    @NotNull
    private Integer bottleSizeInMl;

    @Column(name="ALCOHOL_VOL")
    @NotNull
    private Double alcoholVol;

    @Column(name="QUANTITY")
    @NotNull
    private Integer quantity;
    
    public BeerItem addQuantity(Integer quantity) {
        setQuantity(getQuantity() + quantity);
        return this;
    }

}
