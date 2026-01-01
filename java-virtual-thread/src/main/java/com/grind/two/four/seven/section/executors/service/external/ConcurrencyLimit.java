package com.grind.two.four.seven.section.executors.service.external;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrencyLimit {
    private static final Logger log = LoggerFactory.getLogger(ConcurrencyLimit.class);

    public static void main(String[] args) {
//        execute(Executors.newCachedThreadPool(), 20);
        var factory = Thread.ofVirtual().name("virtual", 1).factory();
        execute(Executors.newFixedThreadPool(3, factory), 20);
    }

    private static void execute(final ExecutorService executorService, final int taskCount) {
        try (executorService) {
            for (int i = 1; i <= taskCount; i++) {
                int j = i;
                executorService.submit(() -> printProductInfo(j));
            }
        }
    }

    private static void printProductInfo(int id) {
        log.info("{} => {}", id, Client.getProduct(id));
    }

}
