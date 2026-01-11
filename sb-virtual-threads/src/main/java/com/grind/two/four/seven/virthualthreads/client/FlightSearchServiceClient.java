package com.grind.two.four.seven.virthualthreads.client;

import com.grind.two.four.seven.virthualthreads.dto.Flight;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

@RequiredArgsConstructor
public class FlightSearchServiceClient {

    private final RestClient restClient;

    public List<Flight> searchFlights(final String departure, final String arrival) {
        return restClient.get().uri("/{departure}/{arrival}", departure, arrival)
                .retrieve().body(new ParameterizedTypeReference<>() {
                });
    }
}
