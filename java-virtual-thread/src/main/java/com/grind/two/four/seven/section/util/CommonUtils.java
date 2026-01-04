package com.grind.two.four.seven.section.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class CommonUtils {

    private static final Logger log = LoggerFactory.getLogger(CommonUtils.class);

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

    public static void sleep(Duration duration, String taskName) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            log.info("Task: {} is interrupted.", taskName, e);
        }
    }
}
