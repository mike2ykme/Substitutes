package com.icrn.substitutes.model;

import com.icrn.substitutes.Exceptions.SchedulingException;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Data
public class Substitute implements UserInterface{
    public long id;
    public String name;
    public String contactNumber;
    public String address;
    private AvailabilitySet holidayAvailability;
    private Availability regularAvailability;
    private AvailabilitySet scheduledTimes;
    private Set<Long> requestIdSet = new HashSet<>();
//    private Map<Long,Request> requestMap;

    public Substitute() {
    }

    public Substitute(long id, String name, String contactNumber, String address,
                      AvailabilitySet holidayAvailability, Availability regularAvailability,
                      AvailabilitySet scheduledTimes, Set<Long> requestIdSet ) {
        this.id = id;
        this.name = name;
        this.contactNumber = contactNumber;
        this.address = address;
        this.holidayAvailability = holidayAvailability;
        this.regularAvailability = regularAvailability;
        this.scheduledTimes = scheduledTimes;
        this.requestIdSet     = requestIdSet;
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
            this.requestIdSet.add(request.getRequestId());
            return request;
        }
        throw new SchedulingException("Unable to schedule substitute");
    }

    public Set<Long> getAllRequestIds() {
        return Collections.unmodifiableSet(this.requestIdSet);
//        return this.requestMap.entrySet().stream()
//                .map(Map.Entry::getValue)
//                .collect(Collectors.toList());
    }

    public Request cancelRequest(Request request) throws SchedulingException{
        if (request.getSubstituteId() != this.getId())
            throw new SchedulingException("Request does not match substitute being called upon");

        this.requestIdSet.remove(request.getRequestId());
        //TODO should this verify it existed in the first place?
        LocalDate day = LocalDate.of(
                request.getStartTime().getYear(),
                request.getStartTime().getMonth(),
                request.getStartTime().getDayOfMonth());
        this.scheduledTimes.removeDay(day);
        return request;
    }
}
