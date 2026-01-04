package com.grind.two.four.seven.section.scoped.value;

import com.grind.two.four.seven.section.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.UUID;

public class ScopedValueRebindingDemo {
    private static final Logger log = LoggerFactory.getLogger(ScopedValueRebindingDemo.class);
    private static final ScopedValue<String> SESSION_TOKEN = ScopedValue.newInstance(); //make sure it's immutable object.

    public static void main(String[] args) {

        Thread.ofVirtual().name("request-1").start(() -> authFilter(ScopedValueRebindingDemo::orderController));
        Thread.ofVirtual().name("request-2").start(() -> authFilter(ScopedValueRebindingDemo::orderController));
        CommonUtils.sleep(Duration.ofSeconds(3));
    }

    //dummy filter
    private static void authFilter(final Runnable runnable) {
        ScopedValue.where(SESSION_TOKEN, authenticate()).run(runnable);
    }

    private static void orderController() {
        log.info("orderController: {}", SESSION_TOKEN.get());
        orderService();
    }

    private static String authenticate() {
        var token = UUID.randomUUID().toString();
        log.info("token={}", token);
        return token;
    }

    private static void orderService() {
        log.info("OrderService: {}", SESSION_TOKEN.get());
        ScopedValue.where(SESSION_TOKEN, "new-token").run(ScopedValueRebindingDemo::callProductService);
        callInventoryService();
    }

    private static void callProductService() {
        log.info("Calling product service with header. Authorization: Bearer {}", SESSION_TOKEN.get());
    }

    private static void callInventoryService() {
        log.info("Calling inventory service with header. Authorization: Bearer {}", SESSION_TOKEN.get());
    }
}
