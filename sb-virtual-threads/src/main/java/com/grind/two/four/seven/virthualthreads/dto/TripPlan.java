package com.grind.two.four.seven.virthualthreads.dto;

import java.util.List;

public record TripPlan(String airportCode, List<Accommodation> accomodations, Weather weather, List<Event> events,
                       LocalRecommendations localRecommendations, Transportation transportation) {
}
