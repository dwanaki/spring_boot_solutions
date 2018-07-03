package de.innogy.emobility.springtraining.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BeerItem {

    private String beerName;

    @NotNull
    private Integer bottleSizeInMl;

    @NotNull
    private Double alcoholVol;

    @NotNull
    private Integer quantity;
    
    public BeerItem addQuantity(Integer quantity) {
        setQuantity(getQuantity() + quantity);
        return this;
    }

}
