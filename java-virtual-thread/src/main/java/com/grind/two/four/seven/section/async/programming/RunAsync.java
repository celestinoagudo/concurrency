package com.grind.two.four.seven.section.async.programming;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class RunAsync {


    private static final Logger log = LoggerFactory.getLogger(RunAsync.class);

    public static void main(String[] args) throws InterruptedException {
        log.info("main starts..");
        runAsync().thenRun(() -> log.info("task is done!")).exceptionally(e -> {
            log.error("An exception happened: {}", e.getMessage());
            return null;
        });
        log.info("main ends..");
        Thread.sleep(Duration.ofSeconds(3));
    }

    private static CompletableFuture<Void> runAsync() {
        log.info("slow method starts..");
        final var cf = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(Duration.ofSeconds(1));
//                log.info("Task completed!");
                throw new RuntimeException("a test actually :)");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, Executors.newVirtualThreadPerTaskExecutor());
        log.info("slow method ends..");
        return cf;
    }
}
