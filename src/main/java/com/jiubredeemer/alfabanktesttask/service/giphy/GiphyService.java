package com.jiubredeemer.alfabanktesttask.service.giphy;

import com.jiubredeemer.alfabanktesttask.domain.Gif;
import com.jiubredeemer.alfabanktesttask.domain.dto.GiphyDTO;
import com.jiubredeemer.alfabanktesttask.exceptions.InternalServiceException;
import com.jiubredeemer.alfabanktesttask.service.exchange.ExchangeStatus;

public interface GiphyService {
    GiphyDTO getRandomGifByTag(String tag) throws InternalServiceException;

    Gif getGifByExchangeStatus(ExchangeStatus exchangeStatus) throws InternalServiceException;

    String getGifPageByExchangeStatus(ExchangeStatus exchangeStatus) throws InternalServiceException;
}
