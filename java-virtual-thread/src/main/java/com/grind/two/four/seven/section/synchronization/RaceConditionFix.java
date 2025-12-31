package com.grind.two.four.seven.section.synchronization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class RaceConditionFix {

    private static final Logger log = LoggerFactory.getLogger(RaceConditionFix.class);
    private static final List<Integer> list = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        demo(Thread.ofVirtual());
        Thread.sleep(Duration.ofSeconds(2));
        log.info("Size: {}", list.size());
    }

    private static void demo(final Thread.Builder builder) {
        for (int i = 0; i < 50; i++) {
            builder.start(() -> {
                log.info("Task started, {}", Thread.currentThread());
                for (int j = 0; j < 200; j++) {
                    inMemoryTask();
                }
                log.info("Task ended, {}", Thread.currentThread());
            });
        }
    }

    private static synchronized void inMemoryTask() {
        list.add(1);
    }
}
