package com.jiubredeemer.alfabanktesttask.client;

import com.jiubredeemer.alfabanktesttask.domain.Exchange;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.URI;


@FeignClient(name = "ExchangeClient", url = "https://openexchangerates.org/api/")
public interface ExchangeClient {

    @GetMapping(consumes= MediaType.APPLICATION_JSON_VALUE)
    Exchange getExchange(URI baseUrl);

}
