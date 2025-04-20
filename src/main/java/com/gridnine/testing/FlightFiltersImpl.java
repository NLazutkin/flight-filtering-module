package com.gridnine.testing;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FlightFiltersImpl implements FlightFilters {
    private final LocalDateTime currentTime;

    public FlightFiltersImpl() {
        this.currentTime = LocalDateTime.now();
    }

    /**
     * Метод возвращает список полетов исключая из него те полеты у которых
     * хоть в одном сегменте время вылета раньше текущего момента времени.
     * Сравнивается время вылета и текущая дата/время сегмента полета
     *
     * @param flights список полетов
     * @return возвращает список {@link List<Flight>}, содержащий отфильтрованный список полетов
     */
    @Override
    public List<Flight> excludeFlightsDepsBeforeNow(List<Flight> flights) {
        if (Objects.nonNull(flights)) {
            return flights.stream()
                    .filter(flight -> flight.getSegments().stream()
                            .allMatch(segment -> segment.getDepartureDate().isAfter(currentTime)))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    /**
     * Метод возвращает список полетов исключая из него те полеты у которых
     * хоть в одном сегменте время прилета раньше времени вылета.
     * Сравнивается время вылета и время приземления сегмента полета
     *
     * @param flights список полетов
     * @return возвращает список {@link List<Flight>}, содержащий отфильтрованный список полетов
     */
    @Override
    public List<Flight> excludeFlightsArrBeforeDep(List<Flight> flights) {
        if (Objects.nonNull(flights)) {
            return flights.stream()
                    .filter(flight -> flight.getSegments().stream()
                            .allMatch(segment -> segment.getDepartureDate().isBefore(segment.getArrivalDate())))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    /**
     * Метод возвращает список полетов исключая из него те полеты у которых общее время,
     * проведённое на земле превышает два часа (время на земле — это интервал между прилётом
     * одного сегмента и вылетом)
     * Сравнивается время взлета из одного сегмента и время приземления из предыдущего сегмента
     *
     * @param flights список полетов
     * @return возвращает список {@link List<Flight>}, содержащий отфильтрованный список полетов
     */
    @Override
    public List<Flight> excludeFlightsTransferLongerTwoHrs(List<Flight> flights) {
        if (Objects.nonNull(flights)) {
            List<Flight> result = flights.stream()
                    .filter(flight -> flight.getSegments().size() > 1)
                    .filter(flight -> {
                        long sum = IntStream.range(1, flight.getSegments().size())
                                .mapToLong(i -> ChronoUnit.HOURS.between(
                                                flight.getSegments().get(i - 1).getArrivalDate(),
                                                flight.getSegments().get(i).getDepartureDate()
                                        )
                                )
                                .sum();
                        return sum <= 2;
                    })
                    .collect(Collectors.toList());

            flights.stream()
                    .filter(flight -> flight.getSegments().size() <= 1)
                    .forEach(result::add);

            return result;
        }

        return new ArrayList<>();
    }
}
