package pro.sky.com.gridnine.testing;


import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SorterOfSegments {
    /**
     * @param list список полетов
     * @return список полетов без дубликатов
     * удаляет дубликаты полетов
     */
    public  List<Flight> removeDuplicates(List<Flight> list) {
        List<Flight> newList = new ArrayList<Flight>();
        for (Flight element : list) {
            if (!newList.contains(element)) {
                newList.add(element);
            }
        }
        return newList;
    }

    /**
     * @param flights список полетов
     * @return полеты с вылетом до текущего момента времени
     */
    public  List<Flight> departureBeforeNow(List<Flight> flights) {
        List<Flight> flightsBeforeNow = new ArrayList<>();
        for (int i = 0; i < flights.size(); i++) {
            for (int j = 0; j < flights.get(i).getSegments().size(); j++) {
                if (flights.get(i).getSegments().get(j).getDepartureDate()
                        .isBefore(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES))) {
                    flightsBeforeNow.add(flights.get(i));
                }
            }
        }
        return removeDuplicates(flightsBeforeNow);
    }

    /**
     * @param flights список полетов
     * @return список сегментов с временем прилета раньше времени вылета
     */
    public  List<Segment> segmentsWithArrivalBeforeDeparture(List<Flight> flights) {
        List<Segment> segmentsArrivalBeforeDeparture = new ArrayList<>();
        for (int i = 0; i < flights.size(); i++) {
            for (int j = 0; j < flights.get(i).getSegments().size(); j++) {
                if (flights.get(i).getSegments().get(j).getArrivalDate()
                        .isBefore(flights.get(i).getSegments().get(j).getDepartureDate())) {
                    segmentsArrivalBeforeDeparture.add(flights.get(i).getSegments().get(j));
                }
            }
        }
        return segmentsArrivalBeforeDeparture;
    }

    /**
     * @param flights список полетов
     * @return Перелеты, где общее время, проведённое на земле, превышает два часа
     */
    public  List<Flight> timeOnEarthIsMoreThanTwoHours(List<Flight> flights) {
        List<Flight> flightsWithTimeOnEarthIsMoreThanTwoHours = new ArrayList<>();
        for (int i = 0; i < flights.size(); i++) {
            Duration timeOnEarth = null;
            if (flights.get(i).getSegments().size() > 1) {

                for (int j = 0; j < flights.get(i).getSegments().size() - 1; j++) {
                    if (timeOnEarth != null) {
                        timeOnEarth = timeOnEarth.plus(Duration.between(flights.get(i).getSegments().get(j + 1).getDepartureDate(),
                                (Temporal) flights.get(i).getSegments().get(j).getArrivalDate()));
                    } else timeOnEarth = Duration.between(flights.get(i).getSegments().get(j + 1).getDepartureDate(),
                            (Temporal) flights.get(i).getSegments().get(j).getArrivalDate());
                }
                if (timeOnEarth.abs() != null) {
                    if (timeOnEarth.abs().getSeconds() > 7200L) {
                        flightsWithTimeOnEarthIsMoreThanTwoHours.add(flights.get(i));
                    }
                }

            }
        }


        return flightsWithTimeOnEarthIsMoreThanTwoHours;
    }
}
