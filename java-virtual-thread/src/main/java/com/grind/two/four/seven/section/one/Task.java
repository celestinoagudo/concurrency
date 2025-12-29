package com.grind.two.four.seven.section.one;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class Task {

    private static final Logger log = LoggerFactory.getLogger(Task.class);


    public static void toIntensive(int i) throws InterruptedException {
        log.info("Starting I/O Task {}", i);
        Thread.sleep(Duration.ofSeconds(60));
        log.info("Ending I/O Task {}", i);
    }
}
