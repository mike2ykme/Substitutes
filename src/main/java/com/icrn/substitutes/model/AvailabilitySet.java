package com.icrn.substitutes.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
@Data
public class AvailabilitySet {

    private Set<String> availability;

    public AvailabilitySet(Set<String> availability) {
        this.availability = availability;
    }

    public boolean isScheduled(LocalDateTime start) {
        return this.isScheduled(
                LocalDate.of(start.getYear(),start.getMonth(),start.getDayOfMonth()));
//                LocalDate.of(end.getYear(),end.getMonth(),end.getDayOfMonth()));
    }

    public AvailabilitySet() {
        this.availability = new HashSet<>();
    }

    public boolean isScheduled(LocalDate start) {
//        if(start.getMonth() == end.getMonth() && start.getDayOfMonth() == end.getDayOfMonth())
            return this.availability.contains(start.toString());

//        return false;
    }

    synchronized public boolean addDay(LocalDate day){
        return this.availability.add(day.toString());
    }

    public boolean removeDay(LocalDate day){
        return this.availability.remove(day.toString());
    }
}
