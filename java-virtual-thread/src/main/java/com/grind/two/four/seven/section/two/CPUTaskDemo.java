package com.grind.two.four.seven.section.two;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class CPUTaskDemo {

    private static final Logger log = LoggerFactory.getLogger(CPUTaskDemo.class);

    private static final int TASK_COUNT = 2 * Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            var platformStart = System.currentTimeMillis();
            demo(Thread.ofPlatform());
            var platformEnd = System.currentTimeMillis();
            log.info("Time taken platform: {}ms", platformEnd - platformStart);
            var virtualStart = System.currentTimeMillis();
            demo(Thread.ofVirtual());
            var virtualEnd = System.currentTimeMillis();
            log.info("Time taken virtual: {}ms", virtualEnd - virtualStart);
        }
    }

    private static void demo(final Thread.Builder builder) {
        var latch = new CountDownLatch(TASK_COUNT);
        for (int i = 0; i < TASK_COUNT; i++) {
            builder.start(() -> {
                Task.cpuIntensive(45);
                latch.countDown();
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
