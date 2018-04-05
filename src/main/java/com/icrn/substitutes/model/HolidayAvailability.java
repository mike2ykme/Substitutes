package com.icrn.substitutes.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
@Data
public class HolidayAvailability {

    private Set<String> availability;

    public HolidayAvailability(Set<String> availability) {
        this.availability = availability;
    }

    public boolean isHolidayScheduled(LocalDateTime date) {
        return this.isHolidayScheduled(LocalDate.of(date.getYear(),date.getMonth(),date.getDayOfMonth()));
    }

    public HolidayAvailability() {
        this.availability = new HashSet<>();
    }

    public boolean isHolidayScheduled(LocalDate date) {
        return this.availability.contains(date.toString());
    }

    public boolean addHoliday(LocalDate day){
        return this.availability.add(day.toString());
    }

    public boolean removeHoliday(LocalDate day){
        return this.availability.remove(day.toString());
    }
}
