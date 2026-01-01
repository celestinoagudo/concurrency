package com.grind.two.four.seven.section.executors.service.external;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorWithVirtualThreads {
    private static final Logger log = LoggerFactory.getLogger(ScheduledExecutorWithVirtualThreads.class);

    public static void main(String[] args) throws InterruptedException {
        scheduled();
    }

    private static void scheduled() throws InterruptedException {
        var scheduler = Executors.newSingleThreadScheduledExecutor();
        var executor = Executors.newVirtualThreadPerTaskExecutor();
        try (scheduler; executor) {
            scheduler.scheduleAtFixedRate(() -> executor.submit(() -> printProductInfo(1))
                    , 0, 3, TimeUnit.SECONDS);
            Thread.sleep(Duration.ofSeconds(15));
        }
    }

    private static void printProductInfo(final int id) {
        final String product = Client.getProduct(id);
        log.info("Product: {}", product);
    }
}
