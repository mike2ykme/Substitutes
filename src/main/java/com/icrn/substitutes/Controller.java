package com.icrn.substitutes;

import com.icrn.substitutes.dao.RequestRepository;
import com.icrn.substitutes.dao.SubstituteRepository;
import com.icrn.substitutes.dao.UserRepository;
import com.icrn.substitutes.model.Request;
import com.icrn.substitutes.Exceptions.SchedulingException;
import com.icrn.substitutes.model.Substitute;
import com.icrn.substitutes.model.User;
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
    public Request saveRequest(Request request) {
        return this.requestRepo.saveRequest(request);
    }
    public List<Request> getRequestByRequestorId(long userId) {
        return this.requestRepo.getAllRequestsByRequestorId(userId);
    }
    public Optional<Request> getRequestByRequestId(long requestId) {
        return this.requestRepo.getRequestById(requestId);
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
    public Request scheduleSubstitute(User user, Substitute substitute, LocalDateTime start,
                                      LocalDateTime end) throws SchedulingException {
            Request request = new Request();
            request.setStatus(Status.SCHEDULED);
            request.setRequestorId(user.getId());
            request.setSubstituteId(substitute.getId());
            request.setStartTime(start);
            request.setEndTime(end);
            this.substituteRepo.scheduleSubstitute(request);
            this.requestRepo.saveRequest(request);
            return request;
    }
    public List<Request> getRequestsByStatus(Status status) {
        return this.requestRepo.getAllRequestsByStatus(status);
    }
    public Request scheduleSubstitute(Request request, Substitute substitute) throws SchedulingException{
        return scheduleSubstitute(
                this.getUserById(request.getRequestorId())
                        .orElseThrow(() -> new SchedulingException("Unable to find user by User Id")),
                substitute,
                request.getStartTime(),
                request.getEndTime()
        );
    }
    private Optional<User> getUserById(long userId) {
        return this.userRepository.getUserById(userId);
    }
    public User saveUser(User user) {
        return this.userRepository.saveUser(user);
    }
}
