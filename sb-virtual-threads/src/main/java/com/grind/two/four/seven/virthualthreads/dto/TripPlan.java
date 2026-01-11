package com.grind.two.four.seven.virthualthreads.dto;

import java.util.List;

public record TripPlan(String airportCode, List<Accomodation> accomodations, Weather weather, List<Event> events,
                       LocalRecommendations localRecommendations, Transportation transportation) {
}
