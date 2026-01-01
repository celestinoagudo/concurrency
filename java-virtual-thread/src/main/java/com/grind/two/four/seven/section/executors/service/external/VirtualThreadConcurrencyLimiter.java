package com.grind.two.four.seven.section.executors.service.external;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.concurrent.*;

public class VirtualThreadConcurrencyLimiter implements AutoCloseable {

    private static final Logger log = LoggerFactory.getLogger(VirtualThreadConcurrencyLimiter.class);

    private final ExecutorService executorService;
    private final Semaphore semaphore;
    private final Queue<Callable<?>> queue;

    public VirtualThreadConcurrencyLimiter(final ExecutorService executorService, final int limit) {
        this.executorService = executorService;
        this.semaphore = new Semaphore(limit);
        this.queue = new ConcurrentLinkedDeque<>();
    }

    public <T> Future<T> submit(final Callable<T> callable) {
        queue.add(callable);
        return executorService.submit(() -> executeTask());
    }

    private <T> T executeTask() {
        try {
            semaphore.acquire();
            return (T) queue.poll().call();
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
