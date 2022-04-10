/*
 * Copyright (c) 2022. Vladislav Golubev
 */
package com.jiubredeemer.alfabanktesttask.client;

import com.jiubredeemer.alfabanktesttask.domain.Exchange;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "ExchangeClient", url = "${exchange.service-url}")
public interface ExchangeClient {

    @GetMapping("/historical/{date}.json?app_id={app_id}&base={base}")
    Exchange getHistoricalExchange(@PathVariable("app_id") String app_id,
                                   @PathVariable("date") String date,
                                   @PathVariable("base") String base);

}
