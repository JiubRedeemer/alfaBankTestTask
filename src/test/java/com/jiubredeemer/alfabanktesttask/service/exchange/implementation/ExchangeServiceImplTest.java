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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest()
@ExtendWith(SpringExtension.class)
class ExchangeServiceImplTest {

    private static final LocalDate TEST_LOCAL_DATE_ERROR = LocalDate.of(2002, 2, 12);
    private static final String TEST_LOCAL_DATE_STRING_ERROR = TEST_LOCAL_DATE_ERROR.toString();

    public static final Exchange TEST_EXCHANGE_HIGH =
            new Exchange("TestDisclaimer1", "TestLicense1", "TestBase1", new HashMap<>());

    public static final Exchange TEST_EXCHANGE_LOW =
            new Exchange("TestDisclaimer2", "TestLicense2", "TestBase2", new HashMap<>());

    static {
        TEST_EXCHANGE_HIGH.getRates().put("RUB", 100.2);
        TEST_EXCHANGE_LOW.getRates().put("RUB", 10.3);
    }


    @Mock
    private ExchangeClient exchangeClient;

    @InjectMocks
    private ExchangeServiceImpl exchangeService;

    @BeforeEach
    private void init() {
        Mockito.when
                        (exchangeClient.getHistoricalExchange(Mockito.any(),
                                Mockito.eq(TEST_LOCAL_DATE_STRING_ERROR),
                                Mockito.any()))
                .thenReturn(null);

        Mockito.when
                        (exchangeClient.getHistoricalExchange(Mockito.any(),
                                Mockito.eq(LocalDate.now().toString()),
                                Mockito.any()))
                .thenReturn(TEST_EXCHANGE_HIGH);

        Mockito.when
                        (exchangeClient.getHistoricalExchange(Mockito.any(),
                                Mockito.eq(LocalDate.now().minusDays(1).toString()),
                                Mockito.any()))
                .thenReturn(TEST_EXCHANGE_LOW);
    }

    @Test
    void getExchangeByDate() throws InternalServiceException {
        Exchange curExchange = exchangeService.getExchangeByDate(LocalDate.now());
        assertEquals(TEST_EXCHANGE_HIGH, curExchange);
    }

    @Test
    void getExchangeByDate_InternalServiceException() {
        assertThrows(InternalServiceException.class, () -> exchangeService.getExchangeByDate(TEST_LOCAL_DATE_ERROR));
    }

    @Test
    void getExchangeStatusByCurrency() throws InvalidCurrencyException, InternalServiceException {
        ExchangeStatus curStatus = exchangeService.getExchangeStatusByCurrency("RUB");
        assertEquals(ExchangeStatus.INCREASED, curStatus);
    }

    @Test
    void getExchangeStatusByCurrency_InvalidCurrencyException() {
        assertThrows(InvalidCurrencyException.class, () -> exchangeService.getExchangeStatusByCurrency("ERR"));
    }
}