package com.gridnine.testing;

import com.gridnine.testing.filter.DepartureBeforeNowFilter;
import com.gridnine.testing.model.Flight;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.gridnine.testing.util.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

public class DepartureBeforeNowFilterTest {
    private final DepartureBeforeNowFilter filterUnderTest = new DepartureBeforeNowFilter();

    @Test
    @DisplayName("Should throw IllegalArgumentException when input is null")
    public void givenNull_whenFilter_shouldThrowIllegalArgumentException() {
        //given

        //when and then
        assertThrows(IllegalArgumentException.class, () -> filterUnderTest.filter(null));
    }

    @Test
    @DisplayName("Should not modify input list")
    public void givenFlights_whenFilter_shouldNotModifyInputData() {
        //given
        List<Flight> flights = getAllFlights();
        List<Flight> flightForTest = getAllFlights();

        //when
        filterUnderTest.filter(flightForTest);

        //then
        assertEquals(flights, flightForTest);
    }

    @Test
    @DisplayName("Should return empty list when input is empty")
    public void givenEmptyList_whenFilter_shouldReturnEmptyList() {
        //given
        List<Flight> emptyList = Collections.emptyList();

        //when
        List<Flight> result = filterUnderTest.filter(emptyList);

        //then
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("should return all flights when all departures are in the future")
    public void givenFlightsWithDepartureInTheFuture_whenFilter_shouldReturnAllFlight() {
        //given
        List<Flight> flights = new ArrayList<>();
        flights.add(getNormalFlightWithTwoHoursDuration());
        flights.add(getNormalMultySegmentFlight());
        flights.add(getFlightThatDepartsBeforeItArrives());
        flights.add(getFlightWithMoreThanTwoHoursGroundTime());
        flights.add(getAnotherFlightWithMoreThanTwoHoursGroundTime());

        //when
        List<Flight> result = filterUnderTest.filter(flights);

        //then
        assertEquals(flights.size(), result.size());
        assertEquals(flights, result);
    }

    @Test
    @DisplayName("Should return all flights with departure in future")
    public void givenFlights_whenFilter_shouldReturnAllFlightsWithDepartureInFuture() {
        //given
        List<Flight> flights = getAllFlights();

        //when
        List<Flight> result = filterUnderTest.filter(flights);

        //then
        assertEquals(5, result.size());
        assertTrue(result.contains(getNormalFlightWithTwoHoursDuration()));
        assertTrue(result.contains(getNormalMultySegmentFlight()));
        assertTrue(result.contains(getFlightThatDepartsBeforeItArrives()));
        assertTrue(result.contains(getFlightWithMoreThanTwoHoursGroundTime()));
        assertTrue(result.contains(getAnotherFlightWithMoreThanTwoHoursGroundTime()));
    }

    @Test
    @DisplayName("Should remove flight depart in the past")
    public void givenFlights_whenFilter_shouldRemoveFlightsDepartingInPast() {
        //given
        List<Flight> flights = getAllFlights();
        Flight flight = getFlightDepartingInThePast();

        //when
        List<Flight> result = filterUnderTest.filter(flights);

        //then
        assertEquals(5, result.size());
        assertFalse(result.contains(flight));
    }

    @Test
    @DisplayName("Should return empty list when all flights depart in the past")
    public void givenFlightsDepartingInThePast_whenFilter_shouldReturnEmptyList() {
        //given
        List<Flight> flights = new ArrayList<>();
        flights.add(getFlightDepartingInThePast());
        flights.add(getFlightDepartingInThePast());
        flights.add(getFlightDepartingInThePast());

        //when
        List<Flight> result = filterUnderTest.filter(flights);

        //then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}