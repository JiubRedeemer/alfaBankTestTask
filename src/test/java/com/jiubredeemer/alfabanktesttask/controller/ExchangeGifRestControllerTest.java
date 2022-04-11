/*
 * Copyright (c) 2022. Vladislav Golubev
 */

package com.jiubredeemer.alfabanktesttask.controller;

import com.github.tomakehurst.wiremock.common.Json;
import com.jiubredeemer.alfabanktesttask.domain.Gif;
import com.jiubredeemer.alfabanktesttask.exceptions.InternalServiceException;
import com.jiubredeemer.alfabanktesttask.exceptions.InvalidCurrencyException;
import com.jiubredeemer.alfabanktesttask.service.exchange.ExchangeStatus;
import com.jiubredeemer.alfabanktesttask.service.exchange.implementation.ExchangeServiceImpl;
import com.jiubredeemer.alfabanktesttask.service.giphy.implementation.GiphyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExchangeGifRestController.class)
@ExtendWith(SpringExtension.class)
class ExchangeGifRestControllerTest {

    private static final ExchangeStatus TEST_EXCHANGE_STATUS = ExchangeStatus.INCREASED;
    private static final String TEST_CURRENCY = "TST";
    private static final Gif TEST_GIF = new Gif("testType",
            "testId",
            "testSlug",
            "testBitly",
            "testEmbed",
            "testUsername",
            "testSource",
            "testRating",
            "testContent",
            "testString",
            "testSource",
            "testUpdate",
            "testCreate",
            "testImport",
            "testTrending",
            "testTitle");


    @MockBean
    private ExchangeServiceImpl exchangeService;

    @MockBean
    private GiphyServiceImpl giphyService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    private void init() throws InvalidCurrencyException, InternalServiceException {
        Mockito.when(exchangeService.getExchangeStatusByCurrency(TEST_CURRENCY)).thenReturn(TEST_EXCHANGE_STATUS);
        Mockito.when(giphyService.getGifByExchangeStatus(TEST_EXCHANGE_STATUS)).thenReturn(TEST_GIF);
    }

    @Test
    void getGif() throws Exception {
        this.mockMvc.perform(get("/api/currencies/" + TEST_CURRENCY + "/get-gif")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(Json.write(TEST_GIF)));
    }
}