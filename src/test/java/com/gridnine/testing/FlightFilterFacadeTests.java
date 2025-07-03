package com.gridnine.testing;

import com.gridnine.testing.filter.*;
import com.gridnine.testing.model.Flight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static com.gridnine.testing.util.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

public class FlightFilterFacadeTests {
    private final List<Flight> flights = getAllFlights();
    private final FlightFilterFacade facadeUnderTest = new FlightFilterFacade();
    private final FlightFilter departureTimeBeforeNowFilter = new DepartureBeforeNowFilter();
    private final FlightFilter arrivalBeforeDepartureFilter = new ArrivalBeforeDepartureFilter();
    private final FlightFilter groundTimeExceedsTwoHoursFilter = new GroundTimeExceedsTwoHoursFilter();

    @BeforeEach
    public void setUp() {
        this.facadeUnderTest.cleanupFilters();
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException then list of flights is null")
    public void givenNullListOfFlights_whenApplyFilters_thenShouldThrowIllegalArgumentException() {
        //given, when and then
        assertThrows(IllegalArgumentException.class, () -> facadeUnderTest.applyFilters(null));
    }

    @Test
    @DisplayName("Should not modify input list")
    public void givenFlights_whenApplyFilters_shouldNotModifyInputList() {
        //given
        List<Flight> flights = getAllFlights();
        List<Flight> flightForTest = getAllFlights();
        facadeUnderTest.addAllFilters(departureTimeBeforeNowFilter,arrivalBeforeDepartureFilter, groundTimeExceedsTwoHoursFilter);

        //when
        facadeUnderTest.applyFilters(flights);

        //then
        assertEquals(flights, flightForTest);
    }

    @Test
    @DisplayName("Should return empty list when input is empty")
    public void givenEmptyList_whenFilter_shouldReturnEmptyList() {
        //given
        List<Flight> emptyList = Collections.emptyList();
        facadeUnderTest.addAllFilters(departureTimeBeforeNowFilter,arrivalBeforeDepartureFilter, groundTimeExceedsTwoHoursFilter);

        //when
        List<Flight> result = facadeUnderTest.applyFilters(emptyList);

        //then
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should return all flights when no filter is added to facade")
    public void givenFlightsAndNoFilters_whenApplyFilters_thenShouldReturnAllFlights() {
        //given

        //when
        List<Flight> result = facadeUnderTest.applyFilters(flights);

        //then
        assertNotNull(result);
        assertEquals(flights.size(), result.size());
        assertEquals(flights, result);
    }

    @Test
    @DisplayName("Should filter out flights departing in the past")
    public void givenFlightsAndDepartureBeforeNowFilter_whenApplyFilters_thenPastFlightsFiltered() {
        //given
        facadeUnderTest.addFilter(departureTimeBeforeNowFilter);

        //when
        List<Flight> result = facadeUnderTest.applyFilters(flights);

        //then
        assertNotNull(result);
        assertTrue(result.contains(getNormalFlightWithTwoHoursDuration()));
        assertTrue(result.contains(getNormalMultySegmentFlight()));
        assertTrue(result.contains(getFlightThatDepartsBeforeItArrives()));
        assertTrue(result.contains(getFlightWithMoreThanTwoHoursGroundTime()));
        assertTrue(result.contains(getAnotherFlightWithMoreThanTwoHoursGroundTime()));
        assertFalse(result.contains(getFlightDepartingInThePast()));
    }

    @Test
    @DisplayName("Should filter out flights where arrival is before departure")
    public void givenFlightsAndArrivalBeforeDepartureFilter_whenApplyFilters_thenInvalidFlightsFiltered() {
        //given
        facadeUnderTest.addFilter(arrivalBeforeDepartureFilter);

        //when
        List<Flight> result = facadeUnderTest.applyFilters(flights);

        //then
        assertNotNull(result);
        assertTrue(result.contains(getNormalFlightWithTwoHoursDuration()));
        assertTrue(result.contains(getNormalMultySegmentFlight()));
        assertTrue(result.contains(getFlightDepartingInThePast()));
        assertTrue(result.contains(getFlightWithMoreThanTwoHoursGroundTime()));
        assertTrue(result.contains(getAnotherFlightWithMoreThanTwoHoursGroundTime()));
        assertFalse(result.contains(getFlightThatDepartsBeforeItArrives()));
    }

    @Test
    @DisplayName("Should filter out flights with ground time exceeding two hours")
    public void givenFlightsAndGroundTimeExceedsFilter_whenApplyFilters_thenLongGroundTimeFlightsFiltered() {
        //given
        facadeUnderTest.addFilter(groundTimeExceedsTwoHoursFilter);

        //when
        List<Flight> result = facadeUnderTest.applyFilters(flights);

        //then
        System.out.println(result.size());
        assertNotNull(result);
        assertTrue(result.contains(getNormalFlightWithTwoHoursDuration()));
        assertTrue(result.contains(getNormalMultySegmentFlight()));
        assertTrue(result.contains(getFlightDepartingInThePast()));
        assertTrue(result.contains(getFlightThatDepartsBeforeItArrives()));
        assertFalse(result.contains(getFlightWithMoreThanTwoHoursGroundTime()));
        assertFalse(result.contains(getAnotherFlightWithMoreThanTwoHoursGroundTime()));
    }

    @Test
    @DisplayName("Should apply multiple filters sequentially")
    public void givenFlightsAndSeveralFilters_whenApplyFilters_thenAllConditionsApplied() {
        //given
        facadeUnderTest.addFilter(departureTimeBeforeNowFilter);
        facadeUnderTest.addFilter(arrivalBeforeDepartureFilter);
        facadeUnderTest.addFilter(groundTimeExceedsTwoHoursFilter);

        //when
        List<Flight> result = facadeUnderTest.applyFilters(flights);

        //then
        assertNotNull(result);
        assertTrue(result.contains(getNormalFlightWithTwoHoursDuration()));
        assertTrue(result.contains(getNormalMultySegmentFlight()));
        assertFalse(result.contains(getFlightDepartingInThePast()));
        assertFalse(result.contains(getFlightThatDepartsBeforeItArrives()));
        assertFalse(result.contains(getFlightWithMoreThanTwoHoursGroundTime()));
        assertFalse(result.contains(getAnotherFlightWithMoreThanTwoHoursGroundTime()));
    }
}