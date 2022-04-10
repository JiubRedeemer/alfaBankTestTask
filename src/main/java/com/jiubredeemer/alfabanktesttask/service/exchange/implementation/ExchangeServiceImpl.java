package com.jiubredeemer.alfabanktesttask.service.exchange.implementation;

import com.jiubredeemer.alfabanktesttask.client.ExchangeClient;
import com.jiubredeemer.alfabanktesttask.domain.Exchange;
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
    public Exchange getExchangeByDate(LocalDate date) {
        return exchangeClient.getHistoricalExchange(exchangeAppId, date.toString(), baseCurrency);
    }

    @Override
    public ExchangeStatus getExchangeStatusByCurrency(String currency) {
        Exchange currencyToday = getExchangeByDate(LocalDate.now());
        Exchange currencyYesterday = getExchangeByDate(LocalDate.now().minusDays(1));

        if (currencyToday.getRates().get(currency) > currencyYesterday.getRates().get(currency)) {
            return ExchangeStatus.RICH;
        } else if (currencyToday.getRates().get(currency) < currencyYesterday.getRates().get(currency)) {
            return ExchangeStatus.BROKE;
        } else return ExchangeStatus.SAME;
    }


}
