package com.jiubredeemer.alfabanktesttask.service.giphy.implementation;

import com.jiubredeemer.alfabanktesttask.client.GiphyClient;
import com.jiubredeemer.alfabanktesttask.domain.Gif;
import com.jiubredeemer.alfabanktesttask.domain.dto.GiphyDTO;
import com.jiubredeemer.alfabanktesttask.service.exchange.ExchangeStatus;
import com.jiubredeemer.alfabanktesttask.service.giphy.GiphyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GiphyServiceImpl implements GiphyService {

    @Autowired
    GiphyClient giphyClient;

    @Value("${gif.api-key}")
    private String apiKey;
    @Value("${gif.rich-tag}")
    private String richTag;
    @Value("${gif.broke-tag}")
    private String brokeTag;
    @Value("${gif.rating}")
    private String rating;

    @Override
    public GiphyDTO getRandomGifByTag(String tag) {
        return giphyClient.getGif(apiKey, tag, rating);
    }

    @Override
    public Gif getGifByExchangeStatus(ExchangeStatus exchangeStatus) {
        switch (exchangeStatus) {
            case RICH:
                return getRandomGifByTag(richTag).getData();
            case BROKE:
                return getRandomGifByTag(brokeTag).getData();
            default:
                throw new IllegalStateException();
        }
    }

    @Override
    public String getGifPageByExchangeStatus(ExchangeStatus exchangeStatus) {
        return "<img src=\"https://media0.giphy.com/media/" + getGifByExchangeStatus(exchangeStatus).getId() + "/giphy.gif\">";
    }
}
