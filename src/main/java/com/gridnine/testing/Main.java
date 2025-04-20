package com.gridnine.testing;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();
        FlightFiltersImpl flightFilter = new FlightFiltersImpl();

        System.out.println("Список полетов:");
        flights.forEach(System.out::println);

        System.out.println("\n Список полетов, исключая полеты до текущего момента времени: ");
        flightFilter.excludeFlightsDepsBeforeNow(flights).forEach(System.out::println);

        System.out.println("\n Список полетов, исключая полеты с датой прилёта раньше даты вылета: ");
        flightFilter.excludeFlightsArrBeforeDep(flights).forEach(System.out::println);

        System.out.println("\n Список полетов, где общее время проведённое на земле не превышает два часа");
        flightFilter.excludeFlightsTransferLongerTwoHrs(flights).forEach(System.out::println);
    }
}
