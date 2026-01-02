package com.grind.two.four.seven.section.async.programming;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class SupplyAsync {


    private static final Logger log = LoggerFactory.getLogger(SupplyAsync.class);

    public static void main(String[] args) throws InterruptedException {
        log.info("main starts..");
        var cf = slowTask();
        cf.thenAccept(log::info);
        log.info("main ends..");
        Thread.sleep(Duration.ofSeconds(3));
    }

    private static CompletableFuture<String> slowTask() {
        log.info("slow method starts..");
        var completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(Duration.ofSeconds(1));
                return "hi";
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, Executors.newVirtualThreadPerTaskExecutor());
        log.info("slow method ends..");
        return completableFuture;
    }

}
