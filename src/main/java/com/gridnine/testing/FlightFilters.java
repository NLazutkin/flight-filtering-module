package com.gridnine.testing;

import java.util.List;

public interface FlightFilters {
    List<Flight> excludeFlightsDepsBeforeNow(List<Flight> flights);

    List<Flight> excludeFlightsArrBeforeDep(List<Flight> flights);

    List<Flight> excludeFlightsTransferLongerTwoHrs(List<Flight> flights);
}
