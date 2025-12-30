package com.grind.two.four.seven.section.util;

public class CommonUtils {

    private CommonUtils() {
    }

    public static long timer(final Runnable runnable) {
        var start = System.currentTimeMillis();
        runnable.run();
        var end = System.currentTimeMillis();
        return end - start;
    }
}
