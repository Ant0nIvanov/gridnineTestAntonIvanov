package com.gridnine.testing.util;

import com.gridnine.testing.model.Flight;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestUtils {
    private static final LocalDateTime THREE_DAYS_FROM_NOW = LocalDateTime.now().plusDays(3);

    private static final Flight NORMAL_FLIGHT_WITH_TWO_HOURS_DURATION =
            FlightBuilder.createFlight(THREE_DAYS_FROM_NOW, THREE_DAYS_FROM_NOW.plusHours(2));

    private static final Flight NORMAL_MULTY_SEGMENT_FLIGHT =
            FlightBuilder.createFlight(THREE_DAYS_FROM_NOW, THREE_DAYS_FROM_NOW.plusHours(2),
            THREE_DAYS_FROM_NOW.plusHours(3), THREE_DAYS_FROM_NOW.plusHours(5));

    private static final Flight FLIGHT_DEPARTING_IN_THE_PAST =
            FlightBuilder.createFlight(THREE_DAYS_FROM_NOW.minusDays(6), THREE_DAYS_FROM_NOW);

    private static final Flight FLIGHT_THAT_DEPARTS_BEFORE_IT_ARRIVES =
            FlightBuilder.createFlight(THREE_DAYS_FROM_NOW, THREE_DAYS_FROM_NOW.minusHours(6));

    private static final Flight FLIGHT_WITH_MORE_THAN_TWO_HOURS_GROUND_TIME =
            FlightBuilder.createFlight(THREE_DAYS_FROM_NOW, THREE_DAYS_FROM_NOW.plusHours(2),
                    THREE_DAYS_FROM_NOW.plusHours(5), THREE_DAYS_FROM_NOW.plusHours(6));

    private static final Flight ANOTHER_FLIGHT_WITH_MORE_THAN_TWO_HOURS_GROUND_TIME =
            FlightBuilder.createFlight(THREE_DAYS_FROM_NOW, THREE_DAYS_FROM_NOW.plusHours(2),
                    THREE_DAYS_FROM_NOW.plusHours(3), THREE_DAYS_FROM_NOW.plusHours(4),
                    THREE_DAYS_FROM_NOW.plusHours(6), THREE_DAYS_FROM_NOW.plusHours(7));

    private static final List<Flight> ALL_FLIGHTS = List.of(
            getNormalFlightWithTwoHoursDuration(),
            getNormalMultySegmentFlight(),
            getFlightDepartingInThePast(),
            getFlightThatDepartsBeforeItArrives(),
            getFlightWithMoreThanTwoHoursGroundTime(),
            getAnotherFlightWithMoreThanTwoHoursGroundTime()
    );
    public static List<Flight> getAllFlights() {
        return new ArrayList<>(ALL_FLIGHTS);
    }

    public static Flight getNormalFlightWithTwoHoursDuration() {
        return NORMAL_FLIGHT_WITH_TWO_HOURS_DURATION;
    }

    public static Flight getNormalMultySegmentFlight() {
        return NORMAL_MULTY_SEGMENT_FLIGHT;
    }

    public static Flight getFlightDepartingInThePast() {
        return FLIGHT_DEPARTING_IN_THE_PAST;
    }

    public static Flight getFlightThatDepartsBeforeItArrives() {
        return FLIGHT_THAT_DEPARTS_BEFORE_IT_ARRIVES;
    }

    public static Flight getFlightWithMoreThanTwoHoursGroundTime() {
        return FLIGHT_WITH_MORE_THAN_TWO_HOURS_GROUND_TIME;
    }

    public static Flight getAnotherFlightWithMoreThanTwoHoursGroundTime() {
        return ANOTHER_FLIGHT_WITH_MORE_THAN_TWO_HOURS_GROUND_TIME;
    }
}