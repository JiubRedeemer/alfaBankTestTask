package com.jiubredeemer.alfabanktesttask.service.giphy;

import com.jiubredeemer.alfabanktesttask.domain.Gif;
import com.jiubredeemer.alfabanktesttask.domain.dto.GiphyDTO;
import com.jiubredeemer.alfabanktesttask.service.exchange.ExchangeStatus;

public interface GiphyService {
    GiphyDTO getRandomGifByTag(String tag);

    Gif getGifByExchangeStatus(ExchangeStatus exchangeStatus);

    String getGifPageByExchangeStatus(ExchangeStatus exchangeStatus);
}
