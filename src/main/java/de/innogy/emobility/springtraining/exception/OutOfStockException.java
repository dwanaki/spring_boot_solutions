package de.innogy.emobility.springtraining.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class OutOfStockException extends Exception {

    @Getter
    private String emptyBeerStock;

}
