/*
 * Copyright (c) 2022. Vladislav Golubev
 */

package com.jiubredeemer.alfabanktesttask.controller;

import com.jiubredeemer.alfabanktesttask.exceptions.InternalServiceException;
import com.jiubredeemer.alfabanktesttask.exceptions.InvalidCurrencyException;
import com.jiubredeemer.alfabanktesttask.service.exchange.implementation.ExchangeServiceImpl;
import com.jiubredeemer.alfabanktesttask.service.giphy.GiphyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class ExchangeGifController {
    @Autowired
    private ExchangeServiceImpl exchangeService;

    @Autowired
    private GiphyService giphyService;

    @GetMapping("currencies/{currency}/get-gif")
    public ResponseEntity<String> getGif(@PathVariable String currency) throws InvalidCurrencyException, InternalServiceException {
        String gif = giphyService.getGifPageByExchangeStatus(exchangeService.getExchangeStatusByCurrency(currency));
        return ResponseEntity.ok(gif);
    }

    @ExceptionHandler(InvalidCurrencyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleInvalidCurrencyException(InvalidCurrencyException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler(InternalServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleInternalServiceException(InternalServiceException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleNullPointerException() {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Internal server error, please, try again later.");
    }
}
