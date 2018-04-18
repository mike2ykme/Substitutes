package com.icrn.substitutes;

import com.icrn.substitutes.Exceptions.SchedulingException;
import com.icrn.substitutes.dao.RequestRepository;
import com.icrn.substitutes.dao.SubstituteRepository;
import com.icrn.substitutes.dao.UserRepository;
import com.icrn.substitutes.model.Request;
import com.icrn.substitutes.model.Substitute;
import com.icrn.substitutes.model.UserInterface;
import com.icrn.substitutes.model.enumerations.Status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class Controller {

    private RequestRepository requestRepo;
    private SubstituteRepository substituteRepo;
    private UserRepository userRepository;

    public Controller(RequestRepository requestRepo,
                      SubstituteRepository substituteRepo,
                      UserRepository userRepository) {
        this.requestRepo = requestRepo;
        this.substituteRepo = substituteRepo;
        this.userRepository = userRepository;
    }


    public List<Request> getAllRequests(){
        return this.requestRepo.getAllRequests();
    }
    public Request saveRequest(Request request){
        return this.requestRepo.saveRequest(request);
    }
    public List<Request> getRequestByRequestorId(long userId) {
        return this.requestRepo.getAllRequestsByRequestorId(userId);
    }
    public Optional<Request> getRequestByRequestId(long requestId) {
        return this.requestRepo.getRequestById(requestId);
    }
    public List<Request> getRequestsByStatus(Status status) {
        return this.requestRepo.getAllRequestsByStatus(status);
    }

    public List<Substitute> getSubstitutesAvailableOnDateTime(LocalDateTime start,LocalDateTime end) {
        return this.substituteRepo.getSubstitutesAvailableForTime(start,end);
    }
    public List<Substitute> getAllSubstitutes() {
        return this.substituteRepo.getAllSubstitutes();
    }
    public Substitute saveSubstitute(Substitute sub) {
        return this.substituteRepo.addSubstitute(sub);
    }
    public Optional<Substitute> getSubstituteById(long id) {
        return this.substituteRepo.getSubstituteById(id);
    }

    public Optional<UserInterface> getUserById(long userId) {
        return this.userRepository.getUserById(userId);
    }
    public UserInterface saveUser(UserInterface user) {
        return this.userRepository.saveUser(user);
    }
    public List<UserInterface> getAllUsers(){
        return this.userRepository.getAllusers();
    }

    public Request scheduleSubstitute(Request request) throws SchedulingException{
        if (request.getSubstituteId() == 0 ||
                request.getRequestorId() == 0 ||
                request.getStartTime() == null ||
                request.getEndTime() == null)
            throw new IllegalArgumentException("Only RequestId and status allowed to be 0/null");

        if (request.getRequestId() == 0) {
                request = this.requestRepo.saveRequest(request);
        }

//        request = this.requestRepo.saveRequest(request);
        if(this.substituteRepo.getSubstituteById(request.getSubstituteId()).orElseThrow(() -> new SchedulingException("Substitute Not found by SubstituteId"))
                .isAvailable(request.getStartTime(),request.getEndTime())) {
            this.substituteRepo.scheduleSubstitute(request);
            request.setStatus(Status.SCHEDULED);
            return this.requestRepo.saveRequest(request);
        }
        return null;

    }
    public Request scheduleSubstitute(UserInterface user, Substitute substitute, LocalDateTime start,
                                      LocalDateTime end) throws SchedulingException {

        Request request = this.requestRepo.saveRequest(new Request());
        request.setStatus(Status.SCHEDULED);
        request.setRequestorId(user.getId());
        request.setSubstituteId(substitute.getId());
        request.setStartTime(start);
        request.setEndTime(end);
        this.substituteRepo.scheduleSubstitute(request);
        this.requestRepo.saveRequest(request);
        return request;
    }
    public Request cancelRequest(long requestId) throws  SchedulingException{
        Request request = this.getRequestByRequestId(requestId)
                            .orElseThrow(() -> new SchedulingException("Request does not exist"));
        request = this.substituteRepo.getSubstituteById(request.getSubstituteId())
                            .orElseThrow(() -> new SchedulingException("Substitute does not exist"))
                .cancelRequest(request);
        request.setStatus(Status.CANCELLED);
        return this.requestRepo.saveRequest(request);
    }
    public Request completeRequest(long requestId) throws  SchedulingException {
        Request request = this.getRequestByRequestId(requestId)
                .orElseThrow(() -> new SchedulingException("Request does not exist"));
        request.setStatus(Status.COMPLETED);
        return this.saveRequest(request);
    }
}
