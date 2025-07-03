package com.gridnine.testing;

import com.gridnine.testing.filter.*;
import com.gridnine.testing.model.Flight;
import com.gridnine.testing.util.FlightBuilder;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();

        FlightFilterFacade filterFacade = new FlightFilterFacade();

        System.out.println("Изначальные данные");
        System.out.println(flights);
        System.out.println(System.lineSeparator());

        System.out.println("Исключаем все перелеты с вылетом до текущего момента времени");
        FlightFilter departureBeforeNowFilter = new DepartureBeforeNowFilter();
        filterFacade.addFilter(departureBeforeNowFilter);
        System.out.println(filterFacade.applyFilters(flights));
        System.out.println(System.lineSeparator());

        System.out.println("Исключаем все перелеты у которых имеются сегменты с датой прилёта раньше даты вылета");
        FlightFilter arrivalBeforeDepartureFilter = new ArrivalBeforeDepartureFilter();
        filterFacade.cleanupFilters();
        filterFacade.addFilter(arrivalBeforeDepartureFilter);
        System.out.println(filterFacade.applyFilters(flights));
        System.out.println(System.lineSeparator());

        System.out.println("Исключаем все перелеты с общим временем проведенным на земле более 2 часов");
        FlightFilter groundTimeExceedsTwoHoursFilter = new GroundTimeExceedsTwoHoursFilter();
        filterFacade.cleanupFilters();
        filterFacade.addFilter(groundTimeExceedsTwoHoursFilter);
        System.out.println(filterFacade.applyFilters(flights));
        System.out.println(System.lineSeparator());

        System.out.println("Исключаем все перелеты подходящие под все 3 фильтра");
        filterFacade.cleanupFilters();
        filterFacade.addAllFilters(departureBeforeNowFilter,
                arrivalBeforeDepartureFilter, groundTimeExceedsTwoHoursFilter);
        System.out.println(filterFacade.applyFilters(flights));
    }
}