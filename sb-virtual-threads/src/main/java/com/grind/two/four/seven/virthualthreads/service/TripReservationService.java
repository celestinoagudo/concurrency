package com.grind.two.four.seven.virthualthreads.service;

import com.grind.two.four.seven.virthualthreads.client.FlightReservationServiceClient;
import com.grind.two.four.seven.virthualthreads.client.FlightSearchServiceClient;
import com.grind.two.four.seven.virthualthreads.dto.Flight;
import com.grind.two.four.seven.virthualthreads.dto.FlightReservationRequest;
import com.grind.two.four.seven.virthualthreads.dto.FlightReservationResponse;
import com.grind.two.four.seven.virthualthreads.dto.TripReservationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class TripReservationService {
    private final FlightReservationServiceClient flightReservationServiceClient;
    private final FlightSearchServiceClient flightSearchServiceClient;

    public FlightReservationResponse reserve(TripReservationRequest request) {
        var flights = flightSearchServiceClient.searchFlights(request.departure(), request.arrival());
        var bestDeal = flights.stream().min(Comparator.comparingInt(Flight::price));
        var flight = bestDeal.orElseThrow(() -> new IllegalStateException("No Flights Found!"));
        var reservationRequest = new FlightReservationRequest(request.departure(), request.arrival(), flight.flightNumber(), request.date());
        return flightReservationServiceClient.reserve(reservationRequest);
    }
}
