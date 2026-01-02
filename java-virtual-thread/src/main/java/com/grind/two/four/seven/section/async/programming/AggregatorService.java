package com.grind.two.four.seven.section.async.programming;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AggregatorService {

    private final ExecutorService executorService;

    public AggregatorService(final ExecutorService executorService) {
        this.executorService = executorService;
    }

    private static final Logger log = LoggerFactory.getLogger(AggregatorService.class);

    public static void main(String[] args) {
        var aggregatorService = new AggregatorService(Executors.newVirtualThreadPerTaskExecutor());
        final var productDto = aggregatorService.getProductDto(51);
        log.info("Product: {}", productDto);
    }

    public ProductDto getProductDto(final int id) {
        var product = CompletableFuture.supplyAsync(() -> Client.getProduct(id), executorService)
                .exceptionally(_ -> "product not found!")
                .orTimeout(2000, TimeUnit.MILLISECONDS)
                .exceptionally(_ -> "timeout while getting product!");
        var rating = CompletableFuture.supplyAsync(() -> Client.getRating(id), executorService)
                .exceptionally(_ -> -1)
                .orTimeout(1000, TimeUnit.MILLISECONDS)
                .exceptionally(_ -> -2);
        return new ProductDto(product.join(), rating.join());
    }
}
