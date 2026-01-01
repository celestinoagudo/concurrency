package com.grind.two.four.seven.section.executors.service.external;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Gatherers;
import java.util.stream.IntStream;

public class MapConcurrentWithVirtualThreads {

    private MapConcurrentWithVirtualThreads() {
    }

    private static final Logger log = LoggerFactory.getLogger(MapConcurrentWithVirtualThreads.class);

    static void main() {
        final List<String> list = IntStream.rangeClosed(1, 50).boxed()
                .gather(Gatherers.mapConcurrent(3, MapConcurrentWithVirtualThreads::getProductInfo))
                .toList();
        log.info("Size: {}", list.size());
    }

    private static String getProductInfo(final int id) {
        final String product = Client.getProduct(id);
        log.info("Product: {}", product);
        return product;
    }
}
