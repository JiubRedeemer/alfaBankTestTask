package com.jiubredeemer.alfabanktesttask;

import com.jiubredeemer.alfabanktesttask.service.ExchangeServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.core.Local;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
@EnableFeignClients
public class AlfaBankTestTaskApplication {
    public static void main(String[] args) {
        SpringApplication.run(AlfaBankTestTaskApplication.class, args);
    }

}
