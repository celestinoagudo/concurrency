package com.grind.two.four.seven.virthualthreads.client;

import com.grind.two.four.seven.virthualthreads.dto.Weather;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestClient;

@RequiredArgsConstructor
public class WeatherServiceClient {

    private final RestClient restClient;

    public Weather getWeather(final String airportCode) {
        return restClient.get().uri("{airportCode}", airportCode)
                .retrieve().body(Weather.class);
    }
}
