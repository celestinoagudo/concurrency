package com.grind.two.four.seven.section.thread.pinning;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadPinningFix {
    private static final Logger log = LoggerFactory.getLogger(ThreadPinningFix.class);
    private static final Lock lock = new ReentrantLock();

    static {
        System.setProperty("jdk.tracePinnedThreads", "full");
    }

    public static void main(String[] args) throws InterruptedException {
        demo(Thread.ofVirtual());
        Thread.sleep(Duration.ofSeconds(15));
    }

    private static void demo(final Thread.Builder builder) {

        for (int i = 0; i < 50; i++) {
            builder.start(() -> {
                log.info("Update started, {}", Thread.currentThread());
                try {
                    updateSharedDocument();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.info("Update ended, {}", Thread.currentThread());
            });
        }

        for (int i = 0; i < 3; i++) {
            builder.start(() -> {
                log.info("Fetch started, {}", Thread.currentThread());
                try {
                    fetchUserProfile();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.info("Fetch ended, {}", Thread.currentThread());
            });
        }
    }

    private static void updateSharedDocument() throws InterruptedException {
        try {
            lock.lock();
            Thread.sleep(Duration.ofSeconds(10));
        } finally {
            lock.unlock();
        }
    }

    private static void fetchUserProfile() throws InterruptedException {
        Thread.sleep(Duration.ofSeconds(1));
    }
}
