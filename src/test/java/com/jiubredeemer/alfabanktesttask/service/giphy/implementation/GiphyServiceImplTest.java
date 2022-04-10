/*
 * Copyright (c) 2022. Vladislav Golubev
 */

package com.jiubredeemer.alfabanktesttask.service.giphy.implementation;

import com.jiubredeemer.alfabanktesttask.client.GiphyClient;
import com.jiubredeemer.alfabanktesttask.domain.Gif;
import com.jiubredeemer.alfabanktesttask.domain.dto.GiphyDTO;
import com.jiubredeemer.alfabanktesttask.exceptions.InternalServiceException;
import com.jiubredeemer.alfabanktesttask.service.exchange.ExchangeStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.ReflectionUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest()
@ExtendWith(SpringExtension.class)
class GiphyServiceImplTest {
    private static final String TEST_RICH_TAG = "testRichTag";
    private static final String TEST_BROKE_TAG = "testBrokeTag";
    private static final String TEST_ERROR_TAG = "error";
    private static final String TEST_RICH_GIF_ID = "11111";
    private static final String TEST_BROKE_GIF_ID = "22222";

    private static final Gif TEST_RICH_GIF = new Gif();
    private static final Gif TEST_BROKE_GIF = new Gif();

    private static final GiphyDTO TEST_RICH_GIPHY_DTO = new GiphyDTO(TEST_RICH_GIF);
    private static final GiphyDTO TEST_BROKE_GIPHY_DTO = new GiphyDTO(TEST_BROKE_GIF);

    private static final String TEST_RICH_PAGE = "<img src=\"https://media0.giphy.com/media/" + TEST_RICH_GIF.getId() + "/giphy.gif\">";
    private static final String TEST_BROKE_PAGE = "<img src=\"https://media0.giphy.com/media/" + TEST_BROKE_GIF.getId() + "/giphy.gif\">";

    @Mock
    GiphyClient giphyClient;

    @InjectMocks
    GiphyServiceImpl giphyService;

    @BeforeEach
    void init() {
        Mockito.when(giphyClient.getGif(Mockito.any(), Mockito.eq(TEST_RICH_TAG), Mockito.any()))
                .thenReturn(TEST_RICH_GIPHY_DTO);
        Mockito.when(giphyClient.getGif(Mockito.any(), Mockito.eq(TEST_BROKE_TAG), Mockito.any()))
                .thenReturn(TEST_BROKE_GIPHY_DTO);
        ReflectionTestUtils.setField(giphyService, "richTag", TEST_RICH_TAG);
        ReflectionTestUtils.setField(giphyService, "brokeTag", TEST_BROKE_TAG);
    }

    @Test
    void getRandomGifByTagSuccessfully() throws InternalServiceException {
        assertEquals(TEST_RICH_GIPHY_DTO, giphyService.getRandomGifByTag(TEST_RICH_TAG));
        assertEquals(TEST_BROKE_GIPHY_DTO, giphyService.getRandomGifByTag(TEST_BROKE_TAG));
    }

    @Test
    void getRandomGifByTagError() {
        assertThrows(InternalServiceException.class, () -> giphyService.getRandomGifByTag(TEST_ERROR_TAG));
    }

    @Test
    void getGifByExchangeStatusSuccessfully() throws InternalServiceException {
        Gif gifIncreased = giphyService.getGifByExchangeStatus(ExchangeStatus.INCREASED);
        Gif gifDecreased = giphyService.getGifByExchangeStatus(ExchangeStatus.DECREASED);

        assertEquals(gifIncreased, TEST_RICH_GIF);
        assertEquals(gifDecreased, TEST_BROKE_GIF);
    }

    @Test
    void getGifByExchangeStatusError() {
        assertThrows(IllegalStateException.class, () -> giphyService.getGifPageByExchangeStatus(ExchangeStatus.EQUALS));
    }

    @Test
    void getGifPageByExchangeStatus() throws InternalServiceException {
        var actualRichPage = giphyService.getGifPageByExchangeStatus(ExchangeStatus.INCREASED);
        var actualBrokePage = giphyService.getGifPageByExchangeStatus(ExchangeStatus.DECREASED);
        assertEquals(actualRichPage, TEST_RICH_PAGE);
        assertEquals(actualBrokePage, TEST_BROKE_PAGE);
    }
}