package com.grind.two.four.seven.virthualthreads.service;

import com.grind.two.four.seven.virthualthreads.client.*;
import com.grind.two.four.seven.virthualthreads.dto.TripPlan;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
public class TripPlanService {
    private static final Logger logger = LoggerFactory.getLogger(TripPlanService.class);
    private final EventServiceClient eventServiceClient;
    private final WeatherServiceClient weatherServiceClient;
    private final AccommodationServiceClient accommodationServiceClient;
    private final TransportationServiceClient transportationServiceClient;
    private final LocalRecommendationsServiceClient localRecommendationsServiceClient;
    private final ExecutorService executorService;

    public TripPlan getTripPlan(final String airportCode) {
        var events = executorService.submit(() -> eventServiceClient.getEvents(airportCode));
        var weather = executorService.submit(() -> weatherServiceClient.getWeather(airportCode));
        var accommodations = executorService.submit(() -> accommodationServiceClient.getAccommodations(airportCode));
        var transportation = executorService.submit(() -> transportationServiceClient.getTransportation(airportCode));
        var recommendations = executorService.submit(() -> localRecommendationsServiceClient.getRecommendations(airportCode));
        return new TripPlan(airportCode,
                getOrElse(accommodations, Collections.emptyList()),
                getOrElse(weather, null),
                getOrElse(events, Collections.emptyList()),
                getOrElse(recommendations, null),
                getOrElse(transportation, null));
    }

    private <T> T getOrElse(Future<T> future, T defaultValue) {
        try {
            return future.get();
        } catch (Exception e) {
            logger.error("error", e);
        }
        return defaultValue;
    }
}
