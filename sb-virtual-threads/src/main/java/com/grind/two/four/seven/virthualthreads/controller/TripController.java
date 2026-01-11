package com.grind.two.four.seven.virthualthreads.controller;

import com.grind.two.four.seven.virthualthreads.dto.FlightReservationResponse;
import com.grind.two.four.seven.virthualthreads.dto.TripPlan;
import com.grind.two.four.seven.virthualthreads.dto.TripReservationRequest;
import com.grind.two.four.seven.virthualthreads.service.TripPlanService;
import com.grind.two.four.seven.virthualthreads.service.TripReservationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("trip")
@RequiredArgsConstructor
public class TripController {

    Logger logger = LoggerFactory.getLogger(TripController.class);

    private final TripPlanService planService;
    private final TripReservationService reservationService;

    @GetMapping("{airportCode}")
    public TripPlan planTrip(@PathVariable final String airportCode) {
        logger.info("Is Virtual? {}", Thread.currentThread().isVirtual());
        return planService.getTripPlan(airportCode);
    }

    @PostMapping("reserve")
    public FlightReservationResponse reserveFlight(@RequestBody final TripReservationRequest request) {
        return reservationService.reserve(request);
    }
}
