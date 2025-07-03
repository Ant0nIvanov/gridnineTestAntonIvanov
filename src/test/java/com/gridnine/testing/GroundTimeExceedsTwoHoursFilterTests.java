package com.gridnine.testing;

import com.gridnine.testing.filter.GroundTimeExceedsTwoHoursFilter;
import com.gridnine.testing.model.Flight;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.gridnine.testing.util.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

public class GroundTimeExceedsTwoHoursFilterTests {
    private final GroundTimeExceedsTwoHoursFilter filterUnderTest = new GroundTimeExceedsTwoHoursFilter();

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
    @DisplayName("Should return all flights when all flights with ground time less than two hours")
    public void givenAllFlightsWithGroundTimeLessThanTwoHours_whenFilter_shouldReturnAllFlight() {
        //given
        List<Flight> flights = new ArrayList<>();
        flights.add(getNormalFlightWithTwoHoursDuration());
        flights.add(getNormalMultySegmentFlight());
        flights.add(getFlightDepartingInThePast());
        flights.add(getFlightThatDepartsBeforeItArrives());

        //when
        List<Flight> result = filterUnderTest.filter(flights);

        //then
        assertEquals(flights.size(), result.size());
        assertEquals(flights, result);
    }

    @Test
    @DisplayName("Should return all flights with ground time less than two hours")
    public void givenFlights_whenFilter_shouldReturnAllFlightsWithGroundTimeLessThanTwoHors() {
        //given
        List<Flight> flights = getAllFlights();

        //when
        List<Flight> result = filterUnderTest.filter(flights);

        //then
        assertEquals(4, result.size());
        assertTrue(result.contains(getNormalFlightWithTwoHoursDuration()));
        assertTrue(result.contains(getNormalMultySegmentFlight()));
        assertTrue(result.contains(getFlightThatDepartsBeforeItArrives()));
        assertTrue(result.contains(getFlightDepartingInThePast()));
    }

    @Test
    @DisplayName("Should remove flight with ground time more than two hours")
    public void givenFlights_whenFilter_shouldRemoveFlightsWithGroundTimeMoreThanTwoHours() {
        //given
        List<Flight> flights = getAllFlights();
        Flight flightWithMoreThanTwoHoursGroundTime = getFlightWithMoreThanTwoHoursGroundTime();
        Flight anotherFlightWithMoreThanTwoHoursGroundTime = getAnotherFlightWithMoreThanTwoHoursGroundTime();

        //when
        List<Flight> result = filterUnderTest.filter(flights);

        //then
        assertEquals(4, result.size());
        assertFalse(result.contains(flightWithMoreThanTwoHoursGroundTime));
        assertFalse(result.contains(anotherFlightWithMoreThanTwoHoursGroundTime));
    }

    @Test
    @DisplayName("Should return empty list when all flights with ground time more than two hours")
    public void givenAllFlightsWithGroundTimeMoreTwoHours_whenFilter_shouldReturnEmptyList() {
        //given
        List<Flight> flights = new ArrayList<>();
        flights.add(getFlightWithMoreThanTwoHoursGroundTime());
        flights.add(getFlightWithMoreThanTwoHoursGroundTime());
        flights.add(getFlightWithMoreThanTwoHoursGroundTime());
        flights.add(getAnotherFlightWithMoreThanTwoHoursGroundTime());
        flights.add(getAnotherFlightWithMoreThanTwoHoursGroundTime());
        flights.add(getAnotherFlightWithMoreThanTwoHoursGroundTime());

        //when
        List<Flight> result = filterUnderTest.filter(flights);

        //then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}