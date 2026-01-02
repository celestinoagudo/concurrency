package com.grind.two.four.seven.section.async.programming;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;

public class GetProducts {

    private static final Logger log = LoggerFactory.getLogger(GetProducts.class);

    public static void main(String[] args) throws Exception {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            final Function<Throwable, String> handleException = t -> {
                log.error("An issue happened: {}", t.getMessage());
                return null;
            };
            final Runnable logCompleted = () -> log.info("Product Retrieval Completed!");
            final Consumer<String> logProduct = product -> log.info("Product: {}", product);
            CompletableFuture.supplyAsync(() -> Client.getProduct(1), executor)
                    .exceptionally(handleException)
                    .thenAccept(logProduct)
                    .thenRun(logCompleted);
            CompletableFuture.supplyAsync(() -> Client.getProduct(2), executor)
                    .exceptionally(handleException)
                    .thenAccept(logProduct)
                    .thenRun(logCompleted);
            CompletableFuture.supplyAsync(() -> Client.getProduct(3), executor)
                    .exceptionally(handleException)
                    .thenAccept(logProduct)
                    .thenRun(logCompleted);
        }
    }
}
