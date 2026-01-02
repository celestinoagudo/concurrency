package com.grind.two.four.seven.section.async.programming;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public class ThenCombineAsync {
    private static final Logger log = LoggerFactory.getLogger(ThenCombineAsync.class);

    record Airfare(String airline, int amount) {
    }

    public static void main(String[] args) {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            var cf1 = getDeltaAirfare(executor);
            var cf2 = getFrontierAirfare(executor);
            final BinaryOperator<Airfare> getMinimumFare = (fare1, fare2) ->
                    fare1.amount <= fare2.amount ? fare1 : fare2;
            final UnaryOperator<Airfare> discount = fare -> {
                log.info("original fare amount: {}", fare.amount);
                return new Airfare(fare.airline, (int) (fare.amount * 0.9));
            };
            final var bestDeal = cf1.thenCombine(cf2, getMinimumFare).thenApply(discount).join();

            log.info("airfare={}", bestDeal);
        }
    }


    private static CompletableFuture<Airfare> getDeltaAirfare(final ExecutorService executorService) {
        return CompletableFuture.supplyAsync(() -> {
            var random = ThreadLocalRandom.current().nextInt(100, 1000);
            try {
                Thread.sleep(Duration.ofMillis(random));
                log.info("Delta: {}", random);
                return new Airfare("Delta", random);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, executorService);
    }

    private static CompletableFuture<Airfare> getFrontierAirfare(final ExecutorService executorService) {
        return CompletableFuture.supplyAsync(() -> {
            var random = ThreadLocalRandom.current().nextInt(100, 1000);
            try {
                Thread.sleep(Duration.ofMillis(random));
                log.info("Frontier: {}", random);
                return new Airfare("Frontier", random);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, executorService);
    }


}
