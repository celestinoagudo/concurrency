package com.grind.two.four.seven.section.async.programming;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class SimpleCompletableFuture {

    private static final Logger log = LoggerFactory.getLogger(SimpleCompletableFuture.class);

    public static void main(String[] args) throws InterruptedException {
        log.info("main starts..");
        var future = slowTask();
        future.thenAccept(log::info);
        log.info("main ends..");
        Thread.sleep(Duration.ofSeconds(3));
    }

    private static CompletableFuture<String> fastTask() {
        log.info("method starts..");
        var completableFuture = new CompletableFuture<String>();
        completableFuture.complete("hi");
        log.info("method ends..");
        return completableFuture;
    }

    private static CompletableFuture<String> slowTask() {
        log.info("slow method starts..");
        var completableFuture = new CompletableFuture<String>();
        Thread.ofVirtual().start(() -> {
            try {
                Thread.sleep(Duration.ofSeconds(1));
                completableFuture.complete("hi");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        log.info("slow method ends..");
        return completableFuture;
    }
}
