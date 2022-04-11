/*
 * Copyright (c) 2022. Vladislav Golubev
 */

package com.jiubredeemer.alfabanktesttask.service.exchange.implementation;

import com.jiubredeemer.alfabanktesttask.client.ExchangeClient;
import com.jiubredeemer.alfabanktesttask.domain.Exchange;
import com.jiubredeemer.alfabanktesttask.exceptions.InternalServiceException;
import com.jiubredeemer.alfabanktesttask.exceptions.InvalidCurrencyException;
import com.jiubredeemer.alfabanktesttask.service.exchange.ExchangeStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest()
@ExtendWith(SpringExtension.class)
class ExchangeServiceImplTest {

    private static final LocalDate TEST_LOCAL_DATE = LocalDate.of(2000, 10, 10);
    private static final LocalDate TEST_LOCAL_DATE_ERROR = LocalDate.of(2000, 10, 10);
    private static final String TEST_LOCAL_DATE_STRING = TEST_LOCAL_DATE.toString();
    private static final String TEST_LOCAL_DATE_STRING_ERROR = TEST_LOCAL_DATE_ERROR.toString();

    public static final Exchange TEST_EXCHANGE_HIGH = new Exchange("TestDisclaimer1", "TestLicense1", "TestBase1", new HashMap<String, Double>() {
    });
    public static final Exchange TEST_EXCHANGE_LOW = new Exchange("TestDisclaimer2", "TestLicense2", "TestBase2", new HashMap<String, Double>());

    {
        TEST_EXCHANGE_HIGH.getRates().put("RUB", 100.2);
        TEST_EXCHANGE_LOW.getRates().put("RUB", 10.3);
    }


    @Mock
    private ExchangeClient exchangeClient;

    @InjectMocks
    private ExchangeServiceImpl exchangeService;

    @BeforeEach
    private void init() throws InternalServiceException {
//        Mockito.when(exchangeService.getExchangeByDate(TEST_LOCAL_DATE)).thenReturn(TEST_EXCHANGE_HIGH);
        Mockito.when
                        (exchangeClient.getHistoricalExchange(Mockito.any(),
                                Mockito.eq(TEST_LOCAL_DATE_STRING),
                                Mockito.any()))
                .thenReturn(TEST_EXCHANGE_HIGH);

        Mockito.when
                        (exchangeClient.getHistoricalExchange(Mockito.any(),
                                Mockito.eq(TEST_LOCAL_DATE_STRING_ERROR),
                                Mockito.any()))
                .thenReturn(null);
    }

//    @Override
//    public Exchange getExchangeByDate(LocalDate date) throws InternalServiceException {
//        Exchange exchange = exchangeClient.getHistoricalExchange(exchangeAppId, date.toString(), baseCurrency);
//        if (exchange == null) {
//            log.error("Exchange service is down");
//            throw new InternalServiceException();
//        }
//        log.info("Get exchange: " + exchange.getBase() + " - " + date);
//        return exchange;
//    }
//
//    @Override
//    public ExchangeStatus getExchangeStatusByCurrency(String currency) throws InvalidCurrencyException, InternalServiceException {
//        Exchange currencyToday = getExchangeByDate(LocalDate.now());
//        Exchange currencyYesterday = getExchangeByDate(LocalDate.now().minusDays(1));
//        if (currencyToday.getRates().get(currency) == null || currencyYesterday.getRates().get(currency) == null) {
//            log.error("Illegal currency");
//            throw new InvalidCurrencyException();
//        }
//        if (currencyToday.getRates().get(currency) > currencyYesterday.getRates().get(currency)) {
//            return ExchangeStatus.INCREASED;
//        } else if (currencyToday.getRates().get(currency) < currencyYesterday.getRates().get(currency)) {
//            return ExchangeStatus.DECREASED;
//        } else return ExchangeStatus.EQUALS;
//    }

    @Test
    void getExchangeByDate() throws InternalServiceException {
        Exchange curExchange = exchangeService.getExchangeByDate(TEST_LOCAL_DATE);
        assertEquals(TEST_EXCHANGE_HIGH, curExchange);
    }
    @Test
    void getExchangeByDate_ERROR() {
        assertThrows(InternalServiceException.class, () -> exchangeService.getExchangeByDate(TEST_LOCAL_DATE_ERROR));
    }

    @Test
    void getExchangeStatusByCurrency() {

    }
}