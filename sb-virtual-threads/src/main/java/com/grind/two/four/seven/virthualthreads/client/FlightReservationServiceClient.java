package com.grind.two.four.seven.virthualthreads.client;

import com.grind.two.four.seven.virthualthreads.dto.FlightReservationRequest;
import com.grind.two.four.seven.virthualthreads.dto.FlightReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestClient;

@RequiredArgsConstructor
public class FlightReservationServiceClient {

    private final RestClient restClient;

    public FlightReservationResponse reserve(final FlightReservationRequest flightReservationRequest) {
        return restClient.post().body(flightReservationRequest).retrieve().body(FlightReservationResponse.class);
    }
}
