package com.icrn.substitutes.dao;

import com.icrn.substitutes.model.Request;
import com.icrn.substitutes.model.Substitute;
import com.icrn.substitutes.Exceptions.SchedulingException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SubstituteRepository {
    List<Substitute> getSubstitutesAvailableForTime(LocalDateTime start, LocalDateTime end);
    List<Substitute> getAllSubstitutes();

    Substitute addSubstitute(Substitute sub);

    Optional<Substitute> getSubstituteById(long id);

    Request scheduleSubstitute(Request request) throws SchedulingException;
}
