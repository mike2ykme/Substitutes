package com.icrn.substitutes;

import com.icrn.substitutes.dao.RequestRepository;
import com.icrn.substitutes.dao.SubstituteRepository;
import com.icrn.substitutes.model.Request;
import com.icrn.substitutes.model.Substitute;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class Controller {

    private RequestRepository requestRepo;
    private SubstituteRepository substituteRepo;

    public Controller(RequestRepository requestRepo,SubstituteRepository substituteRepo) {
        this.requestRepo = requestRepo;
        this.substituteRepo = substituteRepo;
    }

    public List<Request> getAllRequests(){
        return this.requestRepo.getAllRequests();
    }

    public Request addRequest(Request request) {
        return this.requestRepo.saveRequest(request);
    }

    public List<Request> getRequestByRequestorId(long userId) {
        return this.requestRepo.getAllRequestsByRequestorId(userId);
    }

    public Optional<Request> getRequestByRequestId(long requestId) {
        return this.requestRepo.getRequestById(requestId);
    }

    public List<Substitute> getSubstitutesAvailableOnDateTime(LocalDateTime start, LocalDateTime end) {
        return this.substituteRepo.getSubstitutesAvailableForTime(start,end);
    }

    public List<Substitute> getAllSubstitutes() {
        return this.substituteRepo.getAllSubstitutes();
    }
}
