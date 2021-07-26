package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;

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
                /*.map(s-> {
                    if(s==10)
                    {
                        throw new RuntimeException("A wrong value has been encountered");
                    }
                    return s*2;
                })*/
                //.onErrorReturn(Integer.MAX_VALUE)
                //.onErrorMap(original-> new Error("Some Error has occured in this step!"))
                //.onErrorResume(ex->Flux.range(50000,10))
                //.doOnError(ex->logger.error("We encountered some error at this step!",ex))
                //.onErrorContinue((s)
                  /*.doFinally( i->{
                      if (SignalType.ON_ERROR.equals(i)) {
                          System.out.println("Completed with Error ");
                      }
                      if (SignalType.ON_COMPLETE.equals(i)) {
                          System.out.println("Completed without Error ");
                      }
                  });*/
                .delayElements(Duration.ofSeconds(1));


    }
}
