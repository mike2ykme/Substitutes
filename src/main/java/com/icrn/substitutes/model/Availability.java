package com.icrn.substitutes.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class Availability {
    private Map<Integer,StartEnd> mapAvailability = new ConcurrentHashMap<>();

    //http://www.java2s.com/Tutorials/Java/Data_Type_How_to/Date/Get_day_of_week_int_value_and_String_value.htm
    public boolean available(LocalDateTime start, LocalDateTime end) {
        StartEnd startEnd = this.mapAvailability.get(start.getDayOfWeek().getValue());
        if (startEnd != null){
                if ( (startEnd.getStart().isBefore(start.toLocalTime()) || startEnd.getStart().equals(start.toLocalTime()))
                        && (startEnd.getEnd().isAfter(end.toLocalTime()) ||startEnd.getEnd().equals(end.toLocalTime())))
                    return true;
        }
        return false;
    }
}
