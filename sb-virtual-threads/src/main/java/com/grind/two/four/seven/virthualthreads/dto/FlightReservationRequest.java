package com.grind.two.four.seven.virthualthreads.dto;

import java.time.LocalDate;

public record FlightReservationRequest(String departure, String arrival, String flightNumber, LocalDate tripDate) {
}
