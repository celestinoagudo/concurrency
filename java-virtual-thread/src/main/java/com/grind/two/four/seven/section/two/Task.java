package com.grind.two.four.seven.section.two;

import com.grind.two.four.seven.section.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Task {

    private Task() {
    }


    private static final Logger log = LoggerFactory.getLogger(Task.class);

    public static void cpuIntensive(int i) {
//        log.info("starting CPU task. Thread info: {}", Thread.currentThread());
        var timeTaken = CommonUtils.timer(() -> Task.findFib(i));
//        log.info("ending CPU Task. time taken: {}ms", timeTaken);
    }

    public static long findFib(long input) {
        if (input < 2) {
            return input;
        }
        return findFib(input - 1) + findFib(input - 2);
    }
}
