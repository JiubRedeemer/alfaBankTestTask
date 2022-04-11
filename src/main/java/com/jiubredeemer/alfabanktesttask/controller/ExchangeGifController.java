/*
 * Copyright (c) 2022. Vladislav Golubev
 */

package com.jiubredeemer.alfabanktesttask.controller;

import com.jiubredeemer.alfabanktesttask.exceptions.InternalServiceException;
import com.jiubredeemer.alfabanktesttask.exceptions.InvalidCurrencyException;
import com.jiubredeemer.alfabanktesttask.service.exchange.implementation.ExchangeServiceImpl;
import com.jiubredeemer.alfabanktesttask.service.giphy.GiphyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ExchangeGifController {
    @Autowired
    ExchangeServiceImpl exchangeService;

    @Autowired
    GiphyService giphyService;

    @GetMapping("currencies/{currency}/get-gif")
    ResponseEntity<String> getGif(@PathVariable String currency) throws InvalidCurrencyException, InternalServiceException {
        String gif = giphyService.getGifPageByExchangeStatus(exchangeService.getExchangeStatusByCurrency(currency));
        return ResponseEntity.ok(gif);
    }
}
