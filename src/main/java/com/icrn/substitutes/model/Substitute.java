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

    public Substitute() {
    }

    public Substitute(long id, String name, String contactNumber, String address,
                      HolidayAvailability holidayAvailability, Availability regularAvailability) {
        this.id = id;
        this.name = name;
        this.contactNumber = contactNumber;
        this.address = address;
        this.holidayAvailability = holidayAvailability;
        this.regularAvailability = regularAvailability;
    }


    public boolean isAvailableOn(LocalDateTime start, LocalDateTime end) {
        return !this.holidayAvailability.isHolidayScheduled(start) && this.regularAvailability.isAvailable(start,end);
    }
}
