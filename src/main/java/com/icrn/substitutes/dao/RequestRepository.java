package com.icrn.substitutes.dao;

import com.icrn.substitutes.model.Request;
import com.icrn.substitutes.model.enumerations.Status;

import java.util.List;
import java.util.Optional;

public interface RequestRepository {

    Optional<Request> getRequestById(long id);
    List<Request> getAllRequests();
    List<Request> getAllRequestsByRequestorId(long id);
    Request saveRequest(Request request);

    List<Request> getRequestsBySubstituteId(long substituteId);

    List<Request> getAllRequestsByStatus(Status status);
}
