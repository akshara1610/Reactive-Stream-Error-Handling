package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Random;
import java.util.stream.Stream;

@RestController
public class TemperatureController {
    Logger logger = LoggerFactory.getLogger(TemperatureController.class);

    @GetMapping(value = "/temperatures", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Integer> getTemperature() {
        Random r = new Random();
        int low = 0;
        int high = 50;
        return Flux.fromStream(Stream.generate(() -> r.nextInt(high - low) + low)
                .map(s -> String.valueOf(s))
                .peek((msg) -> {
                    logger.info(msg);
                }))
                .map(s -> 100/Integer.valueOf(s))
                //.onErrorReturn(Integer.MAX_VALUE)
                //.onErrorMap(original-> new Error("Some Error has occured in this step!"))
                .onErrorResume(ex-> Flux.range(1,10))
                //.doOnError(ex->logger.error("We encountered some error at this step!",ex))
                .delayElements(Duration.ofSeconds(1));
    }
}
