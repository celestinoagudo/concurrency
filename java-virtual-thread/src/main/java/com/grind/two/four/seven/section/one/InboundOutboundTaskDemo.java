package com.grind.two.four.seven.section.one;

import java.util.concurrent.CountDownLatch;

public class InboundOutboundTaskDemo {

    private static final int MAX_PLATFORM = 10;


    public static void main(String[] args) throws InterruptedException {
        platformThreadDemo3();
    }

    private static void platformThreadDemo() {
        for (int i = 0; i < MAX_PLATFORM; ++i) {
            var j = i;
            var thread = new Thread(() -> {
                try {
                    Task.toIntensive(j);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            thread.start();
        }
    }

    private static void platformThreadDemo2() {
        var threadBuilder = Thread.ofPlatform().name("g247-", 1);
        for (int i = 0; i < MAX_PLATFORM; ++i) {
            var j = i;
            var thread = threadBuilder.unstarted(() -> {
                try {
                    Task.toIntensive(j);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            thread.start();
        }
    }

    private static void platformThreadDemo3() throws InterruptedException {
        var latch = new CountDownLatch(MAX_PLATFORM);
        var threadBuilder = Thread.ofPlatform().daemon().name("g247-daemon-", 1);
        for (int i = 0; i < MAX_PLATFORM; ++i) {
            var j = i;
            var thread = threadBuilder.unstarted(() -> {
                try {
                    Task.toIntensive(j);
                    latch.countDown();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            thread.start();
        }
        latch.await();
    }


}
