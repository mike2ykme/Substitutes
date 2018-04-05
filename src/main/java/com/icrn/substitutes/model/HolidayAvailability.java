package com.icrn.substitutes.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HolidayAvailability {
    private Map<String,Boolean> availabilityMap = new ConcurrentHashMap<>();

    public boolean inList(LocalDateTime date) {
        return this.inList(LocalDate.of(date.getYear(),date.getMonth(),date.getDayOfMonth()));
    }

    public boolean inList(LocalDate date) {
        return availabilityMap.containsKey(date.toString());
    }
}
