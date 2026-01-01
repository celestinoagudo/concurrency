package com.grind.two.four.seven.section.executors.service.external;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;

public class VirtualThreadConcurrencyLimiterWithSemaphore {

    private static final Logger log = LoggerFactory.getLogger(VirtualThreadConcurrencyLimiterWithSemaphore.class);

    public static void main(String[] args) throws Exception {
        var factory = Thread.ofVirtual().name("virtual-", 1).factory();
        var limiter = new VirtualThreadConcurrencyLimiter(Executors.newThreadPerTaskExecutor(factory), 3);
        execute(limiter, 20);

    }

    private static void execute(final VirtualThreadConcurrencyLimiter concurrencyLimiter, final int taskCount) throws Exception {
        try (concurrencyLimiter) {
            for (int i = 1; i <= taskCount; i++) {
                int j = i;
                concurrencyLimiter.submit(() -> printProductInfo(j));
            }
            log.info("submitted");
        }
    }

    private static String printProductInfo(final int id) {
        var product = Client.getProduct(id);
        log.info("{} => {}", id, product);
        return product;
    }

}
