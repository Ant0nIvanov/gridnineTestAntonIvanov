package com.gridnine.testing.filter;

import com.gridnine.testing.model.Segment;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class GroundTimeExceedsTwoHoursFilter extends AbstractFlightFilter {
    public GroundTimeExceedsTwoHoursFilter() {
        this.predicate = flight -> {
            List<Segment> segments = flight.getSegments();
            if (segments.size() == 1) {
                return true;
            }
            long totalGroundTime = 0;
            for (int i = 0; i < segments.size() - 1; i++) {
                LocalDateTime segmentArrivalDate = segments.get(i).getArrivalDate();
                LocalDateTime nextSegmentDepartureDate = segments.get(i + 1).getDepartureDate();
                totalGroundTime += Duration.between(segmentArrivalDate, nextSegmentDepartureDate).toHours();
                if (totalGroundTime > 2) {
                    return false;
                }
            }
            return true;
        };
    }
}