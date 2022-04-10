/*
 * Copyright (c) 2022. Vladislav Golubev
 */

package com.jiubredeemer.alfabanktesttask.service.exchange;

import com.jiubredeemer.alfabanktesttask.domain.Exchange;
import com.jiubredeemer.alfabanktesttask.exceptions.InternalServiceException;
import com.jiubredeemer.alfabanktesttask.exceptions.InvalidCurrencyException;

import java.time.LocalDate;

public interface ExchangeService {

    Exchange getExchangeByDate(LocalDate date) throws InternalServiceException;

    ExchangeStatus getExchangeStatusByCurrency(String currency) throws InvalidCurrencyException, InternalServiceException;

}
