package com.gridnine.testing;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FlightFiltersTest {
    FlightFiltersImpl flightFilters = new FlightFiltersImpl();

    Segment segmentOne = new Segment(LocalDateTime.of(2025, 6, 1, 12, 0, 0),
            LocalDateTime.of(2025, 6, 1, 14, 0, 0));

    Segment segmentTwo = new Segment(LocalDateTime.of(2025, 6, 1, 15, 0, 0),
            LocalDateTime.of(2025, 6, 1, 17, 0, 0));

    Segment segmentThree = new Segment(LocalDateTime.of(2025, 6, 1, 19, 0, 0),
            LocalDateTime.of(2025, 6, 1, 21, 0, 0));

    Segment segmentFour = new Segment(LocalDateTime.of(2025, 6, 1, 16, 0, 0),
            LocalDateTime.of(2025, 6, 1, 14, 0, 0));

    Segment segmentFive = new Segment(LocalDateTime.of(2025, 1, 1, 14, 0, 0),
            LocalDateTime.of(2025, 1, 1, 16, 0, 0));

    List<Segment> segmentListOne = List.of(segmentOne);
    List<Segment> segmentListTwo = List.of(segmentOne, segmentTwo);
    List<Segment> segmentListThree = List.of(segmentOne, segmentTwo, segmentThree);
    List<Segment> segmentListFour = List.of(segmentFour);
    List<Segment> segmentListFive = List.of(segmentFive);
    List<Segment> segmentListSix = List.of(segmentOne, segmentThree);

    Flight flightOne = new Flight(segmentListOne);
    Flight flightTwo = new Flight(segmentListTwo);
    Flight flightThree = new Flight(segmentListThree);
    Flight flightFour = new Flight(segmentListFour);
    Flight flightFive = new Flight(segmentListFive);
    Flight flightSix = new Flight(segmentListSix);

    List<Flight> expected = new ArrayList<>();
    List<Flight> actual = new ArrayList<>();

    @BeforeEach
    void fillSegments() {
        actual.add(flightOne);
        actual.add(flightTwo);
        actual.add(flightThree);
        actual.add(flightFour);
        actual.add(flightFive);
        actual.add(flightSix);
    }

    @AfterEach
    void clearLists() {
        expected.clear();
        actual.clear();
    }

    @Test
    void excludeFlightsDepsBeforeNow() {
        expected.add(flightOne);
        expected.add(flightTwo);
        expected.add(flightThree);
        expected.add(flightFour);
        expected.add(flightSix);

        List<Flight> result = flightFilters.excludeFlightsDepsBeforeNow(actual);

        assertNotNull(result, "Method \"excludeFlightsDepsBeforeNow\" return NULL result");
        assertEquals(expected, result, "");
    }

    @Test
    void excludeFlightsArrBeforeDep() {
        expected.add(flightOne);
        expected.add(flightTwo);
        expected.add(flightThree);
        expected.add(flightFive);
        expected.add(flightSix);

        List<Flight> result = flightFilters.excludeFlightsArrBeforeDep(actual);

        assertNotNull(result, "Method \"excludeFlightsDepsBeforeNow\" return NULL result");
        assertEquals(expected, result, "");
    }

    @Test
    void excludeFlightsTransferLongerTwoHrs() {
        expected.add(flightTwo);
        expected.add(flightOne);
        expected.add(flightFour);
        expected.add(flightFive);

        List<Flight> result = flightFilters.excludeFlightsTransferLongerTwoHrs(actual);

        assertNotNull(result, "Method \"excludeFlightsDepsBeforeNow\" return NULL result");
        assertEquals(expected, result, "");
    }
}
