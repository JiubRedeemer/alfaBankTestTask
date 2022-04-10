package com.jiubredeemer.alfabanktesttask.service;

import com.jiubredeemer.alfabanktesttask.domain.Exchange;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public interface ExchangeService {

    Exchange getExchangeByDate(LocalDate date);
    Status getExchangeStatusByCurrency(String currency);

}
