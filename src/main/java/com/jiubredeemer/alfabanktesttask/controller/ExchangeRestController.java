package com.jiubredeemer.alfabanktesttask.controller;

import com.jiubredeemer.alfabanktesttask.domain.Gif;
import com.jiubredeemer.alfabanktesttask.service.exchange.implementation.ExchangeServiceImpl;
import com.jiubredeemer.alfabanktesttask.service.giphy.GiphyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExchangeRestController {

    @Autowired
    ExchangeServiceImpl exchangeService;

    @Autowired
    GiphyService giphyService;

    @GetMapping("api/currencies/{currency}/get-gif")
    ResponseEntity<Gif> getGif(@PathVariable String currency) {
        Gif gif = giphyService.getGifByExchangeStatus(exchangeService.getExchangeStatusByCurrency(currency));
        return ResponseEntity.ok(gif);
    }
}
