package com.grind.two.four.seven.section.async.programming;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class AnyOfAsync {

    private static final Logger log = LoggerFactory.getLogger(AnyOfAsync.class);

    public static void main(String[] args) {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            var cf1 = getDeltaAirfare(executor);
            var cf2 = getFrontierAirfare(executor);
            log.info("airfare={}", CompletableFuture.anyOf(cf1, cf2).join());
        }
    }

    private static CompletableFuture<String> getDeltaAirfare(final ExecutorService executorService) {
        return CompletableFuture.supplyAsync(() -> {
            var random = ThreadLocalRandom.current().nextInt(100, 1000);
            try {
                Thread.sleep(Duration.ofMillis(random));
                return "Delta-$%s".formatted(random);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, executorService);
    }

    private static CompletableFuture<String> getFrontierAirfare(final ExecutorService executorService) {
        return CompletableFuture.supplyAsync(() -> {
            var random = ThreadLocalRandom.current().nextInt(100, 1000);
            try {
                Thread.sleep(Duration.ofMillis(random));
                return "Frontier-$%s".formatted(random);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, executorService);
    }
}
