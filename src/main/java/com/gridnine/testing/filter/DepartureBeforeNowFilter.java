package com.gridnine.testing.filter;

import java.time.LocalDateTime;

public class DepartureBeforeNowFilter extends AbstractFlightFilter{
    public DepartureBeforeNowFilter() {
        this.predicate = flight -> flight.getSegments().stream()
                .findFirst()
                .map(segment -> segment.getDepartureDate().isAfter(LocalDateTime.now()))
                .orElse(false);
    }
}