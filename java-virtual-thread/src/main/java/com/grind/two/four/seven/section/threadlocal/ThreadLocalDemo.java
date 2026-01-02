package com.grind.two.four.seven.section.threadlocal;

import com.grind.two.four.seven.section.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.UUID;

public class ThreadLocalDemo {
    private static final Logger log = LoggerFactory.getLogger(ThreadLocalDemo.class);
    private static final ThreadLocal<String> sessionTokenHolder = new ThreadLocal<>();

    public static void main(String[] args) {
        authFilter(ThreadLocalDemo::orderController);
        authFilter(ThreadLocalDemo::orderController);
        Thread.ofVirtual().name("request-1").start(() -> authFilter(ThreadLocalDemo::orderController));
        Thread.ofVirtual().name("request-2").start(() -> authFilter(ThreadLocalDemo::orderController));
        CommonUtils.sleep(Duration.ofSeconds(3));
    }

    //dummy filter
    private static void authFilter(final Runnable runnable) {
        try {
            var token = authenticate();
            sessionTokenHolder.set(token);
            //forward the request to next filter or process the request itself.
            runnable.run();
        } finally {
            sessionTokenHolder.remove();
        }
    }

    private static String authenticate() {
        var token = UUID.randomUUID().toString();
        log.info("token={}", token);
        return token;
    }

    private static void orderController() {
        log.info("orderController: {}", sessionTokenHolder.get());
        orderService();
    }

    private static void orderService() {
        log.info("OrderService: {}", sessionTokenHolder.get());
        callProductService();
        callInventoryService();

    }

    private static void callProductService() {
        log.info("Calling product service with header. Authorization: Bearer {}", sessionTokenHolder.get());
    }

    private static void callInventoryService() {
        log.info("Calling inventory service with header. Authorization: Bearer {}", sessionTokenHolder.get());
    }
}
