package com.grind.two.four.seven.virthualthreads.dto;

import java.time.LocalDate;

public record TripReservationRequest(String departure, String arrival, LocalDate date) {
}
