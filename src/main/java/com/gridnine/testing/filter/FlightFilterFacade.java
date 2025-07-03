package com.gridnine.testing.filter;

import com.gridnine.testing.model.Flight;

import java.util.ArrayList;
import java.util.List;

public class FlightFilterFacade {
    private List<FlightFilter> filters = new ArrayList<>();

    public void addFilter(FlightFilter filter) {
        this.filters.add(filter);
    }

    public void addAllFilters(FlightFilter... filters) {
        this.filters.addAll(List.of(filters));
    }

    public void cleanupFilters() {
        this.filters.clear();;
    }

    public List<Flight> applyFilters(List<Flight> flights) {
        if (flights == null) {
            throw new IllegalArgumentException("List of flights can not be null");
        }
        List<Flight> result = flights;
        for (FlightFilter filter : filters) {
            result = filter.filter(result);
        }
        return result;
    }
}