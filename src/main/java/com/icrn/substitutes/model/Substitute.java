package com.icrn.substitutes.model;

import com.icrn.substitutes.Exceptions.SchedulingException;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Data
public class Substitute {
    private long id;
    private String name;
    private String contactNumber;
    private String address;
    private AvailabilitySet holidayAvailability;
    private Availability regularAvailability;
    private AvailabilitySet scheduledTimes;
    private Map<Long,Request> requestMap;

    public Substitute() {
    }

    public Substitute(long id, String name, String contactNumber, String address,
                      AvailabilitySet holidayAvailability, Availability regularAvailability,
                      AvailabilitySet scheduledTimes, Map<Long,Request> requestMap) {
        this.id = id;
        this.name = name;
        this.contactNumber = contactNumber;
        this.address = address;
        this.holidayAvailability = holidayAvailability;
        this.regularAvailability = regularAvailability;
        this.scheduledTimes = scheduledTimes;
        this.requestMap     = requestMap;
    }


    public boolean isAvailable(LocalDateTime start, LocalDateTime end) {
        return !this.holidayAvailability.isScheduled(start)
                && !this.scheduledTimes.isScheduled(start)
                && this.regularAvailability.isAvailable(start,end);
    }



    public Request schedule(Request request) throws SchedulingException {
        if (request.getSubstituteId() != this.getId())
                throw new SchedulingException("Request does not match substitute being called upon");
        if (this.isAvailable(request.getStartTime(),request.getEndTime())){
            LocalDate day = LocalDate.of(
                        request.getStartTime().getYear(),
                        request.getStartTime().getMonth(),
                        request.getStartTime().getDayOfMonth());
            request.setSubstituteId(this.getId());
            this.scheduledTimes.addDay(day);
            return request;
        }
        throw new SchedulingException("Unable to schedule substitute");
    }
}
