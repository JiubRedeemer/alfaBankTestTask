package com.jiubredeemer.alfabanktesttask.service;

import com.jiubredeemer.alfabanktesttask.client.ExchangeClient;
import com.jiubredeemer.alfabanktesttask.domain.Exchange;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Service
@Slf4j
public class ExchangeServiceImpl implements ExchangeService{

    @Autowired
    private ExchangeClient exchangeClient;

    @Value("${exchange.serverUrl}") private String exchangeServerUrl;
    @Value("${exchange.app_id}") private String exchangeAppId;
    @Value("${exchange.baseCurrency}") private String baseCurrency;


    @Override
    public Exchange getExchangeByDate(LocalDate date) {

        String uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(exchangeServerUrl)
                .path("api/historical/")
                .path(date+".json")
                .queryParam("app_id", exchangeAppId)
                .queryParam("base", baseCurrency)
                .toUriString();

        log.info(uri);
        return exchangeClient.getExchange(URI.create(uri));
    }

    @Override
    public Status getExchangeStatusByCurrency(String currency) {
        Exchange currencyToday = getExchangeByDate(LocalDate.now());
        Exchange currencyYesterday = getExchangeByDate(LocalDate.now().minusDays(1));
        if(currencyToday.getRates().get(currency) > currencyYesterday.getRates().get(currency)){
            return Status.RICH;
        } else if(currencyToday.getRates().get(currency) < currencyYesterday.getRates().get(currency)){
            return Status.BROKE;
        } else return Status.SAME;
    }


}
