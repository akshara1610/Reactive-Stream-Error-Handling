package com.example.demo.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class TemperatureClient {
    Logger logger = LoggerFactory.getLogger(TemperatureClient.class);
    @Bean
    WebClient getWebClient() {
        return WebClient.create("http://localhost:8085");
    }

    @Bean
    CommandLineRunner demo(WebClient client) {
        return args -> {
            client.get()
                    .uri("/temperatures")
                    .accept(MediaType.TEXT_EVENT_STREAM)
                    .retrieve()
                    .bodyToFlux(Integer.class)
                    .map(s -> String.valueOf(s))
                    .subscribe(msg -> {
                        logger.info(msg);
                    });
        };
    }
}
