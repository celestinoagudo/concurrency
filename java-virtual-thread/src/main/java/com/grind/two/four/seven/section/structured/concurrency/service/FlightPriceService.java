package com.grind.two.four.seven.section.structured.concurrency.service;

import com.grind.two.four.seven.section.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

public class FlightPriceService {
    private static final Logger log = LoggerFactory.getLogger(FlightPriceService.class);

    public static String getDeltaAirfare() {
        log.info("calling delta");
        CommonUtils.sleep(Duration.ofSeconds(1), "delta");
        return "Delta-$".concat("" + ThreadLocalRandom.current().nextInt(100, 1000));
    }

    public static String getFrontierAirfare() {
        log.info("calling frontier");
        CommonUtils.sleep(Duration.ofSeconds(1), "frontier");
        return "Frontier-$".concat("" + ThreadLocalRandom.current().nextInt(100, 1000));
    }

    public static String getUnitedAirfare() {
        log.info("calling unit");
        throw new RuntimeException("503: Service Down!");
    }
}
