package com.jiubredeemer.alfabanktesttask;

import com.jiubredeemer.alfabanktesttask.domain.Exchange;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AlfaBankTestTaskApplication.class);
    }

}
