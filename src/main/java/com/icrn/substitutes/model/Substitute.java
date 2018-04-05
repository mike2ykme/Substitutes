package com.icrn.substitutes.model;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class Substitute {
    private long id;
    private String name;
    private String contactNumber;
    private String address;
    private HolidayAvailability holidayAvailability;
    private Availability regularAvailability;

//    public boolean avaiableAtStart(LocalDateTime start) {
////        return ((this.holidayAvailability.inList(start)));
//        return !this.holidayAvailability.inList(start) && this.regularAvailability.available()
//    }
//
//    public boolean availableUntilEnd(LocalDateTime end) {
//        return false;
//    }

    public boolean avaiableFor(LocalDateTime start, LocalDateTime end) {
        return !this.holidayAvailability.inList(start) && this.regularAvailability.available(start,end);
    }
}
