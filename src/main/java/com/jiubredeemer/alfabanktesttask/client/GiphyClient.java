/*
 * Copyright (c) 2022. Vladislav Golubev
 */

package com.jiubredeemer.alfabanktesttask.client;

import com.jiubredeemer.alfabanktesttask.domain.dto.GiphyDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "GiphyClient", url = "${gif.service-url}")
public interface GiphyClient {
    @GetMapping("/random?api_key={api_key}&tag={tag}&rating={rating}")
    GiphyDTO getGif(@PathVariable("api_key") String api_key,
                           @PathVariable("tag") String tag,
                           @PathVariable("rating") String rating);
}
