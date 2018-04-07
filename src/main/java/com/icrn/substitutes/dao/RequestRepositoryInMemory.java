package com.icrn.substitutes.dao;

import com.icrn.substitutes.model.Request;
import com.icrn.substitutes.model.enumerations.Status;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class RequestRepositoryInMemory implements RequestRepository {

    private Map<Long,Request> requestMap;

    public RequestRepositoryInMemory(Map<Long, Request> requestMap) {
        this.requestMap = requestMap;
    }

    public RequestRepositoryInMemory() {
        this.requestMap = new ConcurrentHashMap<>();
    }

    @Override
    public Optional<Request> getRequestById(long id) {
        return Optional.ofNullable(requestMap.get(id));
    }

    //https://www.mkyong.com/java8/java-8-filter-a-map-examples/
    @Override
    public List<Request> getAllRequests() {
        return requestMap.entrySet().stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    @Override
    public List<Request> getAllRequestsByRequestorId(long id) {
        return this.requestMap.entrySet().stream()
                .filter(entry -> entry.getValue().getRequestorId() == id)
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    @Override
    public Request saveRequest(Request request) {
        if (request.getRequestId() == 0)
            request.setRequestId(Math.abs(new Random().nextLong()));
        this.requestMap.put(request.getRequestId(),request);
        return request;
    }

    @Override
    public List<Request> getRequestsBySubstituteId(long substituteId) {
        return requestMap.entrySet().stream()
                .filter(entry -> entry.getValue().getSubstituteId() == substituteId)
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    @Override
    public List<Request> getAllRequestsByStatus(Status status) {
        return requestMap.entrySet().stream()
                .filter(entry -> entry.getValue().getStatus().equals(status))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }


}
