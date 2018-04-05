package com.icrn.substitutes.dao;

import com.icrn.substitutes.model.Substitute;

import java.time.LocalDateTime;
import java.util.List;

public interface SubstituteRepository {
    List<Substitute> getSubstitutesAvailableForTime(LocalDateTime start, LocalDateTime end);
    List<Substitute> getAllSubstitutes();
}
