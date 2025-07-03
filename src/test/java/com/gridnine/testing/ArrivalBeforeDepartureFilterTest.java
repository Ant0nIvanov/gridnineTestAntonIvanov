package com.gridnine.testing;

import com.gridnine.testing.filter.ArrivalBeforeDepartureFilter;
import com.gridnine.testing.model.Flight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.gridnine.testing.util.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

public class ArrivalBeforeDepartureFilterTest {
    private ArrivalBeforeDepartureFilter filterUnderTest;

    @BeforeEach
    public void setUp() {
        filterUnderTest = new ArrivalBeforeDepartureFilter();
    }

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
    @DisplayName("Should return all flights when all flights with arrival after departure")
    public void givenFlightsWithArrivalAfterDeparture_whenFilter_shouldReturnAllFlight() {
        //given
        List<Flight> flights = new ArrayList<>();
        flights.add(getNormalFlightWithTwoHoursDuration());
        flights.add(getFlightDepartingInThePast());
        flights.add(getNormalMultySegmentFlight());
        flights.add(getFlightWithMoreThanTwoHoursGroundTime());
        flights.add(getAnotherFlightWithMoreThanTwoHoursGroundTime());

        //when
        List<Flight> result = filterUnderTest.filter(flights);

        //then
        assertEquals(flights.size(), result.size());
        assertEquals(flights, result);
    }

    @Test
    @DisplayName("Should return all flights with departure before arrival")
    public void givenFlights_whenFilter_shouldReturnAllFlightsWithArrivalBeforeDeparture() {
        //given
        List<Flight> flights = getAllFlights();

        //when
        List<Flight> result = filterUnderTest.filter(flights);

        //then
        assertEquals(5, result.size());
        assertTrue(result.contains(getNormalFlightWithTwoHoursDuration()));
        assertTrue(result.contains(getNormalMultySegmentFlight()));
        assertTrue(result.contains(getFlightDepartingInThePast()));
        assertTrue(result.contains(getFlightWithMoreThanTwoHoursGroundTime()));
        assertTrue(result.contains(getAnotherFlightWithMoreThanTwoHoursGroundTime()));
    }

    @Test
    @DisplayName("Should remove flight with arrival before departure")
    public void givenFlights_whenFilter_shouldRemoveFlightsWithArrivalBeforeDeparture() {
        //given
        List<Flight> flights = getAllFlights();
        Flight flight = getFlightThatDepartsBeforeItArrives();

        //when
        List<Flight> result = filterUnderTest.filter(flights);

        //then
        assertEquals(5, result.size());
        assertFalse(result.contains(flight));
    }

    @Test
    @DisplayName("Should return empty list when all flights arrives before departure")
    public void givenFlightsWithArrivesBeforeDeparture_whenFilter_shouldReturnEmptyList() {
        //given
        List<Flight> flights = new ArrayList<>();
        flights.add(getFlightThatDepartsBeforeItArrives());
        flights.add(getFlightThatDepartsBeforeItArrives());
        flights.add(getFlightThatDepartsBeforeItArrives());
        flights.add(getFlightThatDepartsBeforeItArrives());

        //when
        List<Flight> result = filterUnderTest.filter(flights);

        //then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
