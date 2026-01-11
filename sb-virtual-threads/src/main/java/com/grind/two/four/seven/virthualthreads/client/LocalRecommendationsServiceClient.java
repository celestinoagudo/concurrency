package com.grind.two.four.seven.virthualthreads.client;

import com.grind.two.four.seven.virthualthreads.dto.Event;
import com.grind.two.four.seven.virthualthreads.dto.LocalRecommendations;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

@RequiredArgsConstructor
public class LocalRecommendationsServiceClient {

    private final RestClient restClient;

    public LocalRecommendations getRecommendations(final String airportCode) {
        return restClient.get().uri("{airportCode}", airportCode)
                .retrieve().body(LocalRecommendations.class);
    }
}
