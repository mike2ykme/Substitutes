package com.icrn.substitutes.dao;

import com.icrn.substitutes.model.Request;
import com.icrn.substitutes.model.Substitute;
import com.icrn.substitutes.Exceptions.SchedulingException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class SubstituteRepositoryInMemory implements SubstituteRepository {

    private Map<Long,Substitute> substituteMap;

    public SubstituteRepositoryInMemory(Map<Long, Substitute> substituteMap) {
        this.substituteMap = substituteMap;
    }

    public SubstituteRepositoryInMemory() {
        this.substituteMap  = new ConcurrentHashMap<>();
    }

    @Override
    public List<Substitute> getSubstitutesAvailableForTime(LocalDateTime start, LocalDateTime end) {
        return substituteMap.entrySet()
                .stream()
//                .filter(entry -> entry.getValue().avaiableAtStart(start) && entry.getValue().availableUntilEnd(end))
                .filter(entry -> entry.getValue().isAvailable(start,end))
                .map(entry -> entry.getValue())
                .collect(Collectors.toList());
    }

    @Override
    public List<Substitute> getAllSubstitutes() {
        return substituteMap.entrySet().stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    @Override
    public Substitute addSubstitute(Substitute sub) {
        if (sub.getId() == 0)
            sub.setId(Math.abs(new Random().nextLong()));
        this.substituteMap.put(sub.getId(),sub);
        return sub;

    }

    @Override
    public Optional<Substitute> getSubstituteById(long id) {
        return Optional.ofNullable(this.substituteMap.get(id));
    }

    @Override
    synchronized public Request scheduleSubstitute(Request request) throws SchedulingException {
        if (request.getSubstituteId() == 0 )
            throw new IllegalArgumentException("Substitute ID is empty in request");
        if (request.getRequestId() == 0)
            throw new IllegalArgumentException("Request Id is empty in request");

        Substitute sub = this.substituteMap.get(request.getSubstituteId());
        if(sub.isAvailable(request.getStartTime(),request.getEndTime())){
            sub.schedule(request);
            return request;
        }
        throw new SchedulingException("Unable to schedule Substitute for scheduled time");
    }
}
