package com.gridnine.testing.filter;

import com.gridnine.testing.model.Flight;

import java.util.List;
import java.util.function.Predicate;

public abstract class AbstractFlightFilter implements FlightFilter{
    protected Predicate<Flight> predicate;

    @Override
    public List<Flight> filter(List<Flight> flights) {
        if (flights == null) {
            throw new IllegalArgumentException("list of flights cannot be null");
        }
        return flights.stream()
                .filter(predicate)
                .toList();
    }
}