package com.example.forever.common.feign;

import feign.Logger;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@EnableFeignClients
@Configuration
public class FeignClientConfig {
    @Bean
    Logger.Level feignClientLoggerLevel() {
        return Logger.Level.FULL;
    }

}