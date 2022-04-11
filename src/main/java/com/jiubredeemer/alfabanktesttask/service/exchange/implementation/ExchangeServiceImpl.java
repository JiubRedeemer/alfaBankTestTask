/*
 * Copyright (c) 2022. Vladislav Golubev
 */

package com.jiubredeemer.alfabanktesttask.service.exchange.implementation;

import com.jiubredeemer.alfabanktesttask.client.ExchangeClient;
import com.jiubredeemer.alfabanktesttask.domain.Exchange;
import com.jiubredeemer.alfabanktesttask.exceptions.InternalServiceException;
import com.jiubredeemer.alfabanktesttask.exceptions.InvalidCurrencyException;
import com.jiubredeemer.alfabanktesttask.service.exchange.ExchangeService;
import com.jiubredeemer.alfabanktesttask.service.exchange.ExchangeStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
public class ExchangeServiceImpl implements ExchangeService {

    @Autowired
    private ExchangeClient exchangeClient;

    @Value("${exchange.app-id}")
    private String exchangeAppId;

    @Value("${exchange.base-currency}")
    private String baseCurrency;


    @Override
    public Exchange getExchangeByDate(LocalDate date) throws InternalServiceException {
        Exchange exchange = exchangeClient.getHistoricalExchange(exchangeAppId, date.toString(), baseCurrency);
        if (exchange == null) {
            log.error("Exchange service is down");
            throw new InternalServiceException();
        }
        log.info("Get exchange: Base[" + exchange.getBase() + "] - " + date);
        return exchange;
    }

    @Override
    public ExchangeStatus getExchangeStatusByCurrency(String currency) throws InvalidCurrencyException, InternalServiceException {
        Exchange currencyToday = getExchangeByDate(LocalDate.now());
        Exchange currencyYesterday = getExchangeByDate(LocalDate.now().minusDays(1));
        log.info("Get exchange today: " + currency + "-" + currencyToday.getRates().get(currency));
        log.info("Get exchange yesterday: " + currency + "-" + currencyYesterday.getRates().get(currency));
        if (currencyToday.getRates().get(currency) == null || currencyYesterday.getRates().get(currency) == null) {
            log.error("Illegal currency");
            throw new InvalidCurrencyException();
        }
        if (currencyToday.getRates().get(currency) > currencyYesterday.getRates().get(currency)) {
            return ExchangeStatus.INCREASED;
        } else if (currencyToday.getRates().get(currency) < currencyYesterday.getRates().get(currency)) {
            return ExchangeStatus.DECREASED;
        } else return ExchangeStatus.EQUALS;
    }


}
