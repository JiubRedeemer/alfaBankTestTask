/*
 * Copyright (c) 2022. Vladislav Golubev
 */

package com.jiubredeemer.alfabanktesttask.controller;

import com.jiubredeemer.alfabanktesttask.domain.Gif;
import com.jiubredeemer.alfabanktesttask.exceptions.InternalServiceException;
import com.jiubredeemer.alfabanktesttask.exceptions.InvalidCurrencyException;
import com.jiubredeemer.alfabanktesttask.service.exchange.ExchangeService;
import com.jiubredeemer.alfabanktesttask.service.giphy.GiphyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExchangeGifRestController {

    @Autowired
    private ExchangeService exchangeService;

    @Autowired
    private GiphyService giphyService;

    @GetMapping("api/currencies/{currency}/get-gif")
    public ResponseEntity<Gif> getGif(@PathVariable String currency) throws InvalidCurrencyException, InternalServiceException {
        Gif gif = giphyService.getGifByExchangeStatus(exchangeService.getExchangeStatusByCurrency(currency));
        return ResponseEntity.ok(gif);
    }
}
