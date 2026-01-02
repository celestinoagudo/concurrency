package com.grind.two.four.seven.section.util;

import java.time.Duration;

public class CommonUtils {

    private CommonUtils() {
    }

    public static long timer(final Runnable runnable) {
        var start = System.currentTimeMillis();
        runnable.run();
        var end = System.currentTimeMillis();
        return end - start;
    }

    public static void sleep(Duration duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
