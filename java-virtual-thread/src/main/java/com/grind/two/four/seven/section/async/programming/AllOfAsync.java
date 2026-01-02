package com.grind.two.four.seven.section.async.programming;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class AllOfAsync {

    private static final Logger log = LoggerFactory.getLogger(AllOfAsync.class);

    public static void main(String[] args) {
        var executor = Executors.newVirtualThreadPerTaskExecutor();
        var aggregator = new AggregatorService(executor);

        var futures = IntStream.rangeClosed(1, 100)
                .mapToObj(id -> CompletableFuture.supplyAsync(() -> aggregator.getProductDto(id), executor)).toList();
        CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new)).join();
        var list = futures.stream().map(CompletableFuture::join).toList();
        log.info("List: {}", list);
    }
}
