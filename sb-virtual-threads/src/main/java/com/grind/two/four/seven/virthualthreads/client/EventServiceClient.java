package com.grind.two.four.seven.virthualthreads.client;

import com.grind.two.four.seven.virthualthreads.dto.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

@RequiredArgsConstructor
public class EventServiceClient {

    private final RestClient restClient;

    public List<Event> getEvents(final String airportCode) {
        return restClient.get().uri("{airportCode}", airportCode)
                .retrieve().body(new ParameterizedTypeReference<>() {
                });
    }
}
