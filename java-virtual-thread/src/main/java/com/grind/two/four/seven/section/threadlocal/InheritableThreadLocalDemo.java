package com.grind.two.four.seven.section.threadlocal;

import com.grind.two.four.seven.section.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.UUID;

public class InheritableThreadLocalDemo {
    private static final Logger log = LoggerFactory.getLogger(InheritableThreadLocalDemo.class);
    private static final ThreadLocal<String> sessionTokenHolder = new InheritableThreadLocal<>(); //make sure it's immutable object.

    public static void main(String[] args) {
        authFilter(InheritableThreadLocalDemo::orderController);
        authFilter(InheritableThreadLocalDemo::orderController);
        Thread.ofVirtual().name("request-1").start(() -> authFilter(InheritableThreadLocalDemo::orderController));
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
            sessionTokenHolder.remove(); //will not remove for child thread as this is for the main thread.
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
        Thread.ofVirtual().name("child-1").start(InheritableThreadLocalDemo::callProductService); //mutation happens only for child thread
        Thread.ofVirtual().name("child-2").start(InheritableThreadLocalDemo::callInventoryService);
    }

    private static void callProductService() {
        log.info("Calling product service with header. Authorization: Bearer {}", sessionTokenHolder.get());
    }

    private static void callInventoryService() {
        log.info("Calling inventory service with header. Authorization: Bearer {}", sessionTokenHolder.get());
    }
}
