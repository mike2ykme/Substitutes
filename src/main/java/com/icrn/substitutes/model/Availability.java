package com.icrn.substitutes.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class Availability {
    private Map<Integer,StartEnd> mapAvailability;

    public Availability(Map<Integer, StartEnd> mapAvailability) {
        this.mapAvailability = mapAvailability;
    }

    public Availability() {
        this.mapAvailability = new ConcurrentHashMap<>();
    }

    //http://www.java2s.com/Tutorials/Java/Data_Type_How_to/Date/Get_day_of_week_int_value_and_String_value.htm
    public boolean isAvailable(LocalDateTime start, LocalDateTime end) {
        if (start.getDayOfWeek() != end.getDayOfWeek())
            throw new RuntimeException("Days must be same date");

        StartEnd startEnd = this.mapAvailability.get(start.getDayOfWeek().getValue());
        if (startEnd != null){
                if ( (startEnd.getStart().isBefore(start.toLocalTime()) || startEnd.getStart().equals(start.toLocalTime()))
                        && (startEnd.getEnd().isAfter(end.toLocalTime()) ||startEnd.getEnd().equals(end.toLocalTime())))
                    return true;
        }
        return false;
    }
    public StartEnd addAvailabilityTime(int dayOfWeek, StartEnd startEnd){
        return this.mapAvailability.put(dayOfWeek,startEnd);
    }
}
