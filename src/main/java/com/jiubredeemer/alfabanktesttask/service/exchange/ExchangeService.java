package com.jiubredeemer.alfabanktesttask.service.exchange;

import com.jiubredeemer.alfabanktesttask.domain.Exchange;

import java.time.LocalDate;

public interface ExchangeService {

    Exchange getExchangeByDate(LocalDate date);

    ExchangeStatus getExchangeStatusByCurrency(String currency);

}
