package com.jiubredeemer.alfabanktesttask.controller;

import com.jiubredeemer.alfabanktesttask.service.ExchangeServiceImpl;
import com.jiubredeemer.alfabanktesttask.service.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExchangeRestController {

    @Autowired
    ExchangeServiceImpl exchangeService;

    @GetMapping("/api/exchange")
    ResponseEntity<Status> getExchange(){
        Status exchangeToday = exchangeService.getExchangeStatusByCurrency("EUR");
        return ResponseEntity.ok(exchangeToday);
    }
}
