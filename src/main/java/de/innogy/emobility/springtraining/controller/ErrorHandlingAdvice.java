package de.innogy.emobility.springtraining.controller;

import de.innogy.emobility.springtraining.exception.OutOfStockException;
import de.innogy.emobility.springtraining.exception.SorryAlcoholicOnlyDudeException;
import de.innogy.emobility.springtraining.service.SupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorHandlingAdvice {

    @Autowired
    private SupplyService supplyService;

    @ExceptionHandler(OutOfStockException.class)
    public void handleOutOfStockException(OutOfStockException e) {
        supplyService.refillStock(e.getEmptyBeerStock());
    }

    @ExceptionHandler(SorryAlcoholicOnlyDudeException.class)
    @ResponseStatus(code=HttpStatus.NO_CONTENT, reason="I suggest you take a taxi")
    public void handleSorryAlcoholicOnlyDudeException(SorryAlcoholicOnlyDudeException e) {

    }

}
