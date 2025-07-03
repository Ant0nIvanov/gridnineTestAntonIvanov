package com.gridnine.testing.filter;

public class ArrivalBeforeDepartureFilter extends AbstractFlightFilter {
    public ArrivalBeforeDepartureFilter() {
        this.predicate = flight -> flight.getSegments().stream()
                .noneMatch(segment -> segment.getArrivalDate().isBefore(segment.getDepartureDate()));
    }
}