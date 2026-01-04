package com.grind.two.four.seven.section.structured.concurrency;

import com.grind.two.four.seven.section.structured.concurrency.service.FlightPriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.StructuredTaskScope;

public class AwaitAllDemo {
    private static final Logger log = LoggerFactory.getLogger(AwaitAllDemo.class);

    public static void main(String[] args) {
        try (var scope = StructuredTaskScope.open(StructuredTaskScope.Joiner.awaitAll())) {
            var task1 = scope.fork(FlightPriceService::getDeltaAirfare);
            var task2 = scope.fork(FlightPriceService::getFrontierAirfare);

            scope.join();

            log.info("task1 state: {}", task1.state());
            log.info("task2 state: {}", task2.state());

            log.info("task1 result: {}", task1.get());
            log.info("task2 result: {}", task2.get());


        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
