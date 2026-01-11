package com.grind.two.four.seven.virthualthreads.client;

import com.grind.two.four.seven.virthualthreads.dto.Transportation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestClient;

@RequiredArgsConstructor
public class TransportationServiceClient {

    private final RestClient restClient;

    public Transportation getTransportation(final String airportCode) {
        return restClient.get().uri("{airportCode}", airportCode)
                .retrieve().body(Transportation.class);
    }
}
