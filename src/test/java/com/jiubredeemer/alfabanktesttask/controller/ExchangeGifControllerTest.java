/*
 * Copyright (c) 2022. Vladislav Golubev
 */

package com.jiubredeemer.alfabanktesttask.controller;

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

@WebMvcTest(ExchangeGifController.class)
@ExtendWith(SpringExtension.class)
class ExchangeGifControllerTest {

    private static final ExchangeStatus TEST_EXCHANGE_STATUS = ExchangeStatus.INCREASED;
    private static final String TEST_CURRENCY = "TST";
    private static final String TEST_CURRENCY_INVALID = "ERR";
    private static final String TEST_CURRENCY_INTERNAL_ERROR = "INT";
    private static final String TEST_CURRENCY_NULL_POINTER = "NUL";
    private static final String TEST_GIF_PAGE = "TEST_URL";
    private static final String TEST_INVALID_CURRENCY_STRING = "Invalid currency!";
    private static final String TEST_INTERNAL_ERROR_STRING = "Internal service error, try again later";
    private static final String TEST_NULL_POINTER_ERROR_STRING = "Internal server error, please, try again later.";


    @MockBean
    private ExchangeServiceImpl exchangeService;

    @MockBean
    private GiphyServiceImpl giphyService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    private void init() throws InvalidCurrencyException, InternalServiceException {
        Mockito.when(exchangeService.getExchangeStatusByCurrency(TEST_CURRENCY)).thenReturn(TEST_EXCHANGE_STATUS);
        Mockito.when(exchangeService.getExchangeStatusByCurrency(TEST_CURRENCY_INVALID)).thenThrow(InvalidCurrencyException.class);
        Mockito.when(exchangeService.getExchangeStatusByCurrency(TEST_CURRENCY_INTERNAL_ERROR)).thenThrow(InternalServiceException.class);
        Mockito.when(exchangeService.getExchangeStatusByCurrency(TEST_CURRENCY_NULL_POINTER)).thenThrow(NullPointerException.class);
        Mockito.when(giphyService.getGifPageByExchangeStatus(TEST_EXCHANGE_STATUS)).thenReturn(TEST_GIF_PAGE);
    }

    @Test
    void getGif() throws Exception {
        this.mockMvc.perform(get("/currencies/" + TEST_CURRENCY + "/get-gif")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(TEST_GIF_PAGE));
    }

    @Test
    void handleInvalidCurrencyException() throws Exception {
        this.mockMvc.perform(get("/currencies/" + TEST_CURRENCY_INVALID + "/get-gif")
                        .accept(MediaType.ALL))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(TEST_INVALID_CURRENCY_STRING));
    }

    @Test
    void handleInternalServiceException() throws Exception {
        this.mockMvc.perform(get("/currencies/" + TEST_CURRENCY_INTERNAL_ERROR + "/get-gif")
                        .accept(MediaType.ALL))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(TEST_INTERNAL_ERROR_STRING));
    }

    @Test
    void handleJsonProcessingException() throws Exception {
        this.mockMvc.perform(get("/currencies/" + TEST_CURRENCY_NULL_POINTER + "/get-gif")
                        .accept(MediaType.ALL))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(TEST_NULL_POINTER_ERROR_STRING));
    }
}