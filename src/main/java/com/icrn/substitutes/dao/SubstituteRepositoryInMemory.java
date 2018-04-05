package com.icrn.substitutes.dao;

import com.icrn.substitutes.model.Substitute;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class SubstituteRepositoryInMemory implements SubstituteRepository {

    private Map<Long,Substitute> substituteMap = new ConcurrentHashMap<>();

    @Override
    public List<Substitute> getSubstitutesAvailableForTime(LocalDateTime start, LocalDateTime end) {
        return substituteMap.entrySet()
                .stream()
//                .filter(entry -> entry.getValue().avaiableAtStart(start) && entry.getValue().availableUntilEnd(end))
                .filter(entry -> entry.getValue().isAvailableOn(start,end))
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
        return this.substituteMap.put(sub.getId(),sub);

    }
}
