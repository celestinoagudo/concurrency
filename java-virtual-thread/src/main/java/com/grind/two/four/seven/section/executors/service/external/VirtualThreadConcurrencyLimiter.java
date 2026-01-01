package com.grind.two.four.seven.section.executors.service.external;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

public class VirtualThreadConcurrencyLimiter implements AutoCloseable {

    private static final Logger log = LoggerFactory.getLogger(VirtualThreadConcurrencyLimiter.class);

    private final ExecutorService executorService;
    private final Semaphore semaphore;

    public VirtualThreadConcurrencyLimiter(final ExecutorService executorService, final int limit) {
        this.executorService = executorService;
        this.semaphore = new Semaphore(limit);
    }

    public <T> Future<T> submit(final Callable<T> callable) {
        return executorService.submit(() -> wrapCallable(callable));
    }

    private <T> T wrapCallable(Callable<T> callable) {
        try {
            semaphore.acquire();
            return callable.call();
        } catch (final Exception e) {
            log.error("Error: {}", e.getMessage(), e);
        } finally {
            semaphore.release();
        }
        return null;
    }

    @Override
    public void close() throws Exception {
        executorService.close();
    }
}
